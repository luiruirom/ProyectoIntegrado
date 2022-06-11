package cheetah.interfaz;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cheetah.modelo.Cliente;

@Repository
public interface ICliente extends CrudRepository<Cliente, Integer>{
	
	@Query("SELECT c.saldo FROM Cliente c WHERE c.id = :id")
	public double getSaldo(@Param(value = "id") int id);
	
	@Transactional
	@Modifying
	@Query("UPDATE Cliente c SET c.saldo = :saldo WHERE c.id = :id")
	public void addSaldo(@Param(value = "id") int id,  @Param(value = "saldo") double saldo);

}
