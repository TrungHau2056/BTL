package org.example.btl.dao;

import java.util.List;

public interface BaseDAO<T> {
    public void save(T item);

    public void update(T item);

    public void delete(T item);

    public List<T> findAll();

    public T findById(int id);
}
