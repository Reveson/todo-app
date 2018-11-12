package model;

import java.time.Instant;

public class Task {
    private long id;
    private String name;
    private Instant deadline;
    private int timeNeeded; //time needed in seconds
    private int repeatEvery; //repeat every X minutes (counted from 00:00)
    private int priority; //1-5
    private boolean checked;
    private boolean hasStar;

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

    public Instant getDeadline() {
        return deadline;
    }

    public void setDeadline(Instant deadline) {
        this.deadline = deadline;
    }

    public int getTimeNeeded() {
        return timeNeeded;
    }

    public void setTimeNeeded(int timeNeeded) {
        this.timeNeeded = timeNeeded;
    }

    public int getRepeatEvery() {
        return repeatEvery;
    }

    public void setRepeatEvery(int repeatEvery) {
        this.repeatEvery = repeatEvery;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean hasStar() {
        return hasStar;
    }

    public void setStar(boolean hasStar) {
        this.hasStar = hasStar;
    }
}
