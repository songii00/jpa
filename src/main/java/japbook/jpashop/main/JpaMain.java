package japbook.jpashop.main;

import japbook.jpashop.domain.Member;
import japbook.jpashop.domain.Team;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args){
        // before
        // 디비당 하나만 생성됨
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
       // 쉽게 데이터베이스 커넥션 받았다 생각하자
        // 고객의 요청이 올때마다 생성되고 버려짐
        // 쓰레드 간에 공유하면 절대 안됨 (사용하고 버려야 함)
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        try{
            Team team = new Team();
            team.setUsername("TeamA");
            // 연관관계의 주인에 값을 입력하지 않음
            //team.getMembers().add(member);
            entityManager.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.changeTeam(team);
            entityManager.persist(member);


//            entityManager.flush(); // 영속성 컨텍스트 디비랑 싱크 맞춤
//            entityManager.clear(); // 영속성 컨텍스트 클리어

            // 양방향 연관관계 셋팅 시 양쪽 다 값을 셋팅
           // team.getMembers().add(member);
            Team findTeam = entityManager.find(Team.class, team.getId());
            List<Member> members = findTeam.getMembers();

            System.out.println("================");
            for(Member m : members){
                System.out.println("m : " + m.getId());
            }

            // lombok toString 쓰지마라. 무한루프 빠짐
            // 컨트롤러에서 entity 절대 반환하지 마라
            // 1. (Json 생성 라이브러리 쓰는 순간 무한루프)
            // 2. entity 변경 시 api 스펙 바뀌어야함 -> dto 로 변환 반환 해라
            transaction.commit();

        }catch (Exception e){
             transaction.rollback();
        }finally {
            entityManager.close();
        }
        // WAS 내려갈떄 닫아줘야 함
        // 리소스 릴리즈
        entityManagerFactory.close();
    }
}


