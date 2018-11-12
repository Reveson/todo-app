package model;

import java.util.List;

public class Checklist extends TaskElement {
    private List<ChecklistRow> rows;

    public List<ChecklistRow> getRows() {
        return rows;
    }

    public void setRows(List<ChecklistRow> rows) {
        this.rows = rows;
    }
}
