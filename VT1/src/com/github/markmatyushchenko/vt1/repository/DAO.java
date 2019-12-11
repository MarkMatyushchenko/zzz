package com.github.markmatyushchenko.vt1.repository;

public interface DAO<T> {

    void save(Iterable<T> objects);
    Iterable<T> read();

}
