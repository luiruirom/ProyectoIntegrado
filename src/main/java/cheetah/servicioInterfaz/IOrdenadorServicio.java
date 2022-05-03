package cheetah.servicioInterfaz;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import cheetah.modelo.Ordenador;

public interface IOrdenadorServicio {
	public List<Ordenador>listar();
	public Optional<Ordenador>listarId(int id);
	public int save(Ordenador o);
	public void delete(int id);
	
	public void iniciarSesion(int id);
	public void cerrarSesion(int id);
	public void crearFactura(String numSerie, String tarifa, LocalTime inicio, LocalTime fin);
}
