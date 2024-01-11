package com.portalevent.core.organizer.registrationList.model.request;

import com.portalevent.core.common.PageableRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrlFindQuestionsRequest extends PageableRequest {

    private String idEvent;

    private String email;

    private String className;

    private String question;

    private String lecturer;
}
