package br.com.inacio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.inacio.Entidade.Usuario;
import br.com.inacio.Repository.UsuarioRepository;
import br.com.inacio.securityconfig.UserSecurity;

@Service
public class UserSecurityService implements UserDetailsService {
	
	
	
	@Autowired
	private UsuarioRepository repository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario usuario = repository.findByEmail(email);
		if(usuario==null) {
			throw new UsernameNotFoundException(email);
		}
		return new UserSecurity(usuario.getId(), usuario.getEmail(), usuario.getSenha());
	}
	
	
	
}
