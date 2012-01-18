/**
 * 
 */
package com.impetus.kundera.examples.student;

import java.util.Date;

import junit.framework.TestCase;

/**
 * @author Amresh Singh
 *
 */
public class StudentDaoTest extends TestCase
{
    private String persistenceUnit = "addhbase";
    
    private Object studentId;
    Date enrolmentDate;
    Date enrolmentTime;
    Date joiningDateAndTime;
    
    
    private int numberOfStudents = 1000;
    
    StudentDao dao;

    protected void setUp() throws Exception
    {
        super.setUp();
        dao = new StudentDao(persistenceUnit);
        studentId = new Long(123456789);
        
        enrolmentDate = new Date();
        enrolmentTime = new Date();
        joiningDateAndTime = new Date();
        
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
    
    public void testAllDataTypes() {
        //saveStudent();
        findStudent();
    }

    /**
     * Test method for {@link com.impetus.kundera.examples.student.StudentDao#saveStudent(com.impetus.kundera.examples.student.Student)}.
     */
    public void saveStudent()
    {
        Student s = new Student();
        
        s.setStudentId((Long)studentId);
        s.setUniqueId(78575785897L);
        s.setStudentName("Amresh");
        s.setExceptional(true);
        s.setAge(30);
        s.setSemester('C');
        s.setDigitalSignature((byte)5);
        s.setCgpa((short)8);
        s.setPercentage((float)69.6);
        s.setHeight(163.76765654);
        
        s.setEnrolmentDate(enrolmentDate);
        s.setEnrolmentTime(enrolmentTime);
        s.setJoiningDateAndTime(joiningDateAndTime);  
        
        s.setYearsSpent(new Integer(3));
        s.setRollNumber(new Long(978423946455l));
        s.setMonthlyFee(135434.89);
        
        dao.saveStudent(s);
    }

    /**
     * Test method for {@link com.impetus.kundera.examples.student.StudentDao#findStudent(java.lang.String)}.
     */
    public void findStudent()
    {
        Student s = dao.findStudent(studentId);
        assertNotNull(s);
        assertEquals(((Long)studentId).longValue(), s.getStudentId());
        assertEquals(78575785897L, s.getUniqueId());
        assertEquals("Amresh", s.getStudentName());
        assertEquals(true, s.isExceptional());
        assertEquals(30, s.getAge());
        assertEquals('C', s.getSemester());
        assertEquals((byte)5, s.getDigitalSignature());
        assertEquals((short)8, s.getCgpa());
        assertEquals((float)69.6, s.getPercentage());
        assertEquals(163.76765654, s.getHeight());
        
        assertEquals(enrolmentDate.getDate(), s.getEnrolmentDate().getDate());
        assertEquals(enrolmentDate.getMonth(), s.getEnrolmentDate().getMonth());
        assertEquals(enrolmentDate.getYear(), s.getEnrolmentDate().getYear());
        
        assertEquals(enrolmentTime.getHours(), s.getEnrolmentTime().getHours());
        assertEquals(enrolmentTime.getMinutes(), s.getEnrolmentTime().getMinutes());
        assertEquals(enrolmentTime.getSeconds(), s.getEnrolmentTime().getSeconds());
        
        assertEquals(joiningDateAndTime.getDate(), s.getJoiningDateAndTime().getDate());
        assertEquals(joiningDateAndTime.getMonth(), s.getJoiningDateAndTime().getMonth());
        assertEquals(joiningDateAndTime.getYear(), s.getJoiningDateAndTime().getYear());
        assertEquals(joiningDateAndTime.getHours(), s.getJoiningDateAndTime().getHours());
        assertEquals(joiningDateAndTime.getMinutes(), s.getJoiningDateAndTime().getMinutes());
        assertEquals(joiningDateAndTime.getSeconds(), s.getJoiningDateAndTime().getSeconds());
        
        assertEquals(new Integer(3), s.getYearsSpent());        
        assertEquals(new Long(978423946455l), s.getRollNumber());
        assertEquals(new Double(135434.89), s.getMonthlyFee());
        
    }   
    
    

}
