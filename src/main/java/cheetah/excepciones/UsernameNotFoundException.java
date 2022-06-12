package cheetah.excepciones;

public class UsernameNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public UsernameNotFoundException(String string) {
		System.out.println("No se ha encontrado el usuario.");
	}

}