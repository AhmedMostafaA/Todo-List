package com.costaT.Todo_List_Project.dto;

import com.costaT.Todo_List_Project.model.Tasks;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersDTO {

    private int userID;

    @NotNull(message = "User First NameDto is Required")
    private String userFirstName;

    @NotNull(message = "user EmailDto is Required")
    private String userEmail;

    private List<CategoriesDTO> categories  = new ArrayList<>();

    private List<TasksDTO> tasks = new ArrayList<>();

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

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }


    public List<CategoriesDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoriesDTO> categories) {
        this.categories = categories;
    }

    public void addCategory(CategoriesDTO category)
    {
        this.categories.add(category);
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

    //    public boolean removeCategory(CategoriesDTO category)
//    {
//        return this.categories.remove(category);
//    }
//
//    public boolean removeCategories(CategoriesDTO category){
//        return this.categories.removeAll(categories);
//    }
//
//    public void updateCategory(CategoriesDTO category)
//    {
//        Optional<CategoriesDTO> opCat = this.categories.stream().filter(cat -> cat.getCatId()==category.getCatId()).findAny();
//        if (opCat.isPresent())
//        {
//            CategoriesDTO cat = opCat.get();
//            cat.setCatName(category.getCatName());
//            cat.setCatNumber(category.getCatNumber());
//        }
//    }


}
