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
import javax.persistence.Table;

@Entity
@Table(name = "PERSONNEL", schema = "test")
public class PersonnelUniMToM
{
    @Id
    @Column(name = "PERSON_ID")
    private String personId;

    @Column(name = "PERSON_NAME")
    private String personName;

    @Embedded
    PersonalData personalData;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "PERSONNEL_ADDRESS", joinColumns = { @JoinColumn(name = "PERSON_ID") }, inverseJoinColumns = { @JoinColumn(name = "ADDRESS_ID") })
    private Set<HabitatUniMToM> addresses;

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

    public Set<HabitatUniMToM> getAddresses()
    {
        return addresses;
    }

    public void setAddresses(Set<HabitatUniMToM> addresses)
    {
        this.addresses = addresses;
    }

    /**
     * @return the personalData
     */
    public PersonalData getPersonalData()
    {
        return personalData;
    }

    /**
     * @param personalData the personalData to set
     */
    public void setPersonalData(PersonalData personalData)
    {
        this.personalData = personalData;
    }  
    
}
