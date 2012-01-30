/**
 * 
 */
package com.impetus.kundera.examples.student;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

/**
 * @author Amresh Singh
 * 
 */
public class StudentDaoTest extends TestCase {
	private String persistenceUnit = "addcassandra";

	private Object studentId;
	private Date enrolmentDate = new Date();
	private Date enrolmentTime = new Date();
	private Date joiningDateAndTime = new Date();
	private long date = new Date().getTime();
	private java.sql.Date newSqlDate = new java.sql.Date(date);
	private java.sql.Time sqlTime = new java.sql.Time(date);
	private java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(date);
	private BigDecimal bigDecimal = new BigDecimal(123456789);
	private BigInteger bigInteger = new BigInteger("123456789");
	private int numberOfStudents = 1000;
	private Calendar calendar  = Calendar.getInstance();


	StudentDao dao;

	
	protected void setUp() throws Exception {
		super.setUp();
		dao = new StudentDao(persistenceUnit);
		studentId = new Long(123456789);

	}

	protected void tearDown() throws Exception {
		super.tearDown();
		dao.close();
	}

	/**
	 * Test method for
	 * {@link com.impetus.kundera.examples.student.StudentDao#saveStudents(java.util.Set)}
	 * .
	 */
	/*
	 * public void testSaveStudents() { long start = System.currentTimeMillis();
	 * for(int i = 0; i < numberOfStudents; i++) { Student student = new
	 * Student((i + 1) + "", "Student_" + (i + 1), "(" + i + ")");
	 * dao.saveStudent(student); }
	 * 
	 * System.out.println("Time taken to save " + numberOfStudents +
	 * " records(ms): " + (System.currentTimeMillis() - start)); }
	 */

	public void testAllDataTypes() {
		saveStudent();
		findStudent();
	}

	/**
	 * Test method for
	 * {@link com.impetus.kundera.examples.student.StudentDao#saveStudent(com.impetus.kundera.examples.student.Student)}
	 * .
	 */
	public void saveStudent() {
		Student s = new Student();

		s.setStudentId((Long) studentId);
		s.setUniqueId(78575785897L);
		s.setStudentName("Amresh");
		s.setExceptional(true);
		s.setAge(30);
		s.setSemester('C');
		s.setDigitalSignature((byte) 5);
		s.setCgpa((short) 8);
		s.setPercentage((float) 69.6);
		s.setHeight(163.76765654);

		s.setEnrolmentDate(enrolmentDate);
		s.setEnrolmentTime(enrolmentTime);
		s.setJoiningDateAndTime(joiningDateAndTime);

		s.setYearsSpent(new Integer(3));
		s.setRollNumber(new Long(978423946455l));
		s.setMonthlyFee(135434.89);
		s.setSqlDate(newSqlDate);
		s.setSqlTime(sqlTime);
		s.setSqlTimestamp(sqlTimestamp);
		s.setBigDecimal(bigDecimal);
		s.setBigInteger(bigInteger);
		s.setCalendar(calendar);

		dao.saveStudent(s);
	}

	/**
	 * Test method for
	 * {@link com.impetus.kundera.examples.student.StudentDao#findStudent(java.lang.String)}
	 * .
	 */
	@SuppressWarnings("deprecation")
	public void findStudent() {
		Student s = dao.findStudent(studentId);
		assertNotNull(s);
		assertEquals(((Long) studentId).longValue(), s.getStudentId());
		assertEquals(78575785897L, s.getUniqueId());
		assertEquals("Amresh", s.getStudentName());
		assertEquals(true, s.isExceptional());
		assertEquals(30, s.getAge());
		assertEquals('C', s.getSemester());
		assertEquals((byte) 5, s.getDigitalSignature());
		assertEquals((short) 8, s.getCgpa());
		assertEquals((float) 69.6, s.getPercentage());
		assertEquals(163.76765654, s.getHeight());

		assertEquals(enrolmentDate.getDate(), s.getEnrolmentDate().getDate());
		assertEquals(enrolmentDate.getMonth(), s.getEnrolmentDate().getMonth());
		assertEquals(enrolmentDate.getYear(), s.getEnrolmentDate().getYear());

		assertEquals(enrolmentTime.getHours(), s.getEnrolmentTime().getHours());
		assertEquals(enrolmentTime.getMinutes(), s.getEnrolmentTime()
				.getMinutes());
		assertEquals(enrolmentTime.getSeconds(), s.getEnrolmentTime()
				.getSeconds());
		assertEquals(enrolmentTime.getMonth(), s.getEnrolmentTime().getMonth());

		assertEquals(joiningDateAndTime.getDate(), s.getJoiningDateAndTime()
				.getDate());
		assertEquals(joiningDateAndTime.getMonth(), s.getJoiningDateAndTime()
				.getMonth());
		assertEquals(joiningDateAndTime.getYear(), s.getJoiningDateAndTime()
				.getYear());
		assertEquals(joiningDateAndTime.getHours(), s.getJoiningDateAndTime()
				.getHours());
		assertEquals(joiningDateAndTime.getMinutes(), s.getJoiningDateAndTime()
				.getMinutes());
		assertEquals(joiningDateAndTime.getSeconds(), s.getJoiningDateAndTime()
				.getSeconds());

		assertEquals(newSqlDate.getDate(), s.getSqlDate().getDate());
		assertEquals(newSqlDate.getMonth(), s.getSqlDate().getMonth());
		assertEquals(newSqlDate.getYear(), s.getSqlDate().getYear());

		assertEquals(sqlTime.getMinutes(), s.getSqlTime().getMinutes());
		assertEquals(sqlTime.getSeconds(), s.getSqlTime().getSeconds());
		assertEquals(sqlTime.getHours(), s.getSqlTime().getHours());

		assertEquals(sqlTimestamp.getDate(), s.getSqlTimestamp().getDate());
		assertEquals(sqlTimestamp.getMonth(), s.getSqlTimestamp().getMonth());
		assertEquals(sqlTimestamp.getYear(), s.getSqlTimestamp().getYear());
		assertEquals(sqlTimestamp.getHours(), s.getSqlTimestamp().getHours());
		assertEquals(sqlTimestamp.getMinutes(), s.getSqlTimestamp()
				.getMinutes());
		assertEquals(sqlTimestamp.getSeconds(), s.getSqlTimestamp()
				.getSeconds());

		assertEquals(bigDecimal, s.getBigDecimal());
		assertEquals(bigInteger, s.getBigInteger());
		
		assertEquals(calendar.get(Calendar.YEAR), s.getCalendar().get(Calendar.YEAR));
		assertEquals(calendar.get(Calendar.MONTH), s.getCalendar().get(Calendar.MONTH));
		assertEquals(calendar.get(Calendar.WEEK_OF_YEAR), s.getCalendar().get(Calendar.WEEK_OF_YEAR));
		assertEquals(calendar.get(Calendar.WEEK_OF_MONTH), s.getCalendar().get(Calendar.WEEK_OF_MONTH));
		assertEquals(calendar.get(Calendar.DAY_OF_MONTH), s.getCalendar().get(Calendar.DAY_OF_MONTH));
		assertEquals(calendar.get(Calendar.DAY_OF_WEEK), s.getCalendar().get(Calendar.DAY_OF_WEEK));
		assertEquals(calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH), s.getCalendar().get(Calendar.DAY_OF_WEEK_IN_MONTH));
		assertEquals(calendar.get(Calendar.DAY_OF_YEAR), s.getCalendar().get(Calendar.DAY_OF_YEAR));
		assertEquals(calendar.get(Calendar.HOUR), s.getCalendar().get(Calendar.HOUR));
		assertEquals(calendar.get(Calendar.HOUR_OF_DAY), s.getCalendar().get(Calendar.HOUR_OF_DAY));
		assertEquals(calendar.get(Calendar.AM), s.getCalendar().get(Calendar.AM));
		assertEquals(calendar.get(Calendar.PM), s.getCalendar().get(Calendar.PM));
		assertEquals(calendar.get(Calendar.AM_PM), s.getCalendar().get(Calendar.AM_PM));
		
		assertEquals(new Integer(3), s.getYearsSpent());
		assertEquals(new Long(978423946455l), s.getRollNumber());
		assertEquals(new Double(135434.89), s.getMonthlyFee());

	}

}