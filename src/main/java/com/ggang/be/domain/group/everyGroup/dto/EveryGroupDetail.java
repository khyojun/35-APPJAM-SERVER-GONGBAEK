package com.ggang.be.domain.group.everyGroup.dto;

import com.ggang.be.domain.group.everyGroup.EveryGroupEntity;
import com.ggang.be.domain.user.UserEntity;

public record EveryGroupDetail(
        long groupId,
        String title,
        String location,
        String status,
        int currentPeopleCount,
        int maxPeopleCount,
        boolean isHost,
        boolean isApply,
        String introduction,
        String category,
        int coverImg,
        String weekDay,
        double startTime,
        double endTime
) {
    public static EveryGroupDetail toDto(EveryGroupEntity entity, UserEntity currentUser) {
        return new EveryGroupDetail(
                entity.getId(),
                entity.getTitle(),
                entity.getLocation(),
                entity.getStatus().name(),
                entity.getCurrentPeopleCount(),
                entity.getMaxPeopleCount(),
                entity.isHost(currentUser),
                entity.isApply(currentUser),
                entity.getIntroduction(),
                entity.getCategory().toString(),
                entity.getCoverImg(),
                entity.getGongbaekTimeSlotEntity().getWeekDate().toString(),
                entity.getGongbaekTimeSlotEntity().getStartTime(),
                entity.getGongbaekTimeSlotEntity().getEndTime()
                );
    }

}