package com.portalevent.core.approver.eventwaitingapproval.service.impl;

import com.portalevent.core.approver.eventwaitingapproval.model.request.AewaCommentEventDetailRequest;
import com.portalevent.core.approver.eventwaitingapproval.model.request.AewaEventListRequest;
import com.portalevent.core.approver.eventwaitingapproval.model.respone.AewaCommentEventDetailResponse;
import com.portalevent.core.approver.eventwaitingapproval.model.respone.AewaEventListResponse;
import com.portalevent.core.approver.eventwaitingapproval.repository.AewaEventRepository;
import com.portalevent.core.approver.eventwaitingapproval.service.AewaEventService;
import com.portalevent.core.common.PageableObject;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.infrastructure.constant.Message;
import com.portalevent.infrastructure.exeption.rest.RestApiException;
import com.portalevent.util.LoggerUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class AewaEventServiceImpl implements AewaEventService {

    @Autowired
    private AewaEventRepository apEventRepository;

    @Autowired
    private LoggerUtil loggerUtil;

    /**
     * @param request: Thông tin cho sự kiện chờ phê duyệt
     * @return Danh sách các sự kiện ở trạng thái chờ phê duyệt
     */
    @Transactional
    @Override
    public PageableObject<AewaEventListResponse> getListEventNotApproved(AewaEventListRequest request) {
        return new PageableObject<AewaEventListResponse>(apEventRepository.getEventList(PageRequest.of(request.getPage(),
                request.getSize()), request));
    }

    /**
     * @param id: Id sự kiện
     * @return Thông tin chi tiết của sự kiện
     */
    @Transactional
    @Override
    public ResponseObject getDetailEventApproved(String id) {
        //3005 -> không tìm thấy
        return new ResponseObject(apEventRepository.getDetailApprovedById(id).orElseThrow(() -> new RestApiException(Message.APPROVED_DOES_NOT_EXIST)));
    }

    /**
     * @return Danh sách thể loại
     */

    @Override
    public ResponseObject getEventCategory() {
        return new ResponseObject(apEventRepository.getListEventCategory());
    }

    /**
     * @return Danh sách chuyên ngành
     */

    @Override
    public ResponseObject getEventMajor() {
        return new ResponseObject(apEventRepository.getEventMajor());
    }

    /**
     * @param request: Thông tin sự kiện
     * @return Page bình luận của sự kiện
     */

    @Override
    public PageableObject<AewaCommentEventDetailResponse> getCommentEventById(AewaCommentEventDetailRequest request) {
        if (request.getIdEvent() == null) throw new RestApiException("3002");
        return new PageableObject<AewaCommentEventDetailResponse>(apEventRepository.getCommentEventById(PageRequest.of(request.getPage(),
                request.getSize()), request.getIdEvent()));
    }
}
