package controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Task;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class Controller<T> {

    private ObjectMapper mapper = new ObjectMapper();
    private URL urlAdress;


    public Controller(String urlAdress) {
        try {
            this.urlAdress = new URL(urlAdress);
        } catch (MalformedURLException e) {
            //TODO add popup dialog that kills an app
            e.printStackTrace();
        }
    }


    public List<T> getList() throws IOException {
        List<T> taskList = mapper.readValue(urlAdress, new TypeReference<List<T>>() {});
        return taskList;
    }

}
