package org.projectbarbel.playground.confinement;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

import com.rits.cloning.Cloner;

public class ThreadConfinedProcessor<T> {

    private final BlockingQueue<T> subjects;

    public ThreadConfinedProcessor(int capacity, boolean fair) {
        subjects = new ArrayBlockingQueue<T>(capacity, fair);
    }

    private T readSource() {
        return subjects.poll();
    }

    private T process(UnaryOperator<T> processor, T subject) {
        T clone;
        clone = new Cloner().deepClone(subject);
        return processor.apply(clone);
    }

    private void writeTarget(Consumer<T> writer, T subject) {
        writer.accept(subject);
    }

    void process(UnaryOperator<T> processor, Consumer<T> writer) {
        T subject = readSource();
        T processed = process(processor, subject);
        writeTarget(writer, processed);
    }

}
