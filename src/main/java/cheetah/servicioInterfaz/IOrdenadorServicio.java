package cheetah.servicioInterfaz;

import java.util.List;
import java.util.Optional;

import cheetah.modelo.Ordenador;

public interface IOrdenadorServicio {
	public List<Ordenador>listar();
	public Optional<Ordenador>listarId(int id);
	public void save(Ordenador o);
	public void delete(int id);
	
	public int findIdByNumSerie(String numSerie);
	public List<Ordenador>listarCaros();
	public List<Ordenador>listarBaratos();
	public void crearOrdenador(Ordenador o);
	public void habilitar(int id);
	public void deshabilitar (int id);
	public void iniciarSesion(int id);
	public void cerrarSesion(int id);
	public void editarOrdenador(int id, String numSerie, String tarifa);
	
}
