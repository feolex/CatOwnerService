package ru.feolex.DaoLayer.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Owner", schema = "dbo")
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String birthDate; // xx.xx.xxxx format

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Cat> cats = new ArrayList<>();

    public Owner(Long ownerId) {
        id = ownerId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id != null ? id.hashCode() : 0,
                name != null ? name.hashCode() : 0,
                birthDate != null ? birthDate.hashCode() : 0
                , cats != null ? cats.size() : 0
        );
    }

    @Override
    public boolean equals(Object object) {
        return this.hashCode() == object.hashCode();
    }

}
