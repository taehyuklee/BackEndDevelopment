package hellojpa.jpademo;

import hellojpa.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaCrudDemo {

    public static String insert(){

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
            Member member = new Member();
            member.setId(1L);
            member.setName("HelloA");
            em.persist(member);

            tx.commit(); //transactino commit

        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close(); //사용하면 꼭 닫아줘야 한다 왜?
        }
        /*----------------------------------------*/

        emf.close();
        return "Success";
    }

    public static String update(){

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
            Member oldMember = em.find(Member.class, 1L);
            System.out.println(oldMember.getId() + " " + oldMember.getName());

            tx.commit(); //transactino commit

        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close(); //사용하면 꼭 닫아줘야 한다 왜?
        }
        /*----------------------------------------*/

        emf.close();
        return "Success";
    }

    public static String delete(){

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
            Member oldMember = em.find(Member.class, 1L);
            em.remove(oldMember);

            tx.commit(); //transactino commit

        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close(); //사용하면 꼭 닫아줘야 한다 왜?
        }
        /*----------------------------------------*/

        emf.close();
        return "Success";
    }

    public static String jpql(){

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
            List<Member> memberList = em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(5)
                    .setMaxResults(5)
                    .getResultList();

            for(Member member : memberList){
                System.out.println("member.name = " + member.getName());
            }

            tx.commit(); //transactino commit

        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close(); //사용하면 꼭 닫아줘야 한다 왜?
        }
        /*----------------------------------------*/

        emf.close();
        return "Success";
    }

}
