package org.projectbarbel.playground.makefinal;

public class FinalBeanCopyExamples {

    public static void main(String[] args) {
        FinalBeanRaw finalBeanRaw = new FinalBeanRaw("someData", 42);
        @SuppressWarnings("unused")
        FinalBeanRaw finalBeanRawChanged = new FinalBeanRaw(finalBeanRaw.getSomeString(), 4711);
        
        FinalBean finalBean = new FinalBean("someData", 42);
        @SuppressWarnings("unused")
        FinalBean finalBeanChanged = finalBean.setSomeInt(4711);
        
    }
}
