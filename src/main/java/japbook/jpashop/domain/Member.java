package japbook.jpashop.domain;

import javax.persistence.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Entity
public class
Member {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    // 외래키가 있는 곳을 주인으로 정해라
    // 진짜 매핑 - 연관관계의 주인
    // N : 1 의 다 쪽이 연관관계의 주인이 됨
    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Team getTeam() {
        return team;
    }

    // 연관관계 편의 메서드는 한쪽에만! N, 1 아무쪽에나 설정해도 됨. 둘다 있으면 무한루프 될수 있음
    public void changeTeam(Team team) {
        // 연관관계 편의 메소드를 생성하자
        this.team = team;
        team.getMembers().add(this);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
