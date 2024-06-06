package com.intro.junit.testcode.first_service.service;

import com.intro.junit.testcode.first_service.utility.GoodUtility;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class GoodService {

    public void create(){

    }

    public void read(){

    }

    public void useUtility(){
        System.out.println("hi");
        String[] parsed = GoodUtility.parsingMethod("a b c");
        System.out.println("good");
        for(int i=0; i<3; i++){
            System.out.println(parsed[i]);
        }
    }

}
