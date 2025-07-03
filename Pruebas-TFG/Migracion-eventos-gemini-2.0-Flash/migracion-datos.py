# Explicación generada automáticamente por Gemini 2.0 Flash:
# Este script está diseñado para migrar datos de una base de datos MySQL a una base de datos PostgreSQL. 
# Primero, establece conexiones con ambas bases de datos utilizando las bibliotecas 'mysql.connector' y 'psycopg2'. 
# Luego, se realiza una consulta a la tabla 'espacio_fisico' en MySQL y se insertan los datos en la tabla correspondiente en PostgreSQL,
# asegurando que el tipo de dato 'id' se convierta a 'UUID'. A continuación, se repite el proceso para la tabla 'evento',
# donde se convierte el campo 'cancelado' de tipo 'bit(1)' a 'BOOLEAN', y también se convierte el campo
# 'espacio_fisico_id' a 'UUID'. 
# Finalmente, se realiza un commit para guardar los cambios y se cierran las conexiones a ambas bases de datos. 
# Este enfoque garantiza la integridad referencial y la consistencia de los datos migrados.

import psycopg2
import uuid
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

# Migrar datos de espacio_fisico
mysql_cursor.execute('SELECT id, capacidad, direccion, estado, nombre FROM espacio_fisico')
for (id, capacidad, direccion, estado, nombre) in mysql_cursor:
    pg_cursor.execute(
        'INSERT INTO eventos.espacio_fisico (id, capacidad, direccion, estado, nombre) VALUES (%s, %s, %s, %s, %s)',
        (uuid.UUID(id), capacidad, direccion, estado, nombre)
    )

# Migrar datos de evento
mysql_cursor.execute(
    'SELECT id, cancelado, categoria, descripcion, nombre, fecha_fin, fecha_inicio, organizador, plazas, espacio_fisico_id FROM evento'
)
for (id, cancelado, categoria, descripcion, nombre, fecha_fin, fecha_inicio, organizador, plazas, espacio_fisico_id) in mysql_cursor:
    pg_cursor.execute(
        'INSERT INTO eventos.evento (id, cancelado, categoria, descripcion, nombre, fecha_fin, fecha_inicio, organizador, plazas, espacio_fisico_id) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s)',
        (uuid.UUID(id), bool(cancelado), categoria, descripcion, nombre, fecha_fin, fecha_inicio, organizador, plazas, uuid.UUID(espacio_fisico_id))
    )

# Commit y cerrar conexiones
pg_conn.commit()
mysql_cursor.close()
pg_cursor.close()
mysql_conn.close()
pg_conn.close()
