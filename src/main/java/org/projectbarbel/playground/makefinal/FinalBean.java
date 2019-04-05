package org.projectbarbel.playground.makefinal;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class FinalBean {
    
    private final String someString;
    private final int someInt;
    
    public FinalBean(String someString, int someInt) {
        super();
        this.someString = someString;
        this.someInt = someInt;
    }
    
    @Builder
    private FinalBean(String someString, int someInt, FinalBean template) {
        super();
        this.someString = someString == null ? template.getSomeString() : someString;
        this.someInt = someInt == 0 ? template.getSomeInt() : someInt;
    }
    
    public FinalBean copyWithSomeString(String someString) {
        return builder().template(this).someString(someString).build();
    }
    
    public FinalBean copyWithSomeInt(int someInt) {
        return builder().template(this).someInt(someInt).build();
    }
    
}
