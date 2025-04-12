package me.vz11.zoomies.event;

import java.lang.reflect.Method;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventBus {
    private static final CopyOnWriteArrayList<Object> listeners = new CopyOnWriteArrayList<>();

    public static void register(Object listener) {
        listeners.add(listener);
    }

    public static void post(Event event) {
        for (Object listener : listeners) {
            processListener(listener, event);
        }
    }

    private static void processListener(Object listener, Event event) {
        for (Method method : listener.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Event.EventHandler.class) &&
                method.getParameterCount() == 1 && 
                method.getParameterTypes()[0] == event.getClass()) {
                try {
                    method.setAccessible(true);
                    method.invoke(listener, event);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}