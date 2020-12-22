package com.costaT.Todo_List_Project.api;

import com.costaT.Todo_List_Project.dto.CategoriesDTO;
import com.costaT.Todo_List_Project.dto.TasksDTO;
import com.costaT.Todo_List_Project.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "todo/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping(value = {"","/all"})
    public ResponseEntity<List<TasksDTO>> findAllTasks()
    {
        return new ResponseEntity<>(taskService.getAllTasks(), HttpStatus.OK);
    }

    @GetMapping(value = "/id")
    public ResponseEntity<TasksDTO> findTaskById(@RequestParam(name = "id",required = true) int taskId)
    {
        return new ResponseEntity<>(taskService.getTaskById(taskId),HttpStatus.OK);
    }

    @PostMapping(value = "/save")
    public ResponseEntity<String> saveTask(@Valid @RequestBody TasksDTO task)
    {
        if( taskService.addTask(task))
        {
            return new ResponseEntity<>("Task Added Successfully",HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Task Not Added",HttpStatus.NOT_ACCEPTABLE);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<String> updateTask(@Valid @RequestBody TasksDTO task , @RequestParam(name = "id",required = true) int taskId)
    {
        task.setTaskId(taskId);
        if (taskService.modifyTask(task))
        {
            return new ResponseEntity<>("Task updated Successfully",HttpStatus.OK);
        }
        return new ResponseEntity<>("Task Not Updated",HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Void> deleteTask(@RequestParam(name = "id",required = true) int taskId)
    {
        taskService.removeTaskbyId(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/cateTasks")
    public ResponseEntity<List<TasksDTO>> getTaskByCateId(@RequestParam(name = "id")int cateId)
    {
        return new ResponseEntity<>(taskService.getTaskByCateId(cateId),HttpStatus.OK);
    }

    @GetMapping(value = "taskCate")
    public ResponseEntity<CategoriesDTO> getCateByTaskId(@RequestParam(name = "id")int taskId)
    {
        return new ResponseEntity<>(taskService.getCateByTaskId(taskId),HttpStatus.OK);
    }

}
