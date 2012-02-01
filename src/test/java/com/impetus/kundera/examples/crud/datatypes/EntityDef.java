/*******************************************************************************
 * * Copyright 2011 Impetus Infotech.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 ******************************************************************************/
package com.impetus.kundera.examples.crud.datatypes;

/**
 * @author vivek.mishra
 *
 */
public interface EntityDef
{

    long getStudentId();
    void setStudentId(long studentId);
    long getUniqueId();
    void setUniqueId(long uniqueId);
    String getStudentName();
    void setStudentName(String studentName);
    boolean isExceptional();
    void setExceptional(boolean isExceptional);
    int getAge();
    void setAge(int age);
    char getSemester();
    void setSemester(char semester);
    byte getDigitalSignature();
    void setDigitalSignature(byte digitalSignature);
    short getCgpa();
    void setCgpa(short cgpa);
    float getPercentage();
    void setPercentage(float percentage);
    double getHeight();
    void setHeight(double height);
    java.util.Date getEnrolmentDate();
    void setEnrolmentDate(java.util.Date enrolmentDate);
    java.util.Date getEnrolmentTime();
    void setEnrolmentTime(java.util.Date enrolmentTime);
    java.util.Date getJoiningDateAndTime();
    void setJoiningDateAndTime(java.util.Date joiningDateAndTime);
    Integer getYearsSpent();
    void setYearsSpent(Integer yearsSpent);
    Long getRollNumber();
    void setRollNumber(Long rollNumber);
    Double getMonthlyFee();
    void setMonthlyFee(Double monthlyFee);
    java.sql.Date getSqlDate();
}
