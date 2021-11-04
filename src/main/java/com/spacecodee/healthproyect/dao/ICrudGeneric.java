package com.spacecodee.healthproyect.dao;

import javafx.collections.ObservableList;

public interface ICrudGeneric<T> {

    ObservableList<T> load();

    ObservableList<T> findByName(String name);

    boolean add(T value);

    boolean update(T value);

    boolean delete(T value);
}
