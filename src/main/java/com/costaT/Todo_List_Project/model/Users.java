package com.costaT.Todo_List_Project.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "user")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userID;

//    @NotNull(message = "User First Name is Required")
    private String userFirstName;


    private String userLastName;

//    @NotNull(message = "user Email is Required")
    private String userEmail;

    @OneToMany(
            mappedBy = "user",//allow to point that owns relationship
            cascade = CascadeType.ALL,//if we save user and have some tasks assigned to it will persistans auto
            orphanRemoval = true//if remove user that will remove his tasks
    )
    private List<Tasks> tasks = new ArrayList<>();

//(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinTable(
            name = "user_categories",
            joinColumns = {@JoinColumn(name = "catId")},
            inverseJoinColumns = {@JoinColumn(name = "userID")})
    private List<Categories> categories  = new ArrayList<>();

    public Users() {
    }

    public Users(@NotNull(message = "User First Name is Required") String userFirstName, @NotNull(message = "User Last Name is Required") String userLastName, @NotNull(message = "user email is Required") String userEmail) {
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userEmail = userEmail;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public List<Tasks> getTasks() {
        return tasks;
    }

    public void setTasks(List<Tasks> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Tasks task) {
        this.tasks.add(task);
    }

    public List<Categories> getCategories() {
        return categories;
    }

    public void setCategories(List<Categories> categories) {
        this.categories = categories;
    }

    public void addCategory(Categories category)
    {
        this.categories.add(category);
    }

//    public boolean removeCategory(Categories category)
//    {
//        return this.categories.remove(category);
//    }
//
//    public boolean removeCategories(Categories category){
//        return this.categories.removeAll(categories);
//    }
//
//    public void updateCategory(Categories category)
//    {
//        Optional<Categories> opCat = this.categories.stream().filter(cat -> cat.getCatId()==category.getCatId()).findAny();
//        if (opCat.isPresent())
//        {
//            Categories cat = opCat.get();
//            cat.setCatName(category.getCatName());
//            cat.setCatNumber(category.getCatNumber());
//        }
//    }

}