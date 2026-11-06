# Tienda de Móviles

Aplicación web de comercio electrónico para la gestión y venta de teléfonos móviles. Proyecto desarrollado para la asignatura **Cliente / Servidor** (2º GS).

## Descripción del Proyecto

La aplicación combina una interfaz web responsiva con servicios REST y almacenamiento en base de datos relacional, estructurada en dos áreas principales:

* **Zona pública:** Permite explorar el catálogo, realizar búsquedas, seleccionar idioma (español, inglés y francés), gestionar el carrito de compra y completar el proceso de checkout en varios pasos (envío, pago, preferencia y confirmación).
* **Zona de administración:** Área protegida mediante sesión e interceptores para la gestión completa (CRUD) de productos, categorías, usuarios y actualización del estado de los pedidos.

## Tecnologías Utilizadas

### Backend y Base de Datos
* **Java 17:** Lenguaje principal de desarrollo.
* **Spring Boot (v4.1.1):** Framework de aplicación.
* **Spring MVC / Web:** Controladores de páginas y endpoints REST.
* **Spring Data JPA / Hibernate:** Persistencia y mapeo objeto-relacional.
* **H2 Database:** Base de datos relacional local en modo archivo.

### Frontend
* **HTML5 & Tailwind CSS:** Estructura y diseño de la interfaz.
* **JavaScript / jQuery / Mustache:** Peticiones asíncronas y renderizado dinámico de plantillas en el cliente.
* **Thymeleaf:** Motor de plantillas para las vistas administrativas del servidor.

### Construcción
* **Maven:** Gestión de dependencias y empaquetado del proyecto.

## Estructura de la Información de Productos

Cada producto registrado en el catálogo contiene la siguiente información detallada:
* Nombre y modelo
* Almacenamiento interno y memoria RAM
* Peso
* Precio
* Categoría
* Imagen descriptiva
