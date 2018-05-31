package br.com.ssp.eventos.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String form( @Valid Evento evento, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campo!");
			return "redirect:/cadastrarEvento";
		}
		
		er.save(evento);
		attributes.addFlashAttribute("mensagem", "Evento adicionado com sucesso!");
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
	public String detalhesEventoPost(@PathVariable("id") long id,@Valid Convidado convidado, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campo!");
			return "redirect:/{id}";
		}
		
		Evento evento = er.findById(id);
		convidado.setEvento(evento);
		
		cr.save(convidado);
		attributes.addFlashAttribute("mensagem", "Convidado adicionado com sucesso!");
		return "redirect:/{id}";
	}
	
	@RequestMapping("/deletarConvidado")
	public String deletarConvidado(String rg) {
		Convidado convidado = cr.findByRg(rg);
		Evento evento = convidado.getEvento();
		long idEventoLong = evento.getId();
		
		String idEvento = ""+ idEventoLong;
		
		cr.delete(convidado);
		return "redirect:/"+idEvento;
	}
	
	@RequestMapping("/deletarEvento")
	public String deletarEvento( long id) {
		
		Evento evento = er.findById(id);
		er.delete(evento);
		return "redirect:/";
	}
	
	
	
}
