package com.portalevent.core.approver.majormanagement.controller;

import com.portalevent.core.approver.majormanagement.model.request.AmmMajorRequest;
import com.portalevent.core.approver.majormanagement.model.response.AmmMajorResponse;
import com.portalevent.core.approver.majormanagement.service.AmmMajorService;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.infrastructure.constant.Constants;
import com.portalevent.infrastructure.logger.LoggerObject;
import com.portalevent.util.CallApiConsumer;
import com.portalevent.util.LoggerUtil;
import com.portalevent.util.mail.EmailUtils;
import com.portalevent.util.mail.MailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// Author: Huong

@RestController
@RequestMapping(Constants.UrlPath.URL_API_APPROVER_MAJOR_MANAGEMENT)
public class AmmMajorManagementController {
    @Autowired
    private AmmMajorService ammMajorService;

    @Autowired
    private EmailUtils emailUtils;

    @Autowired
    private CallApiConsumer callApiConsumer;

    @Autowired
    private LoggerUtil loggerUtil;

    @PostMapping("/mail")
    private void testMail(@RequestBody MailRequest emails) {
        emailUtils.sendEmail(emails);
        /*
        {
    "mails": ["sonptph19600@fpt.edu.vn", "duongdhph29062@fpt.edu.vn"],
    "subject": "[PTPM-XLDL-UDPM] Thư mời tham dự Spring Boot Tutorials",
    "eventName": "Spring Boot Tutorials",
    "lecturer": ["Vũ Nguyên Hướng - HuongVN4", "Nguyễn Thúy Hằng - HangNT169"],
    "content": ["Springboot là gì ?", "Các cách trở thành master Springboot ?"],
    "time": "19:00 - 21:00",
    "date": "21/09/2023",
    "typeEvent": "Online",
    "linkZoom": "https://www.facebook.com/VuNguyenHuong.Official",
    "linkBackground": "https://www.facebook.com/VuNguyenHuong.Official"
}
         */
    }

    @GetMapping
    public List<AmmMajorResponse> getMajorList(@RequestParam(defaultValue = "", required = false) String value,
                                               @RequestParam(defaultValue = "all", required = false) String mainMajor) {
        return ammMajorService.getMajorList(value, mainMajor);
    }

    @GetMapping("/{id}")
    public ResponseObject getOneMajor(@PathVariable String id) {
        return ammMajorService.getMajor(id);
    }

    @GetMapping("/parent-major")
    public List<AmmMajorResponse> getAll() {
        return ammMajorService.getMajorParentList();
    }

    @PostMapping
    public ResponseObject createMajor(@RequestBody AmmMajorRequest request) {
        return new ResponseObject(ammMajorService.createMajor(request));
    }

    @PutMapping("/{id}")
    public ResponseObject updateMajor(@PathVariable String id, @RequestBody AmmMajorRequest request) {
        return new ResponseObject(ammMajorService.updateMajor(id, request));
    }

    @DeleteMapping("/{id}")
    public String deleteMajor(@PathVariable String id) {
        return ammMajorService.deleteMajor(id);
    }

    @GetMapping("/download-log")
    public ResponseEntity<Resource> downloadCsv() {
        String pathFile = loggerUtil.getPathFileForView(Constants.DisplayName.APPROVER_MAJOR_MANAGEMENT, null);
        return callApiConsumer.handleCallApiDowloadFileLog(pathFile);
    }

    @GetMapping("/history")
    public ResponseEntity<?> showHistory(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                         @RequestParam(name = "size", defaultValue = "50") Integer size
//            , @RequestParam(name = "semesterId", defaultValue = "") Integer semesterId // Truyền thêm nếu có màn nào đó theo kỳ
    ) {
        String pathFile = loggerUtil.getPathFileForView(Constants.DisplayName.APPROVER_MAJOR_MANAGEMENT, null); // Tìm ra tên kỳ đó và truyền vào hàm. Truyền thêm nếu có màn nào đó theo kỳ
        LoggerObject loggerObject = new LoggerObject();
        loggerObject.setPathFile(pathFile);
        return new ResponseEntity<>(callApiConsumer.handleCallApiReadFileLog(loggerObject, page, size), HttpStatus.OK);
    }
}
