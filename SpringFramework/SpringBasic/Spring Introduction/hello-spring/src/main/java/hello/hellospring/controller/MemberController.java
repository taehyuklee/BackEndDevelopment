package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller; //@Controller로 침
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller //Controller를 만드는 순간 Spring Container에 넣어서 관리함
public class MemberController {

    //아래처럼 새로 객체를 생성할수도 있지만, Spring이 관리를 하면 다 스프링 Container에 등록하고 Spring 컨테이너에서 받아서 써야 한다.
    //private final MemberService memberService = new MemberService();
    //MemberService가 여러 Contoller에서 쓰일수 있기에 공용으로 만들어놓고 사용하는 것이 더 이득이다.

    //spring Container에 연결하는 방법은 다음과 같다.
    private final MemberService memberService;

    @Autowired //Autowired를 annotation해주면 Spring 컨테이너에 있는 memberService를 갖다 쓰겠다는 것이다.
    public MemberController(MemberService memberService){
        //MemberController가 생성될때 spring컨테이너 안에 등록되어 있는 MemberService 스프링 빈 객체를 가져다 넣어준다 
        //이것을 -> Dependency Injection이라 한다. 의존관계를 주입해준다 밖에서 스프링이 딱 넣어주는 것이다.
        this.memberService = memberService;
        //Dependency Injection에 3가지 방법이 있다. 이것은 생성자 주입이다.
        //@Autowired private MemberService memberService; 필드 주입이라 한다. (별로 좋지 않다 비추)
        //나중에 Setter로 주입해줄수도 있는데, public으로 열려있어야 한다. 이것이 public하게 노출이 되는 것이 단점이 있다.
        //따라서 DI는 생성자주입으로 하는게 제일 낫다.
    }

    /* springApplication을 실행시켜보면 memberService를 찾을수 없다고 한다. 그 이유는 MemberService는 순수 Java Class로
    * 되어 있기때문에 여기 MemberController와 다르게 @ annotation도 없어서 스프링이 알 방도가 없기때문이다. @Controller같은
    * 경우는 스프링이 로딩될때 스프링 컨테이너에 걸려서 다 담겨진다. */

    @GetMapping("/members/new")
    public String createForm(){
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form){ //Spring이 입력문자열을 MemberForm form <- Spring! 을 넣어준다 어떻게?
        Member member = new Member();
        member.setName(form.getName()); //Member domain에 회원에 대한 객체를 MemberForm을 거쳐서 사용한다. 왜? 돌아가는거지?

        System.out.println("member = " + member.getName());

        memberService.join(member);

        return "redirect:/"; //끝나면 홈화면으로 돌려버리는 것.
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);

        return "members/memberList";
    }
    
}