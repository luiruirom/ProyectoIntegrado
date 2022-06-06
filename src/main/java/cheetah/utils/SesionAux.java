package cheetah.utils;

public class SesionAux {
	private String numSerie;
	private double dineroPorOrdenador;
	
	public String getNumSerie() {
		return numSerie;
	}
	public void setNumSerie(String numSerie) {
		this.numSerie = numSerie;
	}
	public double getDineroPorOrdenador() {
		return dineroPorOrdenador;
	}
	public void setDineroPorOrdenador(double dineroPorOrdenador) {
		this.dineroPorOrdenador = dineroPorOrdenador;
	}
	
	public void sesionParser(String sesion) {
		setNumSerie(sesion.split(",")[0]);
		setDineroPorOrdenador(Double.parseDouble((sesion.split(",")[1])));
	}
}
