package com.backend.springboottemplate.service;

import com.backend.springboottemplate.repository.UserRepository;
import com.backend.springboottemplate.repository.entity.UserEntity;
import com.backend.springboottemplate.service.dto.UserDTO;
import com.backend.springboottemplate.util.AuthenticatedUser;
import org.apache.commons.validator.routines.EmailValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class AuthenticationService implements BaseService<UserDTO, UserEntity>, UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private ModelMapper modelMapper = new ModelMapper();

    private static final int MIN_PASSWORD_LENGTH = 6;

    @Transactional
    public UserDTO addUser(final UserDTO userDTO) {
        validateAddUser(userDTO);

        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userDTO.setActive(true);

        UserDTO savedUserDTO = toDTO(userRepository.save(toEntity(userDTO)));
        savedUserDTO.setPassword(null);

        return savedUserDTO;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsernameAndActiveTrue(username);
        validateAddUser(userEntity);

        return new AuthenticatedUser(String.valueOf(userEntity.getUuid()), userEntity.getPassword(), new ArrayList<>(), userEntity);
    }

    public UserDTO getUser(final UserDTO userDTO) {
        UserEntity userEntity = userRepository.findByUsernameAndActiveTrue(userDTO.getUsername());
        validateAddUser(userEntity);

        if (!this.passwordEncoder.matches(userDTO.getPassword(), userEntity.getPassword())) {
            throw new BadCredentialsException("Invalid username or password!");
        }

        userEntity.setPassword(null);

        return toDTO(userEntity);
    }

    private void validateAddUser(final UserEntity userEntity) throws UsernameNotFoundException {
        if (userEntity == null) {
            throw new UsernameNotFoundException("Username was not found!");
        }
    }

    private void validateAddUser(final UserDTO userDTO) {
        if (!EmailValidator.getInstance().isValid(userDTO.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please provide a valid email!");
        }

        if (userDTO.getPassword() == null || userDTO.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please provide a password of at least 6 characters long!");
        }

        if (userRepository.findByUsernameAndActiveTrue(userDTO.getUsername()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Duplicate Entity found in the database!");
        }
    }

    @Override
    public UserDTO toDTO(final UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDTO.class);
    }

    @Override
    public UserEntity toEntity(final UserDTO userDTO) {
        UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);
        userEntity.setCreatedOn(LocalDate.now());
        userEntity.setUpdatedOn(null);

        return userEntity;
    }
}
