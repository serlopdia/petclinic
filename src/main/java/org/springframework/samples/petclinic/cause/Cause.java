package org.springframework.samples.petclinic.cause;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.samples.petclinic.model.NamedEntity;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.samples.petclinic.donation.Donation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "causes")
public class Cause extends NamedEntity{
	
    @Size(min = 3, max = 200)
	@Column(name = "description")
	@NotNull
	private String description;
    
    @Size(min = 3, max = 50)
	@Column(name = "organization")
	@NotNull
	private String organization;

	@Min(1)
	@NotNull
	private Integer target;

	@OneToOne
	private Owner owner;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cause")
	private Set<Donation> donations;

	protected Set<Donation> getDonationsInternal() {
		if (this.donations == null) {
			this.donations = new HashSet<>();
		}
		return this.donations;
	}

	protected void setDonationsInternal(Set<Donation> donations) {
		this.donations = donations;
	}
	
	@Transactional
	public Integer getDonationsTotal() {
		List<Integer> donationsList = new ArrayList<Integer>();
		this.getDonations().forEach(x->donationsList.add(x.getAmount()));
		Integer addition = 0;
		for(Integer i=0; i<donationsList.size(); i++) {
			addition += donationsList.get(i);
		}
		return addition;
	}

	public List<Donation> getDonations() {
		List<Donation> sortedDonations = new ArrayList<>(getDonationsInternal());
		PropertyComparator.sort(sortedDonations, new MutableSortDefinition("name", true, true));
		return Collections.unmodifiableList(sortedDonations);
	}

	public void addDonation(Donation donation) {
		getDonationsInternal().add(donation);
		donation.setCause(this);
	}
	
	public boolean removeDonation(Donation donation) {
		return getDonationsInternal().remove(donation);
	}
	
	@Transactional
	public Boolean getIsClosed() {
		if (this.getDonationsTotal().equals(this.target)) {
			return true;
		} else {
			return false;
		}
	}
}
