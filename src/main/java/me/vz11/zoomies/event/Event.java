package me.vz11.zoomies.event;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Event {
    public interface Listener {
        void invoke(Event event);
    }
    
    @Retention(RetentionPolicy.RUNTIME)
    public @interface EventHandler {}
}