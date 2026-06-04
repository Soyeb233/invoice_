package com.utiitsl.DMSAuthService.service.userService;

import com.utiitsl.DMSAuthService.common.response.PageResponse;
import com.utiitsl.DMSAuthService.dto.RegisterRequestDTO;
import com.utiitsl.DMSAuthService.dto.user.UserDTO;
import com.utiitsl.DMSAuthService.dto.login.UserRequestDTO;
import com.utiitsl.DMSAuthService.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    UserRequestDTO createUser(UserRequestDTO userDTO, MultipartFile image);
    UserDTO createUser(RegisterRequestDTO userDTO);
    UserDTO getUserById(Integer id);
    UserDTO getUserByUsername(String username);
    List<UserDTO> getAllUser();
    PageResponse<UserDTO> getAllUser(int page, int size);
    UserDTO updateUser(UserDTO userDTO,Integer id);
    String deleteUser(Integer id);

    UserDTO activateDeactivateUser(String username,boolean status);
    UserDTO activateUser(String userName);
    UserDTO deactivateUser(String userName);

    public User findOrCreateOAuthUser(String email, String name);

    Long findTotalActiveUserCount();
}
