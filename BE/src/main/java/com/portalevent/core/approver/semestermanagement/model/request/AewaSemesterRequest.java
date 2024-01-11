package com.portalevent.core.approver.semestermanagement.model.request;

import com.portalevent.entity.Semester;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AewaSemesterRequest {

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    private Long startTime;

    @NotNull
    private Long endTime;

    @NotNull
    private Long startTimeFirstBlock;

    @NotNull
    private Long endTimeFirstBlock;

    @NotNull
    private Long startTimeSecondBlock;

    @NotNull
    private Long endTimeSecondBlock;

    public Semester request(Semester semester){
        semester.setName(this.getName().trim());
        semester.setStartTime(this.getStartTime());
        semester.setEndTime(this.getEndTime());
        semester.setStartTimeFirstBlock(this.getStartTimeFirstBlock());
        semester.setEndTimeFirstBlock(this.getEndTimeFirstBlock());
        semester.setStartTimeSecondBlock(this.getStartTimeSecondBlock());
        semester.setEndTimeSecondBlock(this.getEndTimeSecondBlock());
        return semester;
    }
}
