package com.costaT.Todo_List_Project.service;

import com.costaT.Todo_List_Project.dao.CategoryRepository;
import com.costaT.Todo_List_Project.dto.CategoriesDTO;
import com.costaT.Todo_List_Project.dto.TasksDTO;
import com.costaT.Todo_List_Project.exception_handler.ConflictException;
import com.costaT.Todo_List_Project.exception_handler.NotFoundException;
import com.costaT.Todo_List_Project.mappers.CategoryMapper;
import com.costaT.Todo_List_Project.model.Categories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriesService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TaskService taskService;

    @Autowired
    private CategoryMapper categoryMapper;

    public List<CategoriesDTO> getAllCate()
    {
        return categoryRepository.findAll().stream().map(cate -> categoryMapper.convertEntityToDto(cate) ).collect(Collectors.toList());
    }

    public CategoriesDTO getCateById(int cateId)
    {
        Optional<Categories> opCate = categoryRepository.findById(cateId);
        if (!opCate.isPresent())
        {
            throw new NotFoundException("Not found Category with this ID :"+cateId);
        }
        return categoryMapper.convertEntityToDto(opCate.get());

    }

    public CategoriesDTO getCateByName(String cateName)
    {
        Optional<Categories> opCate = categoryRepository.findByCatName(cateName);

        if (!opCate.isPresent())
        {
            throw new NotFoundException("Not found Category with this Name :"+cateName);
        }
        return categoryMapper.convertEntityToDto(opCate.get());
    }

    public boolean addCate(CategoriesDTO categoriesDTO)
    {
        if(categoryRepository.findByCatName(categoriesDTO.getCatName()).isPresent()) {
           throw new ConflictException("Another Category with same Name exists");
        }
        Categories cate = categoryMapper.convertDtoToEntity(categoriesDTO);
        return categoryRepository.save(cate) != null ? true : false;
    }

    public boolean modifyCate(CategoriesDTO categoriesDTO)
    {
        Categories cate = categoryMapper.convertDtoToEntity(categoriesDTO);
        Optional<Categories> oldCate = categoryRepository.findById(cate.getCatId());
        if (!oldCate.isPresent())
        {
            throw new NotFoundException("Not found Category with this ID :"+categoriesDTO.getCatId());
        }
        cate.setTasks(oldCate.get().getTasks());
        return categoryRepository.save(cate) != null ? true : false;
    }

    public void removeCateById(int cateId)
    {
        getCateById(cateId);
        categoryRepository.deleteById(cateId);
    }

    public boolean addTaskById(int cateId,int taskId)
    {
        CategoriesDTO cate = getCateById(cateId);
        TasksDTO task = taskService.getTaskById(taskId);
        task.setCategory(cate);
        return taskService.modifyTask(task);
    }

    public boolean addTaskByName(String cateName , String taskName)
    {
        CategoriesDTO cate = getCateByName(cateName);
        TasksDTO task = taskService.getTaskByName(taskName);
        task.setCategory(cate);
        return taskService.modifyTask(task);
    }

    public List<TasksDTO> getAllTasksOfCateById(int cateId)
    {
        return getCateById(cateId).getTasks();
    }
}