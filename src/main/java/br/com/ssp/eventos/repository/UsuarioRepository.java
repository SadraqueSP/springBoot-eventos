package br.com.ssp.eventos.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.ssp.eventos.model.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, String> {
	
	Usuario findByLogin(String login);
}
