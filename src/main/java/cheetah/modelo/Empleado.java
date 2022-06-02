package cheetah.modelo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cheetah.utils.Utiles;

@Entity
@Table(name = "Empleado")
public class Empleado {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(unique=true)
	private String dni;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private int telefono;
	private String correo;
	private double salario;
	
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}
	
	public boolean isValid(Empleado e) {
		
		boolean res = false;
		Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(e.getCorreo());
		
        //Se valida el dni
		for(int i = 0; i < e.getDni().length() - 2; i++) {
			if (!Utiles.LISTA_NUMEROS.contains(Character.getNumericValue(e.getDni().charAt(i)))) {
				System.out.println("Error dni");
				return res;
			}		
		}
		
		if (!Character.isLetter(e.getDni().charAt(e.getDni().length() - 1))) {
			System.out.println("Error letra dni");
			return res;
		}
		
		//Se valida el nombre
		for(int i = 0; i < e.getNombre().length() - 1; i++) {
			if (!Character.isLetter(e.getNombre().charAt(i)) && !Character.isWhitespace(e.getNombre().charAt(i))) {
				System.out.println("Error nombre");
				return res;
			}		
		}
		
		//Se valida el primer apellido
		for(int i = 0; i < e.getApellido1().length() - 1; i++) {
			if (!Character.isLetter(e.getApellido1().charAt(i)) && !Character.isWhitespace(e.getNombre().charAt(i))) {
				System.out.println("Error apellido1");
				return res;
			}		
		}
		
		//Se valida el segundo  apellido
		for(int i = 0; i < e.getApellido2().length() - 1; i++) {
			if (!Character.isLetter(e.getApellido2().charAt(i)) && !Character.isWhitespace(e.getNombre().charAt(i))) {
				System.out.println("Error apeellido2");
				return res;
			}		
		}
		
		//Se valida el telefono
		if(e.getTelefono() != (int) e.getTelefono() || e.getTelefono() >= 1000000000 || e.getTelefono() < 600000000) {
			System.out.println("Error telefono");
			return res;
		}
		
		//Se valida el correo
		if(!mat.matches()) {
			System.out.println("Error correo");
			return res;
		}
		
		//Se valida que el salario sea un número positivo
		if(e.getSalario() <= 0) {
			System.out.println("Error salario");
			return res;
		}
		
		res = true;
		return res;
	}
}
