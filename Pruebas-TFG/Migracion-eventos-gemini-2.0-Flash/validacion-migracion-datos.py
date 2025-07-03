# Explicación de la validación de migración de datos y esquema generada por Gemini 2.0 Flash:

# Este script permite validar la migración de datos y esquema de una base de datos MySQL a PostgreSQL. 
# Primero, establece conexiones con ambas bases de datos. Luego, valida la estructura de las tablas, 
# comparando las tablas en MySQL y PostgreSQL. A continuación, se valida la migración de datos en las tablas `espacio_fisico` y `evento`, 
# asegurando que los datos coinciden. Se ejecutan las consultas frecuentes en ambas bases de datos y se comparan los resultados, 
# incluyendo verificar eventos activos, contar eventos futuros, buscar espacios disponibles y comprobar solapamiento. 
# Finalmente, se cierran las conexiones. Este enfoque asegura que todos los datos y la funcionalidad se hayan migrado correctamente
# y que las consultas mantengan la misma semántica en ambas bases de datos.

import psycopg2
import mysql.connector

# Conexión a la base de datos MySQL
mysql_conn = mysql.connector.connect(
    host='localhost',
    user='mysql_user',
    password='mysql_password',
    database='eventos'
)

# Conexión a la base de datos PostgreSQL
pg_conn = psycopg2.connect(
    host='localhost',
    database='eventos',
    user='pg_user',
    password='pg_password'
)

mysql_cursor = mysql_conn.cursor()
pg_cursor = pg_conn.cursor()

# 1. Validar la estructura de las tablas
mysql_cursor.execute("SHOW TABLES")
mysql_tables = mysql_cursor.fetchall()
pg_cursor.execute("SELECT table_name FROM information_schema.tables WHERE table_schema = 'eventos'")
pg_tables = pg_cursor.fetchall()

# Comparar tablas en MySQL y PostgreSQL
if sorted(mysql_tables) != sorted(pg_tables):
    print('Las tablas no coinciden entre MySQL y PostgreSQL')

# 2. Validar datos en espacio_fisico
mysql_cursor.execute('SELECT * FROM espacio_fisico')
mysql_espacios = mysql_cursor.fetchall()
pg_cursor.execute('SELECT * FROM eventos.espacio_fisico')
pg_espacios = pg_cursor.fetchall()

if sorted(mysql_espacios) != sorted(pg_espacios):
    print('Los datos en espacio_fisico no coinciden entre MySQL y PostgreSQL')

# 3. Validar datos en evento
mysql_cursor.execute('SELECT * FROM evento')
mysql_eventos = mysql_cursor.fetchall()
pg_cursor.execute('SELECT * FROM eventos.evento')
pg_eventos = pg_cursor.fetchall()

if sorted(mysql_eventos) != sorted(pg_eventos):
    print('Los datos en evento no coinciden entre MySQL y PostgreSQL')

# 4. Validar consultas frecuentes
# Consultas en PostgreSQL
# 1. Obtener eventos activos para un mes y año dados
month = 1
year = 2023
pg_cursor.execute(
    "SELECT * FROM eventos.evento WHERE cancelado = FALSE AND EXTRACT(YEAR FROM fecha_inicio) = %s AND EXTRACT(MONTH FROM fecha_inicio) = %s",
    (year, month)
)
pg_eventos_activos = pg_cursor.fetchall()

# Consultar en MySQL
mysql_cursor.execute(
    "SELECT * FROM evento WHERE cancelado = 0 AND YEAR(fecha_inicio) = %s AND MONTH(fecha_inicio) = %s",
    (year, month)
)
mysql_eventos_activos = mysql_cursor.fetchall()

if sorted(pg_eventos_activos) != sorted(mysql_eventos_activos):
    print('Los eventos activos para el mes y año dados no coinciden')

# 2. Comprobar si un espacio tiene eventos activos
espacio_fisico_id = 'some-uuid-value'
pg_cursor.execute(
    "SELECT COUNT(*) > 0 FROM eventos.evento WHERE espacio_fisico_id = %s AND fecha_fin > CURRENT_TIMESTAMP",
    (espacio_fisico_id,)
)
pg_tiene_eventos_activos = pg_cursor.fetchone()[0]

mysql_cursor.execute(
    "SELECT COUNT(*) > 0 FROM evento WHERE espacio_fisico_id = %s AND fecha_fin > CURRENT_TIMESTAMP",
    (espacio_fisico_id,)
)
mysql_tiene_eventos_activos = mysql_cursor.fetchone()[0]

if pg_tiene_eventos_activos != mysql_tiene_eventos_activos:
    print('La verificación de eventos activos en el espacio no coincide')

# 3. Buscar espacios disponibles con capacidad mínima y sin solapamiento de eventos
capacidad_minima = 50
fecha_inicio = '2023-01-01'
fecha_fin = '2023-01-10'
pg_cursor.execute(
    "SELECT id FROM eventos.espacio_fisico WHERE capacidad >= %s AND estado = 'ACTIVO' AND NOT EXISTS (SELECT 1 FROM eventos.evento WHERE espacio_fisico_id = eventos.espacio_fisico.id AND fecha_inicio <= %s AND fecha_fin >= %s)",
    (capacidad_minima, fecha_inicio, fecha_fin)
)
pg_espacios_disponibles = pg_cursor.fetchall()

mysql_cursor.execute(
    "SELECT id FROM espacio_fisico WHERE capacidad >= %s AND estado = 'ACTIVO' AND NOT EXISTS (SELECT 1 FROM evento WHERE espacio_fisico_id = espacio_fisico.id AND fecha_inicio <= %s AND fecha_fin >= %s)",
    (capacidad_minima, fecha_inicio, fecha_fin)
)
mysql_espacios_disponibles = mysql_cursor.fetchall()

if sorted(pg_espacios_disponibles) != sorted(mysql_espacios_disponibles):
    print('Los espacios disponibles no coinciden')

# 4. Contar eventos futuros con plazas mayores a una nueva capacidad
nueva_capacidad = 100
pg_cursor.execute(
    "SELECT COUNT(*) FROM eventos.evento WHERE espacio_fisico_id = %s AND fecha_fin > CURRENT_TIMESTAMP AND plazas > %s",
    (espacio_fisico_id, nueva_capacidad)
)
pg_count_eventos_futuros = pg_cursor.fetchone()[0]

mysql_cursor.execute(
    "SELECT COUNT(*) FROM evento WHERE espacio_fisico_id = %s AND fecha_fin > CURRENT_TIMESTAMP AND plazas > %s",
    (espacio_fisico_id, nueva_capacidad)
)
mysql_count_eventos_futuros = mysql_cursor.fetchone()[0]

if pg_count_eventos_futuros != mysql_count_eventos_futuros:
    print('El conteo de eventos futuros no coincide')

# 5. Obtener eventos no cancelados de un espacio
pg_cursor.execute(
    "SELECT * FROM eventos.evento WHERE espacio_fisico_id = %s AND cancelado = FALSE",
    (espacio_fisico_id,)
)
pg_eventos_no_cancelados = pg_cursor.fetchall()

mysql_cursor.execute(
    "SELECT * FROM evento WHERE espacio_fisico_id = %s AND cancelado = 0",
    (espacio_fisico_id,)
)
mysql_eventos_no_cancelados_mysql = mysql_cursor.fetchall()

if sorted(pg_eventos_no_cancelados) != sorted(mysql_eventos_no_cancelados_mysql):
    print('Los eventos no cancelados de un espacio no coinciden')

# 6. Comprobar si existe solapamiento con otro evento en un espacio
pg_cursor.execute(
    "SELECT COUNT(*) > 0 FROM eventos.evento WHERE espacio_fisico_id = %s AND cancelado = FALSE AND fecha_inicio < %s AND fecha_fin > %s",
    (espacio_fisico_id, fecha_fin, fecha_inicio)
)
pg_solapamiento = pg_cursor.fetchone()[0]

mysql_cursor.execute(
    "SELECT COUNT(*) > 0 FROM evento WHERE espacio_fisico_id = %s AND cancelado = 0 AND fecha_inicio < %s AND fecha_fin > %s",
    (espacio_fisico_id, fecha_fin, fecha_inicio)
)
mysql_solapamiento_mysql = mysql_cursor.fetchone()[0]

if pg_solapamiento != mysql_solapamiento_mysql:
    print('La verificación de solapamiento de eventos no coincide')

# Cerrar conexiones
mysql_cursor.close()
pg_cursor.close()
mysql_conn.close()
pg_conn.close()
