package com.backend.springboottemplate.service;

import com.backend.springboottemplate.BaseTest;
import com.backend.springboottemplate.builder.UserDTOBuilder;
import com.backend.springboottemplate.builder.UserProfileDTOBuilder;
import com.backend.springboottemplate.repository.UserProfileRepository;
import com.backend.springboottemplate.repository.UserRepository;
import com.backend.springboottemplate.service.dto.UserDTO;
import com.backend.springboottemplate.service.dto.UserProfileDTO;
import com.backend.springboottemplate.util.AuthenticationMocker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class UserProfileServiceTest extends BaseTest {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserProfileService userProfileService = new UserProfileService();

    private UserProfileDTO defaultUserProfile;

    @Before
    public void before() {
        UserDTO userDTO = new UserDTOBuilder()
                .uuid(UUID.randomUUID())
                .username("username@default.com")
                .password("password")
                .active(true)
                .build();

        UserDTO defaultUser = authenticationService.addUser(userDTO);
        AuthenticationMocker.mockAuthentication(userRepository.findByUuid(defaultUser.getUuid()));

        UserProfileDTO userProfileDTO = new UserProfileDTOBuilder()
                .uuid(UUID.randomUUID())
                .firstName("First Name")
                .lastName("Last Name")
                .birthDate(LocalDate.now())
                .build();

        defaultUserProfile = userProfileService.addUserProfile(userProfileDTO);
    }

    @Test
    public void testAddUserProfile() {
        UserDTO addedUserDTO = authenticationService.addUser(new UserDTOBuilder().buildDefault());
        AuthenticationMocker.mockAuthentication(userRepository.findByUuid(addedUserDTO.getUuid()));

        UserProfileDTO userProfileDTO = new UserProfileDTOBuilder()
                .uuid(UUID.randomUUID())
                .firstName("Add First")
                .lastName("Add Last")
                .birthDate(LocalDate.now())
                .build();

        UserProfileDTO addedDTO = userProfileService.addUserProfile(userProfileDTO);

        assertEquals(userProfileDTO.getUuid(), addedDTO.getUuid());
        assertEquals(userProfileDTO.getBirthDate(), addedDTO.getBirthDate());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testAddUserProfileWithoutBirthDate() {
        UserDTO addedUserDTO = authenticationService.addUser(new UserDTOBuilder().buildDefault());
        AuthenticationMocker.mockAuthentication(userRepository.findByUuid(addedUserDTO.getUuid()));

        UserProfileDTO userProfileDTO = new UserProfileDTOBuilder().buildDefault();
        userProfileDTO.setBirthDate(null);

        userProfileService.addUserProfile(userProfileDTO);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testAddUserProfileWithoutFirstName() {
        UserDTO addedUserDTO = authenticationService.addUser(new UserDTOBuilder().buildDefault());
        AuthenticationMocker.mockAuthentication(userRepository.findByUuid(addedUserDTO.getUuid()));

        UserProfileDTO userProfileDTO = new UserProfileDTOBuilder().buildDefault();
        userProfileDTO.setFirstName(null);

        userProfileService.addUserProfile(userProfileDTO);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testAddUserProfileWithoutLastName() {
        UserDTO addedUserDTO = authenticationService.addUser(new UserDTOBuilder().buildDefault());
        AuthenticationMocker.mockAuthentication(userRepository.findByUuid(addedUserDTO.getUuid()));

        UserProfileDTO userProfileDTO = new UserProfileDTOBuilder().buildDefault();
        userProfileDTO.setLastName(null);

        userProfileService.addUserProfile(userProfileDTO);
    }

    @Test
    public void testAddDuplicateUserProfile() {
        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () -> userProfileService.addUserProfile(defaultUserProfile));

        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
    }

    @Test
    public void testUpdateUserProfile() {
        defaultUserProfile.setFirstName("RayzaN");
        defaultUserProfile.setLastName("The First");
        defaultUserProfile.setBirthDate(LocalDate.now());

        UserProfileDTO updatedDTO = userProfileService.updateUserProfile(defaultUserProfile);
        assertEquals(defaultUserProfile.getUuid(), updatedDTO.getUuid());
        assertEquals(defaultUserProfile.getFirstName(), updatedDTO.getFirstName());
        assertEquals(defaultUserProfile.getLastName(), updatedDTO.getLastName());
        assertEquals(defaultUserProfile.getBirthDate(), updatedDTO.getBirthDate());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testUpdateUserProfileWithoutFirstName() {
        defaultUserProfile.setFirstName(null);

        userProfileService.updateUserProfile(defaultUserProfile);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testUpdateUserProfileWithoutLastName() {
        defaultUserProfile.setLastName(null);

        userProfileService.updateUserProfile(defaultUserProfile);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testUpdateUserProfileWithoutBirthDate() {
        defaultUserProfile.setBirthDate(null);

        userProfileService.updateUserProfile(defaultUserProfile);
    }

    @Test
    public void testGetUserProfile() {
        UserProfileDTO userProfileDTO = userProfileService.getUserProfile();

        assertEquals(defaultUserProfile.getUuid(), userProfileDTO.getUuid());
        assertEquals(defaultUserProfile.getBirthDate(), userProfileDTO.getBirthDate());
    }

    @After
    public void after() {
        userProfileRepository.deleteAll();
        userRepository.deleteAll();
    }
}
