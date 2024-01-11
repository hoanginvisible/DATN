package com.portalevent.core.approver.registrationlist.model.request;

import com.portalevent.core.common.PageableRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArlEventParticipantRequest extends PageableRequest {
    private String email;
    private String className;
    private String lecturer;
}
