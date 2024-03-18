package mock.server.user.service;

import jakarta.xml.bind.JAXBException;
import lombok.extern.slf4j.Slf4j;
import mock.server.user.domain.entity.UserInfo;
import mock.server.user.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.xml.transform.Result;
import java.io.StringWriter;
import java.util.List;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String saveUser(UserInfo userInfo){
        return userRepository.save(userInfo);
    }

    public List<UserInfo> findAll(){
        return userRepository.findAll();
    }

    public UserInfo getUser(long id){
        return userRepository.getUser(id);
    }

    public String xmlConvert(long id) throws JAXBException {
        UserInfo userInfo =  userRepository.getUser(id);
        String xmlResult = userInfo.xmlConvert();
        System.out.println(xmlResult);
        return xmlResult;
    }

}
