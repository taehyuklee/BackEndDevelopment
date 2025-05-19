package orm.jpa.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;

@Entity
@Getter
@Table(name = "MEMBER")
public class Member {

    @Id
    private Long id;

    // Unique - if you use unique constraint in @Column, random name will be provided for unique key name
    @Column(name="name", unique = true, length=10, nullable = false) // Only for DDL Function. Not related to Logic
    private String name;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    // if you use LocalDate, LocalDateTime Type, it will be automatically put your DB
    // You don't need to use @Temporal
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob // CLOB, BLOB
    private String description;

    @Transient // only in memory
    private int temp;

    public Member(){

    }

    public Member(Long id, String name){
        this.id = id;
        this.name = name;
    }

}
