(Generado automáticamente por Gemini 2.0 Flash durante la migración del esquema de MySQL a PostgreSQL)

# PAUTAS DE VALIDACIÓN DEL ESQUEMA MIGRADO

## Introducción

Para validar manualmente la migración del esquema de la base de datos desde MySQL a PostgreSQL y asegurar que la implementación y optimización del nuevo esquema se han realizado correctamente, sigue estas pautas y recomendaciones detalladas:

## 1. Verificación de la Creación del Esquema

- Conéctate a la base de datos PostgreSQL y ejecuta el siguiente comando para listar los esquemas disponibles:
  ```sql
  SELECT schema_name FROM information_schema.schemata;
  ```
- Asegúrate de que el esquema `eventos` se encuentre en la lista.

## 2. Verificación de las Tablas

- Ejecuta el siguiente comando para verificar que las tablas `espacio_fisico` y `evento` han sido creadas dentro del esquema `eventos`:
  ```sql
  SELECT table_name FROM information_schema.tables WHERE table_schema = 'eventos';
  ```
- Confirma que ambas tablas están presentes en el resultado.

## 3. Verificación de la Estructura de las Tablas

### Tabla espacio_fisico

- Para la tabla `espacio_fisico`, ejecuta:
  ```sql
  \d eventos.espacio_fisico;
  ```
- Verifica que las columnas sean:
  - `id` de tipo `UUID` y clave primaria.
  - `capacidad` de tipo `INT`.
  - `direccion`, `estado`, y `nombre` de tipo `VARCHAR(255)`.

### Tabla evento

- Para la tabla `evento`, ejecuta:
  ```sql
  \d eventos.evento;
  ```
- Verifica que las columnas sean:
  - `id` de tipo `UUID` y clave primaria.
  - `cancelado` de tipo `BOOLEAN`.
  - `categoria`, `nombre`, y `organizador` de tipo `VARCHAR(255)`.
  - `descripcion` de tipo `TEXT`.
  - `fecha_fin` y `fecha_inicio` de tipo `TIMESTAMP(6)`.
  - `plazas` de tipo `INT`.
  - `espacio_fisico_id` de tipo `UUID`.
  - La restricción `fk_espacio_fisico` debe existir como clave foránea.

## 4. Verificación de Índices

- Ejecuta el siguiente comando para asegurarte de que los índices se han creado correctamente:
  ```sql
  SELECT indexname FROM pg_indexes WHERE schemaname = 'eventos';
  ```
- Comprueba que existen índices para `fecha_inicio`, `fecha_fin`, `espacio_fisico_id` y `cancelado`.

## 5. Pruebas de Integridad Referencial

- Inserta datos de prueba en `espacio_fisico` y `evento` para verificar que la relación de clave foránea funciona correctamente.
- Intenta insertar un registro en `evento` con un `espacio_fisico_id` que no exista y asegúrate de que se genere un error de violación de clave foránea.

## 6. Pruebas de Consultas Comunes

Realiza las siguientes consultas según las operaciones más frecuentes y verifica que devuelvan resultados esperados:

### 6.1 Obtener eventos activos para un mes y año dados:

```sql
SELECT * FROM eventos.evento
WHERE cancelado = FALSE
AND EXTRACT(YEAR FROM fecha_inicio) = ?
AND EXTRACT(MONTH FROM fecha_inicio) = ?;
```

### 6.2 Comprobar si un espacio tiene eventos activos:

```sql
SELECT COUNT(*) > 0 FROM eventos.evento
WHERE espacio_fisico_id = ?
AND fecha_fin > CURRENT_TIMESTAMP;
```

### 6.3 Buscar espacios disponibles con capacidad mínima y sin solapamiento de eventos:

```sql
SELECT id FROM eventos.espacio_fisico
WHERE capacidad >= ? AND estado = 'ACTIVO'
AND NOT EXISTS (
    SELECT 1 FROM eventos.evento
    WHERE espacio_fisico_id = eventos.espacio_fisico.id
    AND fecha_inicio <= ? AND fecha_fin >= ?
);
```

### 6.4 Contar eventos futuros con plazas mayores a una nueva capacidad:

```sql
SELECT COUNT(*) FROM eventos.evento
WHERE espacio_fisico_id = ?
AND fecha_fin > CURRENT_TIMESTAMP
AND plazas > ?;
```

### 6.5 Obtener eventos no cancelados de un espacio:

```sql
SELECT * FROM eventos.evento
WHERE espacio_fisico_id = ?
AND cancelado = FALSE;
```

### 6.6 Comprobar si existe solapamiento con otro evento en un espacio:

```sql
SELECT COUNT(*) > 0 FROM eventos.evento
WHERE espacio_fisico_id = ?
AND cancelado = FALSE
AND fecha_inicio < ?
AND fecha_fin > ?;
```

## 7. Pruebas de Consistencia de Datos

- Verifica que los tipos de datos se han mapeado correctamente y que la data existente se puede consultar sin pérdidas.
- Asegúrate de que las funciones de fecha como `YEAR()` y `MONTH()` han sido reemplazadas correctamente por `EXTRACT(YEAR FROM ...)` y `EXTRACT(MONTH FROM ...)` en las consultas.

## 8. Revisar el Rendimiento

- Realiza pruebas de rendimiento sobre las consultas más comunes para asegurar que se ejecutan de manera eficiente, especialmente aquellas que implican la búsqueda por índices.

## 9. Documentación de Resultados

- Documenta los resultados de cada prueba, incluyendo cualquier error encontrado o comportamiento inesperado, y toma notas sobre las optimizaciones necesarias.

## Conclusión

Siguiendo estos pasos de verificación y pruebas, podrás asegurarte de que la migración del esquema de MySQL a PostgreSQL se ha realizado correctamente y que el nuevo esquema está optimizado para su uso.
