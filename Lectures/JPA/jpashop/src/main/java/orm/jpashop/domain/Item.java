package orm.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "ITEM")
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name= "ITEM_ID")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;
}
