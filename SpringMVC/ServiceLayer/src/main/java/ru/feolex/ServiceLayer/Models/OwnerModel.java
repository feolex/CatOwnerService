package ru.feolex.ServiceLayer.Models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import ru.feolex.DaoLayer.Entities.Cat;
import ru.feolex.DaoLayer.Entities.Owner;
import ru.feolex.ServiceLayer.Models.Serialization.OwnerModelDeserializator;
import ru.feolex.ServiceLayer.Models.Serialization.OwnerModelSerializator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter(AccessLevel.MODULE)
@EqualsAndHashCode
@SuperBuilder
@JsonSerialize(using = OwnerModelSerializator.class)
@JsonDeserialize(using = OwnerModelDeserializator.class)
public class OwnerModel {

    @Getter(AccessLevel.PUBLIC)
    private Long id;

    @Getter(AccessLevel.PUBLIC)
    private String name;

    @Getter(AccessLevel.PUBLIC)
    private String birthDate; // xx.xx.xxxx format

    @Getter //for tests
    private List<CatModel> cats = new ArrayList<>();

    public OwnerModel(Long id) {
        this.id = id;
    }

    public OwnerModel(Owner owner) {
        this.id = owner.getId();
        this.name = owner.getName();
        this.birthDate = owner.getBirthDate();

        if (owner.getCats() == null) {
            this.cats = new ArrayList<>();
            return;
        }

        this.cats = owner
                .getCats().stream()
                .map(Cat::getId)
                .map(CatModel::new)
                .toList();
    }

    public OwnerModel(Long id, String name, String birthDate, List<CatModel> cats) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.cats = cats;
    }

    public List<CatModel> getCats() {
        return this.cats == null ? new ArrayList<>() : this.cats;
    }

    public List<Long> getCatsIds() {
        return this.cats == null ? new ArrayList<>() : this.cats.stream().map(CatModel::getId).toList();
    }

//    OwnerModel(Owner owner, TreeMap<Long, CatModel> startedCatModels) {
//        this.id = owner.getId();
//        this.name = owner.getName();
//        this.birthDate = owner.getBirthDate();
//
//        this.cats = new ArrayList<>();
//        for (Long l : startedCatModels.keySet()) {
//            if (startedCatModels.get(l).getOwner().equals(this)) {
//                this.cats = startedCatModels.values().stream().toList();
//                return;
//            }
//        }
//
//        Set<Cat> set = owner.getCats().stream().collect(Collectors.toSet());
//        for (var l : set) {
//            if(startedCatModels.keySet().contains(l)){
//                return;
//            }
//        };
//        if (owner.getCats() != null) {
//            for (Cat cat : owner.getCats()) {
//                startedCatModels.put(cat.getId(), new CatModel(cat, startedCatModels));
//                cats.add(new CatModel(cat, startedCatModels));
//            }
//        }
//    }

    public Owner transform() {
        Owner owner = new Owner();
        owner.setId(id);
        owner.setName(name);
        owner.setBirthDate(birthDate);

        if (this.cats == null || cats.isEmpty()) {
            owner.setCats(new ArrayList<>());
        } else {
            owner.setCats(transformCollection(this.cats));
        }

        return owner;
    }

    private static List<Cat> transformCollection(Collection<CatModel> models) {
        List<Cat> cats = new ArrayList<>();
        for (CatModel model : models) {
            cats.add(model.transform());
        }

        return cats;
    }

    public List<String> getFieldsAsString() {
        List<String> result = new ArrayList<>(3);

        result.add(name);
        result.add(birthDate);

        for (CatModel cat : this.cats) {
            result.add(String.valueOf(cat.getId()));
        }

        return result;
    }

    public List<String> getCatsNames() {
        List<String> result = new ArrayList<>();
        for (CatModel cat : cats) {
            result.add(cat.getName());
        }

        return result;
    }
}
