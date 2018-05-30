package br.com.ssp.eventos.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.ssp.eventos.model.Convidado;
import br.com.ssp.eventos.model.Evento;

public interface ConvidadoRepository extends CrudRepository<Convidado, String> {
	
	Iterable<Convidado> findByEvento(Evento evento);
}
