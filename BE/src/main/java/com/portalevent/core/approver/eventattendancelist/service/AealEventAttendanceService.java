package com.portalevent.core.approver.eventattendancelist.service;

import com.portalevent.core.approver.eventattendancelist.model.request.AealEventAttendanceSearchRequest;
import com.portalevent.core.approver.eventattendancelist.model.response.AealEventAttendanceListResponse;
import com.portalevent.core.common.PageableObject;
import java.util.List;

public interface AealEventAttendanceService {
    PageableObject<AealEventAttendanceListResponse> getAllAttendance(AealEventAttendanceSearchRequest request, String idEvent);
    List<AealEventAttendanceListResponse> getAttendanceListByIdEvent(String idEvent);
}
