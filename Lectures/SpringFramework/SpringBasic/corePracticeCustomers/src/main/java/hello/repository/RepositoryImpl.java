package hello.repository;

import hello.customerDto.Customers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositoryImpl implements Repository {

    //Memory DataBase 구현
    private static Map<Long, Customers> store = new HashMap<>();
    //필드에서 직접 Hash들을 넣을수는 없고 Method를 통해서 넣어야 한다.
    private static Long sequence = 0L;


    @Override
    public Customers save(Customers member) {
        ++sequence;
        member.setId(sequence);
        store.put(member.getId(), member);
        return member; //왜 member를 줬지?
    }


    @Override
    public Customers findById(Long id) {
         Customers targetCustomer = store.get(id); //Hashmap

        return targetCustomer;
    }

    @Override
    public Customers findByName(String name) {

        return null;
    }

    @Override
    public List findAll(){

        return null;
    }

}
