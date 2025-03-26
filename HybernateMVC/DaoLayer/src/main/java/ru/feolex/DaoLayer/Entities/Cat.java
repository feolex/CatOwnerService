package ru.feolex.DaoLayer.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Cat", schema = "dbo")
public class Cat implements Comparable<Cat> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String birthDate; //xx.xx.xxxx format
    private String breed; // порода

    @ManyToOne
    @JoinColumn(name = "color", nullable = true)
    private CatColor color;

    @ManyToOne(cascade = CascadeType.ALL)
    private Owner owner;

    @ManyToMany
    @JoinTable(name = "CatFriends",
            joinColumns = @JoinColumn(name = "cat_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_cat_id"))
    Set<Cat> friendsCats = new TreeSet<>();

    @Override
    public int hashCode() {
        return Objects.hash(
                id != null ? id.hashCode() : 0,
                name != null ? name.hashCode() : 0,
                birthDate != null ? birthDate.hashCode() : 0,
                breed != null ? breed.hashCode() : 0,
                color != null ? color.hashCode() : 0,
                owner != null ? owner.hashCode() : 0
               , friendsCats != null ? friendsCats.size() : 0
        );
    }

    @Override
    public boolean equals(Object object) {
        return this.hashCode() == object.hashCode();
    }

    @Override
    public int compareTo(Cat o) {
        if (Objects.equals(o.id, this.id)) {
            return 0;
        }
        if (o.id > this.id) {
            return -1;
        } else {
            return 1;
        }
    }

    @Override
    public String toString() {
        List<String> result = new ArrayList<>();
        result.add("ID: " + (this.id == null ? "NULL" : this.id));
        result.add("Name: " + (this.name == null ? "NULL" : this.name));
        result.add("Birthdate: " + (this.birthDate == null ? "NULL" : this.birthDate));
        result.add("Breed: " + (this.birthDate == null ? "NULL" : this.color));
        result.add("OwnerId: " + (this.owner == null ? "NULL_OWNER" : this.owner.getId() == null ? "NULL_ID" : this.owner.getId()));
        result.add("Friends_Count: " + (this.friendsCats == null ? "NULL_FRIENDS" : this.friendsCats.size()));
        if (this.friendsCats != null && !this.friendsCats.isEmpty()) {
            result.add("Friends_id: ");
            for (Cat friendsCat : this.friendsCats) {
                result.add(String.valueOf(friendsCat.getId()));
            }
        }

        StringBuilder result_string = new StringBuilder();
        for (String s : result) {
            result_string.append(s);
            result_string.append('\n');
        }

        return result_string.toString();
    }
}
