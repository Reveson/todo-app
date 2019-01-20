package controller;

public class QueryParameters {
    private boolean descending;
    public QueryParameters descendingOrder(boolean b) {
        this.descending = b;
        return this;
    }
}
