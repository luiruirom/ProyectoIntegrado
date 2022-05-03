package cheetah.interfaz;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cheetah.modelo.Empleado;

@Repository
public interface IEmpleado extends CrudRepository<Empleado, Integer> {

}
