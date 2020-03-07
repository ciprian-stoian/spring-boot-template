package com.backend.springboottemplate.service;

import com.backend.springboottemplate.BaseTest;
import com.backend.springboottemplate.builder.CredentialsDTOBuilder;
import com.backend.springboottemplate.builder.UserDTOBuilder;
import com.backend.springboottemplate.repository.UserRepository;
import com.backend.springboottemplate.service.dto.CredentialsDTO;
import com.backend.springboottemplate.service.dto.UserDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
                .firstName("First Name")
                .lastName("Last Name")
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
                .firstName("Add First")
                .lastName("Add Last")
                .username("add@user.com")
                .password("Add password")
                .active(true)
                .build();

        UserDTO addedDTO = authenticationService.addUser(userDTO);

        assertEquals(userDTO.getUuid(), addedDTO.getUuid());
        assertEquals(userDTO.getFirstName(), addedDTO.getFirstName());
        assertEquals(userDTO.getLastName(), addedDTO.getLastName());
        assertEquals(userDTO.getUsername(), addedDTO.getUsername());
        assertNull(addedDTO.getPassword());
        assertTrue(addedDTO.getActive());
        assertNotNull(addedDTO.getCreatedOn());
        assertNull(addedDTO.getUpdatedOn());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testAddUserWithoutFirstName() {
        UserDTO userDTO = new UserDTOBuilder().buildDefault();
        userDTO.setFirstName(null);

        authenticationService.addUser(userDTO);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testAddUserWithoutLastName() {
        UserDTO userDTO = new UserDTOBuilder().buildDefault();
        userDTO.setLastName(null);

        authenticationService.addUser(userDTO);
    }

    @Test
    public void testAddUserWithoutUsername() {
        UserDTO userDTO = new UserDTOBuilder().buildDefault();
        userDTO.setUsername(null);

        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () -> authenticationService.addUser(userDTO));

        assertEquals(HttpStatus.BAD_REQUEST,  exception.getStatus());
    }

    @Test
    public void testAddUserWithoutBadUsername() {
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
        CredentialsDTO credentialsDTO = new CredentialsDTOBuilder()
                .username("username@default.com")
                .password("password")
                .build();

        UserDTO userDTO = authenticationService.getUser(credentialsDTO);

        assertEquals(defaultUser.getUuid(), userDTO.getUuid());
        assertEquals(defaultUser.getFirstName(), userDTO.getFirstName());
        assertEquals(defaultUser.getLastName(), userDTO.getLastName());
        assertEquals(defaultUser.getUsername(), userDTO.getUsername());
        assertNull(userDTO.getPassword());
        assertTrue(userDTO.getActive());
        assertNotNull(userDTO.getCreatedOn());
        assertNull(userDTO.getUpdatedOn());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void testGetUserWithBadUsername() {
        CredentialsDTO credentialsDTO = new CredentialsDTOBuilder()
                .username("bad_username@default.com")
                .password("password")
                .build();

        authenticationService.getUser(credentialsDTO);
    }

    @Test(expected = BadCredentialsException.class)
    public void testGetUserWithBadPassword() {
        CredentialsDTO credentialsDTO = new CredentialsDTOBuilder()
                .username("username@default.com")
                .password("bad_password")
                .build();

        authenticationService.getUser(credentialsDTO);
    }

    @After
    public void after() {
        userRepository.deleteAll();
    }
}
