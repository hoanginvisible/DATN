package com.portalevent.core.approver.registrationlist.controller;

import com.portalevent.core.approver.registrationlist.model.request.ArlEventParticipantRequest;
import com.portalevent.core.approver.registrationlist.service.ArlRegistrationListService;
import com.portalevent.core.common.PageableObject;
import com.portalevent.infrastructure.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.UrlPath.URL_API_APPROVER_REGISTRATION_LIST)

public class ArlRegistrationListController {
    @Autowired
    private ArlRegistrationListService service;

    @GetMapping("/{idEvent}")
    public PageableObject getAll(final ArlEventParticipantRequest request,
                                 @PathVariable String idEvent) {
        return service.getAllParticipant(request, idEvent);
    }
}
