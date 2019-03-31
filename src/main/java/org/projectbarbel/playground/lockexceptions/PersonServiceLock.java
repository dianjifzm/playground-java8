package org.projectbarbel.playground.lockexceptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.concurrent.ThreadSafe;

import com.google.errorprone.annotations.concurrent.GuardedBy;
import com.rits.cloning.Cloner;

@ThreadSafe
public class PersonServiceLock {
    @GuardedBy("lock")
    private final Map<String, Person> persons;
    private final Cloner cloner = new Cloner();
    private final Lock lock = new ReentrantLock();

    public PersonServiceLock(Map<String, Person> persons) {
        this.persons = deepCopy(persons);
    }

    public Map<String, Person> getLocations() {
        lock.lock();
        try {
            return deepCopy(persons);
        } finally {
            lock.unlock();
        }
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
                return new Person(person);
            } catch (Exception e) {
                persons.put(copy.getId(), copy);
                throw new IllegalStateException("update failed", e);
            }
        } finally {
            lock.unlock();
        }
    }

    public Person getPerson(String id) {
        lock.lock();
        try {
            Person person = persons.get(id);
            return person == null ? null
                    : new Person(person.getId(), person.getFirstname(), person.getLastname(), person.getStreet(),
                            person.getPostalcode(), person.getCity());
        } finally {
            lock.unlock();
        }
    }

    private static Map<String, Person> deepCopy(Map<String, Person> m) {
        Map<String, Person> result = new HashMap<String, Person>();

        for (String id : m.keySet())
            result.put(id, new Person(m.get(id)));

        return Collections.unmodifiableMap(result);
    }
}
