package shop.jnjeaaaat.easyrsv.domain.model;

import lombok.*;

import javax.persistence.*;

/**
 * Shop Entity
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
public class Shop extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 매장 이름
    @Column(nullable = false)
    private String name;

    // 매장 설명
    @Column(nullable = false)
    private String description;

    // 매장 위치
    @Column(nullable = false)
    private String location;

    // 상점을 등록한 유저 id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id")
    @ToString.Exclude
    private User owner;

}
