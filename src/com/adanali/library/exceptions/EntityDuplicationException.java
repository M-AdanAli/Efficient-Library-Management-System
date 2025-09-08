package com.adanali.library.exceptions;

public class EntityDuplicationException extends Exception{
    private final Class<?> entityClass;

    public EntityDuplicationException(Class<?> entityClass, String message) {
        super(message);
        this.entityClass = entityClass;
    }

    public Class<?> getEntityClass(){
        return entityClass;
    }
}
