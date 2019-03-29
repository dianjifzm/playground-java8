package org.projectbarbel.playground.lockexceptions;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.Validate;

import io.github.benas.randombeans.api.EnhancedRandom;

public class PersonUpdateRecoverLock {

    public static void main(String[] args) {

        Person person = EnhancedRandom.random(Person.class);
        Lock lock = new ReentrantLock();

        System.out.println(String.format("Before: %s", person.toString()));
        Person copy = null;
        lock.lock();
        try {
            copy = new Person(person);
            person.setStreet("23535 Michigan Ave");
            person.setPostalcode("MI 48124");
            Validate.validState(1 == 0);
            person.setCity("Dearborn, USA");
        } catch (Exception e) {
            person = copy;
        } finally {
            lock.unlock();
        }
        System.out.println(String.format("After: %s", person.toString()));

        /**
         * The person did not change -> is "valid"
         */
        Validate.validState(!person.getStreet().equals("23535 Michigan Ave"));
        Validate.validState(!person.getPostalcode().equals("MI 48124"));
        Validate.validState(!person.getCity().equals("Dearborn, USA"));

    }
}
