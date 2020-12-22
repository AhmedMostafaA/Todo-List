package com.costaT.Todo_List_Project.api;

import com.costaT.Todo_List_Project.dto.CategoriesDTO;
import com.costaT.Todo_List_Project.dto.TasksDTO;
import com.costaT.Todo_List_Project.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.BDDMockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    private List<TasksDTO> task;

    @BeforeEach
    public void init()
    {
        task = Arrays.asList(new TasksDTO(1,"task1","des1",null),
                new TasksDTO(2,"task2","des2",null),
                new TasksDTO(3,"task3","des3",null),
                new TasksDTO(4,"task4","des4",null));


    }

    @Test
    void findAllTasksTest_ReturnJsonArrayOfTasks() throws Exception {
        given(taskService.getAllTasks()).willReturn(task);
        mockMvc.perform(get("/todo/tasks/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(4)))
                .andExpect(jsonPath("$[0].taskTitle",equalTo("task1")));
    }

    @Test
    void findTaskByIdTest_ReturnJsonOfTask_ValidID() throws Exception {
        //given(taskService.getTaskById(anyInt())).willReturn(task.get(0));
        Mockito.when(taskService.getTaskById(anyInt())).thenReturn(task.get(0));

        mockMvc.perform(get("/todo/tasks/id").param("id","1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.taskTitle",equalTo("task1")));
    }

    @Test
    void findTaskByIdTest_ReturnJsonOfTask_InvalidID() throws Exception {
        given(taskService.getTaskById(anyInt())).willReturn(null);

        mockMvc.perform(get("/todo/tasks/id").param("id","1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void findTaskByIdTest_ReturnJsonOfTask_InvalidArgs() throws Exception {
        given(taskService.getTaskById(anyInt())).willReturn(null);

        mockMvc.perform(get("/todo/tasks/id").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void saveTaskTest_createdTaskThenReturnString_created() throws Exception {
        given(taskService.addTask(Mockito.any(TasksDTO.class))).willReturn(Boolean.TRUE);

        TasksDTO tasksDTO = new TasksDTO(4,"Task4","blabla",null);
        mockMvc.perform(post("/todo/tasks/save").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(tasksDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Task Added Successfully"));
    }

    @Test
    void saveTaskTest_createdTaskThenReturnString_Notcreated() throws Exception {
        given(taskService.addTask(Mockito.any(TasksDTO.class))).willReturn(Boolean.FALSE);

        TasksDTO tasksDTO = new TasksDTO();
        tasksDTO.setTaskTitle("task4");
        mockMvc.perform(post("/todo/tasks/save").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(tasksDTO)))
                .andExpect(status().isNotAcceptable())
                .andExpect(content().string("Task Not Added"));
    }

    @Test
    void saveTaskTest_createdTaskThenReturnString_NotValidArg() throws Exception {
        given(taskService.addTask(Mockito.any(TasksDTO.class))).willReturn(Boolean.FALSE);

        TasksDTO tasksDTO = new TasksDTO();
        mockMvc.perform(post("/todo/tasks/save").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(tasksDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateTaskTest_updateTaskThenReturnString_Updated() throws Exception {
        given(taskService.modifyTask(Mockito.any(TasksDTO.class))).willReturn(Boolean.TRUE);

        TasksDTO tasksDTO = new TasksDTO(1,"Task1","blabla",null);
        mockMvc.perform(put("/todo/tasks/update").param("id","1").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(tasksDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Task updated Successfully"));
    }

    @Test
    void updateTaskTest_updateTaskThenReturnString_Notupdated() throws Exception {
        given(taskService.modifyTask(Mockito.any(TasksDTO.class))).willReturn(Boolean.FALSE);

        TasksDTO tasksDTO = new TasksDTO();
        tasksDTO.setTaskTitle("task4");
        mockMvc.perform(put("/todo/tasks/update").param("id","5").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(tasksDTO)))
                .andExpect(status().isNotModified())
                .andExpect(content().string("Task Not Updated"));
    }

    @Test
    void updateTaskTest_updateTaskThenReturnString_InvalidArg() throws Exception {
        TasksDTO tasksDTO = new TasksDTO();
        mockMvc.perform(put("/todo/tasks/update").param("id","5").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(tasksDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getTaskByCateIdTest_ReturnJsonArrayOfTasksWithinCategory() throws Exception {
        given(taskService.getTaskByCateId(anyInt())).willReturn(task);

        mockMvc.perform(get("/todo/tasks/cateTasks").param("id","1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(4)))
                .andExpect(jsonPath("$[0].taskTitle",equalTo("task1")));
    }

    @Test
    void getCateByTaskId_ReturnJsonCategoryOfTask() throws Exception {

        CategoriesDTO cate = new CategoriesDTO(1,"cate1",1);
        given(taskService.getCateByTaskId(anyInt())).willReturn(cate);

        mockMvc.perform(get("/todo/tasks/taskCate").param("id","1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.catName",equalTo("cate1")));
    }
}