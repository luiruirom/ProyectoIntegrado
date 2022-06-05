package cheetah.interfaz;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cheetah.modelo.Sesion;

@Repository
public interface ISesion extends CrudRepository<Sesion, Integer>{
	
	@Query("SELECT inicio_Sesion FROM Sesion where id = :id")
	String findHoraInicio(@Param(value = "id") int id);
	
	@Query("SELECT fin_Sesion FROM Sesion where id = :id")
	LocalDateTime findHoraFin(@Param(value = "id") int id);
	
	@Query("SELECT id FROM Sesion s where s.num_Serie = :num_Serie AND s.fin_Sesion = NULL")
	public int findOrdenadorSesionActiva(@Param(value = "num_Serie") String numSerie);
	
	@Query("SELECT COUNT(*) FROM Sesion")
	public int nextId();
		
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO Sesion s (s.id, s.inicio_Sesion, s.num_Serie, s.coste_Total) VALUES (:id, :inicio_Sesion, :num_Serie, '0')", nativeQuery = true)
	public void iniciarSesion(@Param(value = "id") int id, @Param(value = "inicio_Sesion") LocalDateTime horaActual, @Param(value = "num_Serie") String numSerie);
	
	@Transactional
	@Modifying
	@Query("UPDATE Sesion s SET s.fin_Sesion = :fin_Sesion WHERE s.num_Serie = :num_Serie AND s.fin_Sesion = NULL")
	public void cerrarSesion(@Param(value = "fin_Sesion") LocalDateTime finSesion, @Param(value = "num_Serie") String numSerie);
	
	//Ordenador más usado
	
	//Dinero total en caja
	
	

}
