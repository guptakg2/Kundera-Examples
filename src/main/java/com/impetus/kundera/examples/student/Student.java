package com.impetus.kundera.examples.student;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="STUDENT", schema="KunderaExamples@addhbase")
public class Student
{
    @Id   
    @Column(name="STUDENT_ID")    
    private String studentId;
    
    @Column(name="STUDENT_NAME")
    private String studentName;
    
    @Column(name="SEMESTER")
    private String semester;
    
    public Student() {
        
    }
    
    public Student(String studentId, String name, String semester) {
        this.studentId = studentId;
        this.studentName = name;
        this.semester = semester;
    }

    /**
     * @return the studentId
     */
    public String getStudentId()
    {
        return studentId;
    }

    /**
     * @param studentId the studentId to set
     */
    public void setStudentId(String studentId)
    {
        this.studentId = studentId;
    }

    /**
     * @return the studentName
     */
    public String getStudentName()
    {
        return studentName;
    }

    /**
     * @param studentName the studentName to set
     */
    public void setStudentName(String studentName)
    {
        this.studentName = studentName;
    }

    /**
     * @return the semester
     */
    public String getSemester()
    {
        return semester;
    }

    /**
     * @param semester the semester to set
     */
    public void setSemester(String semester)
    {
        this.semester = semester;
    } 
  

}
