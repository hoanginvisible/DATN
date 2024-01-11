package com.portalevent.core.organizer.attendanceList.model.response;

import com.portalevent.entity.Participant;
import com.portalevent.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author thangncph26123
 */
@Projection(types = {Participant.class})
public interface OalAttendanceResponse extends IsIdentified {

    @Value("#{target.stt}")
    Integer getSTT();

    @Value("#{target.email}")
    String getEmail();

    @Value("#{target.class_name}")
    String getClassName();

    @Value("#{target.attendance_time}")
    Long getAttendanceTime();

    @Value("#{target.rate}")
    Byte getRate();

    @Value("#{target.feedback}")
    String getFeedback();
}
