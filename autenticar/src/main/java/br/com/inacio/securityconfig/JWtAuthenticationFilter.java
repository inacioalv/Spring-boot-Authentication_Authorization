package br.com.inacio.securityconfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.inacio.Entidade.CredenciaisAcesso;


public class JWtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private AuthenticationManager authenticationManager;
	
	private JWTUtil jwtutil;
	
	public JWtAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
		setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
		this.authenticationManager = authenticationManager;
		this.jwtutil = jwtUtil;
	}
		
	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, 
			HttpServletResponse res) throws AuthenticationException  {
		try {
			CredenciaisAcesso creds = new ObjectMapper()
			.readValue(req.getInputStream(), CredenciaisAcesso.class);
			UsernamePasswordAuthenticationToken authToken 
			= new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getSenha(), new ArrayList<>());
			
			Authentication auth = authenticationManager.authenticate(authToken);
			return auth;
		}catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
			
			
		
		@Override
		protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
												Authentication auth)throws IOException,ServletException {
			
		String username=((UserSecurity)auth.getPrincipal()).getUsername();
		String token = jwtutil.generateToken(username);
		res.addHeader("Authorization", "Bearer"+token);
		res.addHeader("access-control-expose-headers", "Authorization");
			
		}
		
		
		private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {
	        @Override
	        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
	                throws IOException, ServletException {
	            response.setStatus(401);
	            response.setContentType("application/json"); 
	            response.getWriter().append(json());
	        }
	        
	        private String json() {
	            long date = new Date().getTime();
	            return "{\"timestamp\": " + date + ", "
	                + "\"status\": 401, "
	                + "\"error\": \"Não autorizado\", "
	                + "\"message\": \"Email ou senha inválidos\", "
	                + "\"path\": \"/login\"}";
	        }
	    }
}
