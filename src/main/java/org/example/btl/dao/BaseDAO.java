package org.example.btl.dao;

import org.example.btl.model.HibernateUtils;
import org.hibernate.Session;

import java.util.List;

public interface BaseDAO<T> {
    public void save(T item);

    public void update(T item);

    public void delete(T item);

    public List<T> findAll();

    public T findById(int id);
}
