/**
 * 
 */
package com.impetus.kundera.examples.student;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author Amresh Singh
 *
 */
public class StudentDao
{

    EntityManagerFactory emf;
    
    public StudentDao(String pu) {
        if (emf == null)
        {
            emf = Persistence.createEntityManagerFactory(pu);
        }
    }
    
    public void saveStudents(Set<Student> students) {
        for(Student student : students) {
            saveStudent(student);
        }       
    }
    
    public void saveStudent(Student student) {
      EntityManager em =  emf.createEntityManager();
      em.persist(student);
      em.close();
    }
    
    public void close()
    {
        emf.close();
    } 
    
    public Student findStudent(String studentId) {
        EntityManager em =  emf.createEntityManager();
        Student student = em.find(Student.class, studentId);
        em.close();
        return student;
    }    

}
