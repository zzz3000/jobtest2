package org.zzz.jt.security;

import java.io.IOException;
import java.net.http.HttpHeaders;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.zzz.jt.data.User;
import org.zzz.jt.data.UserWithDetails;
import org.zzz.jt.repository.UserRepository;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

	public static final String AUTH_HEADER ="Authorization";
	
	private final JwtTokenUtil jwtTokenUtil;

	
	private final UserRepository userRepo;

	@Autowired
	public JwtTokenFilter(JwtTokenUtil jwtTokenUtil, UserRepository userRepo) {
		this.jwtTokenUtil = jwtTokenUtil;
		this.userRepo = userRepo;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		// Get authorization header and validate

		// TODO
		final String header = request.getHeader(AUTH_HEADER);

		if (StringUtils.isEmpty(header) || !header.startsWith("Bearer ")) {
			chain.doFilter(request, response);
			return;
		}

		// Get jwt token and validate
		final String token = header.split(" ")[1].trim();
		if (!jwtTokenUtil.validateToken(token)) {
			chain.doFilter(request, response);
			return;
		}

		User user = userRepo.getById(jwtTokenUtil.getUsername(token));
		
		//User user = userRepo.getByName(jwtTokenUtil.getUsername(token)) .orElse(null);
		
		UserDetails userDetails = new UserWithDetails(user);

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
				userDetails == null ? List.of() : userDetails.getAuthorities());

		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
	}

}
