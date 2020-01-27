package sample.utils;

import sample.models.Person;
import sample.models.PersonsTable;

import java.util.ArrayList;
import java.util.List;

public class PersonStorage {

    private PersonsTable personsTable = new PersonsTable();

    public Person findPerson(int id) {
        return personsTable.findById(id);
    }

    public void savePerson(String name, int year, int number, String address) {
        personsTable.save(new Person(name, year, number, address));
    }

    public void deletePerson(int id) {
        Person person = personsTable.findById(id);
        personsTable.delete(person);
    }

    public void updatePerson(int column, Person person, String newValue) {
        switch (column){
            case (1): person.setName(newValue);
                break;
            case (2): person.setYear(Integer.parseInt(newValue));
                break;
            case (3): person.setNumber(Integer.parseInt(newValue));
                break;
            case (4): person.setAddress(newValue);
                break;
        }
        personsTable.update(person);
    }

    public List<Person> findAllUsers() {
        return personsTable.findAll();
    }

    public List<Person> getNumbersByName(String name){
        return personsTable.searchName(name);
    }

    public List<Person> getNameByNumber(String number){
           return personsTable.searchNumber(number);
    }

    public List<Person> getYear(String year){
        return personsTable.searchYear(year);
    }
}
