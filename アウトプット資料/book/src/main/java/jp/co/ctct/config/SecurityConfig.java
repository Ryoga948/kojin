package jp.co.ctct.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable());
		
		//コードの追加
		http.authorizeHttpRequests(authorize -> {
			authorize
				.requestMatchers("/login").permitAll()
				.requestMatchers("/css/**").permitAll()
				.anyRequest().authenticated();
		});
		http.formLogin(form -> {
			form.defaultSuccessUrl("/", true)
				.loginPage("/login")
				.loginProcessingUrl("/login");
		});
		http.logout(logout -> {
			logout.logoutUrl("/logout")
				.logoutSuccessUrl("/login?logout");
		});
		
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
