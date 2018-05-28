package br.com.ssp.eventos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.ssp.eventos.model.EventoModel;
import br.com.ssp.eventos.repository.EventoRepository;

@Controller
public class EventosController {
	@Autowired
	EventoRepository er;
	
	//Retorna o formulário para ser preenchido atravez do método GET
	@RequestMapping(value="/cadastrarEvento", method=RequestMethod.GET)
	public String form() {
		
		return "evento/formEvento";
	}
	
	//Retorna o formulário preenchido ao clicar no botão SLAVAR
	@RequestMapping(value="/cadastrarEvento", method=RequestMethod.POST)
	public String form(EventoModel evento) {
		
		er.save(evento);
		
		return "redirect:/cadastrarEvento";
	}
	
	
	@RequestMapping("/")
	public ModelAndView listaEventos() {
		ModelAndView mv = new ModelAndView("index");
		
		Iterable<EventoModel> eventos = er.findAll();
		mv.addObject("eventos", eventos);
		
		return mv;
	}
	
	@RequestMapping("/{id}")
	public ModelAndView detalhesEvento(@PathVariable("id") long id) {
		EventoModel evento = er.findById(id);
		
		ModelAndView mv = new ModelAndView("evento/detalhes");
		mv.addObject("evento", evento);
		
		return mv;
	}
	
	
}
