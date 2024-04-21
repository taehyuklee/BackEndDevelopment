package hellojpa.persistent;

import hellojpa.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * JPA 영속성 관련한 내용 test해본 것
 * '어떠한 객체에 대한 영속화란?'
 *  한 마디로 어떠한 객체가 JPA가 관리하는 메모리 버퍼에 저장되어 관리대상이 된 상태를 의미한다 (메모리에 저장되어 객체가 관리되는 상태)
 *  -> 여기서 관리란?
 *   Applicatino에서 엔티티를 조회하거나(1차 캐시), 수정할때(더티 체크)마다 자동으로 감지하여 매번 쿼리가 날아가는 것을 막아주는 등
 *   엔티티의 상태를 확인해가며 자동수정, 캐싱 등의 관리를 해준다.
* */
public class PersistTime {

    /***
     * [1차 Cache에 관련된 내용 persist를 하면 1차 Cache에 저장됨]
     * -> find해왔을때나 내가 비영속성에 있던 객체를 영속화해도 다 1차 Cache에 들어가게 된다.
     * 1차 캐시에 (id랑, 객체, 스냅샷) 저장
     * @return
     */
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
            //여기서 persist를 통해 쓰기 지연 SQL을 저장해놨다가 한 번에 보낼수가 있다.
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
        /*----------------------------------------*/
        emf.close();
        return "Success";
    }

    /***
     * [영속성 컨테이너 안에 '쓰기지연 SQL저장소' 관련 내용]
     * 1차캐시에 최초로 영속화한 그 시점의 객체 상태를 스냅샷으로 찍어 놓고 후에 flush하는 시점에 스냅샷과 비교하여 변경된 것들에 대해
     * 비교를 하여 update쿼리를 만들어 SQL저장소에 UPDATE Query를 넣고 DB에 SQL을 날려준다. (변경감지)
     * flush라는 것이 SQL저장소의 SQL들을 DB에 반영해주는 것을 의미한다 (내부 캐시나를 비우는걸 의미하는게 아님)
     * -> 여기서 쓰기지연 SQL저장소에 있는 Query들만 쭉 flush해서 없어진다.
     *
     * * flush 느낌상 씻어내는거같아서 비우는 느낌이 날수도 있음
     * 그리고, 쓰기지연은 sql저장소 덕분에 가능해진것. (한 번에 쿼리를 날려주니까 flush할때)
     */
    public static String dirtyCheck(){

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
            // Database에서 ID가1인 member를 조회한다 (조회는 자동으로 flush가 되나보다)
            Member memeber = em.find(Member.class, 1L);
            // 이렇게 찾아온 member는 1차캐시 엔티티 및 상태가 저장될 것이다. (영속화된다)

            //member이름을 바꿔보자
            memeber.setName("Lee");

            /**
             * set했으니까 다시 바뀐 내용을 영속화해야할까? [X]
             * set을 한 member entity는 이미 JPA관리 상태하에 있어 변화가 있으면 flush할때 알아서 감지되어 update query를 생성한다.
             * */
            //em.persist(memeber);

            System.out.println("====================================");
            tx.commit(); //트랜잭션 commit시점이전에 flush가 된다 (AUTO상태일때)

        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close(); //사용하면 꼭 닫아줘야 한다 왜?
        }
        /*----------------------------------------*/
        emf.close();
        return "Success";
    }

    public static String flush(){

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
            Member member2 = new Member(2L, "HelloA");
            Member member3 = new Member(3L, "HelloA");

            //영속화
            em.persist(member2);
            em.persist(member3);

            //flush하면 DB에 Query가 낭라간다. (원래 아래 절취선 기준으로 commit하면 날아갔었잖아)
            em.flush();

            System.out.println("====================================");
            em.flush(); //여기서 의문 1차캐시는 flush해도 그대로 남아있는데, 쓰기지연 SQL저장소는?

            //영속성에서 빼줘야 1차캐시말고 DB에서 조회하는 것을 확인해볼 수 있다. (따라서 detach해서 DB에 쿼리날아가서 잘 가지고 오는지 확인)
            // 이 부분에서 확인하고 싶은건 commit을하지 않더라도 transaction구간에서 Query를 날려 DB에 반영해주는건가?
            em.detach(member2);
            
            Member targeMember = em.find(Member.class, 2L);
            System.out.println(targeMember);

            System.out.println("====================================");
            //commit을하지 않으면 DB에 Query가 날아가도 최종반영되지는 않는다
            //Transaction범위 안에서는 Query를 날려 순간 반영해주지만, commit을 하지 않으면 rollback시켜버린다.
            // (확인해봄)
            //tx.commit();

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
