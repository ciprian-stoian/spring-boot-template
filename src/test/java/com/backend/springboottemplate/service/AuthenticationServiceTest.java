package com.backend.springboottemplate.service;

import com.backend.springboottemplate.BaseTest;
import com.backend.springboottemplate.builder.UserDTOBuilder;
import com.backend.springboottemplate.repository.UserRepository;
import com.backend.springboottemplate.service.dto.UserDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class AuthenticationServiceTest extends BaseTest {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserRepository userRepository;

    private UserDTO defaultUser;

    @Before
    public void before() {
        UserDTO userDTO = new UserDTOBuilder()
                .uuid(UUID.randomUUID())
                .username("username@default.com")
                .password("password")
                .active(true)
                .build();

        defaultUser = authenticationService.addUser(userDTO);
    }

    @Test
    public void testAddUser() {
        UserDTO userDTO = new UserDTOBuilder()
                .uuid(UUID.randomUUID())
                .username("add@user.com")
                .password("Add password")
                .active(true)
                .build();

        UserDTO addedDTO = authenticationService.addUser(userDTO);

        assertEquals(userDTO.getUuid(), addedDTO.getUuid());
        assertEquals(userDTO.getUsername(), addedDTO.getUsername());
        assertNull(addedDTO.getPassword());
        assertTrue(addedDTO.getActive());
        assertNotNull(addedDTO.getCreatedOn());
        assertNull(addedDTO.getUpdatedOn());
    }

    @Test
    public void testAddUserWithoutUsername() {
        UserDTO userDTO = new UserDTOBuilder().buildDefault();
        userDTO.setUsername(null);

        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () -> authenticationService.addUser(userDTO));

        assertEquals(HttpStatus.BAD_REQUEST,  exception.getStatus());
    }

    @Test
    public void testAddUserWithBadUsername() {
        UserDTO userDTO = new UserDTOBuilder().buildDefault();
        userDTO.setUsername("1");

        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () -> authenticationService.addUser(userDTO));

        assertEquals(HttpStatus.BAD_REQUEST,  exception.getStatus());
    }

    @Test
    public void testAddUserWithoutPassword() {
        UserDTO userDTO = new UserDTOBuilder().buildDefault();
        userDTO.setPassword(null);

        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () -> authenticationService.addUser(userDTO));

        assertEquals(HttpStatus.BAD_REQUEST,  exception.getStatus());
    }

    @Test
    public void testAddUserWithBadPassword() {
        UserDTO userDTO = new UserDTOBuilder().buildDefault();
        userDTO.setPassword("1");

        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () -> authenticationService.addUser(userDTO));

        assertEquals(HttpStatus.BAD_REQUEST,  exception.getStatus());
    }

    @Test
    public void testAddUserWithActiveFalse() {
        UserDTO userDTO = new UserDTOBuilder().buildDefault();
        userDTO.setActive(false);

        UserDTO addedDTO = authenticationService.addUser(userDTO);
        assertTrue(addedDTO.getActive());
    }

    @Test
    public void testAddUserWithSetCreatedOn() {
        UserDTO userDTO = new UserDTOBuilder().buildDefault();
        userDTO.setCreatedOn(LocalDate.of(1992, 6, 8));

        UserDTO addedDTO = authenticationService.addUser(userDTO);
        assertNotEquals(userDTO.getCreatedOn(), addedDTO.getCreatedOn());
    }

    @Test
    public void testAddUserWithSetUpdatedOn() {
        UserDTO userDTO = new UserDTOBuilder().buildDefault();
        userDTO.setUpdatedOn(LocalDate.of(1992, 6, 8));

        UserDTO addedDTO = authenticationService.addUser(userDTO);
        assertNull(addedDTO.getUpdatedOn());
    }

    @Test
    public void testAddDuplicateUser() {
        defaultUser.setPassword("password");

        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () -> authenticationService.addUser(defaultUser));

        assertEquals(HttpStatus.CONFLICT,  exception.getStatus());
    }

    @Test
    public void testGetUser() {
        UserDTO userDTO = new UserDTOBuilder()
                .username("username@default.com")
                .password("password")
                .build();

        UserDTO loadedUserDTO = authenticationService.getUser(userDTO);

        assertEquals(defaultUser.getUuid(), loadedUserDTO.getUuid());
        assertEquals(defaultUser.getUsername(), loadedUserDTO.getUsername());
        assertNull(loadedUserDTO.getPassword());
        assertTrue(loadedUserDTO.getActive());
        assertNotNull(loadedUserDTO.getCreatedOn());
        assertNull(loadedUserDTO.getUpdatedOn());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void testGetUserWithBadUsername() {
        UserDTO userDTO = new UserDTOBuilder()
                .username("bad_username@default.com")
                .password("password")
                .build();

        authenticationService.getUser(userDTO);
    }

    @Test(expected = BadCredentialsException.class)
    public void testGetUserWithBadPassword() {
        UserDTO userDTO = new UserDTOBuilder()
                .username("username@default.com")
                .password("bad_password")
                .build();

        authenticationService.getUser(userDTO);
    }

    @After
    public void after() {
        userRepository.deleteAll();
    }
}
