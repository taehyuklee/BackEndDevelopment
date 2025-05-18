package com.taehyuk._auth.auth_core.member.service;

import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class MemberService {

    public Map<String, String> getInformation() {
        return Map.of(
            "data", "비밀이 아니야"
        );
    }

    public Map<String, String> getInfoForSuper() {
        return Map.of(
                "data", "for Super User"
        );
    }

    public Map<String, String> getInfoForSpecial() {
        return Map.of(
                "data", "for Special User"
        );
    }

}
