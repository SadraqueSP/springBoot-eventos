package br.com.ssp.eventos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.ssp.eventos.model.Convidado;
import br.com.ssp.eventos.model.Evento;
import br.com.ssp.eventos.repository.ConvidadoRepository;
import br.com.ssp.eventos.repository.EventoRepository;

@Controller
public class EventosController {
	@Autowired
	EventoRepository er;
	@Autowired
	ConvidadoRepository cr;
	
	//Retorna o formulário para ser preenchido atravez do método GET
	@RequestMapping(value="/cadastrarEvento", method=RequestMethod.GET)
	public String form() {
		
		return "evento/formEvento";
	}
	
	//Retorna o formulário preenchido ao clicar no botão SLAVAR
	@RequestMapping(value="/cadastrarEvento", method=RequestMethod.POST)
	public String form(Evento evento) {
		
		er.save(evento);
		
		return "redirect:/cadastrarEvento";
	}
	
	
	@RequestMapping("/")
	public ModelAndView listaEventos() {
		ModelAndView mv = new ModelAndView("index");
		
		Iterable<Evento> eventos = er.findAll();
		mv.addObject("eventos", eventos);
		
		return mv;
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ModelAndView detalhesEvento(@PathVariable("id") long id) {
		Evento evento = er.findById(id);
		
		ModelAndView mv = new ModelAndView("evento/detalhes");
		mv.addObject("evento", evento);
		
		Iterable<Convidado> convidados = cr.findByEvento(evento);
		mv.addObject("convidados",convidados);
		
		return mv;
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.POST)
	public String detalhesEventoPost(@PathVariable("id") long id, Convidado convidado) {
		Evento evento = er.findById(id);
		convidado.setEvento(evento);
		
		cr.save(convidado);
		
		return "redirect:/{id}";
	}
	
	
}
