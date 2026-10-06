package com.miguel.tienda.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import javax.smartcardio.CardTerminal;

@Entity
public class Movil {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Size(min = 3, max = 40, message = "El nombre debe tener entre 3 y 40 caracteres")
	@NotEmpty(message = "Debes insertar un nombre")
	@Pattern(regexp = "[a-záéíóúñA-ZÁÉÍÓÚÑüÜ 0-9]+", message = "El modelo solo puede tener letras, numeros y espacios")
	private String nombre;

	@Size(min = 3, max = 40, message = "El modelo debe tener entre 3 y 40 caracteres")
	@NotEmpty(message = "Debes insertar el nombre del modelo")
	@Pattern(regexp = "[a-záéíóúñA-ZÁÉÍÓÚÑüÜ 0-9]+", message = "El modelo solo puede tener letras, numeros y espacios")
	private String modelo;

	@NotNull(message = "Debes indicar el almacenamiento")
	@Min(value = 16, message = "Valor minimo 16")
	@Max(value = 9999, message = "El valor maximo es 9999")
	private int almacenamiento;

	@NotNull(message = "Debes indicar la cantidad de RAM")
	@Min(value = 1, message = "Minimo 1")
	@Max(value = 9999, message = "El valor maximo es 9999")
	private int ram;

	@NotNull(message = "Debes indicar el peso")
	@Min(value = 100, message = "Peso minimo 100")
	@Max(value = 9999, message = "El peso maximo es 9999")
	private int peso;

	@NotNull(message = "Debes indicar el precio")
	@Min(value = 1, message = "Precio minimo 1€")
	@Max(value = 9999, message = "El precio maximo es 9999")
	private int precio;

	//Una forma muy comoda e interestante para hcaer validaciones es usar un estandar que se llama JSR 303
	//Se basa en poner anotaciones de validacion a cada campo


	//Version
	private String sku; //stock keeping unit

	//Este campo va a ser solo para gestionar la subida de archivo, el input type file
	//Esto es solo para recibir facilmente la imagen del form
	//@Transient es para decir que el campo sea ignorado de cara a la base de datos
	@Transient
	private MultipartFile fotoSubida;

	//El siguente campo es para almacenar la imagen en base de datos
	@Lob //para tipos de columna, p.ej blob o clob, para guardar imagenes/archivos/arrays de bytes
	private byte[] imagenPortada;

	@ManyToOne
	private Categoria categoria;

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Movil() {
	}

	public Movil(long id, String nombre, String modelo, int almacenamiento, int ram, int peso, int precio) {
		this.id = id;
		this.nombre = nombre;
		this.modelo = modelo;
		this.almacenamiento = almacenamiento;
		this.ram = ram;
		this.peso = peso;
		this.precio = precio;
	}

	public Movil(String nombre, String modelo, int almacenamiento, int ram, int peso, int precio, String sku, MultipartFile fotoSubida, byte[] imagenPortada) {
		this.nombre = nombre;
		this.modelo = modelo;
		this.almacenamiento = almacenamiento;
		this.ram = ram;
		this.peso = peso;
		this.precio = precio;
		this.sku = sku;
		this.fotoSubida = fotoSubida;
		this.imagenPortada = imagenPortada;
		this.categoria = categoria;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public int getAlmacenamiento() {
		return almacenamiento;
	}

	public void setAlmacenamiento(int almacenamiento) {
		this.almacenamiento = almacenamiento;
	}

	public int getRam() {
		return ram;
	}

	public void setRam(int ram) {
		this.ram = ram;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	public MultipartFile getFotoSubida() {
		return fotoSubida;
	}

	public void setFotoSubida(MultipartFile fotoSubida) {
		this.fotoSubida = fotoSubida;
	}

	public byte[] getImagenPortada() {
		return imagenPortada;
	}

	public void setImagenPortada(byte[] imagenPortada) {
		this.imagenPortada = imagenPortada;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	@Override
	public String toString() {
		return "Movil{" +
				"id=" + id +
				", nombre='" + nombre + '\'' +
				", modelo='" + modelo + '\'' +
				", almacenamiento=" + almacenamiento +
				", ram=" + ram +
				", peso=" + peso +
				", precio=" + precio +
				'}';
	}
}
