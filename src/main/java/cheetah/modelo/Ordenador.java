package cheetah.modelo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Ordenador")
public class Ordenador {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(unique=true)
	private String numSerie;
	private boolean sesion = false;
	private String tarifa;
	private boolean enabled = true;
	
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
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

	public String getTarifa() {
		return tarifa;
	}

	public void setTarifa(String tarifa) {
		this.tarifa = tarifa;
	}
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public boolean isValid(Ordenador o) {
		
		boolean res = false;
		Pattern patternAlta = Pattern.compile("YGO+-100[0-9][0-9]$");
		Pattern patternBaja = Pattern.compile("YGO+-200[0-9][0-9]$");
        Matcher matAlta = patternAlta.matcher(o.getNumSerie());
        Matcher matBaja = patternBaja.matcher(o.getNumSerie());
		
		//Se valida el número de serie
		if(!matAlta.matches() && !matBaja.matches()) {
			System.out.println("Error número de serie");
			return res;
		}
		
		//Se valida que la tarifa sea alta o baja
		if(!o.getTarifa().equals("alta") && !o.getTarifa().equals("baja")) {
			System.out.println("Error tarifa");
			return res;
		}
		
		res = true;
		return res;
	}

}
