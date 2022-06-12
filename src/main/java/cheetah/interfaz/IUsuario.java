package cheetah.interfaz;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cheetah.modelo.Usuario;

@Repository
public interface IUsuario extends CrudRepository<Usuario, Long>{
	public Optional<Usuario> findByUsername(String username);
	
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO Usuario u (u.id, u.enabled, u.password, u.username) VALUES (:id, 1, :password, :username)", nativeQuery = true)
	void crearUser(@Param(value = "id") int id, @Param(value = "password") String password, @Param(value = "username") String username);
	
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO Authorities_users a (usuario_id, authority_id) VALUES (:usuario_id, 1)", nativeQuery = true)
	void linkearUser(@Param(value = "usuario_id") int id);
	
	@Query("SELECT id FROM Usuario u where u.username = :username")
	int findIdByUsername(@Param(value = "username") String username);
	
	@Query("SELECT COUNT(*) FROM Usuario")
	int nextId();
}
