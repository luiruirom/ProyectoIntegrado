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

import cheetah.modelo.Cliente;
import cheetah.servicioInterfaz.IClienteServicio;

@Controller
@RequestMapping
public class ControladorCliente {
	
	@Autowired
	private IClienteServicio servicio;
	
	@GetMapping("/volver")
	public String volver() {
		return "redirect:index";
	}
	
	@GetMapping("/listarClientes")
	public String listar(Model model) {
		List<Cliente>listaClientes = servicio.listar();
		model.addAttribute("listaClientes", listaClientes);
		return "admin/clientes";
	}
	
	@GetMapping("/editarClientes/{id}")
	public String editar(@PathVariable int id, Model model) {
		Optional<Cliente>cliente = servicio.listarId(id);
		model.addAttribute("cliente", cliente);
		return "admin/formClientes";
	}
	
	@GetMapping("/eliminarClientes/{id}")
	public String delete(@PathVariable int id, Model model) {
		servicio.delete(id);
		return "redirect:/listarClientes";
	}
	
	@GetMapping("/newCliente")
	public String agregar(Model model) {
		model.addAttribute("cliente", new Cliente());
		return "admin/formClientes";
	}
	
	@PostMapping("/saveCliente")
	public String save(@Valid Cliente c, Model model) {
		servicio.save(c);
		return "redirect:/listarClientes";
	}
}
