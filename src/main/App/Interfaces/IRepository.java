package Interfaces;

import java.util.List;

public interface IRepository<T> {
    void add(T entity);
    void update(T entity);
    void delete(T entity);
    T getById(int id);
    T getByString(String str);
    List<T> getAll();
}
