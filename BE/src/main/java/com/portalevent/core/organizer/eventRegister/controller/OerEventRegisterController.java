package com.portalevent.core.organizer.eventRegister.controller;

import com.portalevent.core.common.ResponseObject;
import com.portalevent.core.organizer.eventRegister.model.request.OerCreateCategoryRequest;
import com.portalevent.core.organizer.eventRegister.model.request.OerCreateEventRequest;
import com.portalevent.core.organizer.eventRegister.service.OerEventRegisterService;
import com.portalevent.infrastructure.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author thangncph26123
 */
@RestController
@RequestMapping(Constants.UrlPath.URL_API_ORGANIZER_EVENT_REGISTER)

public class OerEventRegisterController {

    @Autowired
    private OerEventRegisterService eventRegisterService;

    @GetMapping("/get-all-category")
    public ResponseObject getAll() {
        return new ResponseObject(eventRegisterService.getAll());
    }

    @PostMapping
    public ResponseObject create(@RequestBody OerCreateEventRequest request) {
        return new ResponseObject(eventRegisterService.create(request));
    }

    @PostMapping("/upload-file")
    public ResponseEntity uploadFileBackgroundAndThumbnail(@RequestParam("idEvent") String idEvent,
                                                           @RequestParam(value = "fileThumbnail", required = false) MultipartFile fileThumbnail,
                                                             @RequestParam(value = "fileBackground", required = false) MultipartFile fileBackground) {
        eventRegisterService.uploadFile(idEvent, fileBackground, fileThumbnail);
        return new ResponseEntity("Thành công", HttpStatus.OK);
    }

    @PostMapping("/create-category")
    public ResponseObject create(@RequestBody OerCreateCategoryRequest request) {
        return new ResponseObject(eventRegisterService.create(request));
    }

    @GetMapping(value = "/get-all-majors")
    public ResponseObject getAllMajors() {
        return new ResponseObject(eventRegisterService.getMajors());
    }


    @GetMapping(value = "/get-all-semesters")
    public ResponseObject getAllSemester() {
        return new ResponseObject(eventRegisterService.getSemesters());
    }
    @GetMapping(value = "/get-all-objects")
    public ResponseObject getAllObject() {
        return new ResponseObject(eventRegisterService.getObjects());
    }
}
