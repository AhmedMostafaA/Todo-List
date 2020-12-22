package com.costaT.Todo_List_Project.api;

import com.costaT.Todo_List_Project.dto.CategoriesDTO;
import com.costaT.Todo_List_Project.dto.TasksDTO;
import com.costaT.Todo_List_Project.service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "todo/categories")
public class CategoriesController {

    @Autowired
    CategoriesService cateService;

    @GetMapping(value = {"","/all"})
    public ResponseEntity<List<CategoriesDTO>> getAllCate()
    {
        return new ResponseEntity<>(cateService.getAllCate(), HttpStatus.OK);
    }

    @GetMapping(value = "/id")
    public ResponseEntity<CategoriesDTO> getById(@RequestParam(name = "id",required = true) int cateId)
    {
        return new ResponseEntity<>(cateService.getCateById(cateId),HttpStatus.OK);
    }

    @GetMapping(value = "/name")
    public ResponseEntity<CategoriesDTO> getByName(@RequestParam(name = "name",required = true) String cateName)
    {
        return new ResponseEntity<>(cateService.getCateByName(cateName),HttpStatus.OK);
    }

    @PostMapping(value = "save")
    public ResponseEntity<String> saveCate(@Valid @RequestBody CategoriesDTO cate)
    {
        if (cateService.addCate(cate))
        {
            return new ResponseEntity<>("Category is added",HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Category is Not added",HttpStatus.NOT_ACCEPTABLE);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<String> updateCate(@Valid @RequestBody CategoriesDTO cateDTO , @RequestParam(name = "id",required = true) int cateId)
    {
        cateDTO.setCatId(cateId);
        if (cateService.modifyCate(cateDTO))
        {
            return new ResponseEntity<>("Category updated Successfully",HttpStatus.OK);
        }
        return new ResponseEntity<>("Category Not Updated",HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Void> deleteCate(@RequestParam(name = "id",required = true) int cateId)
    {
        cateService.removeCateById(cateId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/addTaskid")
    public ResponseEntity<String> addTaskById(@RequestParam(name = "tid")int taskId,@RequestParam(name = "cid")int cateId)
    {
        if (cateService.addTaskById(cateId,taskId))
        {
            return new ResponseEntity<>("Task Added Successfully",HttpStatus.OK);
        }
        return new ResponseEntity<>("Task Not Added",HttpStatus.NOT_ACCEPTABLE);
    }

    @PostMapping(value = "/addTaskname")
    public ResponseEntity<String> addTaskByname(@RequestParam(name = "tname")String taskName,@RequestParam(name = "cname")String cateName)
    {
        if (cateService.addTaskByName(cateName,taskName))
        {
            return new ResponseEntity<>("Task Added Successfully",HttpStatus.OK);
        }
        return new ResponseEntity<>("Task Not Added",HttpStatus.NOT_ACCEPTABLE);
    }

    @GetMapping(value = "/allcattasks")
    public ResponseEntity<List<TasksDTO>> getAllTasksOfCateById(@RequestParam(name = "id")int cateId)
    {
        return new ResponseEntity<>(cateService.getAllTasksOfCateById(cateId),HttpStatus.OK);
    }
}
