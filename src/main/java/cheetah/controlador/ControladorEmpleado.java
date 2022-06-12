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

import cheetah.modelo.Empleado;
import cheetah.servicioInterfaz.IEmpleadoServicio;

@Controller
@RequestMapping
public class ControladorEmpleado {

	@Autowired
	private IEmpleadoServicio servicio;

	@GetMapping("/listarEmpleados")
	public String listar(Model model) {
		List<Empleado> listaEmpleados = servicio.listar();
		model.addAttribute("listaEmpleados", listaEmpleados);
		return "admin/empleados";
	}

	@GetMapping("/editarEmpleados/{id}")
	public String editar(@PathVariable int id, Model model) {
		Optional<Empleado> empleado = servicio.listarId(id);
		model.addAttribute("empleado", empleado);
		return "admin/formEmpleados";
	}

	@GetMapping("/eliminarEmpleados/{id}")
	public String delete(@PathVariable int id, Model model) {
		servicio.delete(id);
		return "redirect:/listarEmpleados";
	}

	@GetMapping("/newEmpleado")
	public String agregar(Model model) {
		model.addAttribute("empleado", new Empleado());
		return "admin/formEmpleados";
	}

	@PostMapping("/saveEmpleado")
	public String save(Empleado e) {
		servicio.save(e);
		return "redirect:/listarEmpleados";
	}

}
