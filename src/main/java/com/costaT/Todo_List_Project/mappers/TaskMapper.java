package com.costaT.Todo_List_Project.mappers;

import com.costaT.Todo_List_Project.dto.TasksDTO;
import com.costaT.Todo_List_Project.model.Tasks;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    ModelMapper modelMapper = new ModelMapper();

    public TasksDTO convertEntityToDto(Tasks tasks)
    {
        TasksDTO tasksDTO = modelMapper.map(tasks, TasksDTO.class);
        return tasksDTO;
    }

    public Tasks convertDtoToEntity(TasksDTO tasksDTO)
    {
        Tasks tasks = modelMapper.map(tasksDTO,Tasks.class);
        return tasks;
    }
}
