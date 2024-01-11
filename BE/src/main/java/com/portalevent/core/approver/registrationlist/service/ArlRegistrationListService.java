package com.portalevent.core.approver.registrationlist.service;

import com.portalevent.core.approver.registrationlist.model.request.ArlEventParticipantRequest;
import com.portalevent.core.approver.registrationlist.model.response.ArlEventParticipantResponse;
import com.portalevent.core.common.PageableObject;

public interface ArlRegistrationListService {
    PageableObject<ArlEventParticipantResponse> getAllParticipant(ArlEventParticipantRequest request, String idEvent);
}
