package japbook.jpashop.main;

import japbook.jpashop.domain.Member;
import japbook.jpashop.domain.Order;
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


