package com.backend.springboottemplate.service;

import com.backend.springboottemplate.repository.UserProfileRepository;
import com.backend.springboottemplate.repository.entity.UserProfileEntity;
import com.backend.springboottemplate.service.dto.UserProfileDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class UserProfileService implements BaseService<UserProfileDTO, UserProfileEntity> {
    @Autowired
    private UserProfileRepository userProfileRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Transactional
    public UserProfileDTO addUserProfile(final UserProfileDTO userProfileDTO) {
        validate(userProfileDTO);

        return toDTO(userProfileRepository.save(toEntity(userProfileDTO)));
    }

    @Transactional
    public UserProfileDTO updateUserProfile(final UserProfileDTO userProfileDTO) {
        return toDTO(userProfileRepository.save(toEntity(userProfileDTO, userProfileDTO.getUserUuid())));
    }

    @Transactional
    public UserProfileDTO getUserProfile(final UUID userUuid) {
        UserProfileEntity userProfileEntity = userProfileRepository.findByUserUuid(userUuid);
        validate(userProfileEntity);

        return toDTO(userProfileRepository.findByUserUuid(userUuid));
    }

    private void validate(final UserProfileEntity userProfileEntity) {
        if (userProfileEntity == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void validate(final UserProfileDTO userProfileDTO) {
        UserProfileEntity userProfileEntity = userProfileRepository.findByUserUuid(userProfileDTO.getUserUuid());

        if (userProfileEntity != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Duplicate Entity found in the database!");
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

    @Override
    public UserProfileEntity toEntity(final UserProfileDTO userProfileDTO, final UUID uuid) {
        UserProfileEntity userProfileEntity = modelMapper.map(userProfileDTO, UserProfileEntity.class);
        UserProfileEntity existingEntity = userProfileRepository.findByUserUuid(uuid);

        if (existingEntity != null) {
            userProfileEntity.setId(existingEntity.getId());

            return userProfileEntity;
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
