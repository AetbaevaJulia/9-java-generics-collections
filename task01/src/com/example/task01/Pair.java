package com.example.task01;

import java.util.Objects;
import java.util.function.BiConsumer;

public class Pair <T1, T2> {
    private final T1 First;
    private final T2 Second;
    private Pair(T1 first, T2 second){
        First = first;
        Second = second;
    }

    public T1 getFirst() {
        return First;
    }

    public T2 getSecond() {
        return Second;
    }

    @Override
    public boolean equals(Object object){
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }

        Pair<?, ?> pair = (Pair<?, ?>) object;
        return Objects.equals(First, pair.First) && Objects.equals(Second, pair.Second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(First, Second);
    }

    public static <T1, T2> Pair<T1, T2> of(T1 first, T2 second) {
        return new Pair<>(first, second);
    }

    public void ifPresent(BiConsumer<? super T1, ? super T2> biConsumer) {
        if (First != null && Second != null) {
            biConsumer.accept(First, Second);
        }
    }
}
