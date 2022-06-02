package cheetah.controlador;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cheetah.modelo.Ordenador;
import cheetah.servicioInterfaz.IOrdenadorServicio;

@Controller
@RequestMapping
public class ControladorOrdenador {
	
	@Autowired
	private IOrdenadorServicio servicio;
	
	@GetMapping({"/", "/index"})
	public String listar(Model model) {
		List<Ordenador>listaOrdenadores = servicio.listar();
		model.addAttribute("listaOrdenadores", listaOrdenadores);
		return "index";
	}
	
	@GetMapping("/loggedIndex")
	public String listarLogin(Model model) {
		List<Ordenador>listaOrdenadores = servicio.listar();
		model.addAttribute("listaOrdenadores", listaOrdenadores);
		return "admin/loggedIndex";
	}
	
	@GetMapping("/editarOrdenador/{id}")
	public String editar(@PathVariable int id, Model model) {
		Optional<Ordenador>ordenador = servicio.listarId(id);
		model.addAttribute("ordenador", ordenador);
		return "admin/formOrdenadores";
	}
	
	@GetMapping("/eliminarOrdenadores/{id}")
	public String delete(@PathVariable int id, Model model) {
		servicio.delete(id);
		return "redirect:/loggedIndex";
	}
	
	@GetMapping("/newOrdenador")
	public String agregar(Model model) {
		model.addAttribute("ordenador", new Ordenador());
		return "admin/formOrdenadores";
	}
	
	@PostMapping("/saveOrdenador")
	public String save(Ordenador o, Model model) {
		if (o.isValid(o)) {
			servicio.save(o);
		} else {
			System.out.println("Intento fallido");
		}
		return "redirect:/loggedIndex";
	}
	
	@GetMapping("/habilitar/{id}")
	public String habilitar(@PathVariable int id){
		servicio.habilitar(id);
		return "redirect:/loggedIndex";	
	}
	
	@GetMapping("/deshabilitar/{id}")
	public String deshabilitar(@PathVariable int id){
		servicio.deshabilitar(id);
		return "redirect:/loggedIndex";	
	}
	
	
	@GetMapping("/encender/{id}")
	public String encender(@PathVariable int id){
		servicio.iniciarSesion(id);
		return "redirect:/loggedIndex";	
	}
	
	@GetMapping("/apagar/{id}")
	public String apagar(@PathVariable int id){
		servicio.cerrarSesion(id);
		return "redirect:/loggedIndex";	
	}
	
	@GetMapping("/apagarGlobal")
	public String apagarGlobal(){
		List<Ordenador>listaOrdenadores = servicio.listar();
		for (Ordenador ordenador: listaOrdenadores) {
			if(ordenador.isSesion()) {
				servicio.cerrarSesion(ordenador.getId());
			}
		}
		return "redirect:/loggedIndex";
	}
}
