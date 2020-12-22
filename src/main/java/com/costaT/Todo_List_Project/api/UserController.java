package com.costaT.Todo_List_Project.api;

import com.costaT.Todo_List_Project.dto.CategoriesDTO;
import com.costaT.Todo_List_Project.dto.TasksDTO;
import com.costaT.Todo_List_Project.dto.UsersDTO;
import com.costaT.Todo_List_Project.model.Users;
import com.costaT.Todo_List_Project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = {"todo/users"})
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(value = {"/all",""})
    public ResponseEntity<List<UsersDTO>> findAllUsers()
    {
        return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.OK);
    }

    @GetMapping(value = "/id")
    public ResponseEntity<UsersDTO> findUserById(@RequestParam(name = "id",required = true) int userId)
    {
        return new ResponseEntity<>(userService.getUserById(userId),HttpStatus.OK);
    }

    @PostMapping(value = "/save")
    public ResponseEntity<String> saveUser(@Valid @RequestBody UsersDTO user)
    {
        if( userService.addUser(user))
        {
            return new ResponseEntity<>("User Added Successfully",HttpStatus.CREATED);
        }
        return new ResponseEntity<>("User Not Added",HttpStatus.NOT_ACCEPTABLE);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<String> updateUser(@Valid @RequestBody UsersDTO user , @RequestParam(name = "id",required = true) int userId)
    {
        user.setUserID(userId);
        if (userService.modifyUser(user))
        {
            return new ResponseEntity<>("User updated Successfully",HttpStatus.OK);
        }
        return new ResponseEntity<>("User Not Updated",HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Void> deleteUser(@RequestParam(name = "id",required = true) int userId)
    {
        userService.dropUserbyId(userId);
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "addtaskid")
    public ResponseEntity<String> addUserTaskById(@RequestParam(name = "uid",required = true)int userId, @RequestParam(name = "tid",required = true)int taskId)
    {
        if (userService.addTaskById(userId,taskId))
        {
            return new ResponseEntity<>("Task Added to User Successfully",HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Task Not Added",HttpStatus.NOT_ACCEPTABLE);
    }

    @PostMapping(value = "addtaskname")
    public ResponseEntity<String> addUserTaskByName(@RequestParam(name = "uid",required = true)int userId, @RequestParam(name = "tname",required = true)String taskName)
    {
        if (userService.addTaskByName(userId,taskName))
        {
            return new ResponseEntity<>("Task Added to User Successfully",HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Task Not Added",HttpStatus.NOT_ACCEPTABLE);
    }

    @PostMapping(value = "addcateid")
    public ResponseEntity<String> addUserCateById(@RequestParam(name = "uid",required = true)int userId, @RequestParam(name = "cid",required = true)int cateId)
    {
        if (userService.addCateById(userId,cateId))
        {
            return new ResponseEntity<>("Category Added to User Successfully",HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Category Not Added",HttpStatus.NOT_ACCEPTABLE);
    }

    @PostMapping(value = "addcatename")
    public ResponseEntity<String> addUserCateByName(@RequestParam(name = "uid",required = true)int userId, @RequestParam(name = "cname",required = true)String cateName)
    {
        if (userService.addCateByName(userId,cateName))
        {
            return new ResponseEntity<>("Category Added to User Successfully",HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Category Not Added",HttpStatus.NOT_ACCEPTABLE);
    }

    @GetMapping(value = "alluserCates")
    public ResponseEntity<List<CategoriesDTO>> getUserCatebyId(@RequestParam(name = "id",required = true) int userId)
    {
        return new ResponseEntity<>(userService.getAllCatebyUserId(userId),HttpStatus.OK);
    }

    @GetMapping(value = "alluserTasks")
    public ResponseEntity<List<TasksDTO>> getUserTasksbyId(@RequestParam(name = "id",required = true)int userId)
    {
        return new ResponseEntity<>(userService.getAllTasksbyUserId(userId),HttpStatus.OK);
    }

    @GetMapping(value = "allusercateTasks")
    public ResponseEntity<List<TasksDTO>> getUserTasksInCate(@RequestParam(name = "uid",required = true)int userId,@RequestParam(name = "cid",required = true) int cateId)
    {
        return new ResponseEntity<>(userService.getAllTasksbyUserandCateId(userId,cateId),HttpStatus.OK);
    }
}
