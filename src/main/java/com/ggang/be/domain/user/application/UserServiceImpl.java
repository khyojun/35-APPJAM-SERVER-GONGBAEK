package com.ggang.be.domain.user.application;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.api.user.service.UserService;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.domain.user.dto.UserSchoolDto;
import com.ggang.be.domain.user.infra.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserEntity getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new GongBaekException(ResponseError.USER_NOT_FOUND));
    }

    @Override
    public UserSchoolDto getUserSchoolById(Long userId) {
        String schoolName = userRepository.findSchoolNameById(userId)
                .orElseThrow(() -> new GongBaekException(ResponseError.USER_NOT_FOUND));

        return new UserSchoolDto(schoolName);
    }
  
    @Override
    public boolean duplicateCheckNickname(String nickname) {
        log.info("nickname {}", nickname);
        if(userRepository.existsUserEntitiesByNickname(nickname))
            throw new GongBaekException(ResponseError.NICKNAME_ALREADY_EXISTS);
        return true;
    }

    private UserEntity findByIdOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new GongBaekException(ResponseError.USER_NOT_FOUND));
    }
}
