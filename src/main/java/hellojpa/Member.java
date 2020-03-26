package hellojpa;



import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

// 테이블 명
@Entity
//@Table(name = "MBR") // insert into MBR
//@Table(uniqueConstraints = "unique이름")
public class Member {

    public Member(){
    }

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO) // db 방언에 맞춰서 자동적으로 생성
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 생성을 데이터베이스에 위임, persist할 때 insert 쿼리가 수행되고
    // 다른 전략들은 commit 할때 insert 가 날라감
    // 버퍼링 안됨
    //@GeneratedValue(strategy = GenerationType.SEQUENCE) // 데이터 타입 Long으로 해야함(크기떄문에)
    // 버퍼링 가능
    // persist 할때 시퀀스만 살짝 가서 가져옴
    private Long id;

    @Column(name = "name")// db 컬럼명
    private String username;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob //varchar를 넘어서는 큰 컨텐츠
    private String description;

    @Transient // db와는 관련 없는 메모리에서 사용되는 변수 정의시 사용
    private String temp;

    private LocalDate testLocalDate; // date 로 맵핑
    private LocalDateTime testLocalDatetime; // timestamp 로 맵핑

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }
}
