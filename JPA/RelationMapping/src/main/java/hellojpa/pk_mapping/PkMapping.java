package hellojpa.pk_mapping;

import hellojpa.entity.IdentityMember;

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
            SeqMember member1 = new SeqMember();
            //여기서는 GeneratedValue를 썼기때문에 직접 id에 setting해주면 안된다.
            member1.setPersonNm("Lee");

            SeqMember member2 = new SeqMember();
            //여기서는 GeneratedValue를 썼기때문에 직접 id에 setting해주면 안된다.
            member2.setPersonNm("Kim");

            SeqMember member3 = new SeqMember();
            //여기서는 GeneratedValue를 썼기때문에 직접 id에 setting해주면 안된다.
            member3.setPersonNm("Jo");

            System.out.println("====================================");
            em.persist(member1);
            System.out.println("member id: " + member1.getId());

            em.persist(member2);
            System.out.println("member id: " + member2.getId());

            em.persist(member3);
            System.out.println("member id: " + member3.getId());
            System.out.println("====================================");
            /*
            * allocation이 50이라고 생각하면 처음 Table이 create 되었을때
            * DB next Value = 1 이고 current Seq = 1이 된다.
            * 
            * 여기서 영속화하 한 번 더 일어날때 (다른 entity에 대해)
            * next Value와 current seq가 같아지므로 next value를 가져와야 한다
            *
            * (allocation 50일때)
            * DB next value = 51이 되고 current Seq = 2가 된다.
            * -- 다음 Entity 영속화 insert --
            * DB next value = 51(유지) current Seq = 3이 된다.
            *
            * 쭉 올라가다가
            *
            * DB next value = 51 과 current Seq = 51이 되고 그 다음 Entity가 영속화(insert)가 될때
            * DB next value = 101이 되고 current Seq = 52가 된다.
            * 
            * 만약 allocation=1이라면, 매번 next value를 call하는 API가 나가게 될 것이다.
            *
            * */

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
