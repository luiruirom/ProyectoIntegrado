
package cheetah.servicio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cheetah.interfaz.IUsuario;
import cheetah.modelo.Authority;
import cheetah.modelo.Usuario;
import cheetah.servicioInterfaz.IUsuarioServicio;

@Service
public class DetallesUsuarioServicio implements UserDetailsService, IUsuarioServicio {

	@Autowired
	IUsuario data;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		// Buscar el usuario con el repositorio y si no existe lanzar una exepcion
		cheetah.modelo.Usuario appUser = data.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("No existe usuario"));

		// Mapear nuestra lista de Authority con la de spring security
		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
		for (Authority authority : appUser.getAuthority()) {
			// ROLE_USER, ROLE_ADMIN,..
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getAuthority());
			grantList.add(grantedAuthority);
		}

		// Crear El objeto UserDetails que va a ir en sesion y retornarlo.
		UserDetails user = (UserDetails) new User(appUser.getUsername(), appUser.getPassword(), grantList);
		return user;
	}

	@Override
	public void crearUser(int id, String password, String username) {
		data.crearUser(id, password, username);
	}

	@Override
	public void linkearUser(int id) {
		data.linkearUser(id);
	}

	@Override
	public int findIdByUsername(String username) {
		return data.findIdByUsername(username);
	}
	
	public int nextId() {
		return data.nextId() + 1;
	}
	
	@Override
	public List<Usuario> listar() {
		return (List<Usuario>) data.findAll();
	}

	@Override
	public Optional<Usuario> listarId(long id) {
		return data.findById(id);
	}

	@Override
	public void save(Usuario u) {
		data.save(u);
	}

	@Override
	public void delete(long id) {
		data.deleteById(id);
	}
}
