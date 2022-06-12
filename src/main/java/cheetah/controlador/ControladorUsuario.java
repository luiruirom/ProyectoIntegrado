package cheetah.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import cheetah.modelo.Usuario;
import cheetah.servicio.DetallesUsuarioServicio;

@Controller
public class ControladorUsuario {
	
	@Autowired
	DetallesUsuarioServicio servicio;
	
	@GetMapping("/login")
	public String login() {
		return "login";	
	}
	
	@GetMapping("/admin")
	public String admin() {
		return "redirect:/loggedIndex";
	}
	
	@GetMapping("/newUser")
	public String formUser(Model model) {
		Usuario usuario = new Usuario();
		model.addAttribute("usuario", usuario);
		return "admin/formUser"; 
	}
	
	@PostMapping("/saveUser")
	public String saveUser(Usuario usuario) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(5);
		int idUser = servicio.nextId();
		
		if (!usuario.isValid(usuario)){
			return "redirect:/loggedIndex";
		}
			
		usuario.setId(idUser);
		String encryptedPass =  bCryptPasswordEncoder.encode(usuario.getPassword());
		servicio.crearUser(idUser, encryptedPass, usuario.getUsername());
		servicio.linkearUser(idUser);
		return "redirect:/loggedIndex";
	}

}
