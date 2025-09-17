package com.adanali.library.repository;

import com.adanali.library.exceptions.EntityDuplicationException;

import java.util.List;
import java.util.Optional;

public interface RepositoryPattern<E, ID>{
    void add(E entity) throws EntityDuplicationException;
    boolean remove(ID id);
    Optional<E> getById(ID id);
    List<E> getAll();
}
