package cheetah.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cheetah.interfaz.ICliente;
import cheetah.modelo.Cliente;
import cheetah.servicioInterfaz.IClienteServicio;

@Service
public class ClienteServicio implements IClienteServicio{
	
	@Autowired
	private ICliente data;
	
	@Override
	public List<Cliente> listar() {
		return (List<Cliente>) data.findAll();
	}

	@Override
	public Optional<Cliente> listarId(int id) {
		return data.findById(id);
	}

	@Override
	public int save(Cliente c) {
		int res = 0;
		Cliente cliente = data.save(c);
		if(!cliente.equals(null)) {
			res = 1;
		}
			
		return res;
	}

	@Override
	public void delete(int id) {
		data.deleteById(id);
	}

}
