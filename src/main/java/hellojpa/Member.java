package hellojpa;



import javax.persistence.Entity;
import javax.persistence.Id;

// 테이블 명
@Entity // jpa 사용하는 애구나. 관리해야겠다 알려줌
public class Member {

    @Id
    private Long id;
    //@Column(name="username")
    private String name;

    // jpa 는 기본 생성자 필요
    public Member(){
    }

    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
