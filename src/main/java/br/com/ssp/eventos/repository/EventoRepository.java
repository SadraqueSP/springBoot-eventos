package br.com.ssp.eventos.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.ssp.eventos.model.Evento;

public interface EventoRepository extends CrudRepository<Evento, String> {

	
	Evento findById(long id);
	
}
