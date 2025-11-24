package ar.edu.utn.frba.dds.servicioUsuario.config;

import ar.edu.utn.frba.dds.servicioUsuario.filters.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    System.out.println("=== CONFIGURANDO SECURITY ===");

    http
          .csrf(AbstractHttpConfigurer::disable)
          .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
          .authorizeHttpRequests(auth -> {
            auth.requestMatchers("/api/auth", "/api/auth/refresh", "/api/agregador/hechos", "/api/agregador/solicitudes"
                    , "/api/usuarios", "/api/fuenteDinamica/hechos", "/api/usuarios/search").permitAll();
            auth.requestMatchers(HttpMethod.GET, "/api/agregador/colecciones", "/api/agregador/colecciones/{coleccionId}",
                    "/api/agregador/colecciones/{coleccionId}/hechos", "/api/agregador/usuarios/{id}/hechos",
                    "/api/agregador/categorias", "/api/agregador/hechos/{idHecho}").permitAll();
            auth.requestMatchers("/api/auth/user/roles-permisos").authenticated();
            auth.anyRequest().authenticated();
          })
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

}
