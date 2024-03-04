package mock.server.user.domain.repository;

import mock.server.user.domain.entity.UserInfo;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepository {

    private Map<Long, UserInfo> memoryDb = new HashMap<>();

    private long id=0;

    public String save(UserInfo userInfo){

        userInfo.setId(id);
        memoryDb.put(id, userInfo);
        id++;

        return "success";
    }

    public List<UserInfo> findAll(){
        Set<Long> keys = memoryDb.keySet();
        List<Long> keysList = new ArrayList<>(keys);
        List<UserInfo> returnList = new ArrayList<>();

        for(int i=0; i<keysList.size(); i++){
            returnList.add(memoryDb.get(keysList.get(i)));
        }
        return returnList;
    }

    public UserInfo getUser(long id){
        return memoryDb.get(id);
    }

}
