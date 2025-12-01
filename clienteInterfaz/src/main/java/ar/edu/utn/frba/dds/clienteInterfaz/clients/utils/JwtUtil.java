package ar.edu.utn.frba.dds.clienteInterfaz.clients.utils;


import ar.edu.utn.frba.dds.clienteInterfaz.dtos.UsuarioDTO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
//    @Getter
    private final Key key;

    private static final long ACCESS_TOKEN_VALIDITY = 15 * 60 * 1000; // 15 min
    private static final long REFRESH_TOKEN_VALIDITY = 7 * 24 * 60 * 60 * 1000;

    public JwtUtil(@Value("${jwt.secret}") String SECRET) {
        this.key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    public String generarAccessToken(UsuarioDTO usuario) {
        return Jwts.builder()
                .setSubject(usuario.getEmail())
                .setIssuer("gestion-alumnos-server")
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY))
                .claim("idUsuario", usuario.getId())
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generarRefreshToken(UsuarioDTO usuario) {
        return Jwts.builder()
                .setSubject(usuario.getEmail())
                .setIssuer("gestion-alumnos-server")
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY))
                .claim("type", "refresh") // diferenciamos refresh del access
                .claim("idUsuario", usuario.getId())
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String validarToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Long getId(String token) {
        return Jwts.parserBuilder().setSigningKey(key)
                .build().parseClaimsJws(token).getBody()
                .get("idUsuario", Long.class);
    }
}
