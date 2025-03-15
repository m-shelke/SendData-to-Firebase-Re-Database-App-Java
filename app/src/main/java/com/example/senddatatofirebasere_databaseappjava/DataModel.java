package com.example.senddatatofirebasere_databaseappjava;

public class DataModel {

    String data;
    String id;

    public DataModel() {
    }

    public DataModel(String id, String data) {
        this.data = data;
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public String getId() {
        return id;
    }
}
