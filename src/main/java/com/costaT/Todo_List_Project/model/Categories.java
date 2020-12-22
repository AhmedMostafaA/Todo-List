package com.costaT.Todo_List_Project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
public class Categories {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int catId;
    @NotNull
    private String catName;
    @NotNull
    private int catNumber;

    @ManyToMany(mappedBy = "categories")
    @JsonIgnore
    private List<Users> users = new ArrayList<>();

    @OneToMany(
            mappedBy = "category",//allow to point that owns relationship
            cascade = CascadeType.ALL,//if we save category and have some tasks assigned to it will persistans auto
            orphanRemoval = true//if remove user that will remove his tasks

    )
    private List<Tasks> tasks= new ArrayList<>();

    public Categories() {
    }

    public Categories(@NotNull(message = "Category Name is Required") String catName, @NotNull(message = "Category Number is Required") int catNumber) {
        this.catName = catName;
        this.catNumber = catNumber;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public int getCatNumber() {
        return catNumber;
    }

    public void setCatNumber(int catNumber) {
        this.catNumber = catNumber;
    }

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }

    public void addUser(Users users)
    {
        this.users.add(users);
    }

    public List<Tasks> getTasks() {
        return tasks;
    }

    public void setTasks(List<Tasks> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Tasks task){
        this.tasks.add(task);
    }
}