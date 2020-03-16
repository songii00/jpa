package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Arrays;
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
            //createMember(entityManager);
            //updateMember(entityManager);
            findMember(entityManager);
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

    /**
     * 생성
     * @param entityManager
     */
    private static void createMember(EntityManager entityManager){
        // jpa 모든 변경의 단위는 트랜잭션 안에서 작업해야 함
        //jpa 의 모든 데이터 변경은 트랜잭션 안에서 실행
        Member member = new Member();
        member.setId(2L);
        member.setName("HelloB");
        entityManager.persist(member);
    }

    /**
     * 수정
     * @param entityManager
     */
    private static void updateMember(EntityManager entityManager){
        //JPA 가 관리 // 트랜잭션 커밋하기 직전에 변경을 감지하면 업데이트 쿼리를 날림
        Member member =  entityManager.find(Member.class, 1L);
       member.setName("HelloJPA");
       System.out.println(member.getId() + " " + member.getName());
    }

    private static void findMember(EntityManager entityManager){
        // 객체를 대상으로 쿼리를 짠다
        //JPQL
        // 방언에 맞춰서 디비에 맞게 번역 해줌
        List<Member> result = entityManager.createQuery("select m from Member as m ", Member.class)
                // 페이징
                .setFirstResult(1)
                .setMaxResults(10)
                .getResultList();

        for(Member member : result){
            System.out.println(member.getId() + " " + member.getName());
        }

    }

}


