package cheetah.interfaz;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cheetah.modelo.Cliente;

@Repository
public interface ICliente extends CrudRepository<Cliente, Integer>{

}
