package cheetah.modelo;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Sesion")
public class Sesion {
	
	@Id
	private Integer id;
	
	@Column(columnDefinition="Timestamp(0)")
	private String inicio_Sesion;
	
	@Column(columnDefinition="Timestamp(0)")
	private LocalDateTime fin_Sesion;
	
	private double coste_Total;
    
	private String num_Serie;
	
	public String getInicioSesion() {
		return inicio_Sesion;
	}

	public void setInicioSesion(String inicioSesion) {
		this.inicio_Sesion = inicioSesion;
	}

	public LocalDateTime getFinSesion() {
		return fin_Sesion;
	}

	public void setFinSesion(LocalDateTime finSesion) {
		this.fin_Sesion = finSesion;
	}

	public double getCosteTotal() {
		return coste_Total;
	}

	public void setCosteTotal(double costeTotal) {
		this.coste_Total = costeTotal;
	}

	public String getNum_Serie() {
		return num_Serie;
	}

	public void setNum_Serie(String numSerie) {
		this.num_Serie = numSerie;
	}

	@Override
	public String toString() {
		return "Sesion [id=" + id + ", inicio_Sesion=" + inicio_Sesion + ", fin_Sesion=" + fin_Sesion + ", coste_Total="
				+ coste_Total + ", num_Serie=" + num_Serie + "]";
	}
	
	
	
}
