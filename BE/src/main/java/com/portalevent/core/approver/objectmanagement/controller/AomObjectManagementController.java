package com.portalevent.core.approver.objectmanagement.controller;

import com.portalevent.core.approver.objectmanagement.model.request.AomObjectManagementCreatRequest;
import com.portalevent.core.approver.objectmanagement.model.request.AomObjectManagementListRequest;
import com.portalevent.core.approver.objectmanagement.service.AomObjectManagementService;
import com.portalevent.core.common.PageableObject;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.infrastructure.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.UrlPath.URL_API_APPROVER_OBJECT_MANAGEMENT)

public class AomObjectManagementController {
    @Autowired
    private AomObjectManagementService objectApproverService;

    //Lấy ra list object
    @GetMapping("/list-object-approved")
    public PageableObject getListObject(final AomObjectManagementListRequest request) {
        return objectApproverService.getListObject(request);
    }
    //END lấy ra list object

    //Lấy ra 1 object trong list object
    @GetMapping("/detail-object/{id}")
    public ResponseObject detailObject(@PathVariable("id") String id) {
        return objectApproverService.getDetailObject(id);
    }
    //END Lấy ra 1 object trong list object

    //Thêm 1 object
    @PostMapping("/post-object")
    public ResponseObject postObject(@RequestBody AomObjectManagementCreatRequest request) {
        return new ResponseObject(objectApproverService.postObject(request));
    }
    //END thêm 1 object

    //Cập nhật 1 object
    @PutMapping("/update-object/{id}")
    public ResponseObject updateObject(@PathVariable("id") String id, @RequestBody AomObjectManagementCreatRequest request) {
        return new ResponseObject(objectApproverService.updateObject(id, request));
    }
    //END cập nhật 1 object

    //Xóa 1 object
    @DeleteMapping("/delete-object/{id}")
    public ResponseObject deleteObject(@PathVariable("id") String id) {
        return new ResponseObject(objectApproverService.deleteObject(id));
    }
    //END xóa 1 object
}
