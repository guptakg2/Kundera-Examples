package com.impetus.kundera.examples.crossdatastore.useraddress.entities;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="PERSON", schema="KunderaExamples")
public class Personnel
{
    @Id   
    @Column(name="PERSON_ID")    
    private String personId;
    
    @Column(name="PERSON_NAME")
    private String personName;
    
    @Embedded
    PersonalData personalData;
   

    public String getPersonId()
    {
        return personId;
    }  
    

    public String getPersonName()
    {
        return personName;
    }

    public void setPersonName(String personName)
    {
        this.personName = personName;
    }  


	public void setPersonId(String personId)
    {
        this.personId = personId;
    }

	public PersonalData getPersonalData() {
		return personalData;
	}


	public void setPersonalData(PersonalData personalData) {
		this.personalData = personalData;
	}
}
