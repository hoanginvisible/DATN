package com.portalevent.core.approver.eventattendancelist.controller;

import com.portalevent.core.approver.eventattendancelist.model.request.AealEventAttendanceSearchRequest;
import com.portalevent.core.approver.eventattendancelist.service.AealEventAttendanceService;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.infrastructure.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.UrlPath.URL_API_APPROVER_ATTENDANCE_LIST)

public class AealEventAttendanceController {
    @Autowired
    private AealEventAttendanceService service;

    @PostMapping("/{idEvent}")
    public ResponseObject getAttendanseList(@RequestBody AealEventAttendanceSearchRequest request,
                                                  @PathVariable(name = "idEvent") String idEvent ) {
        return new ResponseObject(service.getAllAttendance(request, idEvent));
    }

    @GetMapping("/get-attendanse/{idEvent}")
    public ResponseObject getAllAttendanseList(@PathVariable("idEvent") String id) {
        return new ResponseObject(service.getAttendanceListByIdEvent(id));
    }

}
