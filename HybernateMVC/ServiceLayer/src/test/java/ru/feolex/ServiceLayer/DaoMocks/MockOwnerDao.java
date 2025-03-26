package ru.feolex.ServiceLayer.DaoMocks;

import ru.feolex.DaoLayer.Dao.OwnerDaoInterface;
import ru.feolex.DaoLayer.Entities.Owner;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class MockOwnerDao extends MockBaseDao<Owner> implements OwnerDaoInterface {
    @Override
    public Optional<Owner> get(Long id) {
        return DB.get(id) == null ? Optional.empty() : Optional.of(DB.get(id));
    }

    public Collection<Owner> getAll(){
        return DB.values();
    }
    public void update(Owner owner, List<String> params){
        if (DB.containsKey(owner.getId())){
            DB.remove(owner.getId());
            DB.put(owner.getId(), owner);
        }
    }
}
