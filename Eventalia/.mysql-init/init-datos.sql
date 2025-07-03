DROP DATABASE IF EXISTS eventos;

CREATE DATABASE IF NOT EXISTS eventos;

DROP DATABASE IF EXISTS espacios;

CREATE DATABASE IF NOT EXISTS espacios;

USE espacios;

CREATE TABLE IF NOT EXISTS ESPACIOFISICO (
    ID varchar(36) PRIMARY KEY,
    CAPACIDAD int,
    DESCRIPCION longtext,
    DIRECCION varchar(255),
    ESTADO varchar(255),
    LATITUD double,
    LONGITUD double,
    NOMBRE varchar(255),
    PROPIETARIO varchar(255)
);

CREATE TABLE IF NOT EXISTS puntos_interes (
    espacio_fisico_id varchar(36),
    descripcion longtext,
    distancia double,
    nombre varchar(255),
    url varchar(255),
    CONSTRAINT fk_espacio_fisico
      FOREIGN KEY (espacio_fisico_id) REFERENCES ESPACIOFISICO(ID)
);

INSERT INTO ESPACIOFISICO 
(ID, CAPACIDAD, DESCRIPCION, DIRECCION, ESTADO, LATITUD, LONGITUD, NOMBRE, PROPIETARIO) 
VALUES ('74800b33-c205-4c4b-b67c-4ad02febdfa7', 102, 'Espacio flexible para múltiples tipos de eventos.', 'Calle del Bosque 441, Campo Norte', 'ACTIVO', -34.571822, -58.377856, 'Auditorio San Martín', 'gestor');

INSERT INTO ESPACIOFISICO 
(ID, CAPACIDAD, DESCRIPCION, DIRECCION, ESTADO, LATITUD, LONGITUD, NOMBRE, PROPIETARIO) 
VALUES ('c3127765-0306-40a6-888a-3ffb8126be0c', 104, 'Con mobiliario adaptable a diferentes configuraciones.', 'Avenida de la Paz 574, Bahía Azul', 'ACTIVO', -34.53159, -58.308987, 'Sala de Innovación', 'gestor');

INSERT INTO ESPACIOFISICO 
(ID, CAPACIDAD, DESCRIPCION, DIRECCION, ESTADO, LATITUD, LONGITUD, NOMBRE, PROPIETARIO) 
VALUES ('890679de-6040-4254-a3d0-552066183d0d', 109, 'Ambiente moderno y dinámico para eventos creativos.', 'Avenida Siempre Viva 131, Ciudad Central', 'CERRADO_TEMPORALMENTE', -34.615503, -58.35689, 'Centro de Convenciones Norte', 'gestor');

INSERT INTO ESPACIOFISICO 
(ID, CAPACIDAD, DESCRIPCION, DIRECCION, ESTADO, LATITUD, LONGITUD, NOMBRE, PROPIETARIO) 
VALUES ('45487047-f7dc-455a-8402-82142c7229a9', 142, 'Ambiente moderno y dinámico para eventos creativos.', 'Ruta 66 589, Ciudad Jardín', 'CERRADO_TEMPORALMENTE', -34.605485, -58.33084, 'Sala Creativa Alfa', 'gestor');

INSERT INTO ESPACIOFISICO 
(ID, CAPACIDAD, DESCRIPCION, DIRECCION, ESTADO, LATITUD, LONGITUD, NOMBRE, PROPIETARIO) 
VALUES ('0f4d73d4-008f-4818-afe9-2861f39559f5', 26, 'Ubicado en el corazón de la ciudad, con fácil acceso.', 'Calle del Bosque 831, Puerto Claro', 'CERRADO_TEMPORALMENTE', -34.688632, -58.322191, 'Aula Magna Sur', 'gestor');

INSERT INTO ESPACIOFISICO 
(ID, CAPACIDAD, DESCRIPCION, DIRECCION, ESTADO, LATITUD, LONGITUD, NOMBRE, PROPIETARIO) 
VALUES ('8cc13d7b-947e-4123-9d0d-e7b83cf73cd6', 33, 'Sala equipada para videoconferencias internacionales.', 'Calle Aurora 934, Villa Ilustrada', 'ACTIVO', -34.5417, -58.307662, 'Espacio Colaborativo Delta', 'gestor');

INSERT INTO ESPACIOFISICO 
(ID, CAPACIDAD, DESCRIPCION, DIRECCION, ESTADO, LATITUD, LONGITUD, NOMBRE, PROPIETARIO) 
VALUES ('047eadbf-2952-4959-bec0-0ccbd021e03b', 86, 'Ambiente moderno y dinámico para eventos creativos.', 'Vía Láctea 385, Ciudad Jardín', 'ACTIVO', -34.638012, -58.391204, 'Sala Ejecutiva 101', 'gestor');

INSERT INTO ESPACIOFISICO 
(ID, CAPACIDAD, DESCRIPCION, DIRECCION, ESTADO, LATITUD, LONGITUD, NOMBRE, PROPIETARIO) 
VALUES ('ce58498f-3382-423f-96be-442cf6fa6960', 97, 'Diseñado para seminarios y clases magistrales.', 'Paseo Verde 800, Campo Norte', 'ACTIVO', -34.601804, -58.311454, 'Auditorio Tecnológico', 'gestor');

INSERT INTO ESPACIOFISICO 
(ID, CAPACIDAD, DESCRIPCION, DIRECCION, ESTADO, LATITUD, LONGITUD, NOMBRE, PROPIETARIO) 
VALUES ('7a28f17b-a89a-4e03-b93f-5d77a55db8b4', 69, 'Ubicado en el corazón de la ciudad, con fácil acceso.', 'Calle Luna 338, Vista Alegre', 'CERRADO_TEMPORALMENTE', -34.513893, -58.44697, 'Centro Cultural Este', 'gestor');

INSERT INTO ESPACIOFISICO 
(ID, CAPACIDAD, DESCRIPCION, DIRECCION, ESTADO, LATITUD, LONGITUD, NOMBRE, PROPIETARIO) 
VALUES ('89bfd733-7634-4a75-8cfb-2179037d2fb1', 56, 'Con capacidad amplia para presentaciones formales.', 'Calle del Río 947, Campo Norte', 'CERRADO_TEMPORALMENTE', -34.678208, -58.476612, 'Foro Empresarial', 'gestor');

INSERT INTO ESPACIOFISICO 
(ID, CAPACIDAD, DESCRIPCION, DIRECCION, ESTADO, LATITUD, LONGITUD, NOMBRE, PROPIETARIO) 
VALUES ('d2bc1142-a2d9-409a-b52f-2418bd9ef517', 30, 'Ambiente profesional para encuentros de negocios.', 'Calle Aurora 246, Monte Claro', 'CERRADO_TEMPORALMENTE', -34.667789, -58.377418, 'Sala de Proyectos', 'gestor');

INSERT INTO ESPACIOFISICO 
(ID, CAPACIDAD, DESCRIPCION, DIRECCION, ESTADO, LATITUD, LONGITUD, NOMBRE, PROPIETARIO) 
VALUES ('99a08973-66a2-412d-beb9-37b588814bb4', 24, 'Con acceso a internet de alta velocidad y proyectores.', 'Calle Falsa 675, Río Grande', 'ACTIVO', -34.618742, -58.380143, 'Terraza Corporativa', 'gestor');

INSERT INTO ESPACIOFISICO 
(ID, CAPACIDAD, DESCRIPCION, DIRECCION, ESTADO, LATITUD, LONGITUD, NOMBRE, PROPIETARIO) 
VALUES ('cb84da07-2c36-431d-b514-b4d08440906e', 45, 'Un espacio ideal para conferencias y reuniones.', 'Calle del Río 702, Ciudad Central', 'CERRADO_TEMPORALMENTE', -34.515408, -58.440727, 'Pabellón Universitario', 'gestor');

INSERT INTO ESPACIOFISICO 
(ID, CAPACIDAD, DESCRIPCION, DIRECCION, ESTADO, LATITUD, LONGITUD, NOMBRE, PROPIETARIO) 
VALUES ('71a272aa-f535-42af-8ee4-636b89571c76', 90, 'Ubicado en el corazón de la ciudad, con fácil acceso.', 'Camino del Norte 613, Campo Norte', 'CERRADO_TEMPORALMENTE', -34.575213, -58.383742, 'Espacio Natural', 'gestor');

INSERT INTO ESPACIOFISICO 
(ID, CAPACIDAD, DESCRIPCION, DIRECCION, ESTADO, LATITUD, LONGITUD, NOMBRE, PROPIETARIO) 
VALUES ('aed13836-32ed-4624-b148-fe03cfb6a4d1', 52, 'Con capacidad amplia para presentaciones formales.', 'Calle del Río 345, Villa Ilustrada', 'ACTIVO', -34.623898, -58.381997, 'Centro de Formación Continua', 'gestor');

INSERT INTO ESPACIOFISICO 
(ID, CAPACIDAD, DESCRIPCION, DIRECCION, ESTADO, LATITUD, LONGITUD, NOMBRE, PROPIETARIO) 
VALUES ('cb56596a-32bc-4498-b760-a09479c831c2', 21, 'Con mobiliario adaptable a diferentes configuraciones.', 'Boulevard Central 594, Pueblo Nuevo', 'CERRADO_TEMPORALMENTE', -34.654817, -58.537444, 'Auditorio Central', 'gestor');

INSERT INTO ESPACIOFISICO 
(ID, CAPACIDAD, DESCRIPCION, DIRECCION, ESTADO, LATITUD, LONGITUD, NOMBRE, PROPIETARIO) 
VALUES ('afa4d1cb-c934-42ba-a974-e608ebd1d4d8', 44, 'Con acceso a internet de alta velocidad y proyectores.', 'Calle Aurora 709, Pueblo Nuevo', 'CERRADO_TEMPORALMENTE', -34.626611, -58.551869, 'Sala de Conferencias Global', 'gestor');

INSERT INTO ESPACIOFISICO 
(ID, CAPACIDAD, DESCRIPCION, DIRECCION, ESTADO, LATITUD, LONGITUD, NOMBRE, PROPIETARIO) 
VALUES ('dd854268-a0b2-48dd-bcc0-797eb5892f6c', 51, 'Ambiente profesional para encuentros de negocios.', 'Calle Aurora 972, Pueblo Nuevo', 'ACTIVO', -34.544941, -58.449319, 'Aula Taller Norte', 'gestor');

INSERT INTO ESPACIOFISICO 
(ID, CAPACIDAD, DESCRIPCION, DIRECCION, ESTADO, LATITUD, LONGITUD, NOMBRE, PROPIETARIO) 
VALUES ('6fe1a042-37f6-4eee-a6af-476ce285e4a5', 72, 'Un espacio ideal para conferencias y reuniones.', 'Calle del Río 678, Villa Ilustrada', 'ACTIVO', -34.650618, -58.426107, 'Foro Abierto Digital', 'gestor');

INSERT INTO ESPACIOFISICO 
(ID, CAPACIDAD, DESCRIPCION, DIRECCION, ESTADO, LATITUD, LONGITUD, NOMBRE, PROPIETARIO) 
VALUES ('bc2c5644-1dc8-412a-97bc-dd359106ed02', 72, 'Equipado con tecnología de punta para eventos masivos.', 'Camino Real 859, Ciudad Sol', 'CERRADO_TEMPORALMENTE', -34.69811, -58.56822, 'Espacio Startup', 'gestor');

INSERT INTO ESPACIOFISICO 
(ID, CAPACIDAD, DESCRIPCION, DIRECCION, ESTADO, LATITUD, LONGITUD, NOMBRE, PROPIETARIO) 
VALUES ('521444f1-c1fa-4a24-8735-34694bdf1c23', 77, 'Con mobiliario adaptable a diferentes configuraciones.', 'Paseo Verde 697, Ciudad Luz', 'CERRADO_TEMPORALMENTE', -34.505699, -58.43951, 'Sala de Networking', 'gestor');

INSERT INTO ESPACIOFISICO 
(ID, CAPACIDAD, DESCRIPCION, DIRECCION, ESTADO, LATITUD, LONGITUD, NOMBRE, PROPIETARIO) 
VALUES ('10f3eb1b-cdd7-4c38-8d76-91ed6934acde', 56, 'Ambiente profesional para encuentros de negocios.', 'Calle Aurora 638, Bahía Azul', 'CERRADO_TEMPORALMENTE', -34.620729, -58.5695, 'Centro de Ideas', 'gestor');

INSERT INTO ESPACIOFISICO 
(ID, CAPACIDAD, DESCRIPCION, DIRECCION, ESTADO, LATITUD, LONGITUD, NOMBRE, PROPIETARIO) 
VALUES ('0aebb32e-9804-4374-b8e0-c32b216c1a28', 107, 'Equipado con tecnología de punta para eventos masivos.', 'Camino del Norte 589, Monte Claro', 'ACTIVO', -34.602925, -58.422145, 'Punto de Encuentro', 'gestor');

INSERT INTO ESPACIOFISICO 
(ID, CAPACIDAD, DESCRIPCION, DIRECCION, ESTADO, LATITUD, LONGITUD, NOMBRE, PROPIETARIO) 
VALUES ('cd128ed8-9bb1-453a-845b-ed8f06b9308d', 93, 'Diseño elegante para eventos corporativos de alto nivel.', 'Boulevard Central 344, Villa del Mar', 'CERRADO_TEMPORALMENTE', -34.64078, -58.597651, 'Espacio Maker', 'gestor');

INSERT INTO ESPACIOFISICO 
(ID, CAPACIDAD, DESCRIPCION, DIRECCION, ESTADO, LATITUD, LONGITUD, NOMBRE, PROPIETARIO) 
VALUES ('b1cbf50c-aedf-4d15-9615-969ec43b9e59', 131, 'Ubicado en el corazón de la ciudad, con fácil acceso.', 'Avenida Siempre Viva 833, Villa del Mar', 'ACTIVO', -34.528107, -58.326355, 'Laboratorio de Creatividad', 'gestor');

INSERT INTO ESPACIOFISICO 
(ID, CAPACIDAD, DESCRIPCION, DIRECCION, ESTADO, LATITUD, LONGITUD, NOMBRE, PROPIETARIO) 
VALUES ('b0809326-f93a-4b23-a17e-10cd92581e47', 86, 'Sala equipada para videoconferencias internacionales.', 'Avenida Siempre Viva 547, Campo Norte', 'ACTIVO', -34.693702, -58.558758, 'Zona Coworking', 'gestor');

INSERT INTO ESPACIOFISICO 
(ID, CAPACIDAD, DESCRIPCION, DIRECCION, ESTADO, LATITUD, LONGITUD, NOMBRE, PROPIETARIO) 
VALUES ('911c5bcf-ddb4-43d8-b387-eab181e91821', 118, 'Rodeado de naturaleza, ideal para sesiones de brainstorming.', 'Vía Láctea 329, Nueva Esperanza', 'ACTIVO', -34.580668, -58.58876, 'Estudio Multimedia', 'gestor');

INSERT INTO ESPACIOFISICO 
(ID, CAPACIDAD, DESCRIPCION, DIRECCION, ESTADO, LATITUD, LONGITUD, NOMBRE, PROPIETARIO) 
VALUES ('b35afac6-b0f4-4b4d-8345-e53b3834ffd3', 93, 'Sala equipada para videoconferencias internacionales.', 'Ruta 66 641, Villa Ilustrada', 'CERRADO_TEMPORALMENTE', -34.643818, -58.381243, 'Sala de Estrategia', 'gestor');

INSERT INTO ESPACIOFISICO 
(ID, CAPACIDAD, DESCRIPCION, DIRECCION, ESTADO, LATITUD, LONGITUD, NOMBRE, PROPIETARIO) 
VALUES ('dafddbf2-1d4e-4bdc-9432-cd5b220314c3', 55, 'Sala equipada para videoconferencias internacionales.', 'Calle del Río 121, Monte Claro', 'ACTIVO', -34.666851, -58.547243, 'Espacio Emprendedor', 'gestor');

INSERT INTO ESPACIOFISICO 
(ID, CAPACIDAD, DESCRIPCION, DIRECCION, ESTADO, LATITUD, LONGITUD, NOMBRE, PROPIETARIO) 
VALUES ('ffc665a3-02ff-48a2-a459-bcf2f125a2ab', 128, 'Sala equipada para videoconferencias internacionales.', 'Calle del Arte 645, Campo Norte', 'ACTIVO', -34.570871, -58.430144, 'Campus Virtual', 'gestor');



USE eventos;

CREATE TABLE espacio_fisico (
    id varchar(36) PRIMARY KEY,
    capacidad int,
    direccion varchar(255),
    estado varchar(255),
    nombre varchar(255)
);

CREATE TABLE evento (
    id varchar(36) PRIMARY KEY,
    cancelado bit(1),
    categoria varchar(255),
    descripcion longtext,
    nombre varchar(255),
    fecha_fin datetime(6),
    fecha_inicio datetime(6),
    organizador varchar(255),
    plazas int,
    espacio_fisico_id varchar(36),
    CONSTRAINT fk_espacio_fisico
      FOREIGN KEY (espacio_fisico_id) REFERENCES espacio_fisico(id)
);

INSERT INTO espacio_fisico (id, capacidad, direccion, estado, nombre)
VALUES ('74800b33-c205-4c4b-b67c-4ad02febdfa7', 102, 'Calle del Bosque 441, Campo Norte', 'ACTIVO', 'Auditorio San Martín');

INSERT INTO espacio_fisico (id, capacidad, direccion, estado, nombre)
VALUES ('c3127765-0306-40a6-888a-3ffb8126be0c', 104, 'Avenida de la Paz 574, Bahía Azul', 'ACTIVO', 'Sala de Innovación');

INSERT INTO espacio_fisico (id, capacidad, direccion, estado, nombre)
VALUES ('890679de-6040-4254-a3d0-552066183d0d', 109, 'Avenida Siempre Viva 131, Ciudad Central', 'CERRADO_TEMPORALMENTE', 'Centro de Convenciones Norte');

INSERT INTO espacio_fisico (id, capacidad, direccion, estado, nombre)
VALUES ('45487047-f7dc-455a-8402-82142c7229a9', 142, 'Ruta 66 589, Ciudad Jardín', 'CERRADO_TEMPORALMENTE', 'Sala Creativa Alfa');

INSERT INTO espacio_fisico (id, capacidad, direccion, estado, nombre)
VALUES ('0f4d73d4-008f-4818-afe9-2861f39559f5', 26, 'Calle del Bosque 831, Puerto Claro', 'CERRADO_TEMPORALMENTE', 'Aula Magna Sur');

INSERT INTO espacio_fisico (id, capacidad, direccion, estado, nombre)
VALUES ('8cc13d7b-947e-4123-9d0d-e7b83cf73cd6', 33, 'Calle Aurora 934, Villa Ilustrada', 'ACTIVO', 'Espacio Colaborativo Delta');

INSERT INTO espacio_fisico (id, capacidad, direccion, estado, nombre)
VALUES ('047eadbf-2952-4959-bec0-0ccbd021e03b', 86, 'Vía Láctea 385, Ciudad Jardín', 'ACTIVO', 'Sala Ejecutiva 101');

INSERT INTO espacio_fisico (id, capacidad, direccion, estado, nombre)
VALUES ('ce58498f-3382-423f-96be-442cf6fa6960', 97, 'Paseo Verde 800, Campo Norte', 'ACTIVO', 'Auditorio Tecnológico');

INSERT INTO espacio_fisico (id, capacidad, direccion, estado, nombre)
VALUES ('7a28f17b-a89a-4e03-b93f-5d77a55db8b4', 69, 'Calle Luna 338, Vista Alegre', 'CERRADO_TEMPORALMENTE', 'Centro Cultural Este');

INSERT INTO espacio_fisico (id, capacidad, direccion, estado, nombre)
VALUES ('89bfd733-7634-4a75-8cfb-2179037d2fb1', 56, 'Calle del Río 947, Campo Norte', 'CERRADO_TEMPORALMENTE', 'Foro Empresarial');

INSERT INTO espacio_fisico (id, capacidad, direccion, estado, nombre)
VALUES ('d2bc1142-a2d9-409a-b52f-2418bd9ef517', 30, 'Calle Aurora 246, Monte Claro', 'CERRADO_TEMPORALMENTE', 'Sala de Proyectos');

INSERT INTO espacio_fisico (id, capacidad, direccion, estado, nombre)
VALUES ('99a08973-66a2-412d-beb9-37b588814bb4', 24, 'Calle Falsa 675, Río Grande', 'ACTIVO', 'Terraza Corporativa');

INSERT INTO espacio_fisico (id, capacidad, direccion, estado, nombre)
VALUES ('cb84da07-2c36-431d-b514-b4d08440906e', 45, 'Calle del Río 702, Ciudad Central', 'CERRADO_TEMPORALMENTE', 'Pabellón Universitario');

INSERT INTO espacio_fisico (id, capacidad, direccion, estado, nombre)
VALUES ('71a272aa-f535-42af-8ee4-636b89571c76', 90, 'Camino del Norte 613, Campo Norte', 'CERRADO_TEMPORALMENTE', 'Espacio Natural');

INSERT INTO espacio_fisico (id, capacidad, direccion, estado, nombre)
VALUES ('aed13836-32ed-4624-b148-fe03cfb6a4d1', 52, 'Calle del Río 345, Villa Ilustrada', 'ACTIVO', 'Centro de Formación Continua');

INSERT INTO espacio_fisico (id, capacidad, direccion, estado, nombre)
VALUES ('cb56596a-32bc-4498-b760-a09479c831c2', 21, 'Boulevard Central 594, Pueblo Nuevo', 'CERRADO_TEMPORALMENTE', 'Auditorio Central');

INSERT INTO espacio_fisico (id, capacidad, direccion, estado, nombre)
VALUES ('afa4d1cb-c934-42ba-a974-e608ebd1d4d8', 44, 'Calle Aurora 709, Pueblo Nuevo', 'CERRADO_TEMPORALMENTE', 'Sala de Conferencias Global');

INSERT INTO espacio_fisico (id, capacidad, direccion, estado, nombre)
VALUES ('dd854268-a0b2-48dd-bcc0-797eb5892f6c', 51, 'Calle Aurora 972, Pueblo Nuevo', 'ACTIVO', 'Aula Taller Norte');

INSERT INTO espacio_fisico (id, capacidad, direccion, estado, nombre)
VALUES ('6fe1a042-37f6-4eee-a6af-476ce285e4a5', 72, 'Calle del Río 678, Villa Ilustrada', 'ACTIVO', 'Foro Abierto Digital');

INSERT INTO espacio_fisico (id, capacidad, direccion, estado, nombre)
VALUES ('bc2c5644-1dc8-412a-97bc-dd359106ed02', 72, 'Camino Real 859, Ciudad Sol', 'CERRADO_TEMPORALMENTE', 'Espacio Startup');

INSERT INTO espacio_fisico (id, capacidad, direccion, estado, nombre)
VALUES ('521444f1-c1fa-4a24-8735-34694bdf1c23', 77, 'Paseo Verde 697, Ciudad Luz', 'CERRADO_TEMPORALMENTE', 'Sala de Networking');

INSERT INTO espacio_fisico (id, capacidad, direccion, estado, nombre)
VALUES ('10f3eb1b-cdd7-4c38-8d76-91ed6934acde', 56, 'Calle Aurora 638, Bahía Azul', 'CERRADO_TEMPORALMENTE', 'Centro de Ideas');

INSERT INTO espacio_fisico (id, capacidad, direccion, estado, nombre)
VALUES ('0aebb32e-9804-4374-b8e0-c32b216c1a28', 107, 'Camino del Norte 589, Monte Claro', 'ACTIVO', 'Punto de Encuentro');

INSERT INTO espacio_fisico (id, capacidad, direccion, estado, nombre)
VALUES ('cd128ed8-9bb1-453a-845b-ed8f06b9308d', 93, 'Boulevard Central 344, Villa del Mar', 'CERRADO_TEMPORALMENTE', 'Espacio Maker');

INSERT INTO espacio_fisico (id, capacidad, direccion, estado, nombre)
VALUES ('b1cbf50c-aedf-4d15-9615-969ec43b9e59', 131, 'Avenida Siempre Viva 833, Villa del Mar', 'ACTIVO', 'Laboratorio de Creatividad');

INSERT INTO espacio_fisico (id, capacidad, direccion, estado, nombre)
VALUES ('b0809326-f93a-4b23-a17e-10cd92581e47', 86, 'Avenida Siempre Viva 547, Campo Norte', 'ACTIVO', 'Zona Coworking');

INSERT INTO espacio_fisico (id, capacidad, direccion, estado, nombre)
VALUES ('911c5bcf-ddb4-43d8-b387-eab181e91821', 118, 'Vía Láctea 329, Nueva Esperanza', 'ACTIVO', 'Estudio Multimedia');

INSERT INTO espacio_fisico (id, capacidad, direccion, estado, nombre)
VALUES ('b35afac6-b0f4-4b4d-8345-e53b3834ffd3', 93, 'Ruta 66 641, Villa Ilustrada', 'CERRADO_TEMPORALMENTE', 'Sala de Estrategia');

INSERT INTO espacio_fisico (id, capacidad, direccion, estado, nombre)
VALUES ('dafddbf2-1d4e-4bdc-9432-cd5b220314c3', 55, 'Calle del Río 121, Monte Claro', 'ACTIVO', 'Espacio Emprendedor');

INSERT INTO espacio_fisico (id, capacidad, direccion, estado, nombre)
VALUES ('ffc665a3-02ff-48a2-a459-bcf2f125a2ab', 128, 'Calle del Arte 645, Campo Norte', 'ACTIVO', 'Campus Virtual');

INSERT INTO evento (id, cancelado, categoria, descripcion, nombre, fecha_inicio, fecha_fin, organizador, plazas, espacio_fisico_id) VALUES
('d114f6d1-83ba-449b-8794-01b5a07c2a51', 0, 'ACADEMICO',       'Seminario sobre la transformación digital en la educación superior.',                  'Seminario Transformación Digital',    '2025-06-03 09:00:00', '2025-06-03 12:00:00', 'gestor',                95,  '74800b33-c205-4c4b-b67c-4ad02febdfa7'),
('2caa6ccb-5d80-44a7-8227-4b72700f7c91', 0, 'ENTRETENIMIENTO', 'Concierto acústico con bandas universitarias emergentes.',                             'Concierto Primaveral',                '2025-06-10 14:00:00', '2025-06-10 18:00:00', 'fundacion_cultural',     80,  '74800b33-c205-4c4b-b67c-4ad02febdfa7'),

('7c273e2a-78cc-4986-8d04-34e4a77261ef', 0, 'OTROS',           'Hackathon de innovación abierta para resolver retos sociales.',                       'Hackathon Innovación 2025',           '2025-06-04 09:00:00', '2025-06-04 20:00:00', 'universidad',            100, 'c3127765-0306-40a6-888a-3ffb8126be0c'),
('ba3c74c8-5b3d-4e1a-9dc8-d0f4ea1f54c3', 0, 'ACADEMICO',       'Taller práctico de prototipado rápido con impresión 3D.',                             'Taller de Prototipado 3D',            '2025-06-18 10:00:00', '2025-06-18 13:00:00', 'lab-innov',              60,  'c3127765-0306-40a6-888a-3ffb8126be0c'),

('8e1a056d-0519-4762-bc28-0699e0238571', 0, 'CULTURAL',        'Sesión intensiva de escritura creativa y micro-relatos.',                             'Taller Micro-relatos',                '2025-06-05 10:00:00', '2025-06-05 12:00:00', 'fundacion_cultural',     25,  '8cc13d7b-947e-4123-9d0d-e7b83cf73cd6'),
('4323dcea-6b28-46b7-a563-29de89abde26', 0, 'OTROS',           'Proyección y coloquio de cortometrajes universitarios.',                               'Cine Universitario Nocturno',         '2025-06-22 19:00:00', '2025-06-22 22:00:00', 'gestor',                 30,  '8cc13d7b-947e-4123-9d0d-e7b83cf73cd6'),

('4f829e16-0e23-46fb-8ff1-42dc0e3bd7fa', 0, 'DEPORTES',        'Competencia de ajedrez rápido (blitz) entre facultades.',                              'Torneo Blitz Universitario',          '2025-06-06 09:00:00', '2025-06-06 15:00:00', 'club-deportivo',         64,  '047eadbf-2952-4959-bec0-0ccbd021e03b'),
('5b9d5eae-1a21-4b07-b758-7da0627153f6', 0, 'ENTRETENIMIENTO', 'Monólogo de stand-up comedy con artistas locales.',                                     'Noche de Stand-Up',                   '2025-06-24 20:00:00', '2025-06-24 22:00:00', 'fundacion_cultural',     70,  '047eadbf-2952-4959-bec0-0ccbd021e03b'),

('a7b6c9b8-7527-4c90-9c30-6e63c65eac3e', 0, 'ACADEMICO',       'Mesa redonda sobre ética de la inteligencia artificial.',                             'Foro Ética IA',                       '2025-06-07 11:00:00', '2025-06-07 14:00:00', 'gestor',                 90,  'ce58498f-3382-423f-96be-442cf6fa6960'),
('56e0efac-1c6e-4b4d-96a8-74afadc3e0cc', 0, 'CULTURAL',        'Concierto de música de cámara con cuarteto invitado.',                                 'Concierto de Cámara',                 '2025-06-29 18:00:00', '2025-06-29 20:00:00', 'fundacion_cultural',     85,  'ce58498f-3382-423f-96be-442cf6fa6960'),

('e8eaf4b1-27c0-4aea-b3e6-6e3bc5d9d04f', 0, 'ENTRETENIMIENTO', 'Sesión de yoga al atardecer sobre la terraza.',                                        'Yoga Sunset',                         '2025-06-08 19:00:00', '2025-06-08 20:30:00', 'gestor',                 20,  '99a08973-66a2-412d-beb9-37b588814bb4'),
('3df0ab09-8d10-4f3c-af89-083a8a265bd5', 0, 'OTROS',           'Taller práctico de fotografía móvil.',                                                 'Fotografía con Smartphone',           '2025-06-17 10:00:00', '2025-06-17 12:00:00', 'universidad',            18,  '99a08973-66a2-412d-beb9-37b588814bb4'),

('c46b4f96-b76d-4522-8ac5-bb8a0d45d076', 0, 'ACADEMICO',       'Curso intensivo de metodologías ágiles.',                                               'Bootcamp Scrum',                      '2025-06-09 09:00:00', '2025-06-09 17:00:00', 'gestor',                 45,  'aed13836-32ed-4624-b148-fe03cfb6a4d1'),
('ad3ac6e7-ccd2-415e-a734-1e7c17f49a2e', 0, 'ACADEMICO',       'Seminario de actualización pedagógica para docentes.',                                 'Actualización Docente 2025',          '2025-06-27 10:00:00', '2025-06-27 14:00:00', 'universidad',            50,  'aed13836-32ed-4624-b148-fe03cfb6a4d1'),

('cdd20c26-cfb1-4f87-8c89-eaee153b1e60', 0, 'DEPORTES',        'Torneo de vóley mixto interfacultades.',                                                'Vóley Interfacultades',               '2025-06-11 08:00:00', '2025-06-11 15:00:00', 'club-deportivo',         48,  'dd854268-a0b2-48dd-bcc0-797eb5892f6c'),
('f4626893-d962-46c9-9e4e-91302aab1d37', 0, 'ENTRETENIMIENTO', 'Festival de bandas de rock estudiantil.',                                               'Rock Universitario',                  '2025-06-26 17:00:00', '2025-06-26 22:00:00', 'fundacion_cultural',     50,  'dd854268-a0b2-48dd-bcc0-797eb5892f6c'),

('0d5aa27c-9d46-4e32-a611-f480a1b7c993', 0, 'OTROS',           'Encuentro sobre bienestar y salud mental.',                                            'Jornada Bienestar',                   '2025-06-12 10:00:00', '2025-06-12 13:00:00', 'gestor',                 60,  '6fe1a042-37f6-4eee-a6af-476ce285e4a5'),
('fa04b485-02a5-4ddb-a93c-0bb1e0b9ea04', 0, 'CULTURAL',        'Muestra fotográfica de naturaleza.',                                                    'Expo Naturaleza',                     '2025-06-28 09:00:00', '2025-06-28 18:00:00', 'fundacion_cultural',     70,  '6fe1a042-37f6-4eee-a6af-476ce285e4a5'),

('3a20c2fd-6e9b-4f0d-aef5-478e1e025246', 0, 'ACADEMICO',       'Conferencia: Big Data y su impacto en la industria.',                                  'Conferencia Big Data',               '2025-06-13 09:00:00', '2025-06-13 13:00:00', 'empresa-tec',            100, '0aebb32e-9804-4374-b8e0-c32b216c1a28'),
('8a61a382-4992-4b2a-8981-d6d2a1f19725', 0, 'ENTRETENIMIENTO', 'Competencia interuniversitaria de trivia geek.',                                         'Geek Trivia Cup',                     '2025-06-23 15:00:00', '2025-06-23 18:00:00', 'universidad',            90,  '0aebb32e-9804-4374-b8e0-c32b216c1a28'),

('188eaa3b-8d98-4eaf-9dc2-8a0bae4c3076', 0, 'CULTURAL',        'Feria gastronómica con degustaciones regionales.',                                      'Feria Gastronómica',                 '2025-06-14 11:00:00', '2025-06-14 16:00:00', 'gestor',                120, 'b1cbf50c-aedf-4d15-9615-969ec43b9e59'),
('b62e83db-3610-4e0c-a07e-e2e6e7116e5e', 0, 'ACADEMICO',       'Taller de impresión 3D aplicada a la ingeniería.',                                     'Workshop Impresión 3D',              '2025-06-25 09:00:00', '2025-06-25 13:00:00', 'empresa-tec',           110, 'b1cbf50c-aedf-4d15-9615-969ec43b9e59'),

('1ba5e7e2-4ff2-40d2-b7e3-9982a8e9374c', 0, 'OTROS',           'Mesa de debate sobre futuros del trabajo remoto.',                                      'Foro Trabajo Remoto',                '2025-06-15 10:00:00', '2025-06-15 12:00:00', 'universidad',            75,  'b0809326-f93a-4b23-a17e-10cd92581e47'),
('83823d6e-b6fd-4d09-b8e4-11b0cf50dab4', 0, 'ENTRETENIMIENTO', 'Torneo de videojuegos retro.',                                                          'Retro Gaming Cup',                    '2025-06-27 16:00:00', '2025-06-27 22:00:00', 'gestor',                 80,  'b0809326-f93a-4b23-a17e-10cd92581e47'),

('5c7bd6df-2660-48f4-957b-7e44f246ad9f', 0, 'ACADEMICO',       'Seminario sobre periodismo de datos.',                                                  'Data Journalism 2025',               '2025-06-16 09:00:00', '2025-06-16 13:00:00', 'empresa-tec',            90,  '911c5bcf-ddb4-43d8-b387-eab181e91821'),
('23ff86f5-fba6-4b4a-9589-afdbb4ba9a36', 0, 'CULTURAL',        'Conferencia sobre tendencias del arte digital.',                                        'Arte Digital Hoy',                   '2025-06-30 11:00:00', '2025-06-30 14:00:00', 'fundacion_cultural',    110,  '911c5bcf-ddb4-43d8-b387-eab181e91821');

-- ========== 4 EVENTOS CANCELADOS (campos de fecha y espacio a NULL) ==========
INSERT INTO evento (id, cancelado, categoria, descripcion, nombre, fecha_inicio, fecha_fin, organizador, plazas, espacio_fisico_id) VALUES
('9bc5f363-8801-4e7e-99f4-cf5e4c4c1b63', 1, 'ACADEMICO',       'Simposio nacional de investigación educativa.',   'Simposio Investigación 2025',  NULL, NULL, 'universidad',          90,  NULL),
('d6e1b7cb-23ed-4fd2-8fb7-b8e5d1b7a4f9', 1, 'CULTURAL',        'Feria del libro infantil con autores invitados.', 'Feria del Libro Infantil',     NULL, NULL, 'fundacion_cultural',   85,  NULL),
('0894d6a0-ed88-4716-82e0-c370b392cc11', 1, 'DEPORTES',        'Torneo de atletismo universitario cancelado.',    'Torneo Atletismo Cancelado',   NULL, NULL, 'club-deportivo',      104,  NULL),
('4e2a71c4-af97-44b6-9803-48c0026bfe76', 1, 'ENTRETENIMIENTO', 'Festival de bandas emergentes cancelado.',        'Festival Rock Cancelado',      NULL, NULL, 'gestor',               80,  NULL);

