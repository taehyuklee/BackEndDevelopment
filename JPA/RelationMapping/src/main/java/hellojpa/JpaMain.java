package hellojpa;

import hellojpa.pk_mapping.PkMapping;

public class JpaMain {

    //JPA의 전반적인 내용을 봄
    //static JpaCrudDemo jpaCrudDemo = new JpaCrudDemo();

    //영속성을 알아보기 위한 객체
    //static PersistTime persistTime = new PersistTime();

    //Pk mapping관련 설명 내용
    static PkMapping pkMapping = new PkMapping();

    public static void main(String[] args) {

        pkMapping.seqStrategyPkMapping();

//        pkMapping.identityStrategyPkMapping();

    }



}