package org.projectbarbel.playground.makefinal;

import org.apache.commons.lang3.Validate;

public class FinalBeanCopyExamples {

    public static void main(String[] args) {
        FinalBeanRaw finalBeanRaw = new FinalBeanRaw("someData", 42);
        FinalBeanRaw finalBeanRawChanged = new FinalBeanRaw(finalBeanRaw.getSomeString(), 4711);
        Validate.isTrue(finalBeanRawChanged.getSomeInt() == 4711 & finalBeanRawChanged.getSomeString().equals("someData"));
        System.out.println("worked");
        
        FinalBean finalBean = new FinalBean("someData", 42);
        FinalBean finalBeanChanged = finalBean.copyWithSomeInt(4711);
        Validate.isTrue(finalBeanChanged.getSomeInt() == 4711 & finalBeanChanged.getSomeString().equals("someData"));
        FinalBean finalBeanByBuilder = FinalBean.builder().template(finalBean).someInt(4711).build();
        Validate.isTrue(finalBeanByBuilder.getSomeInt() == 4711 & finalBeanByBuilder.getSomeString().equals("someData"));
        System.out.println("worked");
    }
}
