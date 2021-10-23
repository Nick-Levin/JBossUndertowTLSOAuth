package com.c123.persistence;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

public interface PersistenceLayer<E> {

    void create(E e) throws Exception;
    Collection<E> receive();
    Optional<E> receiveById(int id);
    void update(E e) throws SQLException;
    void deleteById(int id) throws SQLException;

}
