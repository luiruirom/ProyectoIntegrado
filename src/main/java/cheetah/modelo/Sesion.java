package cheetah.modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Sesion")
public class Sesion {

	@Id
	private Integer id;

	@Column(columnDefinition = "Timestamp(0)")
	private String inicio_Sesion;

	@Column(columnDefinition = "Timestamp(0)")
	private LocalDateTime fin_Sesion;

	private double coste_Total;

	private String num_Serie;
	
	private String usuario_Reserva;

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
	
	public String getUsuario_Reserva() {
		return usuario_Reserva;
	}

	public void setUsuario_Reserva(String usuarioReserva) {
		this.usuario_Reserva = usuarioReserva;
	}
	
	public boolean isValid(Sesion s) {
		boolean res = true;
		System.out.println(s.getInicioSesion());
		String diaReserva = s.getInicioSesion().split("T")[0];
		String horaReserva = s.getInicioSesion().split("T")[1];
		
		for(int i = 0; i < s.getUsuario_Reserva().length() - 1; i++) {
			if (!Character.isLetter(s.getUsuario_Reserva().charAt(i))) {
				System.out.println("Usuario inválido");
				return res;
			}		
		}
		
		if(LocalDate.parse(diaReserva).isBefore(LocalDate.now()) || LocalDate.parse(diaReserva).isAfter(LocalDate.now()))
			res = false;
		if(LocalTime.parse(horaReserva).isBefore(LocalTime.of(17, 00)) || LocalTime.parse(horaReserva).isAfter(LocalTime.of(21, 00)))
			res = false;
		
		return res;
	}
	
	public boolean isSesion(Sesion s) {
		if(s.inicio_Sesion != null && s.fin_Sesion == null) {
			return true;
		}
		return false;
	}
	@Override
	public String toString() {
		return "Sesion [id=" + id + ", inicio_Sesion=" + inicio_Sesion + ", fin_Sesion=" + fin_Sesion + ", coste_Total="
				+ coste_Total + ", num_Serie=" + num_Serie + ", usuario_Reserva=" + usuario_Reserva + "]";
	}
	
}
