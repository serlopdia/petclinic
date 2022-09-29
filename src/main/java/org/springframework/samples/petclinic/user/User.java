package org.springframework.samples.petclinic.user;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.samples.petclinic.clinic.Clinic;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User{
	@Id
	String username;
	
	String password;
	
	boolean enabled;
	
	@ManyToOne()
	Clinic clinic;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private Set<Authorities> authorities;
}
