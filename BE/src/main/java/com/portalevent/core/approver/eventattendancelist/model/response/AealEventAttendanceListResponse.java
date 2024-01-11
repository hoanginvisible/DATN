package com.portalevent.core.approver.eventattendancelist.model.response;

import com.portalevent.entity.Participant;
import com.portalevent.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {Participant.class})
public interface AealEventAttendanceListResponse extends IsIdentified {
    @Value("#{target.indexs}")
    Integer getIndex();

    @Value("#{target.email}")
    String getEmail();

    @Value("#{target.className}")
    String getClassName();

    @Value("#{target.attendanceTime}")
    Long getAttendanceTime();

    @Value("#{target.rate}")
    Byte getRate();

    @Value("#{target.feedback}")
    String getFeedback();
}
