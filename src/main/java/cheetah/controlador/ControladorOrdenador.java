package cheetah.controlador;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cheetah.modelo.Cliente;
import cheetah.modelo.Ordenador;
import cheetah.modelo.Sesion;
import cheetah.servicioInterfaz.IClienteServicio;
import cheetah.servicioInterfaz.IOrdenadorServicio;
import cheetah.servicioInterfaz.ISesionServicio;
import cheetah.utils.SesionAux;

@Controller
@RequestMapping
public class ControladorOrdenador {
	
	@Autowired
	private IOrdenadorServicio servicioO;
	
	@Autowired
	private ISesionServicio servicioS;
	
	@Autowired
	private IClienteServicio servicioC;
	
	@GetMapping({"/", "/index"})
	public String listar(Model model) {
		List<Ordenador>listaOrdenadoresCaros = servicioO.listarCaros();
		List<Ordenador>listaOrdenadoresBaratos = servicioO.listarBaratos();
		model.addAttribute("listaOrdenadoresCaros", listaOrdenadoresCaros);
		model.addAttribute("listaOrdenadoresBaratos", listaOrdenadoresBaratos);
		return "index";
	}
	
	@GetMapping("/loggedIndex")
	public String listarLogin(Model model) {
		List<Ordenador>listaOrdenadoresCaros = servicioO.listarCaros();
		List<Ordenador>listaOrdenadoresBaratos = servicioO.listarBaratos();
		model.addAttribute("listaOrdenadoresCaros", listaOrdenadoresCaros);
		model.addAttribute("listaOrdenadoresBaratos", listaOrdenadoresBaratos);
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
		return "admin/formCreacionOrdenador";
	}
	
	@PostMapping("/crearOrdenador")
	public String crearOrdenador(Ordenador o, Model model) {
		if (o.isValid(o)) {
			servicioO.crearOrdenador(o);
		} else {
			System.out.println("Intento fallido");
		}
		return "redirect:/loggedIndex";
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
	public String encender(@PathVariable int id, Model model){
		Optional<Ordenador> ordenador = servicioO.listarId(id);
		Sesion sesion = new Sesion();
		model.addAttribute("sesion", sesion);
		model.addAttribute("ordenador", ordenador);
		return "admin/formInicioSesion";	
	}
	
	@PostMapping("/saveEncendido")
	public String saveEncendido(String numSerie, Sesion s) {
		s.setNum_Serie(numSerie);
		int id = servicioO.findIdByNumSerie(s.getNum_Serie());
		servicioO.iniciarSesion(id);
		servicioS.iniciarSesion(id, s.getUsuario_Reserva());
		return "redirect:/loggedIndex";
	}
	
	@GetMapping("/apagar/{id}")
	public String apagar(@PathVariable int id){
		for (Cliente c : servicioC.listar()) {
			if (servicioS.getUsername(id).equals(c.getUsername())) {
				double saldo = c.getSaldo() - servicioS.findCosteSesion(id);
				c.setSaldo(saldo);
			}
		}
		servicioO.cerrarSesion(id);
		servicioS.cerrarSesion(id);
		return "redirect:/loggedIndex";	
	}
	
	@GetMapping("/apagarGlobal")
	public String apagarGlobal(){
		List<Ordenador>listaOrdenadores = servicioO.listar();
		for (Ordenador ordenador: listaOrdenadores) {
			if(ordenador.isSesion()) {
				for (Cliente c : servicioC.listar()) {
					if (servicioS.getUsername(ordenador.getId()).equals(c.getUsername())) {
						double saldo = c.getSaldo() - servicioS.findCosteSesion(ordenador.getId());
						c.setSaldo(saldo);
					}
				}
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
	public String saveReserva(String numSerie, Sesion s) {
		s.setNum_Serie(numSerie);
		int id = servicioO.findIdByNumSerie(s.getNum_Serie());
		if (s.isValid(s)){
			servicioO.iniciarSesion(id);
			servicioS.reservarSesion(LocalDateTime.parse(s.getInicioSesion()), id, s.getUsuario_Reserva());
		} else {
			System.out.println("La fecha está fuera del horario de la tienda");
		}
		return "redirect:/index";
	}
	
	@GetMapping("/informesUso")
	public String informeUso(Model model){
		SesionAux nextSesion = new SesionAux();
		List<SesionAux> listaSesionesAux = new ArrayList<SesionAux>();
		Double dineroCaja = servicioS.dineroTotal();
		List<String>listaSesiones = servicioS.listarSesiones();
		for(int i = 0; i < listaSesiones.size(); i++) {
			nextSesion = new SesionAux();
			nextSesion.sesionParser(listaSesiones.get(i));
			nextSesion.setTiempoMedio(servicioS.getMediaByNumSerie(nextSesion.getNumSerie()).toString()) ;
			listaSesionesAux.add(nextSesion); 
		}
		model.addAttribute("dineroCaja", dineroCaja);
		model.addAttribute("listaSesiones", listaSesionesAux);
		return "admin/informesUso";	
	}
}
