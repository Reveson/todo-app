package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Task;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class TaskController {

    private ObjectMapper mapper = new ObjectMapper();
    private URL urlAdress;

    public TaskController(String urlAdress) throws MalformedURLException {
        this.urlAdress = new URL(urlAdress);
    }

    //TODO just for testing, to be deleted!
    public List<Task> getTaskList(String json) throws IOException {
        List<Task> taskList = mapper.readValue(json, new TypeReference<List<Task>>() {});
        return taskList;
    }

    //TODO to be deleted
    public String getJson(List<Task> taskList) throws JsonProcessingException {
        String jsonString = mapper.writeValueAsString(taskList);
        return jsonString;
    }

    public List<Task> getTaskList() throws IOException {
        List<Task> taskList = mapper.readValue(urlAdress, new TypeReference<List<Task>>() {});
        return taskList;
    }

}
