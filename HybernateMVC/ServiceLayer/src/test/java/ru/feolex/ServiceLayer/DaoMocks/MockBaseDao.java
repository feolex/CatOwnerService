package ru.feolex.ServiceLayer.DaoMocks;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class MockBaseDao<T> {
    private Long id_sequence = 0L;
    protected Map<Long, T> DB = new TreeMap<>();;

    public void save(T object) {
        DB.put(++id_sequence, object);
    }

    public void delete(T object) {
        for (Long l : DB.keySet()) {
            if(Objects.equals(object, DB.get(l))) {
                DB.remove(l);
                System.out.println(object.toString());
            }
        }
    }
}
