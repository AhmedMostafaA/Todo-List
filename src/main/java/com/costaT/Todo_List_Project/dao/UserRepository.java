package com.costaT.Todo_List_Project.dao;

import com.costaT.Todo_List_Project.model.Tasks;
import com.costaT.Todo_List_Project.model.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<Users,Integer> {
    @Override
    List<Users> findAll();

    Optional<Users> findByUserEmail(String userEmail);
}
