package com.costaT.Todo_List_Project.dto;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class CategoriesDTO {

    private int catId;

    @NotNull(message = "Category Name is Required")
    private String catName;

    @NotNull(message = "Category Number is Required")
    private int catNumber;

    private List<TasksDTO> tasks= new ArrayList<>();

    public CategoriesDTO() {
    }

    public CategoriesDTO(int catId, @NotNull(message = "Category Name is Required") String catName, @NotNull(message = "Category Number is Required") int catNumber) {
        this.catId = catId;
        this.catName = catName;
        this.catNumber = catNumber;
    }

    public int getCatId() {
        return catId;
    }

    public String getCatName() {
        return catName;
    }

    public int getCatNumber() {
        return catNumber;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public void setCatNumber(int catNumber) {
        this.catNumber = catNumber;
    }

    public List<TasksDTO> getTasks() {
        return tasks;
    }

    public void setTasks(List<TasksDTO> tasks) {
        this.tasks = tasks;
    }

    public void addTask(TasksDTO task)
    {
        this.tasks.add(task);
    }
}
