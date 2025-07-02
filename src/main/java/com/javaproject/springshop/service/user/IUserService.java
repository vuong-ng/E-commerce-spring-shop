package com.javaproject.springshop.service.user;

import com.javaproject.springshop.dto.UserDto;
import com.javaproject.springshop.model.User;
import com.javaproject.springshop.requests.CreateUserRequest;
import com.javaproject.springshop.requests.UpdateUserRequest;

public interface IUserService {
    User getUserById(Long userId);

    User createUser(CreateUserRequest request);

    User updateUser(UpdateUserRequest request, Long userId);

    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);
    


}
