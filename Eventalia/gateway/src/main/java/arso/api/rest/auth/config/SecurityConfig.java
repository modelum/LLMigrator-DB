package arso.api.rest.auth.config;

import arso.api.rest.auth.filter.JwtRequestFilter;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final JwtRequestFilter authenticationRequestFilter;
  private final SecuritySuccessHandler successHandler;

  public SecurityConfig(
      JwtRequestFilter authenticationRequestFilter, SecuritySuccessHandler successHandler) {
    this.authenticationRequestFilter = authenticationRequestFilter;
    this.successHandler = successHandler;
  }

  @Override
  public void configure(HttpSecurity httpSecurity) throws Exception {

    httpSecurity
        .csrf()
        .disable()
        .httpBasic()
        .disable()
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .authorizeRequests()
        .antMatchers("/auth/**")
        .permitAll()
        .and()
        .oauth2Login()
        .successHandler(this.successHandler)
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    // Establece el filtro de autenticación en la cadena de filtros de seguridad
    httpSecurity.addFilterBefore(
        this.authenticationRequestFilter, UsernamePasswordAuthenticationFilter.class);
  }

    // Configuración de CORS para que podamos hacer peticiones desde el cliente
  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    // Aquí es donde se indican los orígenes permitidos.
    configuration.setAllowedOrigins(List.of("*"));
    configuration.setAllowedMethods(List.of("*"));
    configuration.setAllowedHeaders(List.of("*"));
    configuration.setExposedHeaders(List.of("Location"));
    configuration.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

}
