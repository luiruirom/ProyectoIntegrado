
package cheetah.servicioInterfaz;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface IUsuarioServicio {

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
