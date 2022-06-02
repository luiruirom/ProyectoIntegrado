package cheetah.servicioInterfaz;

import java.util.List;
import java.util.Optional;

import cheetah.modelo.Empleado;

public interface IEmpleadoServicio {
	public List<Empleado>listar();
	public Optional<Empleado> listarId(int id);
	public void save(Empleado e);
	public void delete(int id);

}
