package hellojpa;

import hellojpa.persistent.PersistTime;

public class JpaMain {

    //JPA의 전반적인 내용을 봄
    //static JpaCrudDemo jpaCrudDemo = new JpaCrudDemo();

    //영속성을 알아보기 위한 객체
    static PersistTime persistTime = new PersistTime();

    //Pk mapping관련 설명 내용

    public static void main(String[] args) {

        persistTime.persist();

    }



}