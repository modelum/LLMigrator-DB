-- Explicación del script generada automáticamente por GPT-4o Mini:
-- El script crea dos tablas en PostgreSQL: EVENTO y RESERVA. La tabla EVENTO tiene un campo id de tipo UUID como clave primaria,
-- y contiene información sobre los eventos, incluyendo plazas disponibles, estado de cancelación, fecha de inicio y nombre del evento. 
--La tabla RESERVA también tiene un campo id de tipo UUID como clave primaria y contiene información sobre las reservas, 
-- incluyendo el id_usuario, estado de cancelación, plazas reservadas y una referencia al evento asociado. 
-- Esta referencia se establece como una clave foránea que asegura la integridad referencial entre las reservas y los eventos, 
-- con la opción de eliminar reservas si el evento es eliminado (ON DELETE CASCADE). Además, se crean índices sobre los campos
-- id_usuario, evento y fecha_inicio para optimizar las consultas frecuentes. 
-- Todos los tipos de datos se han ajustado según se especificó: BOOLEAN para valores booleanos, 
-- TIMESTAMP para fechas y INTEGER para números enteros. 
-- La unicidad del identificador _id está garantizada por la definición de clave primaria en ambas tablas.
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE EVENTO (
  id UUID PRIMARY KEY,
  plazas_disponibles INTEGER NOT NULL CHECK (plazas_disponibles >= 0),
  cancelado BOOLEAN NOT NULL,
  fecha_inicio TIMESTAMP,
  nombre_evento VARCHAR NOT NULL,
  _class VARCHAR NOT NULL DEFAULT 'reservas.dominio.Evento'
);

CREATE TABLE RESERVA (
  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  id_usuario UUID NOT NULL,
  cancelado BOOLEAN NOT NULL,
  plazas_reservadas INTEGER NOT NULL CHECK (plazas_reservadas >= 1),
  evento UUID NOT NULL,
  _class VARCHAR NOT NULL DEFAULT 'reservas.dominio.Reserva',
  FOREIGN KEY (evento) REFERENCES EVENTO(id) ON DELETE CASCADE
);

CREATE INDEX idx_usuario ON RESERVA(id_usuario);

CREATE INDEX idx_evento ON RESERVA(evento);

CREATE INDEX idx_fecha_inicio ON EVENTO(fecha_inicio);