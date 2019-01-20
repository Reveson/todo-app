package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Category;
import model.Project;
import model.Task;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskController extends Controller<Task> {
    public TaskController(String urlAdress) throws MalformedURLException {
        super(urlAdress);
    }

    public void setDeadline(Task task, Date date) {

    }

    public void setTimeNeeded(Task tak, int time) {

    }

    public void setProject(Task task, String project) {

    }

    public void setCategory(Task task, String category) {

    }

    public void setPriority(Task task, int priority) {
        if(priority < 0 || priority > 5) {
            //TODO log bug here
        }

    }

    public void addComment(Task task, String text) {

    }

    public void addChecklist(Task task, ArrayList<String> list) {

    }

    public void deleteTask(Task task) {

    }

    public void updateTask(Task task, String text) {

    }

    public void addNewTask(Project project) {

    }

    public List<Task> getListWithPriorities(int i) {

        return null;
    }


    public void setSelected(Task task, boolean selected) {

    }

    public boolean isSelected(Task task) {
        return false;
    }

//
//    //TODO just for testing, to be deleted!
//    public List<Task> getTaskList(String json) throws IOException {
//        List<Task> taskList = mapper.readValue(json, new TypeReference<List<Task>>() {});
//        return taskList;
//    }
//
//    //TODO to be deleted
//    public String getJson(List<Task> taskList) throws JsonProcessingException {
//        String jsonString = mapper.writeValueAsString(taskList);
//        return jsonString;
//    }
//
//    public List<Task> getTaskList() throws IOException {
//        List<Task> taskList = mapper.readValue(urlAdress, new TypeReference<List<Task>>() {});
//        return taskList;
//    }

}
