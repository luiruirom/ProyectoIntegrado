package cheetah.interfaz;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cheetah.modelo.Ordenador;

@Repository
public interface IOrdenador extends CrudRepository<Ordenador, Integer>{
	
	@Transactional
	@Modifying
	@Query("UPDATE Ordenador o SET o.enabled = :enabled WHERE o.id = :id")
	void habilitar(@Param(value = "id") int id, @Param(value = "enabled") boolean enable);
	
	@Transactional
	@Modifying
	@Query("UPDATE Ordenador o SET o.enabled = :enabled WHERE o.id = :id")
	void deshabilitar(@Param(value = "id") int id, @Param(value = "enabled") boolean enable);
	
	@Transactional
	@Modifying
	@Query("UPDATE Ordenador o SET o.sesion = :sesion WHERE o.id = :id")
	void iniciarSesion(@Param(value = "id") int id, @Param(value = "sesion") boolean sesion);
	
	@Transactional
	@Modifying
	@Query("UPDATE Ordenador o SET o.sesion = :sesion WHERE o.id = :id")
	void cerrarSesion(@Param(value = "id") int id, @Param(value = "sesion") boolean sesion);
	
	@Transactional
	@Modifying
	@Query("UPDATE Ordenador o SET o.numSerie = :numSerie, o.tarifa = :tarifa WHERE o.id = :id")
	void editarOrdenador(@Param(value = "id") int id, @Param(value = "numSerie") String numSerie, @Param(value = "tarifa") String tarifa);
	
	@Query("SELECT sesion FROM Ordenador where id = :id")
	boolean isSesionById(@Param(value = "id") int id);
	
	@Query("SELECT id FROM Ordenador where numSerie = :numSerie")
	int findIdByNumSerie(@Param(value = "numSerie") String numSerie);
	
	@Query("SELECT numSerie FROM Ordenador where id = :id")
	String findNumSerie(@Param(value = "id") int id);
	
	@Query("SELECT tarifa FROM Ordenador where id = :id")
	String findTarifa(@Param(value = "id") int id);
	
	@Query("SELECT COUNT(*) FROM Ordenador")
	public int nextId();
}	
