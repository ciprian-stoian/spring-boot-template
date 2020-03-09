package com.backend.springboottemplate.service;

import com.backend.springboottemplate.repository.UserProfileRepository;
import com.backend.springboottemplate.repository.entity.UserProfileEntity;
import com.backend.springboottemplate.service.dto.UserProfileDTO;
import com.backend.springboottemplate.util.AuthenticatedUserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;

@Service
public class UserProfileService implements BaseService<UserProfileDTO, UserProfileEntity> {
    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private AuthenticatedUserEntity authenticatedUserEntity;

    private ModelMapper modelMapper = new ModelMapper();

    @Transactional
    public UserProfileDTO addUserProfile(final UserProfileDTO userProfileDTO) {
        long userId = authenticatedUserEntity.getAuthenticatedUser().getId();

        validateAddUserProfile(userId);

        UserProfileEntity userProfileEntity = toEntity(userProfileDTO);
        userProfileEntity.setUserId(userId);

        return toDTO(userProfileRepository.save(userProfileEntity));
    }

    @Transactional
    public UserProfileDTO updateUserProfile(final UserProfileDTO userProfileDTO) {
        UserProfileEntity existingEntity = validateUpdateUserProfile();

        UserProfileEntity userProfileEntity = modelMapper.map(userProfileDTO, UserProfileEntity.class);
        userProfileEntity.setUuid(existingEntity.getUuid());
        userProfileEntity.setId(existingEntity.getId());
        userProfileEntity.setUserId(existingEntity.getUserId());

        return toDTO(userProfileRepository.save(userProfileEntity));
    }

    @Transactional
    public UserProfileDTO getUserProfile() {
        UserProfileEntity userProfileEntity = userProfileRepository.findByUserId(authenticatedUserEntity.getAuthenticatedUser().getId());

        if (userProfileEntity == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            return toDTO(userProfileEntity);
        }
    }

    private void validateAddUserProfile(final long userId) {
        UserProfileEntity userProfileEntity = userProfileRepository.findByUserId(userId);

        if (userProfileEntity != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Duplicate Entity found in the database!");
        }
    }

    private UserProfileEntity validateUpdateUserProfile() {
        UserProfileEntity existingEntity = userProfileRepository.findByUserId(authenticatedUserEntity.getAuthenticatedUser().getId());

        if (existingEntity == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            return existingEntity;
        }
    }

    @Override
    public UserProfileDTO toDTO(final UserProfileEntity userProfileEntity) {
        return modelMapper.map(userProfileEntity, UserProfileDTO.class);
    }

    @Override
    public UserProfileEntity toEntity(final UserProfileDTO userProfileDTO) {
        return modelMapper.map(userProfileDTO, UserProfileEntity.class);
    }
}
