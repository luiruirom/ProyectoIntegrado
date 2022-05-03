package cheetah.interfaz;

import java.time.LocalTime;

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
	@Query("UPDATE Ordenador o SET o.sesion = :sesion, o.inicioSesion = :inicioSesion WHERE o.id = :id")
	void iniciarSesion(@Param(value = "id") int id, @Param(value = "sesion") boolean sesion, @Param(value = "inicioSesion") LocalTime inicioSesion);
	
	@Transactional
	@Modifying
	@Query("UPDATE Ordenador o SET o.sesion = :sesion, o.finSesion = :finSesion WHERE o.id = :id")
	void cerrarSesion(@Param(value = "id") int id, @Param(value = "sesion") boolean sesion, @Param(value = "finSesion") LocalTime finSesion);
	
	@Query("SELECT numSerie FROM Ordenador where id = :id")
	String findNumSerie(@Param(value = "id") int id);
	
	@Query("SELECT tarifa FROM Ordenador where id = :id")
	String findTarifa(@Param(value = "id") int id);
	
	@Query("SELECT inicioSesion FROM Ordenador where id = :id")
	LocalTime findHoraInicio(@Param(value = "id") int id);
	
	@Query("SELECT finSesion FROM Ordenador where id = :id")
	LocalTime findHoraFin(@Param(value = "id") int id);
}	
