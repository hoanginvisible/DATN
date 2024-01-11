package com.portalevent.core.approver.semestermanagement.controller;

import com.portalevent.core.approver.semestermanagement.model.request.AewaSemesterRequest;
import com.portalevent.core.approver.semestermanagement.service.AewaSemesterService;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.infrastructure.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.UrlPath.URL_API_APPROVER_SEMESTER_MANAGEMENT)

public class AewaSemesterController {

    @Autowired
    private AewaSemesterService service;

    @GetMapping("/list-semester")
    public ResponseObject getListEventApproved(@RequestParam(defaultValue = "0",name = "page") Integer page, @RequestParam("searchName") String searchName) {
        return new ResponseObject(service.getListSemester(page, searchName));
    }

    @PostMapping("/add")
    public ResponseObject addSemester(@RequestBody AewaSemesterRequest createRequest) {
        return new ResponseObject(service.add(createRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseObject updateSemester(@RequestBody AewaSemesterRequest updateRequest,@PathVariable("id") String id) {
        return new ResponseObject(service.update(updateRequest, id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseObject deleteSemester(@PathVariable("id") String id) {
        return new ResponseObject(service.remove(id));
    }

}
