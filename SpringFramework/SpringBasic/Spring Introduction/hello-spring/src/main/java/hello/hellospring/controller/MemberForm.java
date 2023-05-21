package hello.hellospring.controller;

public class MemberForm {
    private String name;

    //여기의 name하고 members의 name하고 매칭되면서 값이 들어올 것이다.

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
