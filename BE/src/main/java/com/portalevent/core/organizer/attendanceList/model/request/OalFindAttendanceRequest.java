package com.portalevent.core.organizer.attendanceList.model.request;

import com.portalevent.core.common.PageableRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author SonPT
 */
@Getter
@Setter
@ToString
public class OalFindAttendanceRequest extends PageableRequest{

    private String idEvent;

    private String email;

    private String className;

    private Byte rate;

    private String feedback;
}
