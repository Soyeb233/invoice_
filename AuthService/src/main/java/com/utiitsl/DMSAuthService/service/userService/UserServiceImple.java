package com.utiitsl.DMSAuthService.service.userService;

import com.utiitsl.DMSAuthService.common.exceptionHandler.UserDefinedException;
import com.utiitsl.DMSAuthService.common.response.PageResponse;
import com.utiitsl.DMSAuthService.constants.ErrorMessage;
import com.utiitsl.DMSAuthService.constants.Role;
import com.utiitsl.DMSAuthService.dto.AuthenticationResponseDTO;
import com.utiitsl.DMSAuthService.dto.RegisterRequestDTO;
import com.utiitsl.DMSAuthService.dto.UserDTO;
import com.utiitsl.DMSAuthService.entity.User;
import com.utiitsl.DMSAuthService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImple implements UserService{


    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;



    @Override
    public UserDTO createUser(RegisterRequestDTO registerRequest) {
        Optional<User> userOptional = userRepository.findByUsername(registerRequest.getUsername());
        Optional<User> userEmailOptional=userRepository.findByEmail(registerRequest.getEmail());
        if (userOptional.isPresent()) {
            throw new UserDefinedException("USER ALREADY EXISTS", HttpStatus.CONFLICT);
        }
        if(userEmailOptional.isPresent()){
            throw new UserDefinedException(ErrorMessage.EMAIL_ALREADY_EXISTS,HttpStatus.CONFLICT);
        }
        try{
            User user = User.builder()
                    .username(registerRequest.getUsername())
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .originalPassword(registerRequest.getPassword())
                    .firstName(registerRequest.getFirstName())
                    .lastName(registerRequest.getLastName())
                    .email(registerRequest.getEmail())
                    .mobileNo(registerRequest.getMobileNo())
                    .role(registerRequest.getRole()).build();

            User savedUser = userRepository.save(user);
            System.out.println("AFTER UPDATING RECORDS :" + savedUser.getId());

            return modelMapper.map(savedUser,UserDTO.class);
        }
        catch(Exception ex){
            ex.printStackTrace();
            throw new UserDefinedException("SOMETHING GOES WRONGS",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public UserDTO getUserById(Integer id){

        Optional<User> userOptional=userRepository.findById(id);
        if(userOptional.isPresent()){
           UserDTO userDTO= modelMapper.map(userOptional.get(),UserDTO.class);

           return userDTO;
        }
        throw new UserDefinedException(ErrorMessage.USER_NOT_FOUND,HttpStatus.NOT_FOUND);
    }

    @Override
    public UserDTO getUserByUsername(String username){

        Optional<User> userOptional=userRepository.findByUsername(username);
        if(userOptional.isPresent()){
            UserDTO userDTO= modelMapper.map(userOptional.get(),UserDTO.class);

            return userDTO;
        }
        throw new UserDefinedException(ErrorMessage.USER_NOT_FOUND,HttpStatus.NOT_FOUND);
    }

    @Override
    public List<UserDTO> getAllUser() {
        List<User> userList=  userRepository.findAll();
        List<UserDTO> userDTOList = userList
                .stream()
                .map(user -> {
                    // Clear sensitive information
                    user.setPassword("");        // Clear the password
//                    user.setOriginalPassword(""); // Clear the originalPassword
                    // Map the user object to UserDTO and return the result
                    return modelMapper.map(user, UserDTO.class);
                })
                .collect(Collectors.toList());
        userDTOList.forEach(e->
                System.err.println(e));

        return userDTOList;
    }

    @Override
    public PageResponse<UserDTO> getAllUser(int page, int size) {
        Page<User> userPage=userRepository.findAll(PageRequest.of(page, size));

        return PageResponse.<UserDTO>builder()
//                .data(userPage.getContent().stream().map(e-> modelMapper.map(e,UserDTO.class)).toList())
                .data(userPage.getContent().stream()
                        .map(e -> modelMapper.map(e, UserDTO.class)) // Correctly mapping each element
                        .collect(Collectors.toList())) // Collect the mapped elements into a list
                .currentPage(userPage.getNumber()).
                totalPages(userPage.getTotalPages())
                .hasNext(userPage.hasNext())
                .hasPrevious(userPage.hasPrevious())
                .totalRecords(userPage.getTotalElements()).
                build();
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO,Integer id) {
        Optional<User> userOptional=userRepository.findById(id);

        User user=null;
        if(userOptional.isPresent()){
            System.err.println(userOptional.get());
            user=userOptional.get();
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            user.setOriginalPassword(userDTO.getPassword());
            user.setEmail(userDTO.getEmail());
            user.setRole(userDTO.getRole());
            user.setActiveStatus(userDTO.getActiveStatus());
        }
        try{
            // update the user
            if(user!=null){
                userRepository.save(user);
            }
            System.out.println("DATA UPDATED..");
            return modelMapper.map(user,UserDTO.class);
        }
        catch (Exception ex){
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
        throw new UserDefinedException(ErrorMessage.USER_ALREADY_EXISTS,HttpStatus.NOT_FOUND);
    }

    @Override
    @Transactional
    public String deleteUser(Integer id) {
        if(userRepository.existsById(id)){
            System.out.println("INSIDE DELETE METHOD");
          int rowAffected= userRepository.updateUserActiveStatus(id,true);
          System.err.println("rowAffected :"+rowAffected);
          if(rowAffected>0){
              System.out.println("USER DELETED SUCCESSFULLY :"+id);
          }
          return ErrorMessage.USER_DELETED_SUCCESSFULLY;
        }
        throw new UserDefinedException(ErrorMessage.USER_NOT_FOUND,HttpStatus.NOT_FOUND);
    }

    // ACTIVATE DEACTIVATE THE USERS
    @Override
    @Transactional
    public UserDTO activateDeactivateUser(String username,boolean status){
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new UserDefinedException("User Not found with username "+ username, HttpStatus.CONFLICT);
        }
        User user = userOptional.get();
        user.setActiveStatus(status);
        user = userRepository.save(user);
        return UserDTO.builder().firstName(user.getFirstName())
                .lastName(user.getLastName()).email(user.getEmail())
                .activeStatus(user.isActiveStatus())
                .username(user.getUsername())
                .build();
    }

    @Override
    @Transactional
    public UserDTO activateUser(String userName) {
        Optional<User> userOptional = userRepository.findByUsername(userName);
        if (userOptional.isEmpty()) {
            throw new UserDefinedException("User Not found with username "+ userName, HttpStatus.CONFLICT);
        }
        User user = userOptional.get();
        user.setActiveStatus(true);
        user = userRepository.save(user);
        return UserDTO.builder().firstName(user.getFirstName())
                .lastName(user.getLastName()).email(user.getEmail())
                .activeStatus(user.isActiveStatus())
                .username(user.getUsername())
                .build();
    }

    @Override
    public UserDTO deactivateUser(String userName) {
        Optional<User> userOptional = userRepository.findByUsername(userName);
        if (userOptional.isEmpty()) {
            throw new UserDefinedException("User Not found with username "+ userName, HttpStatus.CONFLICT);
        }

        User user = userOptional.get();
        user.setActiveStatus(false);
        user = userRepository.save(user);
        return UserDTO.builder().firstName(user.getFirstName())
                .lastName(user.getLastName()).email(user.getEmail())
                .activeStatus(user.isActiveStatus())
                .username(user.getUsername())
                .build();
    }

    @Override
    public User findOrCreateOAuthUser(String email, String name) {

        return userRepository.findByEmail(email)
                .orElseGet(() -> {

                    User user = User.builder()
                            .username(name != null ? name : email)
                            .password("")
                            .originalPassword("")
                            .firstName(name!=null? name :email)
                            .lastName("")
                            .email(email)
                            .role(Role.USER).build();
                    return userRepository.save(user);
                });
    }
}
