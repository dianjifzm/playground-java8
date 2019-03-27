package org.projectbarbel.playground.makefinal;

import lombok.Getter;

@Getter
public class FinalBeanRaw {

    private final String someString;
    private final int someInt;
    public FinalBeanRaw(String someString, int someInt) {
        super();
        this.someString = someString;
        this.someInt = someInt;
    }
    
}
