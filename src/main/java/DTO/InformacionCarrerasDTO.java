package DTO;

public class InformacionCarrerasDTO {
	
	private String nombreCarrera;
	private String nombreEstudiante;
	private String apellidoEstudiante;
	private boolean graduado;
	private int antiguedad;

	public InformacionCarrerasDTO() {
		super();
	}

	public InformacionCarrerasDTO(String nombreCarrera, String nombreEstudiante, String apellidoEstudiante,
			boolean graduado, int antiguedad) {
		super();
		this.nombreCarrera = nombreCarrera;
		this.nombreEstudiante = nombreEstudiante;
		this.apellidoEstudiante = apellidoEstudiante;
		this.graduado = graduado;
		this.antiguedad = antiguedad/365;
	}

	@Override
	public String toString() {
		return "InformacionCarrerasDTO [nombreCarrera=" + nombreCarrera + ", nombreEstudiante=" + nombreEstudiante
				+ ", apellidoEstudiante=" + apellidoEstudiante + ", graduado=" + graduado + ", antiguedad=" + antiguedad
				+ "]";
	}

	public String getNombreCarrera() {
		return nombreCarrera;
	}

	public String getNombreEstudiante() {
		return nombreEstudiante;
	}

	public String getApellidoEstudiante() {
		return apellidoEstudiante;
	}

	public boolean isGraduado() {
		return graduado;
	}

	public int getAntiguedad() {
		return antiguedad;
	}

}
