package hellojpa.pk_mapping.template;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public abstract class TransactionTemplate {

    public String pkMappingTemplate(){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        /*---------------하나의 Transaction 단위-------------------*/
        //한 단위의 Transaction마다 EntityManger를 만들어줘야 한다. [고객의 요청마다, Transactino마다 em생성 닫기]
        EntityManager em = emf.createEntityManager();

        //Transaction설정을 해주지 않으면 에러를 내뱉는다.
        EntityTransaction tx = em.getTransaction();

        /*----------------------------------------*/
        tx.begin(); //database transaction을 시작한다

        try{
            entitySetting();
        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close(); //사용하면 꼭 닫아줘야 한다 왜?
        }
        /*----------------------------------------*/
        emf.close();
        return "Success";
    }

    public abstract String entitySetting();
}
