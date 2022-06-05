package cheetah.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControladorUsuario {
	
	@GetMapping("/login")
	public String login() {
		return "login";	
	}
	
	@GetMapping("/admin")
	public String admin() {
		return "redirect:/loggedIndex";
	}

}
