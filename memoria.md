# Memoria del proyecto

## Tienda de móviles

**Autor/a:** [Nombre y apellidos]  
**Asignatura:** [Asignatura]  
**Curso:** [Curso académico]  
**Fecha:** 6 de noviembre de 2026

---

## Índice

1. [Introducción](#1-introducción)
2. [Descripción general de la tienda](#2-descripción-general-de-la-tienda)
3. [Tecnologías utilizadas](#3-tecnologías-utilizadas)

## 1. Introducción

Este proyecto consiste en una aplicación web para una tienda de teléfonos móviles. Su objetivo es ofrecer un recorrido de compra sencillo a los clientes y proporcionar una zona de administración desde la que gestionar el catálogo y los pedidos. La aplicación combina una interfaz web con servicios REST y una base de datos relacional.

## 2. Descripción general de la tienda

La tienda se organiza en dos áreas:

- **Zona pública:** presenta el catálogo de móviles, permite buscar y consultar productos y ofrece registro e identificación de clientes. Los usuarios identificados pueden añadir productos al carrito, modificarlo y completar un proceso de compra dividido en varios pasos: datos de envío, datos de pago, una preferencia y confirmación del pedido.
- **Zona de administración:** permite gestionar móviles, categorías y usuarios, además de consultar pedidos y actualizar su estado. El acceso a las páginas administrativas se controla mediante sesión e interceptor.

Los productos incluyen información como nombre, modelo, almacenamiento, memoria RAM, peso, precio, categoría e imagen. La aplicación guarda también los datos de clientes, carritos, pedidos y sus líneas. Al iniciar, puede preparar registros iniciales de productos y categorías para mostrar el catálogo.

En la parte técnica, las páginas públicas se componen de plantillas HTML que JavaScript carga según el idioma seleccionado; las peticiones a los servicios REST permiten obtener datos y actualizar la interfaz sin recargar toda la página. La zona de administración utiliza vistas Thymeleaf. La aplicación contempla español, inglés y francés.

El proceso de compra implementa los pasos de recogida de datos y confirmación del pedido. En las dependencias y configuración revisadas no aparece una pasarela externa de pago; por tanto, el flujo no debe confundirse con un procesamiento real de pagos.

## 3. Tecnologías utilizadas

| Tecnología | Uso en el proyecto |
|---|---|
| **Java 17** | Lenguaje principal del servidor. |
| **Spring Boot 4.1.1** | Configuración y ejecución de la aplicación. |
| **Spring MVC y Spring Web** | Controladores de páginas y endpoints REST. |
| **Spring Data JPA / Hibernate** | Persistencia y relación entre las entidades del dominio. |
| **H2** | Base de datos relacional local, configurada en modo archivo. |
| **Thymeleaf** | Renderizado de las vistas de administración. |
| **HTML, CSS y JavaScript** | Interfaz de usuario y comportamiento en el navegador. |
| **jQuery y Mustache** | Peticiones asíncronas y generación de vistas a partir de plantillas. |
| **Tailwind CSS** | Estilos y utilidades de presentación. |
| **Maven** | Gestión de dependencias y construcción del proyecto. |

