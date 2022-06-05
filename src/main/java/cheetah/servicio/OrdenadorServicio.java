package cheetah.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cheetah.interfaz.IOrdenador;
import cheetah.modelo.Ordenador;
import cheetah.servicioInterfaz.IOrdenadorServicio;

@Service
public class OrdenadorServicio implements IOrdenadorServicio {

	@Autowired
	private IOrdenador data;

	@Override
	public List<Ordenador> listar() {
		return (List<Ordenador>) data.findAll();
	}

	@Override
	public Optional<Ordenador> listarId(int id) {
		return data.findById(id);
	}

	@Override
	public void save(Ordenador o) {
		data.save(o);
	}

	@Override
	public void delete(int id) {
		data.deleteById(id);
	} 
	
	@Override
	public int findIdByNumSerie(String numSerie) {
		return data.findIdByNumSerie(numSerie);
	}
	
	@Override
	public void habilitar(int id) {
		data.habilitar(id, true);		
	}

	@Override
	public void deshabilitar(int id) {
		data.deshabilitar(id, false);	
	}
	
	@Override
	public void iniciarSesion(int id) {
		data.iniciarSesion(id, true);
	}

	@Override
	public void cerrarSesion(int id) {
		data.cerrarSesion(id, false);
	}
	
	@Override
	public void editarOrdenador(int id, String numSerie, String tarifa) {
		data.editarOrdenador(id, numSerie, tarifa);	
	}
	
}
