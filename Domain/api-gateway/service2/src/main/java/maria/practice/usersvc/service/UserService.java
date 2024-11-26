//package maria.practice.usersvc.service;
//
//import maria.practice.usersvc.domain.entity.UserInfo;
//import maria.practice.usersvc.domain.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.NoSuchElementException;
//import java.util.Optional;
//
//@Service
//public class UserService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    public String saveUser(UserInfo userInfo){
//        userRepository.save(userInfo);
//        return "Success";
//    }
//
//    public List<UserInfo> findAll(){
//        return userRepository.findAll();
//    }
//
//    public UserInfo getUser(long id){
//        Optional<UserInfo> optInfo = userRepository.findById(id);
////        if(optInfo.isPresent()){
////            return optInfo.get();
////        }else{
////            System.out.println("Nothing Here");
////            return null;
////        }
//
//        return optInfo.orElseThrow(() -> new NoSuchElementException("No here"));
//    }
//
//}
