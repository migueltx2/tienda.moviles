

// Estado de la sesion y del listado
nombre_login = "" // Nombre del usuario identificado.
titulo_a_buscar = "" // Titulo en el buscador.
comienzo_resultados = 0

// Carga de plantillas HTML
// plantilla_inicio = $.get("plantillas_html/inicio.html")
$.get(`plantillas_html_${codigo_idioma}/inicio.html`).done(function (res) {
    plantilla_inicio = res
})
$.get(`plantillas_html_${codigo_idioma}/listado_productos.html`).done(function (res) {
    plantilla_productos = res
})
$.get(`plantillas_html_${codigo_idioma}/detalles_producto.html`).done(function (res) {
    plantilla_detalles_producto = res
})
$.get(`plantillas_html_${codigo_idioma}/registro_usuario.html`).done(function (res) {
    plantilla_registro_usuario = res
})
$.get(`plantillas_html_${codigo_idioma}/identificar_usuario.html`).done(function (res) {
    plantilla_login_usuario = res
})
$.get(`plantillas_html_${codigo_idioma}/carrito.html`).done(function (res) {
    plantilla_carrito = res
})
$.get(`plantillas_html_${codigo_idioma}/check_out_1.html`).done(function (res) {
    plantilla_check_out_1 = res
})
$.get(`plantillas_html_${codigo_idioma}/check_out_2.html`).done(function (res) {
    plantilla_check_out_2 = res
})
$.get(`plantillas_html_${codigo_idioma}/check_out_3.html`).done(function (res) {
    plantilla_check_out_3 = res
})
$.get(`plantillas_html_${codigo_idioma}/check_out_4.html`).done(function (res) {
    plantilla_check_out_4 = res
})

// Utilidades de presentacion
function limpiarClasesContenedor() {
    return $("#contenedor").removeClass()
    //"grid grid-cols-5 xl:grid-cols-5 sm:grid-cols-2 flex-1 flex items-center justify-center w-full max-w-5xl mx-auto p-6"
}

// Catalogo de moviles
function mostrarMoviles() {
    // Primero pedimos el JSON de moviles.
    $.getJSON("restMoviles/listar", {
        titulo: titulo_a_buscar,
        comienzo: comienzo_resultados
    }).done(function (json) {
        console.log(json)

        // Renderizamos y mostramos los productos.
        resultadoMustache = Mustache.render(plantilla_productos, json.moviles)
        totalMoviles = json.totalMoviles

        limpiarClasesContenedor()
            .addClass("mx-auto grid w-full max-w-screen-2xl grid-cols-1 gap-6 p-4 sm:grid-cols-2 lg:grid-cols-3 2xl:grid-cols-4")
            .html(resultadoMustache)
        $("#total_resultados").html(totalMoviles)

        // Mostramos u ocultamos los controles de paginacion.
        if (comienzo_resultados <= 0) {
            $("#enlace_anterior").hide()
        } else {
            $("#enlace_anterior").show()
        }
        if ((comienzo_resultados < 10) < totalMoviles) {
            $("#enlace_siguiente").show()
        } else {
            $("#enlace_siguiente").hide()
        }

        // Conservamos el termino de busqueda y conectamos sus controles.
        $("#titulo_buscador").val(titulo_a_buscar)
        $("#titulo_buscador").focus()
        $("#titulo_buscador").keyup(function () {
            titulo_a_buscar = $(this).val()
            mostrarMoviles()
            comienzo_resultados = 0
        })
        $("#enlace_anterior").click(function () {
            comienzo_resultados -= 10
            mostrarMoviles()
        })
        $("#enlace_siguiente").click(function () {
            comienzo_resultados += 10
            mostrarMoviles()
        })
    })
}

function mostrar_detalles(id) {
    $.getJSON("restMoviles/obtener-movil", {
        id: id
    }).done(function (json) {
        console.log(json)
        // alert("json recibido, vamos a usar una plantilla de plantillas_html para mostrarlo")
        resultadoHtml = Mustache.render(plantilla_detalles_producto, json)
        limpiarClasesContenedor()
            .addClass("flex-1 flex items-center justify-center")
            .html(resultadoHtml)
    })
}

// Registro e identificacion de usuarios
function mostrar_formulario_registro_usuario() {
    limpiarClasesContenedor()
        .addClass("flex-1 flex items-center justify-center")
        .html(plantilla_registro_usuario)

    $("#form_registro_usuario").submit(function (e) {
        // Evitamos que el envio del formulario refresque la pagina.
        e.preventDefault()

        // FormData recoge todos los campos del formulario.
        formulario = document.forms[0]
        formData = new FormData(formulario)
        console.log("BIEN, YA TENGO TODA LA INFORMACION INTRODUCIDA")
        console.log(Object.fromEntries(formData))

        $.ajax("restUsuarios/registrarUsuario", {
            type: "POST",
            data: formData,
            cache: false,
            contentType: false,
            processData: false,
            success: function (res) {
                alert(res)
                confetti()
            }
        })
    })
}

function mostrar_login() {
    limpiarClasesContenedor()
        .addClass("flex-1 flex items-center justify-center")
        .html(plantilla_login_usuario)

    // Comprobamos si hay cookies con la informacion de identificacion.
    // No se deben guardar contrasenas o informacion personal sin cifrar.
    if (typeof (Cookies.get("email")) != "undefined") {
        $("#email").val(Cookies.get("email"))
    }

    if (typeof (Cookies.get("pass")) != "undefined") {
        $("#pass").val(Cookies.get("pass"))
    }

    $("#form_login").submit(function (e) {
        e.preventDefault()
        $.post("restUsuarios/identificarUsuario", {
            email: $("#email").val(),
            pass: $("#pass").val()
        }).done(function (res) {
            nombre_login = res.split(",")[1]
            alert("Identificado correctamente, ya puedes agregar productos al carrito")

            // Guardamos los datos de acceso si se marco la casilla correspondiente.
            if ($("#recordar_pass").prop("checked")) {
                Cookies.set("email", $("#email").val(), { expires: 100 })
                Cookies.set("pass", $("#pass").val(), { expires: 100 })
            }

            mostrarMoviles()
            $("#usuarioActual").html("Bienvenido, " + nombre_login)
            $("#registrarme, #identificarme").addClass("hidden")
            $("#carrito, #pedidos, #datos, #cerrar_sesion").removeClass("hidden")
        })
    })
}

// Carrito
function agregarProductoCarrito(idProducto, boton) {
    const cantidad = Number($(boton).siblings(".cantidad").val());
    if (nombre_login == "") {
        alert("Identificate primero")
        return
    }

    // Mandamos al servicio REST la ID y la cantidad del producto.
    $.post("restCarrito/agregarProducto", {
        id: idProducto,
        cantidad: cantidad
    }).done(function (res) {
        alert(res)
    })
}

function eliminarProductoCarrito(idProducto) {


    $.post("restCarrito/borrarProducto", {
        id: idProducto
    }).done(function () {
        mostrar_productos_carrito()
    }).fail(function () {
        alert("No se pudo eliminar el producto del carrito. Inténtalo de nuevo.")
    })
}

function mostrar_productos_carrito() {
    $.getJSON("restCarrito/obtenerProductos").done(function (res) {
        // console.log("json recibido: ")
        // console.log(res)
        resultadoHtml = Mustache.render(plantilla_carrito, res)
        limpiarClasesContenedor()
            .addClass("flex-1 w-full max-w-5xl mx-auto p-6")
            .html(resultadoHtml)
        $("#realizar_pedido").click(mostrar_check_out_paso_1)
    })
}

// Proceso de compra
function mostrar_check_out_paso_1() {
    limpiarClasesContenedor().html(plantilla_check_out_1)
    $("#aceptar_paso_1").click(enviar_datos_paso1)
}

function enviar_datos_paso1() {
    // Recogemos los valores y los mandamos a PedidosREST.
    nombreCompleto = $("#campo_nombre").val()
    direccion = $("#campo_direccion").val()
    provincia = $("#campo_provincia").val()
    codigo_postal = $("#campo_codigo_postal").val()
    ciudad = $("#campo_ciudad").val()
    correo_electronico = $("#campo_correo_electronico").val()

    // TODO: Validaciones.
    $.post("restPedido/procesarPaso1", {
        nombre: nombreCompleto,
        direccion: direccion,
        provincia: provincia,
        codigoPostal: codigo_postal,
        ciudad: ciudad,
        correo_electronico: correo_electronico
    }).done(function (res) {
        // alert("El servidor responde: " + res)
        if (res == "Ok") {
            limpiarClasesContenedor().html(plantilla_check_out_2)
            $("#aceptar_paso_2").click(enviar_datos_paso2)
        } else {
            alert(res)
        }
    })
}

function enviar_datos_paso2() {
    tipo_tarjeta = $("#tipo_tarjeta").val()
    numero_tarjeta = $("#numero_tarjeta").val()
    titular_tarjeta = $("#titular_tarjeta").val()
    cvv_tarjeta = $("#cvv_tarjeta").val()

    // Validaciones pendientes.
    $.post("restPedido/procesarPaso2", {
        tarjeta: tipo_tarjeta,
        numero: numero_tarjeta,
        titular: titular_tarjeta,
        cvv: cvv_tarjeta,
    }).done(function (res) {
        // alert(res)
        console.log(res)
        limpiarClasesContenedor().html(plantilla_check_out_3)
        $("#aceptar_paso_3").click(function () {
            enviar_datos_paso3(res)
        })
    })
}

function enviar_datos_paso3(resumenPedido) {
    const eleccion = $("#preferencia_movil").val()
    if (!eleccion) {
        alert("Selecciona una preferencia para continuar")
        return
    }

    $.post("restPedido/procesarPaso3", {
        eleccion: eleccion
    }).done(function (res) {
        if (res == "ok") {
            limpiarClasesContenedor().html(Mustache.render(plantilla_check_out_4, resumenPedido))
            $("#boton_confirmar_pedido").click(enviar_datos_paso4)
        } else {
            alert(res)
        }
    }).fail(function () {
        alert("No se pudo guardar la preferencia. Inténtalo de nuevo.")
    })
}

function enviar_datos_paso4() {
    $.post("restPedido/procesarPaso4").done(function (res) {
        alert(res)
        if (res == "ok") {
            confetti()
            alert("Pedido completado! Puedes seguir comprando")
            mostrarMoviles()
        }
    }).fail(function () {
        alert("No se pudo confirmar el pedido. Inténtalo de nuevo.")
    })
}

// Inicializacion de enlaces y controles
$("#inicio").click(function () {
    limpiarClasesContenedor().html(plantilla_inicio)
})

$("#moviles").click(mostrarMoviles)

// Al acceder a la web mostramos los productos.
mostrarMoviles()

$("#registrarme, #registrarmeMovil").click(mostrar_formulario_registro_usuario)
$("#identificarme, #identificarmeMovil").click(mostrar_login)

$("#carrito").click(function () {
    mostrar_productos_carrito()
})
$("#pedidos").click(function () {
    alert("pedidos")
})
$("#datos").click(function () {
    alert("datos del usuario")
})
$("#cerrar_sesion").click(function () {
    alert("cierre de sesion")
    $.post("restUsuarios/cerrarSesion").done(function (res) {
        if (res == "ok") {
        }
    })
})

// Menu movil
const botonMenu = document.getElementById("botonMenu")
const menuMovil = document.getElementById("menuMovil")

botonMenu.addEventListener("click", function () {
    menuMovil.classList.toggle("hidden")
})