package cheetah.servicioInterfaz;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import cheetah.modelo.Sesion;

public interface ISesionServicio {
	public List<Sesion>listar();
	public Optional<Sesion>listarId(int id);
	public void save(Sesion s);
	public void delete(int id);
	
	public String getUsername(int id);
	
	public void reservarSesion(LocalDateTime horaReserva, int id, String usuarioReserva);
	public void iniciarSesion(int id, String nombreUsuario);
	public void cerrarSesion(int id);
	public void crearFactura(String numSerie, String tarifa, LocalDateTime inicio, LocalDateTime fin);
	
	public Double findCosteSesion(int id);
	public List<String> RecaudacionByNumSerie();
	public Double dineroTotal();
	public String getMediaByNumSerie(String numSerie);
}