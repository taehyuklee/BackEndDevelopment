package hello.controller;

import hello.customerDto.Customers;
import hello.repository.Repository;
import hello.repository.RepositoryImpl;

@org.springframework.stereotype.Controller
public class Controller {

    static RepositoryImpl Repo = new RepositoryImpl();
    static Customers members1 = new Customers();
    static Customers members2 = new Customers();
    static Customers members3 = new Customers();


    public static void main(String[] args) {
        members1.setName("Lee");
        members2.setName("Kim");
        members3.setName("Ryan");
        Repo.save(members1);
        Repo.save(members2);
        Repo.save(members3);

        Customers WhoAreYou = Repo.findById(2L);
        System.out.println(WhoAreYou.getName());
        System.out.println("check");
    }


}
