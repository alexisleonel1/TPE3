package Entidades;

import java.sql.Timestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Inscriptos {
	
	@Id
	@ManyToOne
	private Estudiante estudiante;
	
	@Id
	@ManyToOne
	private Carrera carrera;
	
	@Column
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp antiguedad;
	
	@Column
	private boolean graduado;

	public Inscriptos() {
		super();
	}
	
	public Inscriptos(Estudiante estudiante, Carrera carrera, Timestamp antiguedad, boolean graduado) {
		super();
		this.estudiante = estudiante;
		this.carrera = carrera;
		this.antiguedad = antiguedad;
		this.graduado = graduado;
	}

	public Estudiante getEstudiante() {
		return estudiante;
	}

	public Carrera getCarrera() {
		return carrera;
	}

	public Timestamp getAntiguedad() {
		return antiguedad;
	}

	public void setAntiguedad(Timestamp antiguedad) {
		this.antiguedad = antiguedad;
	}

	public boolean isGraduado() {
		return graduado;
	}

	public void setGraduado(boolean graduado) {
		this.graduado = graduado;
	}

}