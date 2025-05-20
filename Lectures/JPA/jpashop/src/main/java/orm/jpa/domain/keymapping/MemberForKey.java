package orm.jpa.domain.keymapping;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class MemberForKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Unique - if you use unique constraint in @Column, random name will be provided for unique key name
    @Column(name="name", unique = true, length=10, nullable = false) // Only for DDL Function. Not related to Logic
    private String username;

    private Integer age;


}
