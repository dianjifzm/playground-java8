package org.projectbarbel.playground.lockexceptions;

import org.apache.commons.lang3.Validate;

import com.rits.cloning.Cloner;

import io.github.benas.randombeans.api.EnhancedRandom;

public class PersonUpdateRecoverClone {

    public static void main(String[] args) {

        Cloner cloner = new Cloner();
        Person person = EnhancedRandom.random(Person.class);

        System.out.println(String.format("Before: %s", person.toString()));
        synchronized (person) {
            final Person copy = cloner.deepClone(person);
            try {
                person.setStreet("23535 Michigan Ave");
                person.setPostalcode("MI 48124");
                Validate.validState(1==0);
                person.setCity("Dearborn, USA");
            } catch (Exception e) {
                person = copy;
            }
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
