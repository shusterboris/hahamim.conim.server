package application.config;

import java.io.IOException;

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
import org.springframework.web.filter.OncePerRequestFilter;

import application.services.UserDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsServiceImpl jwtUserDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		final String requestTokenHeader = request.getHeader("Authorization");

		String username = null;
		String jwtToken = null;
		// токен начинается с Bearer...
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			// откусываем его, оставляем только токен
			jwtToken = requestTokenHeader.substring(7);
			try {
				// извлекаем имя пользователя из токена
				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
			} catch (IllegalArgumentException e) {
				// опа - нет имени в токена
				System.out.println("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				// вышло время действия токена
				System.out.println("JWT Token has expired");
			}
		}

		// проверяем полученный токен
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			// получаем настройки пользователя из сервиса, в нашем случае - реализация
			// возьмет его из базы
			// реализация сервиса настроена в аутентификэйшн менеджере
			UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

			// если токен валидный, создаем
			// authentication
			if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {

				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				// вставляем Authentification в контекст, это говорит о том, что пользователь
				// аутентифицирован
				// это скажет спрингу, что аутентификация успешна
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		// передаем дальше цепочке фильтров
		chain.doFilter(request, response);
	}
}
