package com.example.demo.dto;

import java.util.List;

public class ArrayListRequestDTO {
    private List<String> items;

    public ArrayListRequestDTO() {
    }

    public ArrayListRequestDTO(List<String> items) {
        this.items = items;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }
}
