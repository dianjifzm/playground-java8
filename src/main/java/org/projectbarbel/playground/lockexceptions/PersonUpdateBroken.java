package org.projectbarbel.playground.lockexceptions;

import org.apache.commons.lang3.Validate;

import io.github.benas.randombeans.api.EnhancedRandom;

public class PersonUpdateBroken {

    public static void main(String[] args) {

        Person person = EnhancedRandom.random(Person.class);
        String originalCityValue = person.getCity();

        System.out.println(String.format("Before: %s", person.toString()));
        synchronized (person) {
            try {
                person.setStreet("23535 Michigan Ave");
                person.setPostalcode("MI 48124");
                Validate.validState(1==0);
                person.setCity("Dearborn, USA");
            } catch (Exception e) {
                // NOP
            }
        }
        System.out.println(String.format("After: %s", person.toString()));

        /**
         * The postal code changed to the new postal code
         */
        Validate.validState(person.getPostalcode().equals("MI 48124"));

        /**
         * The city was not updated -> the object is invalid
         */
        Validate.isTrue(person.getCity().equals(originalCityValue));

    }
}
