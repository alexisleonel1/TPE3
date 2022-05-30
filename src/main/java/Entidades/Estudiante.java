package Entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Estudiante {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@Column
	private String nombre;
	@Column
	private String apellido;
	@Column
	private int edad;
	@Column
	private char genero;
	@Column
	private int dni;
	@Column
	private int numLibretaUniversitaria;
	@ManyToOne
	private Ciudad ciudad;
	
	public Estudiante() {
		super();
	}
	
	public Estudiante(String nombre,String apellido, int edad, char genero,int dni,int numLibretaUniversitaria,Ciudad ciudad)
	{
		super();
		this.nombre=nombre;
		this.apellido=apellido;
		this.edad=edad;
		this.genero=genero;
		this.dni=dni;
		this.numLibretaUniversitaria=numLibretaUniversitaria;
		this.ciudad=ciudad;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public char getGenero() {
		return genero;
	}

	public void setGenero(char genero) {
		this.genero = genero;
	}

	public int getDni() {
		return dni;
	}

	public void setDni(int dni) {
		this.dni = dni;
	}

	public int getNumLibretaUniversitaria() {
		return numLibretaUniversitaria;
	}

	public void setNumLibretaUniversitaria(int numLibretaUniversitaria) {
		this.numLibretaUniversitaria = numLibretaUniversitaria;
	}
	
	public Ciudad getCiudad() {
		return ciudad;
	}
	
	public void setCiudad(Ciudad c) {
		ciudad = c;
	}

	@Override
	public String toString() {
		return "Estudiante [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", edad=" + edad + ", genero="
				+ genero + ", dni=" + dni + ", numLibretaUniversitaria=" + numLibretaUniversitaria + ", ciudad="
				+ ciudad + "]";
	}
}
