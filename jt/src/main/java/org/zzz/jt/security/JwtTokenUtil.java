package org.zzz.jt.security;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
public class JwtTokenUtil {

	@Value("${security.jwt.token.secret-key}")
	private String secretKey;
	
	@Value("${security.jwt.token.expire-length}")
	private long validityInMilliseconds ;


	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			throw new CustomException("Expired or invalid JWT token", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	public String createToken(int userId, List<? extends GrantedAuthority> appUserRoles) {

		Claims claims = Jwts.claims().setSubject(userId+"");
		
		/*
		claims.put("auth", appUserRoles.stream().map(s -> new SimpleGrantedAuthority(s.getAuthority()))
				.filter(Objects::nonNull).collect(Collectors.toList()));
				*/
		//TODO
		//claims.put("auth", appUserRoles);

		Date now = new Date();
		Date validity = new Date(now.getTime() + validityInMilliseconds);

		return Jwts.builder()//
				.setClaims(claims)//
				.setIssuedAt(now)//
				.setExpiration(validity)//
				.signWith(SignatureAlgorithm.HS256, secretKey)//
				.compact();
	}
	
	
	public int getUsername(String token) {
		String subject = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
		
		return Integer.parseInt(subject);
	}

}
