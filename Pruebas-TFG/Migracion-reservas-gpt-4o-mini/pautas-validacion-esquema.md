# PAUTAS DE VALIDACIÓN DEL ESQUEMA MIGRADO

## Introducción

Para validar la migración del esquema de MongoDB a PostgreSQL y asegurar la correcta implementación y optimización del nuevo esquema, sigue estas pautas y recomendaciones detalladas:

## 1. Verificación de la Creación de Tablas

- Conéctate a la base de datos PostgreSQL y ejecuta la consulta `\dt` para listar todas las tablas y confirmar que `EVENTO` y `RESERVA` existen.
- Verifica la estructura de las tablas ejecutando `\d EVENTO` y `\d RESERVA` para asegurarte de que todas las columnas y tipos de datos se han creado correctamente.

## 2. Validación de Tipos de Datos y Restricciones

### Verificación de tipos de datos

Asegúrate de que los tipos de datos en las tablas son correctos:

- `id` en ambas tablas debe ser de tipo `UUID`.
- `plazas_disponibles` y `plazas_reservadas` deben ser de tipo `INTEGER`.
- `cancelado` debe ser de tipo `BOOLEAN`.
- `fecha_inicio` debe ser de tipo `TIMESTAMP`.
- `nombre_evento` debe ser de tipo `VARCHAR`.

### Verificación de restricciones

- Verifica las restricciones de NOT NULL y CHECK en `plazas_disponibles` y `plazas_reservadas`.

## 3. Revisión de Claves Foráneas

- Confirma que la clave foránea en la tabla `RESERVA` está correctamente referenciada a la columna `id` de la tabla `EVENTO` ejecutando la consulta:
  ```sql
  SELECT * FROM information_schema.table_constraints WHERE table_name = 'reserva';
  ```
- Asegúrate de que la opción `ON DELETE CASCADE` está implementada correctamente, lo que significa que al eliminar un evento, las reservas asociadas también deben ser eliminadas.

## 4. Pruebas de Integridad Referencial

- Inserta registros de prueba en la tabla `EVENTO` y utiliza esos IDs para crear registros en la tabla `RESERVA`. Después, verifica que las reservas se relacionen correctamente con los eventos.
- Intenta eliminar un registro de `EVENTO` y verifica que las reservas asociadas se eliminen automáticamente.

## 5. Validación de Índices

- Asegúrate de que los índices para `id_usuario`, `evento` y `fecha_inicio` se han creado correctamente. Ejecuta:
  ```sql
  SELECT * FROM pg_indexes WHERE tablename = 'reserva';
  SELECT * FROM pg_indexes WHERE tablename = 'evento';
  ```
- Realiza consultas que utilicen estos índices para comprobar que el rendimiento es óptimo.

## 6. Pruebas de Consultas Comunes

Ejecuta las siguientes consultas para verificar que el esquema soporta correctamente las operaciones requeridas:

### 6.1 Obtener todas las reservas asociadas a un evento concreto:

```sql
SELECT * FROM RESERVA WHERE evento = 'UUID_EVENTO';
```

### 6.2 Comprobar si existen reservas asociadas a un usuario:

```sql
SELECT 1 FROM RESERVA WHERE id_usuario = 'UUID_USUARIO' LIMIT 1;
```

### 6.3 Obtener todas las reservas de un usuario:

```sql
SELECT * FROM RESERVA WHERE id_usuario = 'UUID_USUARIO';
```

### 6.4 Consultar los datos de los eventos con información de disponibilidad:

```sql
SELECT * FROM EVENTO WHERE cancelado = FALSE AND plazas_disponibles > 0;
```

### 6.5 Verificar si un evento ha sido cancelado o si aún tiene plazas disponibles:

```sql
SELECT * FROM EVENTO WHERE id = 'UUID_EVENTO';
```

## 7. Revisión de Unicidad de Identificadores

Verifica que los valores de `id` en ambas tablas sean únicos y que no haya duplicados. Puedes hacerlo ejecutando:

```sql
SELECT id, COUNT(*) FROM RESERVA GROUP BY id HAVING COUNT(*) > 1;
SELECT id, COUNT(*) FROM EVENTO GROUP BY id HAVING COUNT(*) > 1;
```

## 8. Control de Consistencia de Estados

- Realiza consultas para verificar que los estados de cancelación están reflejados correctamente en ambas tablas.
- Asegúrate de que no hay inconsistencias en los registros relacionados.

## 9. Documentación y Registro de Resultados

- Documenta todos los resultados de las pruebas realizadas, cualquier inconsistencia encontrada y cómo se resolvió.
- Esto ayudará en futuras auditorías y mantenimientos.

## Conclusión

Siguiendo estos pasos de verificación y recomendaciones de pruebas, podrás asegurarte de que la migración del esquema se ha realizado correctamente y que el nuevo esquema en PostgreSQL está optimizado para el rendimiento y la integridad de los datos.
