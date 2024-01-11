package com.portalevent.core.organizer.eventRegistered.service.impl;

import com.portalevent.core.approver.eventattendancelist.model.response.AealEventAttendanceListResponse;
import com.portalevent.core.common.PageableObject;
import com.portalevent.core.organizer.eventRegistered.model.request.OerdFilterEventRequest;
import com.portalevent.core.organizer.eventRegistered.model.request.OerdFindEventRequest;
import com.portalevent.core.organizer.eventRegistered.model.response.*;
import com.portalevent.core.organizer.eventRegistered.repository.OerdCategoryRepository;
import com.portalevent.core.organizer.eventRegistered.repository.OerdEventOrganizerRepository;
import com.portalevent.core.organizer.eventRegistered.repository.OerdEventRepository;
import com.portalevent.core.organizer.eventRegistered.repository.OerdSemesterRepository;
import com.portalevent.core.organizer.eventRegistered.service.OerdEventRegisteredService;
import com.portalevent.infrastructure.exeption.rest.RestApiException;
import com.portalevent.infrastructure.session.PortalEventsSession;
import com.portalevent.util.CallApiIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author SonPT
 */

@Service
public class OerdEventRegisteredServiceImpl implements OerdEventRegisteredService {

    @Autowired
    private OerdCategoryRepository categoryRepository;

    @Autowired
    private OerdSemesterRepository semesterRepository;

    @Autowired
    private OerdEventRepository eventRepository;

    @Autowired
    private OerdEventOrganizerRepository eventOrganizerRepository;

    @Autowired
    private CallApiIdentity callApiIdentity;

    @Autowired
    private PortalEventsSession session;

    /**
     * @param:
     * @return: OerdCategoryResponse
     * */
    @Override
    public List<OerdCategoryResponse> getAll() {
        return categoryRepository.getAll();
    }

    /**
     * @param:
     * @return: OerdSemesterResponse
     * */
    @Override
    public List<OerdSemesterResponse> getAllSemester() {
        return semesterRepository.getAll();
    }

    /**
     * @param: OerdFindEventRequest
     * @return: OerdEventResponseDTO
     * */
    @Override
    public PageableObject<OerdEventResponseDTO> getAllEventByIdOrganizer(final OerdFindEventRequest req) {
        Long startTimeLong = null;
        Long endTimeLong = null;
        if (req.getStartTime() != null && req.getEndTime() != null
                && !req.getStartTime().equals("null") && !req.getEndTime().equals("null")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date startTime = null;
            Date endTime = null;
            try {
                startTime = sdf.parse(req.getStartTime());
                endTime = sdf.parse(req.getEndTime());
            } catch (ParseException e) {
                e.printStackTrace();
                throw new RestApiException("2001"); // Ngày không hợp lệ
            }
            startTimeLong = startTime.getTime();
            endTimeLong = endTime.getTime();
        }
        OerdFilterEventRequest omFilterEventRequest = new OerdFilterEventRequest();
        omFilterEventRequest.setIdOrganizer(session.getCurrentIdUser()); //get Id Organizer from current session
        omFilterEventRequest.setIdCategory(req.getIdCategory());
        omFilterEventRequest.setIdSemester(req.getIdSemester());
        omFilterEventRequest.setFormality(req.getFormality().trim().equals("-1") ? null : req.getFormality().equals("0") ? (short) 0 : (short) 1);
        omFilterEventRequest.setName(req.getName());
        omFilterEventRequest.setStatus(req.getStatus());
        if (req.getStartTime() != null && req.getEndTime() != null) {
            omFilterEventRequest.setEndTime(endTimeLong);
            omFilterEventRequest.setStartTime(startTimeLong);
        } else {
            omFilterEventRequest.setEndTime(null);
            omFilterEventRequest.setStartTime(null);
        }
        omFilterEventRequest.setStatusSort(req.getStatusSort());

        Pageable pageable = PageRequest.of(req.getPage(), 5);
        Page<OerdEventResponse> listEventResult = eventRepository.getAllEventByIdOrganizer(pageable, omFilterEventRequest);
        PageableObject pageableObject = new PageableObject(listEventResult);
        List<OerdEventResponseDTO> listResponse = new ArrayList<>();
        for (OerdEventResponse x : listEventResult) {
            List<String> listIdOrganizer =
                eventOrganizerRepository.getListOrganizerId(x.getId())
                .stream()
                .map(s -> {
                    return s.getIdOrganizer();
                })
                .distinct()
                .collect(Collectors.toList())
                ;
            String listUserNameOrganizer = callApiIdentity.handleCallApiGetListUserByListId(listIdOrganizer)
                    .stream()
                    .map(s -> {
                        return s.getUserName();
                    })
                    .collect(Collectors.toList())
                    .toString();
            listUserNameOrganizer = listUserNameOrganizer.substring(1, listUserNameOrganizer.length() - 1);
            listResponse.add(new OerdEventResponseDTO(x, getListLocation(x.getId()), listUserNameOrganizer.equals("") ? "--" : listUserNameOrganizer));
        }
        pageableObject.setData(listResponse);
        return pageableObject;
    }

    /**
     * @param: String idEvent
     * @return: OerdLocationResponse
     * get list from table event location by id event
     * */
    @Override
    public List<OerdLocationResponse> getListLocation(String idEvent) {
        return eventRepository.getLocationByEventId(idEvent);
    }
}
