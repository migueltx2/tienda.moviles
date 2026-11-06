const regexp_nombre = /^[a-z áéíóúüñ]{2,20}$/i
const regexp_localidad = /^[a-z áéíóúüñ][a-z áéíóúüñ'-]{1,49}$/i
const regexp_direccion = /^[a-z0-9 áéíóúüñ.,#/'ºª-]{5,100}$/i
const regexp_email = /^[^\s@]+@[^\s@]+\.[^\s@]+$/

function validarCampo(valor, expresion, mensaje) {
    const texto = valor == null ? "" : String(valor).trim()
    if (expresion.test(texto)) {
        return true
    }

    alert(mensaje)
    return false
}

function validarNombre(nombre) {
    return validarCampo(nombre, regexp_nombre, "El nombre debe contener letras y espacios, de 2 a 20 caracteres.")
}

function validarApellidos(apellidos) {
    return validarCampo(apellidos, regexp_nombre, "Los apellidos deben contener letras y espacios, de 2 a 20 caracteres.")
}

function validarDireccion(direccion) {
    return validarCampo(direccion, regexp_direccion, "La dirección debe tener entre 5 y 100 caracteres y solo puede incluir letras, números y signos habituales.")
}

function validarLocalidad(localidad, campo) {
    return validarCampo(localidad, regexp_localidad, campo + " debe tener entre 2 y 50 caracteres y contener solo letras, espacios, guiones o apóstrofos.")
}

function validarCodigoPostal(codigoPostal) {
    return validarCampo(codigoPostal, /^\d{5}$/, "El código postal debe contener 5 números.")
}

function validarEmail(email) {
    const texto = email == null ? "" : String(email).trim()
    if (texto.length <= 254 && regexp_email.test(texto)) {
        return true
    }

    alert("Introduce un correo electrónico válido.")
    return false
}

function validarTelefono(telefono) {
    const texto = telefono == null ? "" : String(telefono).trim()
    const normalizado = texto.replace(/[ ()-]/g, "")
    if (/^\+?\d{7,15}$/.test(normalizado) && /^[+0-9 ()-]+$/.test(texto)) {
        return true
    }

    alert("Introduce un teléfono válido (de 7 a 15 números, con prefijo internacional opcional).")
    return false
}

function validarContrasena(contrasena) {
    const texto = contrasena == null ? "" : String(contrasena)
    if (texto.length >= 8 && texto.length <= 100) {
        return true
    }

    alert("La contraseña debe tener entre 8 y 100 caracteres.")
    return false
}

function validarTipoTarjeta(tipo) {
    if (tipo === "visa" || tipo === "master_card") {
        return true
    }

    alert("Selecciona un tipo de tarjeta.")
    return false
}

function validarNumeroTarjeta(numero) {
    const texto = numero == null ? "" : String(numero).trim()
    const normalizado = texto.replace(/[ -]/g, "")
    if (/^\d{13,19}$/.test(normalizado)) {
        return true
    }

    alert("El número de tarjeta debe contener entre 13 y 19 números.")
    return false
}

function validarTitularTarjeta(titular) {
    return validarCampo(titular, /^[a-z áéíóúüñ'-]{2,50}$/i, "El titular debe contener entre 2 y 50 caracteres y solo letras, espacios, guiones o apóstrofos.")
}

function validarCvv(cvv) {
    return validarCampo(cvv, /^\d{3,4}$/, "El CVV debe contener 3 o 4 números.")
}

function validarPreferencia(preferencia) {
    if (preferencia === "android" || preferencia === "apple") {
        return true
    }

    alert("Selecciona una preferencia para continuar.")
    return false
}