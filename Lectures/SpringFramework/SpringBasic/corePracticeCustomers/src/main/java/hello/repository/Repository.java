package hello.repository;

import hello.customerDto.Customers;

import java.util.List;

public interface Repository {

    Customers save(Customers member);

    Customers findById(Long id);

    Customers findByName(String name);

    List findAll();


}
