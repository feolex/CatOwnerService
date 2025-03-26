package ru.feolex.ServiceLayer.Models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import ru.feolex.DaoLayer.Entities.Cat;
import ru.feolex.DaoLayer.Entities.Owner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter(AccessLevel.MODULE)
@AllArgsConstructor
@EqualsAndHashCode
public class OwnerModel {
    @Getter(AccessLevel.PUBLIC)
    private Long id;
    @Getter(AccessLevel.PUBLIC)
    private String name;
    @Getter(AccessLevel.PUBLIC)
    private String birthDate; // xx.xx.xxxx format
    private List<CatModel> cats = new ArrayList<>();

    public OwnerModel(Owner owner) {
        this.id = owner.getId();
        this.name = owner.getName();
        this.birthDate = owner.getBirthDate();

        this.cats = new ArrayList<>();
        if (owner.getCats() != null) {
            for (Cat cat : owner.getCats()) {
                cats.add(new CatModel(cat));
            }
        }
    }


    public Owner transform() {
        Owner owner = new Owner();
        owner.setId(id);
        owner.setName(name);
        owner.setBirthDate(birthDate);

        if (this.cats == null || cats.isEmpty()) {
            owner.setCats(new ArrayList<>());
        } else {
            owner.setCats(this.transform().getCats());
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
