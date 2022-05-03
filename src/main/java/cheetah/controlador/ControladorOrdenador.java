package cheetah.controlador;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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
	
	@GetMapping("/")
	public String listar(Model model) {
		List<Ordenador>listaOrdenadores = servicio.listar();
		model.addAttribute("listaOrdenadores", listaOrdenadores);
		return "index";
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
		return "redirect:/";
	}
	
//	@GetMapping("/newOrdenador")
//	public String agregar(Model model) {
//		model.addAttribute("ordenador", new Ordenador());
//		return "admin/formOrdenadores";
//	}
	
	@PostMapping("/saveOrdenador")
	public String save(@Valid Ordenador e, Model model) {
		servicio.save(e);
		return "redirect:/";
	}
	
	@GetMapping("/encender/{id}")
	public String encender(@PathVariable int id){
		servicio.iniciarSesion(id);
		return "redirect:/";	
	}
	
	@GetMapping("/apagar/{id}")
	public String apagar(@PathVariable int id){
		servicio.cerrarSesion(id);
		return "redirect:/";	
	}
	
	@GetMapping("/apagarGlobal")
	public String apagarGlobal(){
		List<Ordenador>listaOrdenadores = servicio.listar();
		for (Ordenador ordenador: listaOrdenadores) {
			if(ordenador.isSesion()) {
				servicio.cerrarSesion(ordenador.getId());
			}
		}
		return "redirect:/";
	}
}
