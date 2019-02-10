package controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class AuthController {
    private ObjectMapper mapper = new ObjectMapper();
    private URL urlAdress;

    public boolean logIn(String login, String password) {
        //gregory531
        //1234
        try {
            urlAdress = new URL("http://192.168.0.16:8005/login.php?login="+login+"&pass="+password);
            JsonNode jsonNode = mapper.readTree(urlAdress);
            String result = jsonNode.get("result").asText();
            if(result.equals("1")) {
//                return true;
                URL u = new URL("http://192.168.0.16:8005/GetCategories.php");
                JsonNode jn = mapper.readTree(u);
//                System.out.println(jn.asText());
                String r2 = jn.get("0").asText();
                System.out.println("works?"+r2);
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
