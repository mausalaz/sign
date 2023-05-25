# sign
exercise for bci
como pre requisitos, la maquina donde se ejecute el programa, debe tener instalado gradle.

descargar el proyecto desde https://github.com/mausalaz/sign.git

navegar dentro de la carpeta sign y ejecutar el comando ./gradlew build para construir el projecto.
Luego ejecutar el comando ./gradlew bootRun
para levantar el proyecto.

se adjunta coleccion de postman con los request
sign-up para el registro de un usuario (es el unico endpoint permitido sin token)
y endpoint login que actualiza el token y el lastlogin del usuario.