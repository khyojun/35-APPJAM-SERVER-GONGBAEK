package com.ggang.be.api.facade;

import com.ggang.be.api.common.ResponseError;
import com.ggang.be.api.exception.GongBaekException;
import com.ggang.be.api.lectureTimeSlot.service.LectureTimeSlotService;
import com.ggang.be.api.school.service.SchoolService;
import com.ggang.be.api.user.dto.SignupRequest;
import com.ggang.be.api.user.dto.SignupResponse;
import com.ggang.be.api.user.service.UserService;
import com.ggang.be.api.user.vo.TimeTableVo;
import com.ggang.be.domain.timslot.lectureTimeSlot.vo.LectureTimeSlotVo;
import com.ggang.be.domain.school.SchoolEntity;
import com.ggang.be.domain.user.UserEntity;
import com.ggang.be.domain.user.dto.SaveUserSignUp;
import com.ggang.be.global.jwt.JwtService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class SignupFacade {

    private final UserService userService;
    private final SchoolService schoolService;
    private final LectureTimeSlotService lectureTimeSlotService;
    private final JwtService jwtService;


    public void duplicateCheckNickname(final String nickname) {
        userService.duplicateCheckNickname(nickname);
    }


    @Transactional
    public SignupResponse signup(SignupRequest request) {

        duplicateUserCheck(request);

        SchoolEntity schoolEntityByName = schoolService.findSchoolEntityByName(
            request.schoolName());

        SaveUserSignUp saveUserSignUp = SignupRequest.toSaveUserSignUp(request,
            schoolEntityByName);



        UserEntity userEntity = userService.saveUserBySignup(saveUserSignUp);

        List<LectureTimeSlotVo> lectureTimeSlotVos = request.timeTable().stream()
            .map(TimeTableVo::toLectureTimeSlotVo)
            .toList();

        lectureTimeSlotService.saveLectureTimeSlot(lectureTimeSlotVos, userEntity);

        return madeSignupResponse(userEntity);
    }

    private void duplicateUserCheck(SignupRequest request) {
        try{
            duplicateCheckNickname(request.nickname());
        }catch (GongBaekException e){
            throw new GongBaekException(ResponseError.USERNAME_ALREADY_EXISTS);
        }
    }

    private SignupResponse madeSignupResponse(final UserEntity userEntity) {
        Long userId = userEntity.getId();
        String accessToken = jwtService.createAccessToken(userId);
        String refreshToken = jwtService.createRefreshToken(userId);

        userService.updateRefreshToken(refreshToken, userEntity);

        return SignupResponse.of(userId, accessToken, refreshToken);
    }
}
