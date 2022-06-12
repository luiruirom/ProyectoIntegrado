package cheetah.servicioInterfaz;

import java.util.List;
import java.util.Optional;

import cheetah.modelo.Cliente;

public interface IClienteServicio {
	public List<Cliente>listar();
	public Optional<Cliente>listarId(int id);
	public void save(Cliente c);
	public void delete(int id);
	
	public double getSaldo(int id);
	public void addSaldo(int id, double saldo);
}
