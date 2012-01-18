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
    private String persistenceUnit = "addcassandra";
    
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
    /*public void testSaveStudents()
    {
        long start = System.currentTimeMillis();
        for(int i = 0; i < numberOfStudents; i++) {
            Student student = new Student((i + 1) + "", "Student_" + (i + 1), "(" + i + ")");
            dao.saveStudent(student);
        }
        
        System.out.println("Time taken to save " + numberOfStudents + " records(ms): " + (System.currentTimeMillis() - start));
    }*/
    
    public void test() {
        //saveStudent();
        findStudent();
    }

    /**
     * Test method for {@link com.impetus.kundera.examples.student.StudentDao#saveStudent(com.impetus.kundera.examples.student.Student)}.
     */
    public void saveStudent()
    {
        Student s = new Student();
        
        s.setStudentId("1");
        s.setUniqueId(78575785897L);
        s.setStudentName("Amresh");
        s.setExceptional(true);
        s.setAge(30);
        s.setSemester('C');
        s.setDigitalSignature((byte)5);
        s.setCgpa((short)8);
        s.setPercentage((float)69.6);
        s.setHeight(163.76765654);
        s.setEnrolmentDate(new java.util.Date());
        s.setEnrolmentTime(new java.util.Date());
        s.setJoiningDateAndTime(new java.util.Date());  
        
        s.setYearsSpent(3);
        s.setRollNumber(978423946455l);
        s.setMonthlyFee(135434.89);
        
        dao.saveStudent(s);
    }

    /**
     * Test method for {@link com.impetus.kundera.examples.student.StudentDao#findStudent(java.lang.String)}.
     */
    public void findStudent()
    {
        Student s = dao.findStudent("1");
        System.out.println(s);
    }
    
    
    

}
