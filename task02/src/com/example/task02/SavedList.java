package com.example.task02;

import java.io.*;
import java.util.AbstractList;
import java.util.List;
import java.util.ArrayList;

//Реализуйте класс SavedList, представляющий собой список элементов с произвольным доступом,
// копия которого хранится в виде файла на жестком диске.
//
//SavedList должен обновлять содержимое файла при каждом изменении списка элементов.
//
//При создании нового экземпляра SavedList с указанием существующего файла,
// он должен загрузить список элементов из этого файла.
//
//SavedList должен корректно обрабатывать ситуацию отсутствия файла
// (отсутствие файла означает отсутствие элементов в списке).
//
//Для хранения списка в оперативной памяти можно использовать коллекции из java.util.
//
//Для сохранения и загрузки элементов можно использовать ObjectOutputStream и ObjectInputStream.

public class SavedList<E extends Serializable> extends AbstractList<E> {
    private final File file;
    private List<E> list;

    public SavedList(File file) {
        this.file = file;
        if (file.exists()) {
            try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(file))) {
                list = (ArrayList<E>) input.readObject();
            } catch (Exception e) { }
        } else {
            list = new ArrayList<>();
        }
    }

    private void save() {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file))) {
            output.writeObject(list);
        } catch (Exception e) { }
    }

    @Override
    public E get(int index) {
        return list.get(index);
    }

    @Override
    public E set(int index, E element) {
        E e = list.set(index, element);
        save();
        return e;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public void add(int index, E element) {
        list.add(index, element);
        save();
    }

    @Override
    public E remove(int index) {
        E e = list.remove(index);
        save();
        return e;
    }
}
