package ru.feolex.SecurityLayer.Security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import ru.feolex.DaoLayer.Entities.Owner;
import ru.feolex.SecurityLayer.Security.Serialization.ApiUserDeserialization;
import ru.feolex.SecurityLayer.Security.Serialization.ApiUserSerialization;

import java.util.Objects;


@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ApiUser", schema = "dbo")
@JsonSerialize(using = ApiUserSerialization.class)
@JsonDeserialize(using = ApiUserDeserialization.class)
public class ApiUser {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Getter
    private Long id;

    @Column
    @Getter
    @Setter
    private String email;

    @Column
    @Getter
    @Setter
    private String password;

    @Column
    @Getter
    @Setter
    private Role role;

    @OneToOne
    @Getter
    private Owner owner;

    @Transient
    @JsonIgnore
    @Getter
    private Long ownerId;

    public ApiUser(Long id, String email, String password, Role role, Owner owner) {
        this.setEmail(email);
        this.setPassword(password);
        this.setRole(role);
        this.owner = owner;
        this.ownerId = owner.getId();
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        ApiUser apiUser = (ApiUser) o;
        return getId() != null && Objects.equals(getId(), apiUser.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
