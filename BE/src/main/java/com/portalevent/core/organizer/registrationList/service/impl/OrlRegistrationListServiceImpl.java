package com.portalevent.core.organizer.registrationList.service.impl;

import com.portalevent.core.common.PageableObject;
import com.portalevent.core.organizer.registrationList.model.request.OrlFindQuestionsRequest;
import com.portalevent.core.organizer.registrationList.model.response.OrlQuestionResponse;
import com.portalevent.core.organizer.registrationList.repository.OrlEventRepository;
import com.portalevent.core.organizer.registrationList.repository.OrlParticipantRepository;
import com.portalevent.core.organizer.registrationList.service.OrlRegistrationListService;
import com.portalevent.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author SonPT
 */

@Service
public class OrlRegistrationListServiceImpl implements OrlRegistrationListService {

    @Autowired
    private OrlEventRepository eventRepository;

    @Autowired
    private OrlParticipantRepository participantRepository;

    @Override
    public Event detail(String id) {
        return eventRepository.findById(id).get();
    }

    @Override
    public PageableObject<OrlQuestionResponse> getAllQuestion(final OrlFindQuestionsRequest req) {
        Pageable pageable = PageRequest.of(req.getPage(), req.getSize());
        Page<OrlQuestionResponse> res = participantRepository.getAllQuestion(pageable, req);
        return new PageableObject(res);
    }

    @Override
    public Integer countAllSearchQuestion(final OrlFindQuestionsRequest req) {
        return participantRepository.countAllSearchQuestion(req);
    }


}
