package model;


import java.util.List;

public class Category {
    private long id;
    private String name;
    private List<Task> tasks;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<Task> getTasks() {
        return tasks;
    }
}
