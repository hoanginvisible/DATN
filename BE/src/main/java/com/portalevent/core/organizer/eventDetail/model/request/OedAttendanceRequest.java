package com.portalevent.core.organizer.eventDetail.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OedAttendanceRequest {
    @NotBlank
    @NotNull
    private String id;

    @NotNull
    private Boolean isAttendance;
}
