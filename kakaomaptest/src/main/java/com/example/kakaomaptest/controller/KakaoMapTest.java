package com.example.kakaomaptest.controller;

import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

@RestController
@RequestMapping("/babap/kakao")
public class KakaoMapTest {
    //입력한 주소의 json값을 가져오는 Controller.   참고 블로그 >>> https://minutemaid.tistory.com/174?category=1295581
    @RequestMapping(value = "/map", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    //@GetMapping(value = "/map", produces = "application/json;charset=UTF-8")
    public String getKakaoApiFromAddress(@RequestParam("address")String roadFullAddr){
        System.out.println("debug >> 카카오맵 API 불러오기.");
        //카카오 api키(REST API KEY)
        String apiKey = "a968c5740cf5ab97bd370ed550326dc6";
        //카카오맵 API URL
        String apiUrl = "https://dapi.kakao.com/v2/local/search/address.json";
        //최종 결과를 만들어 낼 문자열. try-catch에서 BufferedReader에 append한 값을 문자열로 변환한 값을 저장.
        String jsonString = null;
        System.out.println("debug >>> input address : " + roadFullAddr );
        try{
            //1. URL 인코딩 : URL로 사용할 수 없는 문제를 '%XX'의 형태로 변환
            roadFullAddr = URLEncoder.encode(roadFullAddr, "UTF-8");
            //2. 요청 URL 만들기
            String addr = apiUrl + "?query=" + roadFullAddr;
            //3. URL 객체 생성
            URL url = new URL(addr);
            //4. URL Connection객체 생성
            URLConnection conn = url.openConnection();
            //5. 헤더값 설정해주기("KakaoAK "에서 공백 입력 필수!)
            conn.setRequestProperty("Authorization", "KakaoAK " + apiKey);
            //6. StringBuffer에 값을 넣고 String으로 변환하고 jsonString을 return
            BufferedReader rd = null;
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuffer docJson = new StringBuffer();
            String line;

            while((line=rd.readLine()) != null){
                docJson.append(line);
            }//while end

            jsonString = docJson.toString();
            rd.close();
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }//try-catch end
        return jsonString;
    }//method end

}//class end
