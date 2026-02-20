# Laboratorio ARSW - Chat con RMI

Descripción
Este laboratorio implementa un sistema de chat distribuido usando RMI (Remote Method Invocation) de Java. La aplicación permite que varios clientes se conecten a un servidor y chateen en tiempo real mediante callbacks para notificar mensajes entrantes.

Objetivo
- Permitir que múltiples clientes se conecten a un servidor.
- Enviar y recibir mensajes entre clientes a través del servidor.
- Usar RMI para comunicación remota y callbacks.

Clases principales
- `EchoServer.java` (interfaz remota): Define los métodos remotos:
  - `String enviarMensaje(String mensaje, String remitente)`
  - `void registrarCliente(EchoServer clienteCallback, String nombre)`
  - `void recibirMensaje(String mensaje, String remitente)`
- `EchoServerImpl.java` (servidor): Publica el servicio RMI `"servidorChat"`, mantiene listas de clientes y nombres, y reenvía mensajes a los demás clientes.
- `EchoClient.java` (cliente): Exporta el cliente como objeto remoto para recibir callbacks, se registra en el servidor y permite enviar mensajes desde consola.

Cómo funciona (resumen)
1. El servidor arranca y crea un registro RMI en el puerto indicado, publicando el servicio `servidorChat`.
2. Cada cliente exporta su propio stub local (puerto local) para recibir callbacks y se registra en el servidor con un nombre.
3. Cuando un cliente envía un mensaje, llama a `enviarMensaje` en el servidor; el servidor reenvía el mensaje a todos los clientes registrados excepto al remitente mediante `recibirMensaje`.
4. Los clientes muestran los mensajes entrantes en consola.

Cómo ejecutar
1. Compilar el proyecto.
2. Iniciar el servidor :
   - Ejecutar `EchoServerImpl.java`.
   - Ingresar el puerto para publicar el servidor (ej. `23000`).
3. Iniciar uno o varios clientes:
   - Ejecutar `EchoClient.java` (permitir múltiples instancias si es necesario).
   - Ingresar:
     - Puerto local para exportar el stub del cliente (ej. `2400`, `2500`, ...).
     - IP del servidor (ej. `127.0.0.1`).
     - Puerto del servidor (ej. `23000`).
     - Nombre de usuario (ej. `Ana`, `Juan`).
   - Escribir mensajes en la consola. Para desconectarse, escribir `salir`.

## Estructura del proyecto

A continuación se muestra la estructura principal del proyecto y una breve descripción de los archivos más relevantes:

```text
arswlab4CallReturn-RMI/
├─ README.md                      # Documentación del proyecto
├─ pom.xml (opcional)             # Archivo de construcción (si aplica)
└─ src/
   └─ main/
      └─ java/
         └─ arsw/
            └─ demo/
               ├─ EchoServer.java       # Interfaz remota (métodos RMI)
               ├─ EchoServerImpl.java   # Implementación del servidor RMI
               └─ EchoClient.java       # Cliente que se registra y recibe callbacks
```

Descripción breve de los elementos:
- README.md: documentación y pasos para ejecutar el servidor y los clientes.
- EchoServer.java: define los métodos remotos: enviarMensaje, registrarCliente y recibirMensaje.
- EchoServerImpl.java: crea el registro RMI, publica el servicio `servidorChat`, mantiene listas de clientes y reenvía mensajes.
- EchoClient.java: exporta el cliente como objeto remoto para callbacks, se registra en el servidor y permite enviar/recibir mensajes desde consola.
