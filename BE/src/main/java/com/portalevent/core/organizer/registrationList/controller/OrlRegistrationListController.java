package com.portalevent.core.organizer.registrationList.controller;

import com.portalevent.core.common.ResponseObject;
import com.portalevent.core.organizer.registrationList.model.request.OrlFindQuestionsRequest;
import com.portalevent.core.organizer.registrationList.service.OrlRegistrationListService;
import com.portalevent.infrastructure.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.UrlPath.URL_API_ORGANIZER_REGISTRATION_LIST)

public class OrlRegistrationListController {

    @Autowired
    private OrlRegistrationListService registrationListService;

    @GetMapping
    public ResponseObject getAllQuestion(final OrlFindQuestionsRequest request) {
        return new ResponseObject(registrationListService.getAllQuestion(request));
    }
}
