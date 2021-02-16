package com.twofactorauth.boundary;

import java.util.List;

public interface AbstractBean<T,ID> {
  T create(T entity);
  T edit(T entity);
  boolean remove(T entity);
  T find(ID primaryKey);
  List<T> findAll();
  List<T> findRange(int[] range);
  long count();
}
