package br.com.mytasks.dao;

import java.util.List;

public interface IDAO<T> {

    void save(T instance) ;

    void update(T instance, long id) ;

    T findById(long id) ;

    List<T> findAll() ;

    void delete(long id);

}
