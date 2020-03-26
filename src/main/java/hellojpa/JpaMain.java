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

            Member member = new Member();
            member.setId(2L);
            member.setRoleType(RoleType.ADMIN);

            entityManager.persist(member);
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


