package com.cpt.payments.Service;

@FunctionalInterface
public interface Supplier<T> {
    T get();
}
