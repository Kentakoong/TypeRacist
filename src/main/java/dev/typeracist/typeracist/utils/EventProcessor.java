package dev.typeracist.typeracist.utils;

import javafx.event.Event;

import java.util.EventListener;

@FunctionalInterface
public interface EventProcessor<T extends Event> extends EventListener {
    T process(T event);
}
