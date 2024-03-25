package com.example.kakaomaptest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/babap")
public class KakaoMapUITest {
    //jsp를 활용해 view를 통한 데이터 출력.
    @GetMapping(value = "/kakaomaptest")
    public String mymap(){
        System.out.println("카카오 맵 API 테스트");
        return "address2";
        //return "address";
    }//method end

}//class end
