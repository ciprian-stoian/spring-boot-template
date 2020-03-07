package com.backend.springboottemplate.service;

import com.backend.springboottemplate.repository.UserRepository;
import com.backend.springboottemplate.repository.entity.UserEntity;
import com.backend.springboottemplate.service.dto.CredentialsDTO;
import com.backend.springboottemplate.service.dto.UserDTO;
import org.apache.commons.validator.routines.EmailValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

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
        validate(userDTO);

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
        validate(userEntity);

        return new User(String.valueOf(userEntity.getUuid()), userEntity.getPassword(), new ArrayList<>());
    }

    public UserDTO getUser(final CredentialsDTO credentialsDTO) {
        UserEntity userEntity = userRepository.findByUsernameAndActiveTrue(credentialsDTO.getUsername());
        validate(userEntity);

        if (!this.passwordEncoder.matches(credentialsDTO.getPassword(), userEntity.getPassword())) {
            throw new BadCredentialsException("Invalid username or password!");
        }

        userEntity.setPassword(null);

        return toDTO(userEntity);
    }

    private void validate(final UserEntity userEntity) throws UsernameNotFoundException {
        if (userEntity == null) {
            throw new UsernameNotFoundException("Username was not found!");
        }
    }

    @Override
    public void validate(final UserDTO userDTO) {
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

    @Override
    public UserEntity toEntity(final UserDTO userDTO, final UUID uuid) {
        return null;
    }
}
