package sample.models;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import sample.utils.HibernateSessionFactoryUtil;

import java.util.List;

public class PersonsTable {

    public Person findById(int id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Person person = session.get(Person.class, id);
        session.close();
        return person;
    }

    public void save(Person person) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(person);
        tx1.commit();
        session.close();
    }

    public void update(Person person) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(person);
        tx1.commit();
        session.close();
    }

    public void delete(Person person) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(person);
        tx1.commit();
        session.close();
    }

    public List<Person> findAll() {
        List<Person> persons = (List<Person>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From Person").list();
        return persons;
    }

    public List<Person> searchName(String name) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Query query = session.createQuery("FROM Person WHERE name = " + name);
        List results = query.list();
        return results;
    }

    public List<Person> searchNumber(String number) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Query query = session.createQuery("FROM Person WHERE number = " + number);
        List<Person> list = query.list();
        return list;
    }

    public List<Person> searchYear(String year) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Query query = session.createQuery("FROM Person WHERE year = " + year);
        List<Person> results = query.list();
        return results;
    }
}