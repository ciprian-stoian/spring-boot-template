package com.backend.springboottemplate.service;

import com.backend.springboottemplate.BaseTest;
import com.backend.springboottemplate.builder.UserDTOBuilder;
import com.backend.springboottemplate.builder.UserProfileDTOBuilder;
import com.backend.springboottemplate.repository.UserProfileRepository;
import com.backend.springboottemplate.repository.UserRepository;
import com.backend.springboottemplate.service.dto.UserDTO;
import com.backend.springboottemplate.service.dto.UserProfileDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class UserProfileServiceTest extends BaseTest {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    private UserDTO defaultUser;

    private UserProfileDTO defaultUserProfile;

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

        UserProfileDTO userProfileDTO = new UserProfileDTOBuilder()
                .uuid(UUID.randomUUID())
                .userUuid(userDTO.getUuid())
                .birthDate(LocalDate.now())
                .build();

        defaultUserProfile = userProfileService.addUserProfile(userProfileDTO);
    }

    @Test
    public void testAddUserProfile() {
        UserDTO addedUserDTO = authenticationService.addUser(new UserDTOBuilder().buildDefault());

        UserProfileDTO userProfileDTO = new UserProfileDTOBuilder()
                .uuid(UUID.randomUUID())
                .userUuid(addedUserDTO.getUuid())
                .birthDate(LocalDate.now())
                .build();

        UserProfileDTO addedDTO = userProfileService.addUserProfile(userProfileDTO);

        assertEquals(userProfileDTO.getUuid(), addedDTO.getUuid());
        assertEquals(userProfileDTO.getUserUuid(), addedDTO.getUserUuid());
        assertEquals(userProfileDTO.getBirthDate(), addedDTO.getBirthDate());
    }

    public void testAddUserProfileWithoutUserUuid() {
        UserProfileDTO userProfileDTO = new UserProfileDTOBuilder().buildDefault();
        userProfileDTO.setUserUuid(null);

        userProfileService.addUserProfile(userProfileDTO);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testAddUserProfileWithoutBirthDate() {
        UserProfileDTO userProfileDTO = new UserProfileDTOBuilder().buildDefault();
        userProfileDTO.setBirthDate(null);

        userProfileService.addUserProfile(userProfileDTO);
    }

    public void testAddDuplicateUserProfile() {
        userProfileService.addUserProfile(defaultUserProfile);
    }

    @Test
    public void testUpdateUserProfile() {
        defaultUserProfile.setBirthDate(LocalDate.now());

        UserProfileDTO updatedDTO = userProfileService.updateUserProfile(defaultUserProfile);
        assertEquals(defaultUserProfile.getUuid(), updatedDTO.getUuid());
        assertEquals(defaultUserProfile.getBirthDate(), updatedDTO.getBirthDate());
    }

    public void testUpdateUserProfileWithoutUserUuid() {
        defaultUserProfile.setUserUuid(null);

        userProfileService.updateUserProfile(defaultUserProfile);
    }

    public void testUpdateUserProfileWithoutBirthDate() {
        defaultUserProfile.setBirthDate(null);

        userProfileService.updateUserProfile(defaultUserProfile);
    }

    @Test
    public void testGetUserProfile() {
        UserProfileDTO userProfileDTO = userProfileService.getUserProfile(defaultUser.getUuid());

        assertEquals(defaultUserProfile.getUuid(), userProfileDTO.getUuid());
        assertEquals(defaultUserProfile.getUserUuid(), userProfileDTO.getUserUuid());
        assertEquals(defaultUserProfile.getBirthDate(), userProfileDTO.getBirthDate());
    }

    public void testGetUserProfileWithBadUserUuid() {
        userProfileService.getUserProfile(UUID.randomUUID());
    }

    @After
    public void after() {
        userProfileRepository.deleteAll();
        userRepository.deleteAll();
    }
}
