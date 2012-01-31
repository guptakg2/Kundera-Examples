package com.impetus.kundera.examples.crossdatastore.useraddress.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "PERSON", schema = "KunderaExamples@twingo")
public class PersonnelUniMToM {
	@Id
	@Column(name = "PERSON_ID")
	private String personId;

	@Column(name = "PERSON_NAME")
	private String personName;
	
	@Embedded
	PersonalData personalData;

	@ManyToMany
	  @JoinTable(name = "PERSON_ADDRESS", 
	    joinColumns = {
	      @JoinColumn(name="PERSON_ID")           
	    },
	    inverseJoinColumns = {
	      @JoinColumn(name="ADDRESS_ID")
	    }
	  )
	private Set<HabitatUniMToM> addresses;

	public String getPersonId() {
		return personId;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public PersonalData getPersonalData() {
		return personalData;
	}

	public void setPersonalData(PersonalData personalData) {
		this.personalData = personalData;
	}

	public Set<HabitatUniMToM> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<HabitatUniMToM> addresses) {
		this.addresses = addresses;
	}
}

	
