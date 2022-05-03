package cheetah.servicio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cheetah.interfaz.IOrdenador;
import cheetah.modelo.Ordenador;
import cheetah.servicioInterfaz.IOrdenadorServicio;

@Service
public class OrdenadorServicio implements IOrdenadorServicio {

	@Autowired
	private IOrdenador data;

	@Override
	public List<Ordenador> listar() {
		return (List<Ordenador>) data.findAll();
	}

	@Override
	public Optional<Ordenador> listarId(int id) {
		return data.findById(id);
	}

	@Override
	public int save(Ordenador o) {
		int res = 0;
		Ordenador ordenador = data.save(o);
		if (!ordenador.equals(null)) {
			res = 1;
		}

		return res;
	}

	@Override
	public void delete(int id) {
		data.deleteById(id);
	} 
	
	@Override
	public void iniciarSesion(int id) {
		LocalTime tiempoActual = LocalTime.now();
		data.iniciarSesion(id, true, tiempoActual);
	}

	@Override
	public void cerrarSesion(int id) {
		LocalTime tiempoActual = LocalTime.now();
		data.cerrarSesion(id, false, tiempoActual);
		crearFactura(data.findNumSerie(id),data.findTarifa(id),data.findHoraInicio(id), data.findHoraFin(id));
	}
	
	@Override
	public void crearFactura(String numSerie, String tarifa, LocalTime inicio, LocalTime fin){
		try {
			double costeTotal = Math.round(((fin.toSecondOfDay() - (inicio.toSecondOfDay()))/60)/60);
			LocalDate fechaFactura = LocalDate.now();
			LocalDateTime horasFactura = LocalDateTime.now();
			int horaFactura = horasFactura.getHour();
			int minutosFactura = horasFactura.getMinute();
			int segundosFactura = horasFactura.getSecond();
			String nombreFactura = "F:\\Luis\\dev\\spring-workspace\\CheetahApp\\src\\main\\resources\\assets\\facturas\\" + fechaFactura.toString() + "." + horaFactura + "." + minutosFactura + "." + segundosFactura +".txt";
			FileOutputStream archivoFactura = new FileOutputStream(nombreFactura);
			DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(archivoFactura));
			String factura = " ***** FACTURA ***** " + 
			"\n" + "Equipo: " + numSerie + 
			"\n" + "Hora de inicio: " + inicio.toString() + 
			"\n" + "Hora de fin: " + fin.toString() + 
			"\n" + "Tipo de tarifa: " + tarifa +
			"\n" + "*******************************" + 
			"\n" + "TOTAL: " + costeTotal;
			outStream.writeUTF(factura);
			outStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//	LocalTime hora1 = LocalTime.now(); 
//	LocalTime hora2 = LocalTime.now(); 
//	servicio.crearFactura("Nice", "alta", hora1, hora2, 1);

}
