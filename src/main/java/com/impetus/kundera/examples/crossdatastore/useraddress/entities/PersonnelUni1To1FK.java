package com.impetus.kundera.examples.crossdatastore.useraddress.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PERSONNEL", schema = "KunderaExamples@twissandra")
public class PersonnelUni1To1FK
{
    @Id
    @Column(name = "PERSON_ID")
    private String personId;

    @Column(name = "PERSON_NAME")
    private String personName;

    @Embedded
    PersonalData personalData;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ADDRESS_ID")
    private HabitatUni1To1FK address;

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

    public HabitatUni1To1FK getAddress()
    {
        return address;
    }

    public void setAddress(HabitatUni1To1FK address)
    {
        this.address = address;
    }

}
