package com.portalevent.core.approver.eventattendancelist.service.impl;

import com.portalevent.core.approver.eventattendancelist.model.request.AealEventAttendanceSearchRequest;
import com.portalevent.core.approver.eventattendancelist.model.response.AealEventAttendanceListResponse;
import com.portalevent.core.approver.eventattendancelist.repository.AealEventAttendanceRepository;
import com.portalevent.core.approver.eventattendancelist.service.AealEventAttendanceService;
import com.portalevent.core.common.PageableObject;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AealEventAtendanceServiceImpl implements AealEventAttendanceService {

    @Autowired
    private AealEventAttendanceRepository repository;

    @Override
    public PageableObject<AealEventAttendanceListResponse> getAllAttendance(AealEventAttendanceSearchRequest request, String idEvent) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<AealEventAttendanceListResponse> res = repository.getPageAttendanceList(pageable, request, idEvent);
        return new PageableObject(res);
    }

    @Override
    public List<AealEventAttendanceListResponse> getAttendanceListByIdEvent(String idEvent) {
        return repository.getAttendanceListByIdEvent(idEvent);
    }
}
