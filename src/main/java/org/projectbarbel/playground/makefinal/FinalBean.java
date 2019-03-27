package org.projectbarbel.playground.makefinal;

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
    
    public FinalBean setSomeString(String someString) {
        return builder().withTemplate(this).withSomeString(someString).build();
    }
    
    public FinalBean setSomeInt(int someInt) {
        return builder().withTemplate(this).withSomeInt(someInt).build();
    }
    
    private FinalBean(Builder builder) {
        this.someString = builder.someString == null ? builder.template.getSomeString() : builder.someString;
        this.someInt = builder.someInt == 0 ? builder.template.getSomeInt() : builder.someInt;
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static final class Builder {
        private String someString;
        private int someInt;
        private FinalBean template;

        private Builder() {
        }

        public Builder withSomeString(String someString) {
            this.someString = someString;
            return this;
        }

        public Builder withTemplate(FinalBean template) {
            this.template = template;
            return this;
        }
        
        public Builder withSomeInt(int someInt) {
            this.someInt = someInt;
            return this;
        }

        public FinalBean build() {
            return new FinalBean(this);
        }
    }
    
}
