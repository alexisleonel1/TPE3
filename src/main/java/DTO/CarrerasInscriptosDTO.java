package DTO;

public class CarrerasInscriptosDTO {
	
	private String nombre;
	
	public CarrerasInscriptosDTO(String nombre) {
		super();
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}
	
	@Override
	public String toString() {
		return "CarrerasInscriptos [nombre=" + nombre + "]";
	}
	
}
