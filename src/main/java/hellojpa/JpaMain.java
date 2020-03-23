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
            //persistcontext(entityManager);
           // modify(entityManager);
            //flush(entityManager);
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

    /**
     * 영속 / 비영속
     * @param entityManager
     */
    private static void persistcontext(EntityManager entityManager){
        // 비영속
        Member member = new Member();
        member.setId(101L);
        member.setName("HelloJPA");

        // 영속
        System.out.println("BEFORE");
        entityManager.persist(member);
        System.out.println("AFTER");

        Member findMember = entityManager.find(Member.class, 101L);
        System.out.println("findMember id = " + findMember.getId());
        System.out.println("findMember name = " + findMember.getName());

        // 준영속
        //entityManager.detach(member);
        // 각채를 삭제한 상태
        //entityManager.refresh(member);
    }

    /**
     * 변경 감지
     * @param entityManager
     */
    public static void modify(EntityManager entityManager){

        Member member = new Member();
        member.setId(150L);
        member.setName("HelloJPA");


        Member findMember = entityManager.find(Member.class, 150L);
        member.setName("ZZZ");

        // persis 호출 해야 되는것이 아닌가?
        // 자바 컬렉션을 다루는 것처럼 데이터를 다루겠다.
        //entityManager.persist(findMember);


    }

    /**
     * 플러쉬
     * @param entityManager
     */
    public static void flush(EntityManager entityManager){

        Member member = new Member();
        member.setId(150L);
        member.setName("HelloJPA");
        entityManager.persist(member);
        // db 로 영속성 컨텍스트에 저장된게 날라감
        // insert 쿼리 즉시 나감
        // 아직 commit 은 안됨
        // 1차 캐시 그대로 유지
        // 영속성 컨텍스 > 쓰기저장 sql 저장소 > 여기에 있는 것만 db에 반영
        // 쿼리 실행 시에 무조건 플러시 로 날리고 쿼리 실행함
        entityManager.flush();

    }

    /**
     * 영속 / 준영속
     * @param entityManager
     */
    private static void persistcontext_2(EntityManager entityManager){

        // 영속 상태
        Member findMember = entityManager.find(Member.class, 150L);
        findMember.setName("ZZZ");

        // 준영속
        // 영속성 컨텍스트에서 관리하지 말라고 끄집어냄
        // 특정 엔티티만
        entityManager.detach(findMember);
        // 영속성 컨텍스트 안에 내용을 다 지움
        entityManager.clear();
    }

}


