# Nombre del Proyecto:  VALIDACION IDENTIDAD

Esta es una aplicación encargada de transformar el mensaje entrante para que quede listo para enviar a una cola de ActiveMQ. El objetivo principal es eliminar los campos innecesarios del mensaje, conservando únicamente el campo de documento.

## Requerimientos para despliegue a modo local:

- Ejecutar ActiveMQ actualmente se realizaron las pruebas con esta versión: apache-activemq-5.19.0-SNAPSHOT

- Se usó el IDE: RED HAT CodeReady Studio (en teoría se podría usar Eclipse con los respectivos ajustes para Fuse).

- Para lanzar peticiones se usó: SoapUI

- Se creó un proyecto tipo REST en: http://localhost:3000/mock-endpoint

- Se creó un MockService en: http://localhost:8090/mockCedulasBinding

## Arquitectura y Componentes

El proyecto está basado en Spring Boot y Apache Camel, y consta de varios componentes y clases importantes:

1. `Archivo camel-context.xml`: Este archivo define el contexto de Camel y contiene la configuración de enrutamiento. Utiliza el componente "ActiveMQ" de Camel para integrarse con Apache ActiveMQ. Configura un bean "activemq" que utiliza la conexión de ActiveMQ definida en la clase "ActiveMQConfig". Luego, se definen dos rutas de Camel. La primera ruta, con ID "_route1", consume mensajes de una cola, los transforma utilizando el bean "listenerTransESB1" y los envía a otra cola. La segunda ruta, con ID "_route2", consume mensajes de una cola, los transforma utilizando el bean "responseTransformerBean" y los envía a otra cola.

2. `Clase ActiveMQConfig`: Esta clase es una configuración de Spring que define los beans necesarios para la conexión y el manejo de transacciones con ActiveMQ. Proporciona un bean "connectionFactory" que configura la conexión a ActiveMQ utilizando los valores definidos en el archivo "application-dev.properties". También define un bean "jmsTransactionManager" para administrar las transacciones y un bean "jmsTemplate" para enviar mensajes a las colas.

3. `Clase InitRegistryIntegrationValidationESB1`: Esta clase es la aplicación principal de Spring Boot que inicia la aplicación. Está anotada con "@SpringBootApplication" y también se importa el recurso XML de configuración "spring/camel-context.xml" utilizando la anotación "@ImportResource". La aplicación se ejecuta mediante el método "main" que llama a "SpringApplication.run".

4. `Clase "ListenerESB1`: Esta clase es un componente de Spring anotado con "@Component" y también implementa la interfaz "MessageListener" de JMS. Se utiliza como un listener para recibir mensajes de una cola. En el método "onMessage", se procesa el mensaje recibido. Si el mensaje es del tipo "TextMessage", se obtiene el texto del mensaje, se llama al método "transformMessage" para transformar el mensaje eliminando el campo "tipoDocumento" y se envía el mensaje transformado a otra cola utilizando el bean "jmsTemplate".

5. `Clase ResponseTransformer`: Esta clase es un componente de Spring anotado con "@Component" y también implementa la interfaz "MessageListener" de JMS. Se utiliza como un listener para recibir mensajes de una cola. En el método "onMessage", se procesa el mensaje recibido. Si el mensaje es del tipo "TextMessage", se obtiene el texto del mensaje, se llama al método "transformResponse" para transformar el mensaje eliminando los campos no deseados y se envía el mensaje transformado a otra cola utilizando el bean "jmsTemplate".

## Flujo General del Proyecto

A continuación, se muestra el flujo general de la aplicación a través del archivo `camel-context.xml`:

![FlujoServicio1](https://github.com/marudsa/ESB_ValidacionIdentidad/assets/110506792/ca57a6e9-d364-4c59-b361-7ba02c131488)

## Instrucciones para Ejecutar

Sigue estos pasos para ejecutar el proyecto en modo local:

1. Asegúrate de tener ActiveMQ en ejecución y configurado correctamente con la versión `apache-activemq-5.19.0-SNAPSHOT`.

2. Importa el proyecto en tu IDE (preferiblemente RED HAT CodeReady Studio o Eclipse con los ajustes para Fuse).

3. Ejecuta la clase `Application` para iniciar la aplicación.

4. Utiliza SoapUI u otra herramienta para enviar peticiones al servicio REST simulado en `http://localhost:3000/mock-endpoint`.

5. Observa los registros en la consola y el procesamiento de mensajes a través de ActiveMQ y las rutas de Camel.

¡Listo! Ahora la aplicación está en funcionamiento y procesará los mensajes enviados al servicio REST simulado.


**Nota:** Asegúrate de seguir los requerimientos y configuraciones adecuadas para un correcto despliegue y funcionamiento de la aplicación en tu entorno local.


