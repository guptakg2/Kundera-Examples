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
package com.impetus.kundera.examples.perf.person;

import java.util.List;

import junit.framework.TestCase;

import com.impetus.kundera.examples.perf.dao.person.PersonDao;
import com.impetus.kundera.examples.perf.dto.PersonDTO;

/**
 * @author amresh.singh
 * 
 */
public class PersonDaoTest extends TestCase {
	PersonDao personDao;
	List<PersonDTO> persons;

	protected void setUp() throws Exception {
		// super.setUp();
		// personDao = PersonDaoFactory.getPersonDao("kundera");
		// personDao.init();

		// persons = new ArrayList<PersonDto>();
	}

	/*
	 * 
	 * public void testInsertPersons() { for(int i = 0; i < 40000; i++) {
	 * PersonDto person = new PersonDto(); person.setPersonId("person_id_" + i);
	 * 
	 * PersonalDataDTO pers = new PersonalDataDTO(); pers.setLoginId("login_id_"
	 * + i); pers.setFirstName("first_name_" + i); pers.setLastName("last_name_"
	 * + i); pers.setCountry("country_" + i); person.setPersonalData(pers);
	 * 
	 * ProfessionalDataDTO prof = new ProfessionalDataDTO();
	 * prof.setDegree("degree_" + i); prof.setExperience("experience_" + i);
	 * prof.setCompany("company_" + i); prof.setPrimarySkill("primary_skill_" +
	 * i);
	 * 
	 * 
	 * person.setProfessionalData(prof);
	 * 
	 * persons.add(person); }
	 * 
	 * personDao.insertPersons(persons);
	 * 
	 * }
	 * 
	 * 
	 * protected void tearDown() throws Exception { super.tearDown();
	 * personDao.cleanup(); }
	 */
	public void testDummy() {

	}
}
