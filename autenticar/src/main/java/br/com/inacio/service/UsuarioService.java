package br.com.inacio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.inacio.Entidade.Usuario;
import br.com.inacio.Repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private UsuarioRepository repository;
	
	public List<Usuario> findAll(){
		return repository.findAll();
	}
	
	
	
	public Usuario post(Usuario obj) {
		obj.setSenha(encoder.encode(obj.getSenha()));
		return repository.save(obj);
	}

}
