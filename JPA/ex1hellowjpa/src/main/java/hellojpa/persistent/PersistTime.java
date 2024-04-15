package hellojpa.persistent;

import hellojpa.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class PersistTime {

    public static String persist(){

        //아까 persistence.xml의  <persistence-unit name="hello"> 즉, DB당 emf를 하나씩 생성해줘야 한다
        //EntityManagerFactory는 DB에 대한 연결성 관리를 해주고 있는 객체라 행각하면 된다.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        /*---------------하나의 Transaction 단위-------------------*/
        //한 단위의 Transaction마다 EntityManger를 만들어줘야 한다. [고객의 요청마다, Transactino마다 em생성 닫기]
        EntityManager em = emf.createEntityManager();

        //Transaction설정을 해주지 않으면 에러를 내뱉는다.
        EntityTransaction tx = em.getTransaction();

        /*----------------------------------------*/
        tx.begin(); //database transaction을 시작한다

        try{

            //비영속
            Member member1 = new Member(1L, "HelloA");
            Member member2 = new Member(2L, "HelloA");

            //영속 (JPA 영속에서 관리가 되는 것이다) - persist() API를 날릴때는 Query가 날아가지 않는다.
            em.persist(member1);
            em.persist(member2);

            System.out.println("====================================");
            /* commit 할때 Query가 두 번 날아간다. */
            tx.commit(); //transactino commit

        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close(); //사용하면 꼭 닫아줘야 한다 왜?
        }

        emf.close();
        /*----------------------------------------*/
        return "Success";
    }
}
