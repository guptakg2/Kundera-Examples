/**
 * 
 */
package com.impetus.kundera.examples.crud.student;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The Class StudentDaoTest.
 *
 * @author Vivek Mishra
 */
public class StudentCassandraTest extends StudentBase<StudentCassandra>
{
    
    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @Before
    public void setUp() throws Exception
    {

        setupInternal("twissandra");
    }

    /**
     * Tear down.
     *
     * @throws Exception the exception
     */
    @After
    public void tearDown() throws Exception
    {
        // dao.close();
    }

    /**
     * Test method for.
     *
     * @throws InstantiationException the instantiation exception
     * @throws IllegalAccessException the illegal access exception
     * {@link com.impetus.kundera.examples.student.StudentDao#saveStudent(com.impetus.kundera.examples.student.StudentCassandra)}
     * .
     */
    @SuppressWarnings("deprecation")
    @Test
    public void onInsert() throws InstantiationException, IllegalAccessException
    {
        onInsert(new StudentCassandra());

        // find by id.
        StudentEntityDef s = em.find(StudentCassandra.class, studentId1);
        assertOnDataTypes((StudentCassandra) s);

        // // find by name.
        assertFindByName(em, "StudentCassandra", StudentCassandra.class, "Amresh","STUDENT_NAME");

        // find by name and age.
        assertFindByNameAndAge(em, "StudentCassandra", StudentCassandra.class, "Amresh", "10","STUDENT_NAME");

        // find by name, age clause
        assertFindByNameAndAgeGTAndLT(em, "StudentCassandra", StudentCassandra.class, "Amresh", "10", "20","STUDENT_NAME");
        //
        // // find by between clause
        assertFindByNameAndAgeBetween(em, "StudentCassandra", StudentCassandra.class, "Amresh", "10", "15","STUDENT_NAME");

        // find by Range.
        assertFindByRange(em, "StudentCassandra", StudentCassandra.class, "12345677", "12345678","STUDENT_ID");

        // find by without where clause.
        assertFindWithoutWhereClause(em, "StudentCassandra", StudentCassandra.class);
    }

    /**
     * On merge.
     */
//    @Test
    public void onMerge()
    {
        em.persist(prepareData((Long) studentId1, 78575785897L, "Amresh", true, 10, 'C', (byte) 5, (short) 8,
                (float) 69.6, 163.76765654, enrolmentDate, enrolmentTime, joiningDateAndTime, new Integer(3), new Long(
                        978423946455l), 135434.89, newSqlDate, sqlTime, sqlTimestamp, bigDecimal, bigInteger, calendar,
                new StudentCassandra()));
        StudentCassandra s = em.find(StudentCassandra.class, studentId1);
        Assert.assertNotNull(s);
        Assert.assertEquals("Amresh", s.getStudentName());
        // modify record.
        s.setStudentName("NewAmresh");
        em.merge(s);
        // emf.close();
        assertOnMerge(em, "StudentCassandra", StudentCassandra.class, "Amresh", "NewAmresh","STUDENT_NAME");
    }
}