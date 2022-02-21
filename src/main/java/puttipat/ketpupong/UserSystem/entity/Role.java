package puttipat.ketpupong.UserSystem.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long roleId;

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private ERole name;

    public Role(ERole name) {
        this.name = name;
    }
}
