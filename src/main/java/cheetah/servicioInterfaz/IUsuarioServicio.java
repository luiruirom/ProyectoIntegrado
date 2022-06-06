
package cheetah.servicioInterfaz;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface IUsuarioServicio {

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
	
	public void crearUser(int id, String password, String username);
	public void linkearUser(int id);
	public int findIdByUsername(String username);
	public int nextId();
}
