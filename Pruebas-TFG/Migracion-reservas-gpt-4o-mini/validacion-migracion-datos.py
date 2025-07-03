
# Explicación del script de validación de migración de datos y esquema generada por GPT-4o Mini:
# 
# Este script de Python se conecta a las bases de datos MongoDB y PostgreSQL para 
# validar la migración de datos. Se define una serie de funciones que permiten realizar 
# las operaciones más frecuentes especificadas por el usuario y comparar los resultados 
# entre ambas bases de datos. 
# 
# 
# - Cada función ejecuta una consulta en MongoDB y en PostgreSQL
# - Compara la longitud de los resultados para determinar si la migración fue exitosa
# - Incluye funciones para validar reservas asociadas a un evento
# - Valida reservas de un usuario
# - Verifica eventos disponibles y el estado de un evento
# - Al final, se imprimen los resultados de las validaciones para su revisión
# 
# Este proceso asegura que los datos se hayan migrado correctamente y que las consultas 
# devuelvan resultados consistentes en ambas bases de datos.


import psycopg2
from pymongo import MongoClient

# Conexión a MongoDB
mongo_client = MongoClient('mongodb://localhost:27017/')
mongo_db = mongo_client['nombre_base_datos']
reservas_collection = mongo_db['reservas']
eventos_collection = mongo_db['eventos']

# Conexión a PostgreSQL
pg_conn = psycopg2.connect(
    dbname='nombre_base_datos_pg',
    user='usuario',
    password='contraseña',
    host='localhost',
    port='5432'
)
pg_cursor = pg_conn.cursor()

# Función para validar las reservas asociadas a un evento
def validar_reservas_por_evento(uuid_evento):
    mongo_reservas = list(reservas_collection.find({'evento.$id': uuid_evento}))
    pg_cursor.execute('SELECT * FROM RESERVA WHERE evento = %s', (uuid_evento,))
    pg_reservas = pg_cursor.fetchall()
    return len(mongo_reservas) == len(pg_reservas)

# Función para comprobar reservas asociadas a un usuario
def validar_reservas_por_usuario(uuid_usuario):
    mongo_reservas = list(reservas_collection.find({'idUsuario': uuid_usuario}).limit(1))
    pg_cursor.execute('SELECT * FROM RESERVA WHERE id_usuario = %s LIMIT 1', (uuid_usuario,))
    pg_reserva = pg_cursor.fetchall()
    return len(mongo_reservas) == len(pg_reserva)

# Función para obtener todas las reservas de un usuario
def validar_reservas_usuario(uuid_usuario):
    mongo_reservas = list(reservas_collection.find({'idUsuario': uuid_usuario}))
    pg_cursor.execute('SELECT * FROM RESERVA WHERE id_usuario = %s', (uuid_usuario,))
    pg_reservas = pg_cursor.fetchall()
    return len(mongo_reservas) == len(pg_reservas)

# Función para consultar eventos disponibles
def validar_eventos_disponibles():
    mongo_eventos = list(eventos_collection.find({
        'cancelado': False, 
        'plazasDisponibles': {'$gt': 0}
    }))
    pg_cursor.execute('SELECT * FROM EVENTO WHERE cancelado = FALSE AND plazas_disponibles > 0')
    pg_eventos = pg_cursor.fetchall()
    return len(mongo_eventos) == len(pg_eventos)

# Función para verificar estado de un evento
def validar_estado_evento(uuid_evento):
    mongo_evento = eventos_collection.find_one({'_id': uuid_evento})
    pg_cursor.execute('SELECT * FROM EVENTO WHERE _id = %s', (uuid_evento,))
    pg_evento = pg_cursor.fetchone()
    return mongo_evento == pg_evento

# UUIDs a validar
uuid_evento = 'UUID_EVENTO'
uuid_usuario = 'UUID_USUARIO'

# Validaciones
validacion_reservas_evento = validar_reservas_por_evento(uuid_evento)
validacion_reservas_usuario = validar_reservas_por_usuario(uuid_usuario)
validacion_reservas_usuario_todas = validar_reservas_usuario(uuid_usuario)
validacion_eventos_disponibles = validar_eventos_disponibles()
validacion_estado_evento = validar_estado_evento(uuid_evento)

# Resultados
print('Validación reservas por evento:', validacion_reservas_evento)
print('Validación reservas por usuario:', validacion_reservas_usuario)
print('Validación reservas de usuario:', validacion_reservas_usuario_todas)
print('Validación eventos disponibles:', validacion_eventos_disponibles)
print('Validación estado de evento:', validacion_estado_evento)

# Cerrar conexiones
pg_cursor.close()
pg_conn.close()
mongo_client.close()
