package com.costaT.Todo_List_Project.dao;

import com.costaT.Todo_List_Project.model.Categories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<Categories,Integer> {
    @Override
    List<Categories> findAll();

    Optional<Categories> findByCatName(String catName);
}
