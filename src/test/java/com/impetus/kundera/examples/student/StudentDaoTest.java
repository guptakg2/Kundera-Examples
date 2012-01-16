/**
 * 
 */
package com.impetus.kundera.examples.student;

import junit.framework.TestCase;

/**
 * @author impadmin
 *
 */
public class StudentDaoTest extends TestCase
{
    private String persistenceUnit = "addhbase";
    
    private int numberOfStudents = 1000;
    
    StudentDao dao;

    protected void setUp() throws Exception
    {
        super.setUp();
        dao = new StudentDao(persistenceUnit);
    }

    protected void tearDown() throws Exception
    {
        super.tearDown();
        dao.close();
    }

    /**
     * Test method for {@link com.impetus.kundera.examples.student.StudentDao#saveStudents(java.util.Set)}.
     */
    public void testSaveStudents()
    {
        long start = System.currentTimeMillis();
        for(int i = 0; i < numberOfStudents; i++) {
            Student student = new Student((i + 1) + "", "Student_" + (i + 1), "(" + i + ")");
            dao.saveStudent(student);
        }
        
        System.out.println("Time taken to save " + numberOfStudents + " records(ms): " + (System.currentTimeMillis() - start));
    }

    /**
     * Test method for {@link com.impetus.kundera.examples.student.StudentDao#saveStudent(com.impetus.kundera.examples.student.Student)}.
     */
    public void testSaveStudent()
    {
        //Student student = new Student("1", "Amry", "IV");
        //dao.saveStudent(student);
    }

    /**
     * Test method for {@link com.impetus.kundera.examples.student.StudentDao#findStudent(java.lang.String)}.
     */
    public void testFindStudent()
    {
        //fail("Not yet implemented");
    }

}
