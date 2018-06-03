package br.com.ssp.eventos.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.springframework.security.core.GrantedAuthority;

@Entity
public class Role implements GrantedAuthority {
	
	@Id
	private String nomeRole;
	
	@ManyToMany//muitas regras para muitos usuarios
	private List<Usuario> usuarios;
	
	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return nomeRole;
	}

	public String getNomeRole() {
		return nomeRole;
	}

	public void setNomeRole(String nomeRole) {
		this.nomeRole = nomeRole;
	}

}
