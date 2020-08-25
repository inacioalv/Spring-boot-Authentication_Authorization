package br.com.inacio.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.inacio.Entidade.Endereco;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

}
