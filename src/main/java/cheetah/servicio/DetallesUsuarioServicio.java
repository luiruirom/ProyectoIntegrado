
package cheetah.servicio;

import java.util.ArrayList;
import java.util.List;

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
		List grantList = new ArrayList();
		for (Authority authority : appUser.getAuthority()) {
			// ROLE_USER, ROLE_ADMIN,..
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getAuthority());
			grantList.add(grantedAuthority);
		}

		// Crear El objeto UserDetails que va a ir en sesion y retornarlo.
		UserDetails user = (UserDetails) new User(appUser.getUsername(), appUser.getPassword(), grantList);
		return user;
	}
}