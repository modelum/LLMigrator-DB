"""
Explicación generada automáticamente por Gemini 2.0 Flash:
Este script está diseñado para migrar datos de una base de datos MySQL a una base de datos PostgreSQL. 
Primero, establece conexiones con ambas bases de datos utilizando las bibliotecas 'mysql.connector' y 'psycopg2'. 
Luego, se realiza una consulta a la tabla 'espacio_fisico' en MySQL y se insertan los datos en la tabla 
correspondiente en PostgreSQL, asegurando que el tipo de dato 'id' se convierta a 'UUID'. A continuación, 
se repite el proceso para la tabla 'evento', donde se convierte el campo 'cancelado' de tipo 'bit(1)' a 
'BOOLEAN', y también se convierte el campo 'espacio_fisico_id' a 'UUID'. 

Finalmente, se realiza un commit para guardar los cambios y se cierran las conexiones a ambas bases de datos. 
Este enfoque garantiza la integridad referencial y la consistencia de los datos migrados.

Características mejoradas:
- Configuración centralizada de conexiones
- Manejo robusto de errores y excepciones
- Prevención de duplicados con ON CONFLICT
- Transacciones seguras con rollback automático
- Estructura modular y reutilizable
"""

import mysql.connector
import psycopg2
import uuid

# Configuración de la base de datos MySQL (origen)
mysql_config = {
    'host': 'your_mysql_host',
    'user': 'your_mysql_user',
    'password': 'your_mysql_password',
    'database': 'eventos'
}

# Configuración de la base de datos PostgreSQL (destino)
postgres_config = {
    'host': 'your_postgres_host',
    'user': 'your_postgres_user',
    'password': 'your_postgres_password',
    'database': 'eventos',
    'port': '5432'  # Puerto por defecto de PostgreSQL
}

def migrate_data():
    """
    Función principal que realiza la migración de datos desde MySQL a PostgreSQL.
    Incluye manejo de errores y rollback automático en caso de fallos.
    """
    mysql_conn = None
    postgres_conn = None
    
    try:
        # Conexión a MySQL
        mysql_conn = mysql.connector.connect(**mysql_config)
        mysql_cursor = mysql_conn.cursor(dictionary=True)

        # Conexión a PostgreSQL
        postgres_conn = psycopg2.connect(**postgres_config)
        postgres_cursor = postgres_conn.cursor()

        # Migración de la tabla espacio_fisico
        print("Iniciando migración de la tabla 'espacio_fisico'...")
        mysql_cursor.execute(
            "SELECT id, capacidad, direccion, estado, nombre FROM espacio_fisico"
        )
        espacios_fisicos = mysql_cursor.fetchall()

        for espacio_fisico in espacios_fisicos:
            postgres_cursor.execute(
                """INSERT INTO espacio_fisico (id, capacidad, direccion, estado, nombre)
                   VALUES (%s, %s, %s, %s, %s) 
                   ON CONFLICT (id) DO NOTHING;""",  # Evitar duplicados
                (
                    uuid.UUID(espacio_fisico['id']), 
                    espacio_fisico['capacidad'], 
                    espacio_fisico['direccion'], 
                    espacio_fisico['estado'], 
                    espacio_fisico['nombre']
                )
            )

        print(f"Migrados {len(espacios_fisicos)} espacios físicos.")

        # Migración de la tabla evento
        print("Iniciando migración de la tabla 'evento'...")
        mysql_cursor.execute(
            "SELECT id, cancelado, categoria, descripcion, nombre, fecha_fin, fecha_inicio, organizador, plazas, espacio_fisico_id FROM evento"
        )
        eventos = mysql_cursor.fetchall()

        for evento in eventos:
            postgres_cursor.execute(
                """INSERT INTO evento (id, cancelado, categoria, descripcion, nombre, fecha_fin, fecha_inicio, organizador, plazas, espacio_fisico_id)
                   VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s)
                   ON CONFLICT (id) DO NOTHING;""",  # Evitar duplicados
                (
                    uuid.UUID(evento['id']), 
                    bool(evento['cancelado']), 
                    evento['categoria'], 
                    evento['descripcion'], 
                    evento['nombre'],
                    evento['fecha_fin'], 
                    evento['fecha_inicio'], 
                    evento['organizador'], 
                    evento['plazas'], 
                    uuid.UUID(evento['espacio_fisico_id'])
                )
            )

        print(f"Migrados {len(eventos)} eventos.")

        # Confirmar los cambios y cerrar las conexiones
        postgres_conn.commit()
        mysql_cursor.close()
        mysql_conn.close()
        postgres_cursor.close()
        postgres_conn.close()

        print("✅ Migración de datos completada exitosamente.")

    except mysql.connector.Error as mysql_err:
        print(f"❌ Error de MySQL: {mysql_err}")
        if mysql_conn:
            mysql_conn.rollback()
            mysql_cursor.close()
            mysql_conn.close()
    except psycopg2.Error as pg_err:
        print(f"❌ Error de PostgreSQL: {pg_err}")
        if postgres_conn:
            postgres_conn.rollback()
            postgres_cursor.close()
            postgres_conn.close()
    except Exception as e:
        print(f"❌ Error inesperado: {e}")
        if mysql_conn:
            mysql_conn.rollback()
            mysql_cursor.close()
            mysql_conn.close()
        if postgres_conn:
            postgres_conn.rollback()
            postgres_cursor.close()
            postgres_conn.close()

if __name__ == "__main__":
    migrate_data()
