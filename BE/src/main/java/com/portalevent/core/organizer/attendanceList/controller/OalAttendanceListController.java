package com.portalevent.core.organizer.attendanceList.controller;

import com.portalevent.core.common.ResponseObject;
import com.portalevent.core.organizer.attendanceList.model.request.OalFindAttendanceRequest;
import com.portalevent.core.organizer.attendanceList.service.OalAttendanceListService;
import com.portalevent.infrastructure.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SonPT
 */

@RestController
@RequestMapping(Constants.UrlPath.URL_API_ORGANIZER_ATTENDANCE_LIST)

public class OalAttendanceListController {

    @Autowired
    private OalAttendanceListService attendanceListService;

    @GetMapping("/event-detail/{id}")
    public ResponseObject eventDetail(@PathVariable("id") String id) {
        return new ResponseObject(attendanceListService.detail(id));
    }

    @PostMapping
    public ResponseObject getAllAttendance(@RequestBody OalFindAttendanceRequest request) {
        return new ResponseObject(attendanceListService.getAllAttendance(request));
    }

    @GetMapping("/count")
    public ResponseObject countAllSearch(final OalFindAttendanceRequest request) {
        return new ResponseObject(attendanceListService.countAllSearch(request));
    }

}
