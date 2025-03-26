package ru.feolex.ServiceLayer.DaoMocks;

import lombok.NoArgsConstructor;
import ru.feolex.DaoLayer.Dao.CatDaoInterface;
import ru.feolex.DaoLayer.Entities.Cat;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
public class MockCatDao extends MockBaseDao<Cat> implements CatDaoInterface {
    @Override
    public Optional<Cat> get(Long id) {
        return DB.get(id) == null ? Optional.empty() : Optional.of(DB.get(id));
    }

    public Collection<Cat> getAll() {
        return DB.values();
    }

    public void update(Cat cat, List<String> params) {
        if (DB.containsKey(cat.getId())) {
            DB.remove(cat.getId());
            DB.put(cat.getId(), cat);
        }
    }
}
