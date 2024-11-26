//package maria.practice.usersvc.domain.entity;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//@Getter
//@Setter
//@Entity
//public class UserInfo {
//
//    @Id
//    @GeneratedValue(strategy= GenerationType.IDENTITY)
//    private Long id;
//    private String name;
//    private String pwd;
//
//    public UserInfo(String name, String pwd){
//        this.name = name;
//        this.pwd = pwd;
//    }
//
//    //만약 기본 생성자가 없으면
//    public UserInfo(){
//
//    }
//
//    @Override
//    public String toString(){
//        return "name: " + this.name + " pwd: " + this.pwd;
//    }
//
//}
