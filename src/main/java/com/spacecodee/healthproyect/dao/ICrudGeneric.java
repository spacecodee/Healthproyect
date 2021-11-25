package com.spacecodee.healthproyect.dao;

import java.util.ArrayList;

public interface ICrudGeneric<T> {

    ArrayList<T> load();

    ArrayList<T> findValue(T value);

    boolean add(T value);

    boolean update(T value);

    boolean delete(T value);
}
