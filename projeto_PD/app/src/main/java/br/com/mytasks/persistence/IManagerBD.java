package br.com.mytasks.persistence;

import java.util.List;

public interface IManagerBD <T>{

    void insert(T ob);

    void update(T ob,  long id);

    void delete(long id);

    List<T> findAll();

}
