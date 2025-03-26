package ru.feolex.DaoLayer.Dao;

import ru.feolex.DaoLayer.Exceptions.UpdateArgsException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

interface DaoInterface<T> {
    Optional<T> get(Long id);
    Collection<T> getAll();

    void save(T t);

    void update(T t, List<String> params) throws UpdateArgsException;

    void delete(T t);
}
