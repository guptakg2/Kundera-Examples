package com.impetus.kundera.examples.crossdatastore.useraddress.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PERSONNEL", schema = "test")
public class PersonnelUniMTo1 {
	@Id
	@Column(name = "PERSON_ID")
	private String personId;

	@Column(name = "PERSON_NAME")
	private String personName;
	
	@Embedded
	private PersonalData personalData;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="ADDRESS_ID")
	private HabitatUniMTo1 address;

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

	public HabitatUniMTo1 getAddress() {
		return address;
	}

	public void setAddress(HabitatUniMTo1 address) {
		this.address = address;
	}

}
