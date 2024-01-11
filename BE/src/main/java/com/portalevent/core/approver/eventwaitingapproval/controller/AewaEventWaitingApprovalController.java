package com.portalevent.core.approver.eventwaitingapproval.controller;

import com.portalevent.core.approver.eventwaitingapproval.model.request.AewaCommentEventDetailRequest;
import com.portalevent.core.approver.eventwaitingapproval.model.request.AewaEventListRequest;
import com.portalevent.core.approver.eventwaitingapproval.service.AewaEventService;
import com.portalevent.core.common.PageableObject;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.infrastructure.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.UrlPath.URL_API_APPROVER_EVENT_WAITING_APPROVAL)

public class AewaEventWaitingApprovalController {

    @Autowired
    private AewaEventService apEventService;


    @PostMapping("/list-event-waiting-approve")
    public PageableObject getListEventNotApproved(@RequestBody AewaEventListRequest request) {
        return apEventService.getListEventNotApproved(request);
    }

    @GetMapping("/waiting-approval/detail/{id}")
    public ResponseObject getEventApprovedDetail(@PathVariable("id") String id) {
        return apEventService.getDetailEventApproved(id);
    }

    @GetMapping("/event-category/list")
    public ResponseObject getEventCategory() {
        return apEventService.getEventCategory();
    }

    @GetMapping("/event-major/list")
    public ResponseObject getEventMajor() {
        return apEventService.getEventMajor();
    }

    @PostMapping("/detail-comment-event")
    public PageableObject getCommentEventById(@RequestBody AewaCommentEventDetailRequest request) {
        return apEventService.getCommentEventById(request);
    }

}
