package ru.feolex.DaoLayer.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "CatColor", schema = "dbo")
public class CatColor {
    @Id
    private Long id;

    private String colorName;

    @OneToMany(mappedBy="color")
    private Set<Cat> items;

    public CatColor(Long id, String colorName){
        this.id = id;
        this.colorName = colorName;
    }
}
