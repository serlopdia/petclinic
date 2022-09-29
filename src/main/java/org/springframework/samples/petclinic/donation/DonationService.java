package org.springframework.samples.petclinic.donation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DonationService 
{
	private DonationRepository donationRepository;
	
	@Autowired
	public DonationService(DonationRepository donationRepository) 
	{
		this.donationRepository = donationRepository;
	}
	
	@Transactional
	public void save(Donation donation) 
	{
		donationRepository.save(donation);
	}
	
	@Transactional
	public Optional<Donation> findDonationById(Integer donationId)
	{
		return donationRepository.findById(donationId);
	}
	
	@Transactional
	public void delete(Donation donation) 
	{
		donationRepository.delete(donation);
	}	
	
	@Transactional
	public Iterable<Donation> findDonationsByCauseId(Integer donationId) 
	{
		return donationRepository.findDonationsByCauseId(donationId);
	}
	
}
