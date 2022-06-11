package cheetah.servicio;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cheetah.interfaz.IOrdenador;
import cheetah.interfaz.ISesion;
import cheetah.modelo.Sesion;
import cheetah.servicioInterfaz.ISesionServicio;

@Service
public class SesionServicio implements ISesionServicio{
	
	@Autowired
	private ISesion dataS;
	
	@Autowired
	private IOrdenador dataO;
	
	@Override
	public List<Sesion> listar() {
		return (List<Sesion>) dataS.findAll();
	}

	@Override
	public Optional<Sesion> listarId(int id) {
		return dataS.findById(id);
	}

	@Override
	public void save(Sesion s) {
		dataS.save(s);
	}

	@Override
	public void delete(int id) {
		dataS.deleteById(id);
	} 
	
	@Override
	public String getUsername(int id) {
		String numSerieOrdenador = dataO.findNumSerie(id);
		int idSesion = dataS.findOrdenadorSesionActiva(numSerieOrdenador);
		return dataS.findUser(idSesion);
	}
	
	@Override
	public void iniciarSesion(int id, String username) {
		LocalDateTime horaActual = LocalDateTime.now();
		int nextId = dataS.nextId() + 1;
		dataS.iniciarSesion(nextId, horaActual, dataO.findNumSerie(id), username);
		dataO.iniciarSesion(id, true);
	}
	
	public void reservarSesion(LocalDateTime horaReserva, int id, String usuarioReserva) {
		int nextId = dataS.nextId() + 1;
		dataS.reservarSesion(nextId, horaReserva, dataO.findNumSerie(id), usuarioReserva);
		dataO.iniciarSesion(id, true);
	}

	@Override
	public void cerrarSesion(int id) {
		String numSerieOrdenador = dataO.findNumSerie(id);
		int idSesion = dataS.findOrdenadorSesionActiva(numSerieOrdenador);
		String horaInicio = dataS.findHoraInicio(idSesion).substring(0,10)+'T'+dataS.findHoraInicio(idSesion).substring(11);
		LocalDateTime horaActual = LocalDateTime.now();
		double costeTotal = Math.round(((horaActual.toLocalTime().toSecondOfDay() - (LocalDateTime.parse(horaInicio).toLocalTime().toSecondOfDay()))/60)/60);	
		
		dataS.cerrarSesion(horaActual, costeTotal, numSerieOrdenador);
		dataO.cerrarSesion(id, false);
		String tarifa = dataO.findTarifa(id);
		crearFactura(numSerieOrdenador, tarifa, LocalDateTime.parse(horaInicio), horaActual);
	}
	
	@Override
	public void crearFactura(String numSerie, String tarifa, LocalDateTime inicio, LocalDateTime fin){
		double costeTotal = 0;
		try {			
			costeTotal = Math.round(((fin.toLocalTime().toSecondOfDay() - (inicio.toLocalTime().toSecondOfDay()))/60)/60);
			LocalDate fechaFactura = LocalDate.now();
			LocalDateTime horasFactura = LocalDateTime.now();
			int horaFactura = horasFactura.getHour();
			int minutosFactura = horasFactura.getMinute();
			int segundosFactura = horasFactura.getSecond();
			String nombreFactura = "F:\\Luis\\dev\\spring-workspace\\CheetahApp\\src\\main\\resources\\static\\facturas\\" + numSerie + "." + fechaFactura.toString() + "." + horaFactura + "." + minutosFactura + "." + segundosFactura +".txt";
			
			if(tarifa.equals("alta")) {
				costeTotal = costeTotal*2;
			}
			
			BufferedWriter bw = new BufferedWriter(new FileWriter(nombreFactura));
			
			String factura = " ***** FACTURA ***** " + 
			"\n" + "Equipo: " + numSerie + 
			"\n" + "Hora de inicio: " + inicio.toLocalTime().toString() + 
			"\n" + "Hora de fin: " + fin.toLocalTime().withNano(0).toString() + 
			"\n" + "Tipo de tarifa: " + tarifa +
			"\n" + "*******************************" + 
			"\n" + "TOTAL: " + costeTotal;
			bw.write(factura);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Double findCosteSesion(int id) {
		String numSerieOrdenador = dataO.findNumSerie(id);
		int idSesion = dataS.findOrdenadorSesionActiva(numSerieOrdenador);
		return dataS.findCosteSesion(idSesion);
	}
	
	@Override
	public String getMediaByNumSerie(String numSerie) {
		int contadorOrdenadores = 0;
		ArrayList<Duration> listaDuraciones = new ArrayList<Duration>();
		for (Sesion ordenador : dataS.findAll()) {
			if (ordenador.getNum_Serie().equals(numSerie)) {
				listaDuraciones.add(Duration.between(LocalDateTime.parse(ordenador.getInicioSesion().substring(0,10)+'T'+ordenador.getInicioSesion().substring(11)), ordenador.getFinSesion()));
				contadorOrdenadores++;
			}
		}
		
		for(int i = 1; i < listaDuraciones.size(); i++) {
			listaDuraciones.get(0).plus(listaDuraciones.get(i));
		}
		
		String duracion = listaDuraciones.get(0).dividedBy(contadorOrdenadores).toString().substring(2);
		String parsedDuracion = duracion.replace("-", " ");
		
		System.out.println(parsedDuracion);
		
		return parsedDuracion;
	}
	
	@Override
	public List<String> listarSesiones() {
		return dataS.listarSesiones();
	}
	
	@Override
	public Double dineroTotal() {
		return dataS.dineroTotal();
	}
}
