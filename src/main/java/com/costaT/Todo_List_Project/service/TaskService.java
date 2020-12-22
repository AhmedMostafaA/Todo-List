package com.costaT.Todo_List_Project.service;

import com.costaT.Todo_List_Project.dao.TaskRepository;
import com.costaT.Todo_List_Project.dto.CategoriesDTO;
import com.costaT.Todo_List_Project.dto.TasksDTO;
import com.costaT.Todo_List_Project.dto.UsersDTO;
import com.costaT.Todo_List_Project.exception_handler.ConflictException;
import com.costaT.Todo_List_Project.exception_handler.NotFoundException;
import com.costaT.Todo_List_Project.mappers.CategoryMapper;
import com.costaT.Todo_List_Project.mappers.TaskMapper;
import com.costaT.Todo_List_Project.mappers.UserMapper;
import com.costaT.Todo_List_Project.model.Categories;
import com.costaT.Todo_List_Project.model.Tasks;
import com.costaT.Todo_List_Project.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoriesService categoriesService;

    @Autowired
    private TaskMapper taskMapper;

    public List<TasksDTO> getAllTasks()
    {
        return taskRepository.findAll().stream().map(tsk -> taskMapper.convertEntityToDto(tsk)).collect(Collectors.toList());
    }

    public TasksDTO getTaskById(int taskId)
    {
        Optional<Tasks> task = taskRepository.findById(taskId);
        if (!task.isPresent())
        {
            throw new NotFoundException("Not found Task with this ID :"+taskId);
        }
        return taskMapper.convertEntityToDto(task.get());
    }

    public TasksDTO getTaskByName(String taskName)
    {
        Optional<Tasks> task = taskRepository.findByTaskTitle(taskName);
        if (!task.isPresent())
        {
            throw new NotFoundException("Not found Task with this Name :"+taskName);
        }
        return taskMapper.convertEntityToDto(task.get());
    }

    public boolean addTask(TasksDTO task)
    {
        if(taskRepository.findByTaskTitle(task.getTaskTitle()).isPresent()) {
            throw new ConflictException("Another Task with same Name exists");
        }
        return (taskRepository.save(taskMapper.convertDtoToEntity(task)) != null) ? true : false;
    }

    public boolean modifyTask(TasksDTO task)
    {
        if (task.getCategory()==null || task.getUser()==null)
        {
            TasksDTO oldtask = getTaskById(task.getTaskId());
            if (task.getCategory()==null)
            {
                task.setCategory(oldtask.getCategory());

            }
            if (task.getUser()==null)
            {
                task.setUser(oldtask.getUser());
            }
        }
        Tasks tsk = taskMapper.convertDtoToEntity(task);
        return (taskRepository.save(tsk)!= null)?true:false;
    }

    public void removeTaskbyId(int taskId)
    {
        getTaskById(taskId);
        taskRepository.deleteById(taskId);
    }

    public List<TasksDTO> getTaskByCateId(int cateId)
    {
        CategoryMapper cm = new CategoryMapper();
        CategoriesDTO cateDTO = categoriesService.getCateById(cateId);
        Categories cate =  cm.convertDtoToEntity(cateDTO);

       return taskRepository.findAllByCategory(cate).stream().map(tsk -> taskMapper.convertEntityToDto(tsk)).collect(Collectors.toList());

    }

    public List<TasksDTO> getTaskByCateIdANDUserId(int cateId , int userId)
    {
        CategoryMapper cm = new CategoryMapper();
        UserMapper um = new UserMapper();
        CategoriesDTO cateDTO = categoriesService.getCateById(cateId);
        UsersDTO userDTO = userService.getUserById(userId);

        Categories cate =  cm.convertDtoToEntity(cateDTO);
        Users user =  um.convertDtoToEntity(userDTO);


        return taskRepository.findAllByCategoryAndUser(cate,user)
                .stream()
                .map(tsk -> taskMapper.convertEntityToDto(tsk))
                .collect(Collectors.toList());


    }

    public CategoriesDTO getCateByTaskId(int taskId)
    {
        return getTaskById(taskId).getCategory();
    }
}