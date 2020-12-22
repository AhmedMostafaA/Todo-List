package com.costaT.Todo_List_Project.mappers;


import com.costaT.Todo_List_Project.dto.UsersDTO;
import com.costaT.Todo_List_Project.model.Users;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    ModelMapper modelMapper = new ModelMapper();

    public UsersDTO convertEntityToDto(Users user)
    {
        UsersDTO usersDTO = modelMapper.map(user, UsersDTO.class);
        return usersDTO;
    }

    public Users convertDtoToEntity(UsersDTO userDTO)
    {
        Users user = modelMapper.map(userDTO,Users.class);
        return user;
    }
}
