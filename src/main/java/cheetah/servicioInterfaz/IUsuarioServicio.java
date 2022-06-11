
package cheetah.servicioInterfaz;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import cheetah.modelo.Usuario;

public interface IUsuarioServicio {

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
	
	public List<Usuario>listar();
	public Optional<Usuario>listarId(long id);
	public void save(Usuario u);
	public void delete(long id);
	
	public void crearUser(int id, String password, String username);
	public void linkearUser(int id);
	public int findIdByUsername(String username);
	public int nextId();
}
