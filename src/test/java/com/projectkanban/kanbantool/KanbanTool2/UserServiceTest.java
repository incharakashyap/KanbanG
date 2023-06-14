package com.projectkanban.kanbantool.KanbanTool2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.projectkanban.kanbantool.KanbanTool2.domain.User;
import com.projectkanban.kanbantool.KanbanTool2.repositories.UserRepository;
import com.projectkanban.kanbantool.KanbanTool2.services.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest

public class UserServiceTest {

	@Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
       
    }

    @Test
    void saveUser_UniqueUsername_Success() {
        // Arrange
        User newUser = new User();
        newUser.setUsername("testuser");
        newUser.setPassword("password");
        
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(newUser);
        
        // Act
        User savedUser = userService.saveUser(newUser);
        
        // Assert
        assertEquals("testuser", savedUser.getUsername());
        assertEquals("encodedPassword", savedUser.getPassword());
        assertEquals("", savedUser.getConfirmPassword());
        verify(bCryptPasswordEncoder).encode("password");
        verify(userRepository).save(newUser);
    }
    
   
  

//    @Test
//    void saveUser_DuplicateUsername_ExceptionThrown() {
//        // Arrange
//        User existingUser = new User();
//        existingUser.setUsername("existinguser");
//        when(userRepository.findByUsername(anyString())).thenReturn(existingUser);
//
//        User newUser = new User();
//        newUser.setUsername("existinguser");
//
//        // Act & Assert
//        assertThrows(UsernameAlreadyExistsException.class, () -> userService.saveUser(newUser));
//        verify(userRepository).findByUsername("existinguser");
//    }
}

