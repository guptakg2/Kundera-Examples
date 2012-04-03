package com.impetus.kundera.examples.crossdatastore.useraddress.dao;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class UserAddressDaoImpl extends BaseDao
{
    private String persistenceUnit;

    public UserAddressDaoImpl(String pu)
    {
        this.persistenceUnit = pu;
    }

    public Query createQuery(String query)
    {
        EntityManager em = getEntityManager(persistenceUnit);
        Query q = em.createQuery(query);
        return q;
    }

    public void insert(Object person)
    {
        em = getEntityManager(persistenceUnit);
        em.persist(person);
        closeEntityManager();
    }

    public void update(Object obj)
    {
        em.merge(obj);
        closeEntityManager();
    }

    public Object findPerson(Class entityClass, String personId)
    {
        em = getEntityManager(persistenceUnit);
        Object personnel = em.find(entityClass, personId);
        //closeEntityManager();
        return personnel;
    }

    public void removePerson(Object personnel)
    {
        em = getEntityManager(persistenceUnit);
        em.remove(personnel);
        closeEntityManager();
    }
    
    public void removeAddress(Object address)
    {
        em = getEntityManager(persistenceUnit);
        em.remove(address);
        closeEntityManager();
    }

    public void savePersons(Set<?> personnels)
    {
        EntityManager em = getEntityManager(persistenceUnit);

        for (Object personnel : personnels)
        {
            em.persist(personnel);
        }

        closeEntityManager();
    }

    public List<?> getAllPersons(String className)
    {
        EntityManager em = getEntityManager(persistenceUnit);
        Query q = em.createQuery("select p from " + className + " p");
        List<?> persons = q.getResultList();
        return persons;
    }

    public void merge(Object personnel)
    {
        em = getEntityManager(persistenceUnit);
        em.merge(personnel);
        //closeEntityManager();
    }
}