package model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class GenericBuilder<T> {
    private final Supplier<T> instantiator;
    private List<Consumer<T>> instanceModifiers = new ArrayList<>();

    /**
     * .
     * @param instantiator .
     */
    public GenericBuilder(Supplier<T> instantiator) {
        this.instantiator = instantiator;
    }

    /**
     * .
     * @param instantiator .
     * @param <T>          .
     * @return .
     */
    public static <T> GenericBuilder<T> of(Supplier<T> instantiator) {
        return new GenericBuilder<T>(instantiator);
    }

    /**
     * .
     * @param consumer .
     * @param value    .
     * @param <U>      .
     * @return .
     */
    public <U> GenericBuilder<T> with(BiConsumer<T, U> consumer, U value) {
        Consumer<T> c = instance -> consumer.accept(instance, value);
        instanceModifiers.add(c);
        return this;
    }

    /**
     * .
     * @return .
     */
    public T build() {
        T value = instantiator.get();
        instanceModifiers.forEach(modifier -> modifier.accept(value));
        instanceModifiers.clear();
        return value;
    }
}