package ru.feolex.ServiceLayer.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.feolex.DaoLayer.Dao.CatRepository;
import ru.feolex.DaoLayer.Entities.Cat;
import ru.feolex.OperationClasses.OperationResult;
import ru.feolex.ServiceLayer.Models.CatModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CatService implements CatServiceInterface {

    @Autowired
    private CatRepository catRep;

    @Override
    public OperationResult addCat(CatModel cat) {
        Cat catRegistred = catRep.save(cat.transform());

        if (catRep.existsById(catRegistred.getId())) {
            return new OperationResult.Success();
        } else return new OperationResult.Failure("By unkhown problem cat didn't added to db!");
    }

    @Override
    public OperationResult updateCat(CatModel cat) {
        Cat catRegistred = catRep.save(cat.transform());

        if (catRep.existsById(catRegistred.getId())) {
            return new OperationResult.Success();
        } else return new OperationResult.Failure("By unkhown problem cat didn't update in db!");
    }

    @Override
    public OperationResult deleteCat(CatModel cat) {
        Cat transformedCat = cat.transform();
        catRep.delete(transformedCat);
        if (!catRep.existsById(transformedCat.getId())) {
            return new OperationResult.Success();
        } else return new OperationResult.Failure("By unkhown problem cat didn't delete from db!");
    }

    @Override
    public OperationResult getCatById(Long id) {
        Optional<Cat> result = catRep.findById(id);
        if (result.isEmpty()) {
            return new OperationResult.Failure("Cannot find cat with this id!");
        }

        return new OperationResult.SuccessWithMeaning<CatModel>(new CatModel(result.get()));
    }

    @Override
    public OperationResult getAllCatsOrderedById() {
        List<Cat> resultCats = catRep.findAll();
        if (!resultCats.isEmpty()) {
            List<CatModel> resultModels = new ArrayList<>();
            for (Cat resultCat : resultCats) {
                resultModels.add(new CatModel(resultCat));
            }

            return new OperationResult.SuccessWithMeaning<List<CatModel>>(resultModels);
        }

        return new OperationResult.Failure("No Cats has been found!");
    }

//    private Cat transform(CatModel catModel){
//
//            if (catModel.getFriendsCats() == null || catModel.getFriendsIds().isEmpty()) {
//
//                return new Cat(
//                        catModel.getId(),
//                        catModel.getName(),
//                        catModel.getBirthDate(),
//                        catModel.getBreed(),
//                        ColorMapper.MapColor(catModel.getColor()),
//                        catModel.getOwnerId() == null ? null : new,
//                        new TreeSet<>());
//            } else {
//
//                return new Cat(
//                        id,
//                        name,
//                        birthDate,
//                        breed,
//                        ColorMapper.MapColor(color),
//                        owner == null ? null : owner.transform(),
//                        CatModel.transformCollection(friendsCats));
//            }
//    }
//
//    private Collection<Cat> transformCollection(Collection<CatModel> models){
//         {
//            Set<Cat> catSet = new TreeSet<>();
//            for (CatModel model : models) {
//                catSet.add(model.transform());
//            }
//            return catSet;
//
//    }

}
