package hellojpa;

import hellojpa.pk_mapping.PkMapping;

public class JpaMain {

    //Pk mapping관련 설명 내용
    static PkMapping pkMapping = new PkMapping();

    public static void main(String[] args) {

        pkMapping.seqStrategyPkMapping();

//        pkMapping.identityStrategyPkMapping();

    }



}