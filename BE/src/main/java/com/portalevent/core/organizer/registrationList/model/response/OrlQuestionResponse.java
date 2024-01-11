package com.portalevent.core.organizer.registrationList.model.response;

import com.portalevent.entity.Participant;
import com.portalevent.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {Participant.class})
public interface OrlQuestionResponse extends IsIdentified {

    @Value("#{target.indexs}")
    Integer getSTT();

    @Value("#{target.email}")
    String getEmail();

    @Value("#{target.className}")
    String getClassName();

    @Value("#{target.question}")
    String getQuestion();

    String getLecturerName();

    @Value("#{target.createDate}")
    Long getCreateDate();
}
