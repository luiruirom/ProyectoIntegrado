package cheetah.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeneradorContraseñas {
	public static void main(String[] args) {
		
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(5);

		System.out.println(bCryptPasswordEncoder.encode("admin"));
	}
}
