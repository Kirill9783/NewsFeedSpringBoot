package com.news.spring.services;

import java.util.List;

public interface CRUDService<T> {

    T getById(Long id);
    List<T> getAll();
    void create(T item);
    void update(Long id, T item);
    void deleteById(Long id);
}
