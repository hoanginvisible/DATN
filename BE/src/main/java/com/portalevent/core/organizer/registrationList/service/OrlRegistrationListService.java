package com.portalevent.core.organizer.registrationList.service;

import com.portalevent.core.common.PageableObject;
import com.portalevent.core.organizer.registrationList.model.request.OrlFindQuestionsRequest;
import com.portalevent.core.organizer.registrationList.model.response.OrlQuestionResponse;
import com.portalevent.entity.Event;

/**
 * @author SonPT
 */
public interface OrlRegistrationListService {

    Event detail(String id);

    /***
     *
     * @param req
     * @return Danh sách người tham gia
     */
    PageableObject<OrlQuestionResponse> getAllQuestion(final OrlFindQuestionsRequest req);

    Integer countAllSearchQuestion(final OrlFindQuestionsRequest req);

}
