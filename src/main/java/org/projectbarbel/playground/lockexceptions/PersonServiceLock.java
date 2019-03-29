package org.projectbarbel.playground.lockexceptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.concurrent.ThreadSafe;

import com.rits.cloning.Cloner;

@ThreadSafe
public class PersonServiceLock {
    private final Map<String, Person> persons;
    private final Cloner cloner = new Cloner();
    private final Lock lock = new ReentrantLock();

    public PersonServiceLock(Map<String, Person> persons) {
        this.persons = deepCopy(persons);
    }

    public synchronized Map<String, Person> getLocations() {
        return deepCopy(persons);
    }

    public Person updateAdress(String id, String street, String postalcode, String city) {
        lock.lock();
        try {
            Person person = persons.get(id);
            Person copy = cloner.deepClone(person);
            try {
                person.setStreet(street);
                person.setPostalcode(postalcode);
                person.setCity(city);
            } catch (Exception e) {
                persons.put(copy.getId(), copy);
                throw new IllegalStateException("update failed", e);
            }
            return new Person(person);
        } finally {
            lock.unlock();
        }
    }

    public synchronized Person getPerson(String id) {
        Person person = persons.get(id);
        return person == null ? null
                : new Person(person.getId(), person.getFirstname(), person.getLastname(), person.getStreet(),
                        person.getPostalcode(), person.getCity());
    }

    private static Map<String, Person> deepCopy(Map<String, Person> m) {
        Map<String, Person> result = new HashMap<String, Person>();

        for (String id : m.keySet())
            result.put(id, new Person(m.get(id)));

        return Collections.unmodifiableMap(result);
    }
}
