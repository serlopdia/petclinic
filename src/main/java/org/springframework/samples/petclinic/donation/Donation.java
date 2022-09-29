package org.springframework.samples.petclinic.donation;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.cause.Cause;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.owner.Owner;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "donations")
public class Donation extends BaseEntity{
	
	@Min(1)
	@NotNull
	private Integer amount;
	
	@Column(name = "donation_date")        
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@NotNull
	private LocalDate date;

	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Owner owner;
	
	@ManyToOne
	@JoinColumn(name = "cause_id")
	private Cause cause;

	public Donation() {
		this.date = LocalDate.now();
	}
}
