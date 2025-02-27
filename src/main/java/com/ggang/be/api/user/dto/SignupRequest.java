package com.ggang.be.api.user.dto;

import com.ggang.be.api.user.vo.TimeTableVo;
import com.ggang.be.domain.constant.Gender;
import com.ggang.be.domain.constant.Mbti;
import com.ggang.be.domain.school.SchoolEntity;
import com.ggang.be.domain.user.dto.SaveUserSignUp;

import java.util.List;

public record SignupRequest(Integer profileImg,
                            String nickname,
                            Mbti mbti,
                            String schoolName,
                            String schoolMajor,
                            Integer schoolGrade,
                            Integer enterYear,
                            String introduction,
                            Gender sex,
                            List<TimeTableVo> timeTable
                            ) {

    public static SaveUserSignUp toSaveUserSignUp(SignupRequest request, SchoolEntity school) {
        return new SaveUserSignUp(
                request.profileImg(),
                request.nickname(),
                request.mbti(),
                request.schoolMajor(),
                request.schoolGrade(),
                request.enterYear(),
                request.introduction(),
                request.sex(),
                school
        );
    }
}
