package com.example.kakaomaptest.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
@RequestMapping("/babap/charger")
public class ChargerMapTest {
    static final String baseURL = "http://infuser.odcloud.kr/oas/docs?namespace=15119741/v1" ; //API의 스펙을 설명하는 문서를 위한 URL
    static final String realDataURL = "https://api.odcloud.kr/api/15119741/v1/uddi:fe904caf-636f-4a49-aa94-e9064a446b3e"; //실제 API데이터를 가져오는 URL
    static final String myServiceKey = "By8%2BzbzlZwxRaJwkLoTWe7rgJIYf3TIkEnbrCY5mNB8f3clGoYgnY8J7f5C8bDSD1p21ek7oJoGHFbWhwRMRhw%3D%3D";//API키 >> 본인 키로 변경

    //API의 스펙을 설명하는 Swagger 문서입니다.
    //이 문서는 API가 어떤 경로(paths), 매개변수(parameters), 응답(responses)을 가지는지,
    //그리고 각종 데이터 모델(definitions)을 어떻게 정의하는지를 나타냅니다.
    //해당 문서를 XML로 불러오는 메서드
    @GetMapping("/xmlapi")
    public String callApiWithXml() { //확인하지 않아도 됨.
        System.out.println("debug >> XML로 불러오기.");
        StringBuffer result = new StringBuffer();
        try {
            String apiUrl = baseURL + "&serviceKey=" + myServiceKey + "&numOfRows=10" + "&pageNo=4";
            URL url = new URL(apiUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            String returnLine;
            result.append("<xmp>");
            while((returnLine = bufferedReader.readLine()) != null) {
                result.append(returnLine + "\n");
            }//while end
            urlConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }//try-catch end
        return result + "</xmp>";
    }//method end
    //API의 스펙을 설명하는 Swagger 문서입니다.
    //이 문서는 API가 어떤 경로(paths), 매개변수(parameters), 응답(responses)을 가지는지,
    //그리고 각종 데이터 모델(definitions)을 어떻게 정의하는지를 나타냅니다.
    //해당 문서를 JSON으로 불러오는 메서드
    @GetMapping("/jsonapi")
    public String callApiWithJson() { //확인하지 않아도 됨.
        System.out.println("debug >> JSON으로 불러오기.");
        StringBuffer result = new StringBuffer();
        String jsonPrintString = null;
        try {
            String apiUrl = baseURL + "&serviceKey=" + myServiceKey + "&numOfRows=10" + "&pageNo=4";
            URL url = new URL(apiUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
            String returnLine;
            while((returnLine = bufferedReader.readLine()) != null) {
                result.append(returnLine);
            }//while end
            bufferedReader.close();
            // JSON 데이터 처리를 위해 ObjectMapper 사용
            ObjectMapper jsonMapper = new ObjectMapper();
            System.out.println(jsonMapper);
            JsonNode node = jsonMapper.readTree(result.toString().getBytes());
            System.out.println(node);
            jsonPrintString = jsonMapper.writeValueAsString(node);
            System.out.println(jsonPrintString);
        } catch (Exception e) {
            System.err.println("XML to JSON 변환 실패: " + e.getMessage());
            e.printStackTrace();
        }//try-catch end
        return jsonPrintString;
    }//method end

    //API에서 제공하는 실제 데이터값 가져오기
    @GetMapping("/realApiData")
    public String callRealApi() {
        System.out.println("debug >> 실제 API 데이터 가져오기.");
        StringBuffer result = new StringBuffer(); //
        String jsonPrintString = null; //최종 결과값을 저장하는 변수 jsonPrintString
        try {//네트워크 연결, 데이터 읽기, 자원관리와 같은 예외가 발생할 수 있으므로 예외처리를 수행.
            String apiUrl = realDataURL + "?serviceKey="+ myServiceKey + "&page=1&perPage=10"; //URL 생성
            URL url = new URL(apiUrl);//URL 객체 생성
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();//요청하고자 하는 URL과 통신하기 위한 Connection 객체 생성
            urlConnection.setRequestMethod("GET");//통신을 위한 메소드 지정.(GET)
            urlConnection.connect();//URL 객체를 통해 생성된 HttpURLConnection 인스턴스에 대해 네트워크 연결을 시작하는 명령
            //HttpURLConnection 객체를 통해 서버로부터 받은 응답 데이터를 읽을 수 있는 InputStream으로 반환.
            //InputStream은 네트워크를 통해 직접 데이터를 읽기 때문에, 데이터를 한 번에 많은 양을 읽어서 버퍼에 저장하는 BufferedInputStream을 사용하여 효율성을 높입니다.
            //BufferedInputStream은 내부적으로 데이터를 버퍼링함으로써, 매번 네트워크로부터 직접 데이터를 읽는 것보다 더 빠르게 데이터를 처리할 수 있게 해줍니다.
            BufferedInputStream bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));//BufferedReader객체 생성.
            String returnLine;
            while((returnLine = bufferedReader.readLine()) != null) {
                //readLine()메서드를 통해 데이터가 존재하지 않을 때 까지 읽고 String으로 반환함.
                //만약 더 이상 읽을 데이터가 없다면 null반환
                //이 반복문은 null이 반환될 때까지, 즉 모든 데이터를 읽을 때까지 계속됨.
                //반복문을 통해 계속적으로 응답받는 데이터들을 result에 담는다.
                result.append(returnLine);
            }//while end
            bufferedReader.close();//BufferedReader 종료
            // 결과가 이미 JSON 형태이므로 추가 변환 필요 없음
            jsonPrintString = result.toString();
            //System.out.println("debug >>> " + jsonPrintString);
        } catch (Exception e) {
            e.printStackTrace();
        }//try-catch end
        return jsonPrintString;
    }//method end

    //주소, 충전기타입, 충전기ID만 가져오기
    @GetMapping("/realApiData2")
    public String callRealApiPartData() {
        System.out.println("debug >> 특정 데이터 가져오기.");
        StringBuffer result = new StringBuffer();
        String jsonPrintString = null; //최종 결과값을 저장할 변수 jsonPrintString
        try {//네트워크 연결, 데이터 읽기, 자원관리와 같은 예외가 발생할 수 있으므로 예외처리를 수행.
            String apiUrl = realDataURL + "?serviceKey="+ myServiceKey+ "&page=1&perPage=10";//URL 생성
            URL url = new URL(apiUrl);//URL 객체 생성
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();//요청하고자 하는 URL과 통신하기 위한 Connection 객체 생성
            urlConnection.setRequestMethod("GET");//통신을 위한 메소드 지정.(GET)
            urlConnection.connect();//URL 객체를 통해 생성된 HttpURLConnection 인스턴스에 대해 네트워크 연결을 시작하는 명령
            BufferedInputStream bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));//BufferedReader객체 생성.
            String returnLine;
            while((returnLine = bufferedReader.readLine()) != null) {
                //readLine()메서드를 통해 데이터가 존재하지 않을 때 까지 읽고 String으로 반환함.
                //만약 더 이상 읽을 데이터가 없다면 null반환
                //이 반복문은 null이 반환될 때까지, 즉 모든 데이터를 읽을 때까지 계속됨.
                //반복문을 통해 계속적으로 응답받는 데이터들을 result에 담는다.
                result.append(returnLine);
            }//while end
            bufferedReader.close();//BufferedReader 종료
            //원본 JSON 문자열 파싱
            //result.toString() 메서드를 통해 result에 저장된 문자열 데이터를 String으로 변환.
            JSONObject jsonObject = new JSONObject(result.toString());

            //getJSONArray 메서드는 JSONObject에서 특정 키에 해당하는 값을 JSONArray로 추출합니다
            //이 경우, "data"라는 키에 해당하는 값을 추출합니다.
            //"data" 키에 해당하는 값은 JSON 배열이어야 합니다.
            //이 배열에는 JSON 객체들이나 다른 데이터들이 순서대로 저장되어 있을 수 있습니다.
            //dataArray 변수에는 추출된 JSON 배열이 저장되어, 이 배열에 포함된 각 요소에 접근할 수 있게 됩니다.
            JSONArray dataArray = jsonObject.getJSONArray("data");

            //새로운 JSONArray 객체를 생성합니다. 이 배열은 초기에 비어 있으며,
            //필요에 따라 JSON 객체나 다른 값들을 이 배열에 추가할 수 있습니다.
            //newDataArray는 이후 코드에서 수정하거나 추가된 데이터를 담기 위해 사용될 수 있습니다.
            //예를 들어, dataArray에서 특정 조건을 만족하는 요소들만 선택하여 newDataArray에 추가하는 식으로 데이터를 처리할 수 있습니다.
            JSONArray newDataArray = new JSONArray();
            //dataArray에 담긴 JSON배열의 길이만큼 반복
            for (int i = 0; i < dataArray.length(); i++) {
                //원본 데이터 객체 추출.
                //Json배열의 i번째 요소를 getJSONObject()메서드를 사용해서 추출.
                //dataObject는 dataArray의 i번째 JSON 객체를 가리키게 된다.
                JSONObject dataObject = dataArray.getJSONObject(i);
                //보고싶은 데이터의 값을 담기위한 객체 newDataobject
                JSONObject newDataObject = new JSONObject();
                newDataObject.put("주소", dataObject.getString("주소"));
                newDataObject.put("충전기타입", dataObject.getString("충전기타입"));
                newDataObject.put("충전기ID", dataObject.getInt("충전기ID"));
                //완성된 newDataObject를 newDataArray JSON 배열에 추가
                //이 과정은 반복될 때마다 실행되므로, newDataArray는 최종적으로 dataArray의 모든 요소를 순회하며
                //생성된 모든 newDataObject들을 포함하게 됩니다.
                newDataArray.put(newDataObject);
            }//for end
            //
            JSONObject newJsonObject = new JSONObject();
            newJsonObject.put("data", newDataArray);
            jsonPrintString = newJsonObject.toString(4);
            //System.out.println(jsonPrintString);
        } catch (Exception e) {
            e.printStackTrace();
        }//try-catch end
        return jsonPrintString;
    }//method end

    //pathvalue를 통해 주소 검색
    @GetMapping("/realApiData3/{address}")
    public String callRealApiWithAddress(@PathVariable("address") String address) {
        System.out.println("debug >> 실제 API 데이터에서 주소 필터링하여 가져오기: " + address);
        StringBuffer result = new StringBuffer();
        String jsonPrintString = null;
        try {
            String apiUrl = realDataURL + "?serviceKey=" + myServiceKey + "&page=1&perPage=100"; // perPage를 증가시켜 더 많은 데이터를 검색할 수 있음
            URL url = new URL(apiUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
            String returnLine;
            while ((returnLine = bufferedReader.readLine()) != null) {
                result.append(returnLine);
            }//while end
            bufferedReader.close();

            // 원본 JSON 문자열 파싱
            JSONObject jsonObject = new JSONObject(result.toString());
            JSONArray dataArray = jsonObject.getJSONArray("data");
            JSONArray filteredDataArray = new JSONArray();

            int count = 0;

            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject dataObject = dataArray.getJSONObject(i);
                if (dataObject.getString("주소").contains(address)) { // 주소 필터링
                    JSONObject newDataObject = new JSONObject();
                    newDataObject.put("주소", dataObject.getString("주소"));
                    newDataObject.put("충전기타입", dataObject.getString("충전기타입"));
                    newDataObject.put("충전기ID", dataObject.getInt("충전기ID"));
                    filteredDataArray.put(newDataObject);
                    count++;
                }//if end
            }//for end

            JSONObject newJsonObject = new JSONObject();
            newJsonObject.put("data", filteredDataArray);
            jsonPrintString = newJsonObject.toString(4); // Indented with 4 spaces for readability
            System.out.println("총 검색결과 개수 = " + count);
            //System.out.println(jsonPrintString);
        } catch (Exception e) {
            e.printStackTrace();
        }//try-catch end
        return jsonPrintString;
    }//method end
}//class end
