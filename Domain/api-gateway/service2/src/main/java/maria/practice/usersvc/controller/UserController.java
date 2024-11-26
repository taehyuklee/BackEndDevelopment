//package maria.practice.usersvc.controller;
//
//import maria.practice.usersvc.domain.entity.UserInfo;
//import maria.practice.usersvc.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//public class UserController {
//
//    @Autowired
//    private UserService userService;
//
//    @GetMapping("/user")
//    public UserInfo getUser(@RequestParam(value="userId") long id){
//        return userService.getUser(id);
//    }
//
//    @PostMapping("/user")
//    public String saveUser(@RequestBody UserInfo userInfo){
//        return userService.saveUser(userInfo);
//    }
//
//    @GetMapping("/user/all")
//    public List<UserInfo> findAll(){
//        return userService.findAll();
//    }
//}
