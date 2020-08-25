package br.com.inacio.Resource;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.inacio.Entidade.Usuario;
import br.com.inacio.service.UsuarioService;

@RestController
@RequestMapping(value = "/Usuario")
public class UsuarioResource {
	
	@Autowired
	private UsuarioService service;
	
	//Buscar Todos
	@RequestMapping(method = RequestMethod.GET)
	public List<Usuario> getUser(){
		return service.findAll();
	}
	
	// Salvar
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> postUser(@RequestBody Usuario usuario) {
	 Usuario obj= service.post(usuario);
	 URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
	 return ResponseEntity.created(uri).build();
	}

}
