/**
 * 
 */
package com.impetus.kundera.examples.crud.datatypes;

import java.util.List;

import javax.persistence.Query;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.impetus.kundera.examples.cli.HBaseCli;
import com.impetus.kundera.examples.crud.datatype.entities.StudentCassandra;
import com.impetus.kundera.examples.crud.datatype.entities.StudentEntityDef;
import com.impetus.kundera.examples.crud.datatype.entities.StudentHbase;

/**
 * The Class Student test case for HBase.
 * 
 * @author Kuldeep.mishra
 */
public class StudentHbaseTest extends StudentBase<StudentHbase>
{

    /**
     * Sets the up.
     * 
     * @throws Exception
     *             the exception
     */
    @Before
    public void setUp() throws Exception
    {

        HBaseCli.startCluster();
        HBaseCli.createTable("KunderaExamples");
        HBaseCli.addColumnFamily("KunderaExamples", "STUDENT");

        setupInternal("twibase");
    }

    /**
     * Tear down.
     * 
     * @throws Exception
     *             the exception
     */
    @After
    public void tearDown() throws Exception
    {
        HBaseCli.stopCluster();

        // dao.close();
    }

    /**
     * Test method for.
     * 
     * @throws InstantiationException
     *             the instantiation exception
     * @throws IllegalAccessException
     *             the illegal access exception
     *             {@link com.impetus.kundera.examples.student.StudentDao#saveStudent(com.impetus.kundera.examples.crud.datatype.entities.StudentHbase)}
     *             .
     */
    @SuppressWarnings("deprecation")
    @Test
    public void onInsert() throws InstantiationException, IllegalAccessException
    {
        onInsert(new StudentHbase());

        // find by id.
        StudentEntityDef s = em.find(StudentHbase.class, studentId1);
        assertOnDataTypes((StudentHbase) s);

        // // find by name.
        assertFindByName(em, "StudentHbase", StudentHbase.class, "Amresh", "STUDENT_NAME");

        // find by name and age.
        assertFindByNameAndAge(em, "StudentHbase", StudentHbase.class, "Amresh", "10", "STUDENT_NAME");

        // find by name, age clause
        assertFindByNameAndAgeGTAndLT(em, "StudentHbase", StudentHbase.class, "Amresh", "10", "20", "STUDENT_NAME");
        //
        // // find by between clause
        assertFindByNameAndAgeBetween(em, "StudentHbase", StudentHbase.class, "Amresh", "10", "15", "STUDENT_NAME");

        // find by Range.
        assertFindByRange(em, "StudentHbase", StudentHbase.class, "12345677", "12345678", "STUDENT_ID");

        // find by without where clause.
        assertFindWithoutWhereClause(em, "StudentHbase", StudentHbase.class);
    }

    /**
     * On merge.
     */
//     @Test
    public void onMerge()
    {
        em.persist(prepareData((Long) studentId1, 78575785897L, "Amresh", true, 10, 'C', (byte) 5, (short) 8,
                (float) 69.6, 163.76765654, enrolmentDate, enrolmentTime, joiningDateAndTime, new Integer(3), new Long(
                        978423946455l), 135434.89, newSqlDate, sqlTime, sqlTimestamp, bigDecimal, bigInteger, calendar,
                new StudentHbase()));
        StudentHbase s = em.find(StudentHbase.class, studentId1);
        Assert.assertNotNull(s);
        Assert.assertEquals("Amresh", s.getStudentName());
        // modify record.
        s.setStudentName("NewAmresh");
        em.merge(s);
        // emf.close();
        Query q = em.createQuery("Select p from StudentHbase p where p.STUDENT_NAME = NewAmresh");
        List<StudentCassandra> results = q.getResultList();
        Assert.assertNotNull(results);
        Assert.assertEquals(1, results.size());
    }
}
