package com.impetus.kundera.examples.crossdatastore.useraddress.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "PERSONNEL", schema = "hibernatepoc")
public class PersonnelBiMToM
{
    @Id
    @Column(name = "PERSON_ID")
    private String personId;

    @Column(name = "PERSON_NAME")
    private String personName;

    @Embedded
    PersonalData personalData;

    @ManyToMany
    @JoinTable(name = "PERSONNEL_ADDRESS", 
      joinColumns = {
        @JoinColumn(name="PERSON_ID")           
      },
      inverseJoinColumns = {
        @JoinColumn(name="ADDRESS_ID")
      }
    )
    private Set<HabitatBiMToM> addresses;

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

    public PersonalData getPersonalData()
    {
        return personalData;
    }

    public void setPersonalData(PersonalData personalData)
    {
        this.personalData = personalData;
    }

    public Set<HabitatBiMToM> getAddresses()
    {
        return addresses;
    }

    public void setAddresses(Set<HabitatBiMToM> addresses)
    {
        this.addresses = addresses;
    }

}
