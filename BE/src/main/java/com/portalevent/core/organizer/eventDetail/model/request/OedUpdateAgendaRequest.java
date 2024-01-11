package com.portalevent.core.organizer.eventDetail.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class OedUpdateAgendaRequest {

    @NotBlank
    private String id;

    @NotBlank
    private String eventId;

    private String organizerId;

    @NotBlank
    private String startTime;

    @NotBlank
    private String endTime;

    @NotBlank
    private String description;
}
