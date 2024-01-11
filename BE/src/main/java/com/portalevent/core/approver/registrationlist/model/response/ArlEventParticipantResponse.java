package com.portalevent.core.approver.registrationlist.model.response;

import org.springframework.beans.factory.annotation.Value;

public interface ArlEventParticipantResponse {
    @Value("#{target.indexs}")
    Integer getIndex();

    String getEmail();

    String getClassName();

    String getQuestion();

    String getLecturerName();
    Long getCreateDate();
}
