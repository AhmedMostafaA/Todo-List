package com.costaT.Todo_List_Project.service;

import com.costaT.Todo_List_Project.dao.UserRepository;
import com.costaT.Todo_List_Project.dto.CategoriesDTO;
import com.costaT.Todo_List_Project.dto.TasksDTO;
import com.costaT.Todo_List_Project.dto.UsersDTO;
import com.costaT.Todo_List_Project.exception_handler.ConflictException;
import com.costaT.Todo_List_Project.exception_handler.NotFoundException;
import com.costaT.Todo_List_Project.mappers.CategoryMapper;
import com.costaT.Todo_List_Project.mappers.UserMapper;
import com.costaT.Todo_List_Project.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoriesService categoryService;

    @Autowired
    TaskService taskService;

    @Autowired
    UserMapper userMapper;


    public List<UsersDTO> getAllUsers()
    {
        return userRepository.findAll().stream().map(usr -> userMapper.convertEntityToDto(usr)).collect(Collectors.toList());
    }

    public UsersDTO getUserById(int userId)
    {
        Optional<Users> user = userRepository.findById(userId);
        if (!user.isPresent())
        {
            throw new NotFoundException("Not found user with this ID :"+userId);
        }
        return userMapper.convertEntityToDto(user.get());
    }

    public boolean addUser(UsersDTO userDTO)
    {
        if (userRepository.findByUserEmail(userDTO.getUserEmail()).isPresent())
        {
            throw new ConflictException("Another User with same Email exists");
        }
        Users usr = userMapper.convertDtoToEntity(userDTO);
        return (userRepository.save(usr)!= null)?true:false;
    }

    public boolean modifyUser(UsersDTO userDTO)
    {
        Users usr = userMapper.convertDtoToEntity(userDTO);
        Optional<Users> opUsr = userRepository.findById(usr.getUserID());
        if (!opUsr.isPresent())
        {
            throw new NotFoundException("Not found user with this ID" + usr.getUserID());
        }
        Users oldUsr = opUsr.get();
        usr.setTasks(oldUsr.getTasks());
        usr.setCategories(oldUsr.getCategories());
        return (userRepository.save(usr)!= null)?true:false;
    }

    public void dropUserbyId(int UserId)
    {
        getUserById(UserId);
        userRepository.deleteById(UserId);
    }

    public boolean addTaskById(int userId,int taskId)
    {
        TasksDTO task = taskService.getTaskById(taskId);
        if (task != null)
        {
            if (task.getCategory() != null)
            {
                UsersDTO user = getUserById(userId);
                if (user != null)
                {
                    if (user.getCategories().indexOf(task.getCategory())==-1)
                    {
                        addCateById(userId,task.getCategory().getCatId());
                    }
                    task.setUser(user);
                    return taskService.modifyTask(task);
                }
            }
        }
        return false;
    }

    public boolean addTaskByName(int userId,String taskName)
    {
        TasksDTO task = taskService.getTaskByName(taskName);
        if (task != null)
        {
            if (task.getCategory() != null)
            {
                UsersDTO user = getUserById(userId);
                if (user != null)
                {
                    task.setUser(user);
                    return taskService.modifyTask(task);
                }
            }
        }
        return false;
    }

    public boolean addCateById(int userId,int cateId)
    {
        CategoriesDTO cate = categoryService.getCateById(cateId);
        if (cate != null)
        {
            UsersDTO userdto = getUserById(userId);
            Users user = userMapper.convertDtoToEntity(userdto);
            CategoryMapper cm = new CategoryMapper();
            user.addCategory(cm.convertDtoToEntity(cate));
            return (userRepository.save(user)!=null)?true:false;

        }
        return false;
    }

    public boolean addCateByName(int userId,String cateName)
    {
        CategoriesDTO cate = categoryService.getCateByName(cateName);
        if (cate != null)
        {
            UsersDTO userdto = getUserById(userId);
            Users user = userMapper.convertDtoToEntity(userdto);
            CategoryMapper cm = new CategoryMapper();
            user.addCategory(cm.convertDtoToEntity(cate));
            return (userRepository.save(user)!=null)?true:false;

        }
        return false;
    }

    public List<CategoriesDTO> getAllCatebyUserId(int userId)
    {
        UsersDTO usersDTO = getUserById(userId);
        return usersDTO.getCategories();
    }

    public List<TasksDTO> getAllTasksbyUserId(int userId)
    {
        UsersDTO usersDTO = getUserById(userId);
        return usersDTO.getTasks();
    }

    public List<TasksDTO> getAllTasksbyUserandCateId(int userId,int cateId)
    {
        return taskService.getTaskByCateIdANDUserId(cateId,userId);
    }
}
