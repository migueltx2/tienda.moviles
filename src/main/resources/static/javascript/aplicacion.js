

    //Variables globales
    nombre_login = "" //variable que indica el nombre del usuario si se ha identificado


    // Parte de descarga de plantillas html

    //plantilla_inicio = $.get("plantillas_html/inicio.html")
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


    // Parte de atencion a enlaces

    function limpiarClasesContenedor() {
    return $("#contenedor").removeClass()
    //"grid grid-cols-5 xl:grid-cols-5 sm:grid-cols-2 flex-1 flex items-center justify-center w-full max-w-5xl mx-auto p-6"

}

    $("#inicio").click(
    function () {
    limpiarClasesContenedor().html(plantilla_inicio)
})
    titulo_a_buscar = "" //Titulo en el buscador
    comienzo_resultados = 0

    function mostrarMoviles() {
    //Primero pedimos el json de moviles
    $.getJSON("restMoviles/listar",{ titulo: titulo_a_buscar, comienzo: comienzo_resultados }).done(
        function (json) {
            console.log(json)
            //vamos a usar el json para listar los productos
            resultadoMustache = Mustache.render(plantilla_productos, json.moviles)
            totalMoviles = json.totalMoviles

            limpiarClasesContenedor()
                .addClass("mx-auto grid w-full max-w-screen-2xl grid-cols-1 gap-6 p-4 sm:grid-cols-2 lg:grid-cols-3 2xl:grid-cols-4")
                .html(resultadoMustache)
            $("#total_resultados").html(totalMoviles)

            //Vamos a ocultar el anterior o siguiente cuando no proceda
            if (comienzo_resultados <= 0){
                //En este caso ocultamos el anterior
                $("#enlace_anterior").hide()
            } else {
                $("#enlace_anterior").show()
            }
            if ((comienzo_resultados < 10) < totalMoviles ){
                //En este casi si queremos que aparezca siguiente
                $("#enlace_siguiente").show()
            } else {
                $("#enlace_siguiente").hide()
            }


            //Para no perder el titulo:
            $("#titulo_buscador").val(titulo_a_buscar)
            $("#titulo_buscador").focus()
            //Una vez volcado el contenido voy a decir como funcionan el buscado, el anterior/siguiente y el span de total de resultados
            $("#titulo_buscador").keyup(
                function (){
                    titulo_a_buscar = $(this).val()
                    mostrarMoviles()
                    comienzo_resultados = 0
                }
            )//End keyup
            $("#enlace_anterior").click(
                function (){
                    comienzo_resultados -= 10
                    mostrarMoviles()
                }
            ) //end click anterior
            $("#enlace_siguiente").click(
                function (){
                    comienzo_resultados += 10
                    mostrarMoviles()
                }
            ) //end click siguiente

        }
    )
} //end mostrarMoviles

    function mostrar_detalles(id) {
    $.getJSON("restMoviles/obtener-movil",
        {
            id: id
        }
    ).done(
        function (json) {
            console.log(json)
            //alert("json recibido, vamos a usar una plantilla de plantillas_html para mostrarlo")
            resultadoHtml = Mustache.render(plantilla_detalles_producto, json)
            limpiarClasesContenedor()
                .addClass("flex-1 flex items-center justify-center")
                .html(resultadoHtml)
        }
    )//end done
}

    function mostrar_formulario_registro_usuario (){
    limpiarClasesContenedor()
        .addClass("flex-1 flex items-center justify-center")
        .html(plantilla_registro_usuario)

    $("#form_registro_usuario").submit(
    function(e){
    //codigo a ejecutar cuando se haga el submit del form
    e.preventDefault() //Para evitar  que el boton del form refresque la pagina
    //Validar los datos del form aqui

    //Lo normal es ir recogiendo campo a campo pero tenemos una cosa llamada FormData para obtener todo de una sola vez
    formulario = document.forms[0]
    formData = new FormData(formulario)
    console.log("BIEN, YA TENGO TODA LA INFORMACION INTRODUCIDA")
    console.log(Object.fromEntries(formData))
    //Mandar este formData a un servicio REST (UsuariosREST)
    $.ajax("restUsuarios/registrarUsuario", {
    type : "POST",
    data: formData,
    cache: false,
    contentType: false,
    processData: false,
    success: function (res){
    alert(res)
    confetti()
}

})

}
    )
}

    $("#moviles").click(
    mostrarMoviles
    )


    //Por defecto cuando se acceda a la web te muestre los productos
    mostrarMoviles()

    $("#registrarme, #registrarmeMovil").click(
    mostrar_formulario_registro_usuario
    )

    $("#identificarme, #identificarmeMovil").click(
    mostrar_login
    )

    function mostrar_login(){
    limpiarClasesContenedor()
        .addClass("flex-1 flex items-center justify-center")
        .html(plantilla_login_usuario)

    //Vamos a comprobar si hay alguna cookie con la info de identificacion
    // Y la cargamos, mucho cuidado con guardar contraseñas o informacion personal sin encriptar

    if ( typeof(Cookies.get("email")) != "undefined"){
        $("#email").val(Cookies.get("email"))
    }

    if ( typeof(Cookies.get("pass")) != "undefined"){
        $("#pass").val(Cookies.get("pass"))
    }

    //Despues de mostrar la plantilla html de indentificar usuario
    //Vamos a decir que hace cuando el usuario haga click en identificarme
    $("#form_login").submit(
    function (e){
    e.preventDefault()
    $.post("restUsuarios/identificarUsuario",
{
    email: $("#email").val(),
    pass: $("#pass").val()
}
    ).done(function (res){
    nombre_login = res.split(",")[1]
    //aprovechamos y listamos productos
    alert("Identificado correctamente, ya puedes agregar productos al carrito");

    //Una vez identificado correctament
    //Miramos si la casilla recordar datos esta marcada
    if ( $("#recordar_pass").prop("checked")){
        //Guardar los datos de accesso en una cookie
        //Cookie no es mas que un archivo que guarda el navegador
        //Para cada sitio web
        Cookies.set("email", $("#email").val(), { expires: 100 })
        Cookies.set("pass", $("#pass").val(), { expires: 100 })
    }


    mostrarMoviles()
    $("#usuarioActual").html("Bienvenido, " + nombre_login)
    $("#registrarme, #identificarme").addClass("hidden")
    $("#carrito, #pedidos, #datos, #cerrar_sesion").removeClass("hidden")

})
}
    )
}

    function agregarProductoCarrito(idProducto){
    if ( nombre_login == ""){
    alert("Identificate primero")
    return
}

    //alert("Agregar producto de id: " + idProducto + " al carrito de " + nombre_login)
    //Mandar a un servicio rest la ID y la cantidad del producto
    cantidad = 1 //para hacer pruebas
    $.post("restCarrito/agregarProducto", {
    id: idProducto,
    cantidad: 1
}).done(function (res){
    alert(res)
})
}

    function eliminarProductoCarrito(idProducto){

}

    function mostrar_productos_carrito(){
    $.getJSON("restCarrito/obtenerProductos").done(
        function(res){
            //console.log("json recibido: ")
            //console.log(res)
            resultadoHtml = Mustache.render(plantilla_carrito, res)
            limpiarClasesContenedor()
                .addClass("flex-1 w-full max-w-5xl mx-auto p-6")
                .html(resultadoHtml)
            $("#realizar_pedido").click(
                mostrar_check_out_paso_1
            )
        }
    )
}

    function mostrar_check_out_paso_1(){
    limpiarClasesContenedor().html(plantilla_check_out_1)
    $("#aceptar_paso_1").click(
    enviar_datos_paso1
    )
}

    function enviar_datos_paso1(){
    //Obtener los valores introducidos
    //Mandarlos a PedidosREST
    nombreCompleto = $("#campo_nombre").val()
    direccion = $("#campo_direccion").val()
    provincia = $("#campo_provincia").val()
    codigo_postal = $("#campo_codigo_postal").val()
    ciudad = $("#campo_ciudad").val()
    correo_electronico = $("#campo_correo_electronico").val()

    //TO-DO Validaciones

    $.post("restPedido/procesarPaso1",
{
    nombre: nombreCompleto,
    direccion: direccion,
    provincia: provincia,
    codigoPostal: codigo_postal,
    ciudad: ciudad,
    correo_electronico: correo_electronico
}
    ).done(function(res){
    //alert("El servidor responde: " + res)
    if (res == "Ok"){
    limpiarClasesContenedor().html(plantilla_check_out_2)
    $("#aceptar_paso_2").click(
    enviar_datos_paso2
    )
} else {
    alert(res)
}
})
}

    function enviar_datos_paso2(){
    tipo_tarjeta = $("#tipo_tarjeta").val()
    numero_tarjeta = $("#numero_tarjeta").val()
    titular_tarjeta = $("#titular_tarjeta").val()
    cvv_tarjeta = $("#cvv_tarjeta").val()


    //Validarlos (importante to-do)

    $.post("restPedido/procesarPaso2",
{
    tarjeta: tipo_tarjeta,
    numero: numero_tarjeta,
    titular: titular_tarjeta,
    cvv: cvv_tarjeta,
}
    ).done(function(res){
    //alert(res)
    console.log(res)
    //Usar la informacion recibida para mostrar lo necesario en el paso 3 - resumen del pedido
    limpiarClasesContenedor().html(Mustache.render(plantilla_check_out_4, res))
    $("#boton_confirmar_pedido").click(
    enviar_datos_paso3
    )
})
}

    function enviar_datos_paso3(){
    $.post("restPedido/procesarPaso3").done(
        function (res){
            alert(res)
            if (res == "ok"){
                confetti()
                alert("Pedido completado! Puedes seguir comprando")
                mostrarMoviles()
            }
        }
    )
}



    $("#carrito").click(
    function () {
    mostrar_productos_carrito()
}
    )
    $("#pedidos").click(
    function () {
    alert("pedidos")
}
    )
    $("#datos").click(
    function () {
    alert("datos del usuario")
}
    )
    $("#cerrar_sesion").click(
    function () {
    alert("cierre de sesion")
        $.post("restUsuarios/cerrarSesion").done(
            function (res){
                if (res == "ok"){
                    
                }
            }
        )
    }
    )
// Script boton movil
    const botonMenu = document.getElementById("botonMenu");
    const menuMovil = document.getElementById("menuMovil");

    botonMenu.addEventListener("click", function () {

    menuMovil.classList.toggle("hidden");

});