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

import br.com.inacio.Entidade.Endereco;
import br.com.inacio.service.EnderecoService;

@RestController
@RequestMapping("/Endereco")
public class EnderecoResource {
	
	@Autowired
	private EnderecoService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Endereco> get(){
		return service.findAll();
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> post(@RequestBody Endereco endereco) {
		Endereco obj =service.save(endereco);
		URI uri =ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	} 

}
