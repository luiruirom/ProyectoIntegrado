package cheetah;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import cheetah.servicio.DetallesUsuarioServicio;
import cheetah.utils.Utiles;

@Configuration
@EnableWebSecurity
public class CheetahConfig extends WebSecurityConfigurerAdapter{
	
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
    DetallesUsuarioServicio userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
				http
				.authorizeRequests()
				.antMatchers(Utiles.RESOURCES).permitAll()
				.antMatchers("/", "/index", "/reservarOrdenador/*", "/saveReserva").permitAll()
				.antMatchers("/admin*").access("hasRole('ADMIN')")
	                .anyRequest().authenticated()
	                .and()
	            .formLogin()
	                .loginPage("/login")
	                .permitAll()
	                .defaultSuccessUrl("/loggedIndex")
	                .failureUrl("/login?error=true")
	                .usernameParameter("username")
	                .passwordParameter("password")
	                .and()
	            .logout()
	                .permitAll()
	                .logoutSuccessUrl("/login?logout");		
    }
	
    
    
    //Crea el encriptador de contraseñas	
    public BCryptPasswordEncoder passwordEncoder() {
		bCryptPasswordEncoder = new BCryptPasswordEncoder(5);
		//El numero 4 representa que tan fuerte quieres la encriptacion.
		//Se puede en un rango entre 4 y 31. 
		//Si no pones un numero el programa utilizara uno aleatoriamente cada vez
		//que inicies la aplicacion, por lo cual tus contrasenas encriptadas no funcionaran bien
        return bCryptPasswordEncoder;
    }
	
    
    //Registra el service para usuarios y el encriptador de contrasena
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { 
        // Setting Service to find User in the database.
        // And Setting PassswordEncoder
        auth.userDetailsService (userDetailsService).passwordEncoder(passwordEncoder());     
    }
	
}	
