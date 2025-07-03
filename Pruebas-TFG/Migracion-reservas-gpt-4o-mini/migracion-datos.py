
#Explicación del script de migración de datos generada por OpenAI GPT-4o Mini:
#
#Este script de Python realiza la migración de datos desde una base de datos MongoDB 
#a una base de datos PostgreSQL. Primero, se establece la conexión con MongoDB y 
#PostgreSQL. Luego, se itera sobre cada documento en la colección 'eventos' y se 
#inserta en la tabla 'EVENTO' de PostgreSQL, asegurando que los UUID se conviertan 
#correctamente. Posteriormente, se realiza un procedimiento similar para la colección 
#'reservas', donde se insertan los datos en la tabla 'RESERVA', incluyendo la 
#referencia al evento usando el UUID correspondiente. Al final, se confirman los 
#cambios en la base de datos de PostgreSQL y se cierran todas las conexiones. Este 
#proceso asegura la integridad referencial y la consistencia de los datos migrados.
#
#Características principales:
#- Migración desde MongoDB (NoSQL) a PostgreSQL (SQL)
#- Conversión automática de ObjectId a UUID
#- Mantenimiento de integridad referencial
#- Mapeo de campos específicos entre esquemas


import psycopg2
import uuid
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

# Migrar datos de EVENTO
for evento in eventos_collection.find():
    pg_cursor.execute(
        'INSERT INTO EVENTO (_id, plazas_disponibles, cancelado, fecha_inicio, nombre_evento, _class) VALUES (%s, %s, %s, %s, %s, %s)',
        (
            uuid.UUID(evento['_id']), 
            evento['plazasDisponibles'], 
            evento['cancelado'], 
            evento['fechaInicio'], 
            evento['nombreEvento'], 
            evento['_class']
        )
    )

# Migrar datos de RESERVA
for reserva in reservas_collection.find():
    pg_cursor.execute(
        'INSERT INTO RESERVA (_id, id_usuario, cancelado, plazas_reservadas, evento, _class) VALUES (%s, %s, %s, %s, %s, %s)',
        (
            uuid.UUID(reserva['_id']), 
            uuid.UUID(reserva['idUsuario']), 
            reserva['cancelado'], 
            reserva['plazasReservadas'], 
            uuid.UUID(reserva['evento']['$id']), 
            reserva['_class']
        )
    )

# Confirmar cambios y cerrar conexiones
pg_conn.commit()
pg_cursor.close()
pg_conn.close()
mongo_client.close()
