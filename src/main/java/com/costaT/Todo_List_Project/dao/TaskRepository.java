package com.costaT.Todo_List_Project.dao;

import com.costaT.Todo_List_Project.model.Categories;
import com.costaT.Todo_List_Project.model.Tasks;
import com.costaT.Todo_List_Project.model.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends CrudRepository<Tasks,Integer> {
    @Override
    List<Tasks> findAll();
//
//    Tasks findByTaskIdAndUser(Integer taskId, Users user);
//
    @Override
    void delete(Tasks tasks);

    Optional<Tasks> findByTaskTitle(String taskTitle);

    List<Tasks> findAllByCategory(Categories category);

    List<Tasks> findAllByCategoryAndUser(Categories category, Users user);
}
