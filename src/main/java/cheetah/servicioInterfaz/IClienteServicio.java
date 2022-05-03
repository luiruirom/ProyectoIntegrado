package cheetah.servicioInterfaz;

import java.util.List;
import java.util.Optional;

import cheetah.modelo.Cliente;

public interface IClienteServicio {
	public List<Cliente>listar();
	public Optional<Cliente>listarId(int id);
	public int save(Cliente c);
	public void delete(int id);
}
