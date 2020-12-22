package com.costaT.Todo_List_Project.mappers;

import com.costaT.Todo_List_Project.dto.CategoriesDTO;
import com.costaT.Todo_List_Project.model.Categories;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    ModelMapper modelMapper = new ModelMapper();

    public CategoriesDTO convertEntityToDto(Categories categories)
    {
        CategoriesDTO categoryDTO = modelMapper.map(categories, CategoriesDTO.class);
        return categoryDTO;
    }

    public Categories convertDtoToEntity(CategoriesDTO categoriesDTO)
    {
        Categories categories = modelMapper.map(categoriesDTO,Categories.class);
        return categories;
    }
}
