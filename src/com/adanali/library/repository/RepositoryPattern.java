package com.adanali.library.repository;

import java.util.List;
import java.util.Optional;

public interface RepositoryPattern<E, ID>{
    boolean add(E entity);
    boolean remove(ID id);
    Optional<E> getById(ID id);
    List<E> getAll();
}
