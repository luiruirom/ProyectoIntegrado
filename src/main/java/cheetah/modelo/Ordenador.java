package cheetah.modelo;

import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Ordenador")
public class Ordenador {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String numSerie;
	private boolean sesion;
	private LocalTime inicioSesion;
	private LocalTime finSesion;
	private String tarifa;
	
	public int getId() {
		return id;
	}
	public String getNumSerie() {
		return numSerie;
	}
	public void setNumSerie(String numSerie) {
		this.numSerie = numSerie;
	}
	public boolean isSesion() {
		return sesion;
	}
	public void setSesion(boolean sesion) {
		this.sesion = sesion;
	}
	public LocalTime getInicioSesion() {
		return inicioSesion;
	}
	public void setInicioSesion(LocalTime inicioSesion) {
		this.inicioSesion = inicioSesion;
	}
	public LocalTime getFinSesion() {
		return finSesion;
	}
	public void setFinSesion(LocalTime finSesion) {
		this.finSesion = finSesion;
	}
	public String getTarifa() {
		return tarifa;
	}
	public void setTarifa(String tarifa) {
		this.tarifa = tarifa;
	}
	
}
