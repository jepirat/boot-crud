package web.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import web.pojo.UserQuery;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class RestApiController {
    String SESSIONID;

    @GetMapping
    public String getSession() throws MalformedURLException {
        String key;

        UserQuery userQuery = new UserQuery();
        userQuery.setName("James");
        userQuery.setLastName("Brown");
        userQuery.setAge((byte) 35);


        UserQuery userQuery1 = new UserQuery();
        userQuery1.setId(3L);
        userQuery1.setName("Thomas");
        userQuery1.setLastName("Shelby");
        userQuery1.setAge((byte) 35);




        List<String> listSession = new ArrayList<>();
        RestTemplate templateGet = new RestTemplate();
        ResponseEntity<String> responseEntityGet = templateGet.getForEntity("http://91.241.64.178:7081/api/users", String.class);
        responseEntityGet.getHeaders().get("Set-Cookie").stream().forEach(s -> listSession.add(s));
        String [] strings = listSession.get(0).split(";");


        ResponseEntity<String> responseEntityPost;
        MultiValueMap<String, String> headersPOST = new LinkedMultiValueMap<>();
        headersPOST.add("Cookie", listSession.get(0));
        headersPOST.add("Content-Type", "application/json");
        headersPOST.add("method", "POST");
        RestTemplate restTemplatePost = new RestTemplate();
        restTemplatePost.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        HttpEntity<UserQuery> httpEntityPost = new HttpEntity<>(userQuery, headersPOST);
        responseEntityPost = restTemplatePost.postForEntity("http://91.241.64.178:7081/api/users", httpEntityPost, String.class);
        key = responseEntityPost.getBody();




        ResponseEntity<String> responseEntityPut;
        MultiValueMap<String, String> headersPUT = new LinkedMultiValueMap<>();
        headersPUT.add("Cookie", listSession.get(0));
        headersPUT.add("Content-Type", "application/json");
        headersPUT.add("method", "PUT");
        RestTemplate restTemplatePut = new RestTemplate();
        restTemplatePut.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        HttpEntity<UserQuery> httpEntityPut = new HttpEntity<>(userQuery1, headersPUT);
        responseEntityPut = restTemplatePut.exchange("http://91.241.64.178:7081/api/users", HttpMethod.PUT, httpEntityPut, String.class);
        key += responseEntityPut.getBody();



        Map<String, String> params = new HashMap<>();
        params.put("id", "3");
        ResponseEntity<String> responseEntityDelete;
        MultiValueMap<String, String> headersDelete = new LinkedMultiValueMap<>();
        headersDelete.add("Cookie", listSession.get(0));
        headersDelete.add("Content-Type", "application/json");
        headersDelete.add("method", "DELETE");
        RestTemplate restTemplateDelete = new RestTemplate();
        restTemplateDelete.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        HttpEntity<String> httpEntityDelete = new HttpEntity<>(headersDelete);
        responseEntityDelete = restTemplateDelete.exchange("http://91.241.64.178:7081/api/users/{id}", HttpMethod.DELETE, httpEntityDelete, String.class, params);
        key += responseEntityDelete.getBody();
        System.out.println(key);

        return key;
    }
}
