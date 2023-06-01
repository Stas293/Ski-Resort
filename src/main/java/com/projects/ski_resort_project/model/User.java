package com.projects.ski_resort_project.model;

import com.projects.ski_resort_project.listeners.UserListener;
import com.projects.ski_resort_project.utility.FullNameInterface;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.projects.ski_resort_project.enums.Defaults.SPACE;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@DynamicUpdate
@Setter
@ToString
@EntityListeners(UserListener.class)
@Table(name = "user_list")
public class User implements BaseEntity<Long>, FullNameInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Embedded
    private PersonalInfo personalInfo;

    private String password;

    private Boolean enabled;

    private Boolean oAuth2;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Builder.Default
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @ToString.Exclude
    private List<Role> roles = new ArrayList<>();

    @OneToMany(
            mappedBy = "user",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    @ToString.Exclude
    @Builder.Default
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<Feedback> feedbacks = new ArrayList<>();

    public String getFullName() {
        return personalInfo.getFirstname() + SPACE.getValue() + personalInfo.getLastname();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
