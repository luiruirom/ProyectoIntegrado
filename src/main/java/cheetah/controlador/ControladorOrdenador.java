package cheetah.controlador;

import java.time.LocalDateTime;
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
import cheetah.modelo.Sesion;
import cheetah.servicioInterfaz.IOrdenadorServicio;
import cheetah.servicioInterfaz.ISesionServicio;

@Controller
@RequestMapping
public class ControladorOrdenador {
	
	@Autowired
	private IOrdenadorServicio servicioO;
	
	@Autowired
	private ISesionServicio servicioS;
	
	@GetMapping({"/", "/index"})
	public String listar(Model model) {
		List<Ordenador>listaOrdenadores = servicioO.listar();
		model.addAttribute("listaOrdenadores", listaOrdenadores);
		return "index";
	}
	
	@GetMapping("/loggedIndex")
	public String listarLogin(Model model) {
		List<Ordenador>listaOrdenadores = servicioO.listar();
		model.addAttribute("listaOrdenadores", listaOrdenadores);
		return "admin/loggedIndex";
	}
	
	@GetMapping("/editarOrdenador/{id}")
	public String editar(@PathVariable int id, Model model) {
		Optional<Ordenador>ordenador = servicioO.listarId(id);
		model.addAttribute("ordenador", ordenador);
		return "admin/formOrdenadores";
	}
	
	@GetMapping("/eliminarOrdenadores/{id}")
	public String delete(@PathVariable int id, Model model) {
		servicioO.delete(id);
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
			servicioO.save(o);
		} else {
			System.out.println("Intento fallido");
		}
		return "redirect:/loggedIndex";
	}
	
	@GetMapping("/habilitar/{id}")
	public String habilitar(@PathVariable int id){
		servicioO.habilitar(id);
		return "redirect:/loggedIndex";	
	}
	
	@GetMapping("/deshabilitar/{id}")
	public String deshabilitar(@PathVariable int id){
		servicioO.deshabilitar(id);
		return "redirect:/loggedIndex";	
	}
	
	
	@GetMapping("/encender/{id}")
	public String encender(@PathVariable int id){
		servicioO.iniciarSesion(id);
		servicioS.iniciarSesion(id);
		return "redirect:/loggedIndex";	
	}
	
	@GetMapping("/apagar/{id}")
	public String apagar(@PathVariable int id){
		servicioO.cerrarSesion(id);
		servicioS.cerrarSesion(id);
		return "redirect:/loggedIndex";	
	}
	
	@GetMapping("/apagarGlobal")
	public String apagarGlobal(){
		List<Ordenador>listaOrdenadores = servicioO.listar();
		for (Ordenador ordenador: listaOrdenadores) {
			if(ordenador.isSesion()) {
				servicioO.cerrarSesion(ordenador.getId());
				servicioS.cerrarSesion(ordenador.getId());
			}
		}
		return "redirect:/loggedIndex";
	}
	
	@GetMapping("/reservarOrdenador/{id}")
	public String reservar(@PathVariable int id, Model model) {
		Optional<Ordenador> ordenador = servicioO.listarId(id);
		Sesion sesion = new Sesion();
		model.addAttribute("sesion", sesion);
		model.addAttribute("ordenador", ordenador);
		return "admin/formReserva";
	}
	
	@PostMapping("/saveReserva")
	public String saveReserva(Sesion s, String numSerie) {
		System.out.println(s.toString());
		s.setNum_Serie(numSerie);
		int id = servicioO.findIdByNumSerie(s.getNum_Serie());
		LocalDateTime.parse(s.getInicioSesion());
		servicioO.iniciarSesion(id);
		servicioS.iniciarSesion(id);
		return "redirect:/index";
	}
}
