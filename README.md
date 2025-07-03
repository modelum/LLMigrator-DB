[![Contributors][contributors-shield]][contributors-url]
[![Issues][issues-shield]][issues-url]
![GitHub Repo stars](https://img.shields.io/github/stars/modelum/LLMigrator-DB?style=for-the-badge)

# LLMigrator-DB

## Tabla de Contenidos

- [LLMigrator-DB](#llmigrator-db)
  - [Tabla de Contenidos](#tabla-de-contenidos)
  - [📋 Descripción](#-descripción)
  - [🚀 Utiliza la herramienta](#-utiliza-la-herramienta)
    - [Clona el repositorio](#clona-el-repositorio)
    - [Preparación del entorno y su ejecución en local](#preparación-del-entorno-y-su-ejecución-en-local)
      - [Preparación del entorno en local](#preparación-del-entorno-en-local)
      - [Ejecución en local](#ejecución-en-local)
    - [Preparación del entorno en Docker y despliegue](#preparación-del-entorno-en-docker-y-despliegue)
      - [Preparación del entorno en Docker](#preparación-del-entorno-en-docker)
      - [Despliegue en Docker](#despliegue-en-docker)
  - [📖 Uso](#-uso)
  - [🧪 Pruebas generadas](#-pruebas-generadas)
  - [🛠️ Stack Tecnológico](#️-stack-tecnológico)
    - [🔧 Tecnologías Principales](#-tecnologías-principales)
    - [🤖 LLM presentados en el desarrollo del TFG.](#-llm-presentados-en-el-desarrollo-del-tfg)
      - [Añade un nuevo LLM según tus necesidades](#añade-un-nuevo-llm-según-tus-necesidades)
  - [📜 Créditos y Licencias](#-créditos-y-licencias)

## 📋 Descripción

LLMigrator-DB es una herramienta genérica basada en LLMs para automatizar la migración de bases de datos, la cual ha sido desarrollada como parte del [Trabajo Fin de Grado](./TFG_Fabian_Sola.pdf) de [Fabián Sola Durán](<(https://github.com/fsoladur)>), realizado en el seno del [grupo ModelUM](https://modelum.github.io/), diseñada para facilitar la migración de bases de datos. Entre los principales requisitos de LLMigrator-DB podemos destacar: el soporte de migraciones homogéneas y heterogéneas, la independencia de SGBD y modelos de datos, y también de LLMs concretos y el soporte de la migración de código (de acceso a datos) además de la migración de esquemas de bases de datos. Esta independencia de la plataforma se ha conseguido mediante el uso de plantillas de prompt para los modelos de datos y SGBD, así como la utilización de una API unificada para acceder a distintos LLMs, como es **OpenRouter**.

La construcción y uso de LLMigrator-DB ha permitido evaluar la viabilidad de los LLM para realizar migraciones de bases de datos y potenciar la automatización de este proceso por parte de los desarrolladores, facilitando la generación de código y la adaptación de esquemas entre diferentes tecnologías de bases de datos.

## 🚀 Utiliza la herramienta

### Clona el repositorio

Para comenzar a utilizar LLMigrator-DB, primero clona el repositorio en tu máquina local:

```bash
git clone https://github.com/modelum/LLMigrator-DB.git
```

### Preparación del entorno y su ejecución en local

#### Preparación del entorno en local

Para desplegar la herramienta y que funcione correctamente, es necesario configurar las siguientes variables de entorno:

- `OPENROUTER_API_KEY`: API key para acceder a OpenRouter. la cual puedes obtenerla registrándote en [OpenRouter](https://openrouter.ai/).

Tras haber obtenido la API Key, puedes establecerla en tu sistema operativo con los siguientes comandos dependiendo de tu sistema:

**En Linux/MacOS**

```bash
export OPENROUTER_API_KEY="tu_api_key_aqui"
```

**En Windows (CMD)**

```cmd
set OPENROUTER_API_KEY=tu_api_key_aqui
```

**En Windows (PowerShell)**

```powershell
$env:OPENROUTER_API_KEY="tu_api_key_aqui"
```

#### Ejecución en local

Posteriormente puedes ejecutar la aplicación con el siguiente comando:

```bash
cd LLMigrator-DB-API
./mnvw clean install
./mvnw spring-boot:run
```

### Preparación del entorno en Docker y despliegue

#### Preparación del entorno en Docker

En caso de que quieras utilizar Docker para desplegar la aplicación, puedes establecer la variable de entorno pertinente creando un fichero `.env` en el directorio [LLMigrator-DB-API](./LLMigrator-DB-API/) del proyecto con el siguiente contenido:

```env
OPENROUTER_API_KEY=tu_api_key_aqui
```

Otra alternativa es ejecutar los siguientes comandos distintas terminales de los distintos sistemas operativos para crear el fichero `.env` directamente dentro del directorio [LLMigrator-DB-API](./LLMigrator-DB-API/):

**En Linux/MacOS**

```bash
echo OPENROUTER_API_KEY=tu_api_key_aqui > .env
```

**En windows (CMD)**

```cmd
echo OPENROUTER_API_KEY=tu_api_key_aqui > .env
```

**En windows (PowerShell)**

```powershell
"OPENROUTER_API_KEY=tu_api_key_aqui" > .env
```

#### Despliegue en Docker

Tras haber creado el fichero `.env`, puedes construir y ejecutar la aplicación en un contenedor Docker con el siguiente comando.

> [!WARNING]
> ¡No olvides tener el demonio de docker activo! Abre Docker Desktop o ejecuta el servicio de Docker en tu sistema operativo.

```bash
docker compose up -d
```

## 📖 Uso

Una vez desplegada la aplicación, puedes interactuar con la API a través de los siguientes endpoints:

- `POST /api/v1/migrations`: Inicia una nueva migración de base de datos.
- `POST /api/v1/code-migrations`: Inicia una nueva migración de código.

El cuerpo de la solicitud debe contener un objeto JSON con los detalles de la migración, incluyendo el modelo de lenguaje a utilizar y los parámetros específicos de la migración.

Se adjunta el fichero postman [`LLMigrator-DB API.postman_collection`](LLMigrator-DB%20API.postman_collection.json) para facilitar las pruebas de los diferentes endpoints con las peticiones de ejemplo que nosotros hemos utilizado. Aclarar que la API no requiere autenticación, por lo que puedes probarla directamente sin necesidad de configurar ningún token de acceso.

Esta colección de Postman incluye ejemplos de migraciones homogéneas y heterogéneas además de las respuestas generadas por la herramienta LLMigrator-DB. Estas respuestas han sido generadas de forma iterativa para hacer frente al no determinismo de los LLM.

## 🧪 Pruebas generadas

Se han guardado los resultados de las pruebas expuestas durante el TFG en el directorio [`Pruebas-TFG`](./Pruebas-TFG/), donde se pueden encontrar ejemplos de migraciones de bases de datos y el código asociado a las mismas. Dentro de este directorio, se incluyen los siguientes subdirectorios:

- [`Migracion-eventos-gemini-2.0-Flash`](./Pruebas-TFG/Migracion-eventos-gemini-2.0-Flash/): Contiene el ejemplo utilizado en el TFG para la migración homogénea del microservicio de eventos utilizado en el TFG.
- [`Migracion-reservas-gpt-4o-mini`](./Pruebas-TFG/Migracion-reservas-gpt-4o-mini/): Contiene el ejemplo utilizado en el TFG para la migración heterogénea del microservicio de reservas utilizado en el TFG.

Para cada uno de estos ejemplos, se incluyen los ficheros llamados `env-local` que contienen las variables de entorno a definir en tu fichero `.env` para que la aplicación funcione correctamente.

Además, se adjuntan los proyectos de Spring Boot generados para cada una de las migraciones, los cuales se pueden ejecutar con docker utilizando el comando `docker compose up -d` en los directorios [Eventalia](./Eventalia/) y [Eventalia-migrado](./Eventalia-migrado/). Donde el primero contiene el conjunto de microservicios originales y el segundo contiene los microservicios migrados a partir de los ejemplos de migración generados por LLMigrator-DB.

## 🛠️ Stack Tecnológico

<div align="center">

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.2-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Spring AI](https://img.shields.io/badge/Spring%20AI-Latest-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![OpenRouter](https://img.shields.io/badge/OpenRouter-API-FF6B6B?style=for-the-badge&logo=openai&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)

</div>

### 🔧 Tecnologías Principales

| Tecnología      | Versión | Descripción                                           |
| --------------- | ------- | ----------------------------------------------------- |
| **Spring Boot** | 3.4.2   | Framework principal para el desarrollo de la API REST |
| **Spring AI**   | Latest  | Integración con modelos de lenguaje (LLMs)            |
| **OpenRouter**  | API     | API unificada para acceso a múltiples LLMs            |
| **Java**        | 21      | Lenguaje de programación principal                    |
| **Maven**       | 3.9.6   | Gestión de dependencias y construcción                |
| **Docker**      | Latest  | Contenedorización de la aplicación                    |

### 🤖 LLM presentados en el desarrollo del TFG.

- **Google Gemini 2.0 Flash** (`google/gemini-2.0-flash-001`) - Modelo utilizado para la migración homogénea del microservicio de eventos
- **OpenAI GPT-4o Mini** (`openai/gpt-4o-mini`) - Modelo utilizado para la migración heterogénea del microservicio de reservas
- **Deepseek R1 0528** (`deepseek/deepseek-r1-0528:free`) - Modelo utilizado en el TFG como ejemplo para añadir un nuevo LLM en la herramienta de forma sencilla

#### Añade un nuevo LLM según tus necesidades

En primer lugar accede a [OpenRouter](https://openrouter.ai/) y busca el identificador del LLM que quieres utilizar. Asegúrate de que el LLM permite el uso de mensajes de sistema y usuario, ya que la herramienta LLMigrator-DB utiliza estos mensajes para interactuar con LLM.

Para añadir un nuevo LLM, simplemente debes incluir su identificador en el fichero de configuración `application.yml` bajo la sección `spring.ai.openrouter.chat.options.models`.

```yaml
spring:
  ai:
    # Otras configuraciones de la aplicación...
    openrouter:
      chat:
        options:
          models:
            - deepseek/deepseek-r1-0528:free
            - google/gemini-2.0-flash-001
            - openai/gpt-4o-mini
```

Posteriormente, añade en los enumerados `SupportedLanguageLargeModelsDto` y `SupportedLanguageLargeModels` el identificador del nuevo LLM de acuerdo a la estructura ya definida.

> [!TIP]
> 🎉 **¡Y ya está!** Ya puedes experimentar con dicho LLM y probar su uso como asistente de migración de bases de datos.

## 📜 Créditos y Licencias

Este proyecto reutiliza y adapta partes del repositorio [spring-ai-openrouter-example](https://github.com/pacphi/spring-ai-openrouter-example), publicado bajo licencia [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0).

En concreto, se han reutilizado y adaptado componentes relacionados con la configuración de clientes `ChatClient`, integración con OpenRouter y la gestión de modelos en paralelo. Los archivos modificados incluyen comentarios de atribución directa.

Consulta el archivo [`LICENSE-APACHE-2.0.txt`](./LLMigrator-DB-API/LICENSE-APACHE-2.0.txt) para más información.

<!-- MARKDOWN LINKS & IMAGES -->

[contributors-shield]: https://img.shields.io/github/contributors/modelum/LLMigrator-DB.svg?style=for-the-badge
[contributors-url]: https://github.com/modelum/LLMigrator-DB/graphs/contributors
[issues-shield]: https://img.shields.io/github/issues/modelum/LLMigrator-DB.svg?style=for-the-badge
[issues-url]: https://github.com/modelum/LLMigrator-DB/issues
