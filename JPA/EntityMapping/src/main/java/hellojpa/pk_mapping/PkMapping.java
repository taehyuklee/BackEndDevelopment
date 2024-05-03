package hellojpa.pk_mapping;

import hellojpa.entity.IdentityMember;
import hellojpa.entity.Person;
import hellojpa.entity.SeqMember;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class PkMapping {

    public static String defaultPkMapping(){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        /*---------------하나의 Transaction 단위-------------------*/
        //한 단위의 Transaction마다 EntityManger를 만들어줘야 한다. [고객의 요청마다, Transactino마다 em생성 닫기]
        EntityManager em = emf.createEntityManager();

        //Transaction설정을 해주지 않으면 에러를 내뱉는다.
        EntityTransaction tx = em.getTransaction();

        /*----------------------------------------*/
        tx.begin(); //database transaction을 시작한다

        try{
            Person person = new Person();
            //여기서는 GeneratedValue를 썼기때문에 직접 id에 setting해주면 안된다.
            person.setPersonNm("Lee");

            em.persist(person);

            System.out.println("====================================");
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

    public static String seqStrategyPkMapping (){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        /*---------------하나의 Transaction 단위-------------------*/
        //한 단위의 Transaction마다 EntityManger를 만들어줘야 한다. [고객의 요청마다, Transactino마다 em생성 닫기]
        EntityManager em = emf.createEntityManager();

        //Transaction설정을 해주지 않으면 에러를 내뱉는다.
        EntityTransaction tx = em.getTransaction();

        /*----------------------------------------*/
        tx.begin(); //database transaction을 시작한다

        try{
            SeqMember member = new SeqMember();
            //여기서는 GeneratedValue를 썼기때문에 직접 id에 setting해주면 안된다.
            member.setPersonNm("Lee");

            em.persist(member);

            System.out.println("====================================");
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


    public static String identityStrategyPkMapping(){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        /*---------------하나의 Transaction 단위-------------------*/
        //한 단위의 Transaction마다 EntityManger를 만들어줘야 한다. [고객의 요청마다, Transactino마다 em생성 닫기]
        EntityManager em = emf.createEntityManager();

        //Transaction설정을 해주지 않으면 에러를 내뱉는다.
        EntityTransaction tx = em.getTransaction();

        /*----------------------------------------*/
        tx.begin(); //database transaction을 시작한다

        try{
            IdentityMember member = new IdentityMember();
            //여기서는 GeneratedValue를 썼기때문에 직접 id에 setting해주면 안된다.
            member.setPersonNm("Lee");

            System.out.println("====================================");
            em.persist(member);
            System.out.println("memberId is: " + member.getId());
            System.out.println("====================================");

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
