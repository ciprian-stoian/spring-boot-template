package com.backend.springboottemplate.controller;

import com.backend.springboottemplate.controller.config.RestApiPathConstants;
import com.backend.springboottemplate.service.AuthenticationService;
import com.backend.springboottemplate.service.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = RestApiPathConstants.AuthenticationController.AUTHENTICATION_PATH)
public class AuthenticationController implements RestApiPathConstants.AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, path = REGISTER_RELATIVE_PATH)
    public UserDTO addUser(@RequestBody @Valid final UserDTO userDTO) {
        return authenticationService.addUser(userDTO);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, path = LOGIN_RELATIVE_PATH)
    public UserDTO getUser(
            @RequestBody @Valid final UserDTO userDTO) {
        return authenticationService.getUser(userDTO);
    }
}
