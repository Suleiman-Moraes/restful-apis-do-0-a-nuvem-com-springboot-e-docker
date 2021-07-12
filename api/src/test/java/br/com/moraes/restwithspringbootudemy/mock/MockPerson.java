package br.com.moraes.restwithspringbootudemy.mock;

import java.util.LinkedList;
import java.util.List;

import br.com.moraes.restwithspringbootudemy.api.data.model.Person;
import br.com.moraes.restwithspringbootudemy.api.data.vo.PersonVo;

public class MockPerson {

	public Person mockEntity() {
		return mockEntity(0);
	}

	public PersonVo mockVo() {
		return mockVo(0);
	}

	public List<Person> mockEntityList() {
		List<Person> persons = new LinkedList<>();
		for (int i = 0; i < 14; i++) {
			persons.add(mockEntity(i));
		}
		return persons;
	}

	public List<PersonVo> mockVoList() {
		List<PersonVo> persons = new LinkedList<>();
		for (int i = 0; i < 14; i++) {
			persons.add(mockVo(i));
		}
		return persons;
	}

	private PersonVo mockVo(int i) {
		final Long id = new Long(i);
		return new PersonVo(id, "First Name Test" + id, "Last Name Test" + id, "Addres Test" + id,
				(id % 2 == 0) ? "Male" : "Female");
	}

	private Person mockEntity(int i) {
		final Long id = new Long(i);
		return new Person(id, "First Name Test" + id, "Last Name Test" + id, "Addres Test" + id,
				(id % 2 == 0) ? "Male" : "Female");
	}
}
