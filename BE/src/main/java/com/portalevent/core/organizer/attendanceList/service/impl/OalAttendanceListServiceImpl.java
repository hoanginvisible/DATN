package com.portalevent.core.organizer.attendanceList.service.impl;

import com.portalevent.core.common.PageableObject;
import com.portalevent.core.organizer.attendanceList.model.request.OalFindAttendanceRequest;
import com.portalevent.core.organizer.attendanceList.model.response.OalAttendanceResponse;
import com.portalevent.core.organizer.attendanceList.repository.OalEventRepository;
import com.portalevent.core.organizer.attendanceList.repository.OalParticipantRepository;
import com.portalevent.core.organizer.attendanceList.service.OalAttendanceListService;
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
public class OalAttendanceListServiceImpl implements OalAttendanceListService {


    @Autowired
    private OalEventRepository eventRepository;

    @Autowired
    private OalParticipantRepository participantRepository;

    @Override
    public Event detail(String id) {
        return eventRepository.findById(id).get();
    }

    /***
     *
     * @param request
     * @return Danh sách Điểm danh
     */
    @Override
    public PageableObject<OalAttendanceResponse> getAllAttendance(OalFindAttendanceRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<OalAttendanceResponse> res = participantRepository.getAllAttendance(pageable, request);
        return new PageableObject(res);
    }

    @Override
    public Integer countAllSearch(final OalFindAttendanceRequest req) {
        return participantRepository.countAllSearch(req);
    }

}
