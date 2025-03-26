package ru.feolex.ServiceLayer.Models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import ru.feolex.DaoLayer.Entities.Cat;
import ru.feolex.ServiceLayer.Enums.Color;
import ru.feolex.ServiceLayer.Enums.ColorMapper;
import ru.feolex.ServiceLayer.Models.Serialization.CatModelDeserializator;
import ru.feolex.ServiceLayer.Models.Serialization.CatModelSerializator;

import java.util.*;
import java.util.stream.Collectors;

@Getter(AccessLevel.PUBLIC)
@EqualsAndHashCode
@SuperBuilder
@JsonSerialize(using = CatModelSerializator.class)
@JsonDeserialize(using = CatModelDeserializator.class)
public class CatModel implements Comparable<CatModel>{
    private Long id;
    private String name;
    private String birthDate; //xx.xx.xxxx format
    private String breed; // порода
    private Color color;

    private OwnerModel owner;
    @Getter(AccessLevel.MODULE)
    private Set<CatModel> friendsCats;

    public CatModel(Long id) {
        this.id = id;
    }

    ;

    public CatModel(Cat cat) {
        this(cat, new TreeMap<>());
    }

    public CatModel(Long id, String name, String birthDate, String breed, Color color, OwnerModel owner, Set<CatModel> friendsCats) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.breed = breed;
        this.color = color;
        this.owner = owner;
        this.friendsCats = friendsCats;
    }

    public CatModel(Long id, String name, String birthdate, Color color, String breed, Long ownerId, List<Long> friendsIdsList) {
        this.id = id;
        this.name = name;
        this.birthDate = birthdate;
        this.breed = breed;
        this.color = color;

        this.owner = new OwnerModel(ownerId);
        this.friendsCats = friendsIdsList
                .stream().map(CatModel::new)
                .collect(Collectors.toSet());
    }

    public List<Long> getFriendsIds() {
        return this.friendsCats == null ? new ArrayList<>() : this.friendsCats.stream().map(CatModel::getId).toList();
    }

    CatModel(Cat cat, TreeMap<Long, CatModel> startedCatModels) {
        this.id = cat.getId();
        this.name = cat.getName();
        this.birthDate = cat.getBirthDate();
        this.breed = cat.getBreed();
        this.color = ColorMapper.MapColorId(cat.getColor().getId());
        this.owner = cat.getOwner() == null ? null : new OwnerModel(cat.getOwner());

        this.friendsCats = new TreeSet<>();
        if (startedCatModels.containsKey(cat.getId())) {
            return;
        }
        startedCatModels.put(this.id, this);
        if (cat.getFriendsCats() != null) {
            for (Cat friendCat : cat.getFriendsCats()) {
                friendsCats.add(new CatModel(friendCat, startedCatModels));
            }
        }
        friendsCats.remove(this);
    }

    public Cat transform() {

        if (this.friendsCats == null || friendsCats.isEmpty()) {

            return new Cat(
                    id,
                    name,
                    birthDate,
                    breed,
                    ColorMapper.MapColor(color),
                    owner == null ? null : owner.transform(),
                    new TreeSet<>());
        } else {

            return new Cat(
                    id,
                    name,
                    birthDate,
                    breed,
                    ColorMapper.MapColor(color),
                    owner == null ? null : owner.transform(),
                    CatModel.transformCollection(friendsCats));
        }
    }

    private static Set<Cat> transformCollection(Set<CatModel> models) {
        Set<Cat> catSet = new TreeSet<>();
        for (CatModel model : models) {
            catSet.add(model.transform());
        }
        return catSet;
    }

    public List<String> getFieldsAsString() {

        List<String> result = new ArrayList<>(6);

        result.add(name);
        result.add(birthDate);
        result.add(breed);
        result.add(String.valueOf(ColorMapper.MapColor(color)));
        result.add(String.valueOf(owner == null ? "NULL" : owner.getId()));

        for (CatModel friendsCat : friendsCats) {
            result.add(String.valueOf(friendsCat.getId()));
        }

        return result;
    }

    @Override
    public String toString() {
        return String.valueOf(getFieldsAsString());
    }

    public List<CatModel> getFriendsCatsList() {
        return this.friendsCats.stream().toList();
    }

    @Override
    public int compareTo(CatModel o) {
        if (Objects.equals(o.id, this.id)) {
            return 0;
        }
        if (o.id > this.id) {
            return -1;
        } else {
            return 1;
        }
    }
}
