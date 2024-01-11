package com.portalevent.core.organizer.attendanceList.service;

import com.portalevent.core.common.PageableObject;
import com.portalevent.core.organizer.attendanceList.model.request.OalFindAttendanceRequest;
import com.portalevent.core.organizer.attendanceList.model.response.OalAttendanceResponse;
import com.portalevent.entity.Event;

/**
 * @author SonPT
 */
public interface OalAttendanceListService {

    Event detail(String id);

    PageableObject<OalAttendanceResponse> getAllAttendance(OalFindAttendanceRequest request);

    Integer countAllSearch(final OalFindAttendanceRequest req);

}
