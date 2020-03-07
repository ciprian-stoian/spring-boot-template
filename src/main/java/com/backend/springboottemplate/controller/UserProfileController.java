package com.backend.springboottemplate.controller;

import com.backend.springboottemplate.controller.config.RestApiPathConstants;
import com.backend.springboottemplate.service.UserProfileService;
import com.backend.springboottemplate.service.dto.UserProfileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(path = RestApiPathConstants.UserProfileController.USER_PROFILE_PATH)
public class UserProfileController implements RestApiPathConstants.UserProfileController {
    @Autowired
    private UserProfileService userProfileService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public UserProfileDTO addUserProfile(
            @RequestBody @Valid final UserProfileDTO userProfileDTO) {
        userProfileDTO.setUserUuid(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName()));

        return userProfileService.addUserProfile(userProfileDTO);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public UserProfileDTO updateUserProfile(
            @RequestBody @Valid final UserProfileDTO userProfileDTO) {
        userProfileDTO.setUserUuid(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName()));

        return userProfileService.updateUserProfile(userProfileDTO);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public UserProfileDTO getUserProfile() {
        return userProfileService.getUserProfile(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName()));
    }
}
