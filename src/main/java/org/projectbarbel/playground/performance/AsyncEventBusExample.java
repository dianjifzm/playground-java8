package org.projectbarbel.playground.performance;

import java.util.concurrent.Executors;

import org.projectbarbel.histo.event.EventType.DefaultSubscriberExceptionHandler;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class AsyncEventBusExample {

    public static void main(String[] args) {
        EventBus bus = new AsyncEventBus(Executors.newFixedThreadPool(4, r -> {
            Thread t = Executors.defaultThreadFactory().newThread(r);
            t.setDaemon(true);
            return t;
        }), new DefaultSubscriberExceptionHandler());
        bus.register(new EventListener());
        bus.post("string event");
    }
    
    public static class EventListener {
        @Subscribe
        public void stringEvent(String message) {
            System.out.println(message + " executed asynchronously ...");
        }
    }
}
