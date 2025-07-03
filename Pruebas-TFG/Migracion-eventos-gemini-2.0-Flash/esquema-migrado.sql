-- SCRIPT DE MIGRACIÓN DE ESQUEMA DE EVENTOS (Explicación generada automáticamente por Gemini 2.0 Flash en la migración de MySQL a PostgreSQL)
--
-- Este script elimina el esquema 'eventos' si existe y lo vuelve a crear.
-- Se crean dos tablas principales: 'espacio_fisico' y 'evento'.
--
-- CARACTERÍSTICAS PRINCIPALES:
-- - Se utiliza 'UUID' como tipo para las columnas 'id' y 'espacio_fisico_id' 
--   en ambas tablas, asegurando una identificación única.
-- - El tipo 'bit(1)' se convierte a 'BOOLEAN' y 'longtext' a 'TEXT', 
--   cumpliendo con las especificaciones de PostgreSQL.
-- - Las columnas 'fecha_fin' y 'fecha_inicio' se definen como 'TIMESTAMP(6)' 
--   para mantener la precisión de los microsegundos.
--
-- OPTIMIZACIÓN:
-- - Se crean índices en las columnas más consultadas para optimizar 
--   el rendimiento de las búsquedas.
-- - La relación entre las tablas se mantiene a través de la clave foránea, 
--   asegurando la integridad referencial.
DROP SCHEMA IF EXISTS eventos CASCADE;

CREATE SCHEMA eventos;

CREATE TABLE eventos.espacio_fisico (
  id UUID PRIMARY KEY,
  capacidad INT,
  direccion VARCHAR(255),
  estado VARCHAR(255),
  nombre VARCHAR(255)
);

CREATE TABLE eventos.evento (
  id UUID PRIMARY KEY,
  cancelado BOOLEAN,
  categoria VARCHAR(255),
  descripcion TEXT,
  nombre VARCHAR(255),
  fecha_fin TIMESTAMP(6),
  fecha_inicio TIMESTAMP(6),
  organizador VARCHAR(255),
  plazas INT,
  espacio_fisico_id UUID,
  CONSTRAINT fk_espacio_fisico FOREIGN KEY (espacio_fisico_id) REFERENCES eventos.espacio_fisico(id)
);

CREATE INDEX idx_fecha_inicio ON eventos.evento(fecha_inicio);

CREATE INDEX idx_fecha_fin ON eventos.evento(fecha_fin);

CREATE INDEX idx_espacio_fisico_id ON eventos.evento(espacio_fisico_id);

CREATE INDEX idx_cancelado ON eventos.evento(cancelado);