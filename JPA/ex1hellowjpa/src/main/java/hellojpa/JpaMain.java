package hellojpa;

import hellojpa.jpademo.JpaCrudDemo;
import hellojpa.persistent.PersistTime;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    static JpaCrudDemo jpaCrudDemo = new JpaCrudDemo();
    static PersistTime persistTime = new PersistTime();

    public static void main(String[] args) {

        persistTime.flush();

    }



}