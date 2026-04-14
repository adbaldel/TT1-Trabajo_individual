# **Web App de Simulaciones - Trabajo Individual TT1**

**Autor:** Adrián Baldellou Aguirre.

## **Descripción del Proyecto**

Este proyecto es una aplicación web que actúa como una capa de presentación amigable (frontend) para el servidor de simulaciones desarrollado en el Trabajo en Grupo de TT1.

Desarrollada bajo el framework Spring Boot, esta aplicación se encarga de interactuar con la API REST del backend para enviar solicitudes de simulación, consultar su estado, obtener resultados y ofrecer una interfaz gráfica de usuario fácil de usar mediante plantillas HTML dinámicas.

## **Estructura Principal**

La aplicación sigue el patrón arquitectónico Modelo-Vista-Controlador (MVC) y se divide en los siguientes componentes principales:

* **Controladores (Presentación):** Gestionan el enrutamiento web y las peticiones del usuario (ej. `SolicitudController`, `GridController`).
* **Lógica / Integración:** Servicios encargados de la comunicación con la API del servidor de simulaciones (`ContactoSim`) y la gestión de notificaciones (`EnviarEmails`).
* **Vistas:** Páginas HTML renderizadas dinámicamente en el servidor (ej. `solicitud.html`, `grid.html`, `formResult.html`).

## **Tecnologías Utilizadas**

* **Lenguaje:** Java 21
* **Framework Principal:** Spring Boot (v4.0.3)
* **Gestor de dependencias:** Maven
* **Motor de plantillas:** Thymeleaf

## **Dependencias**

El proyecto utiliza Maven para la gestión de dependencias. Las más destacadas incluyen:

* **Spring Boot Starter Web MVC:** Proporciona las herramientas necesarias para crear la aplicación web y exponer endpoints (Controladores).
* **Spring Boot Starter Thymeleaf:** Motor de plantillas de servidor que permite crear las interfaces gráficas y enlazar datos del modelo de Spring directamente al HTML.
* **Spring Boot Starter REST Client:** Permite que la aplicación consuma los endpoints HTTP del servidor backend del trabajo en grupo.
* **Spring Boot Starter Test:** Herramientas para la ejecución de pruebas unitarias sobre los controladores web y clientes REST.

## **Instrucciones de Ejecución**

Para compilar y ejecutar el proyecto localmente usando Maven, puedes utilizar el siguiente comando en la raíz del proyecto:

```bash
mvn spring-boot:run
```

Para generar un `.jar` ejecutar puedes utilizar el siguiente comando en la raíz del proyecto:
```bash
mvn clean package
```