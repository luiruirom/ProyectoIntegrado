package cheetah.interfaz;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cheetah.modelo.Usuario;

@Repository
public interface IUsuario extends CrudRepository<Usuario, Long>{
	public Optional<Usuario> findByUsername(String username);

}
