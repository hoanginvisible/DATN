package com.portalevent.core.approver.registrationlist.service.impl;

import com.portalevent.core.approver.registrationlist.model.request.ArlEventParticipantRequest;
import com.portalevent.core.approver.registrationlist.model.response.ArlEventParticipantResponse;
import com.portalevent.core.approver.registrationlist.repository.ArlParticipantRepository;
import com.portalevent.core.approver.registrationlist.service.ArlRegistrationListService;
import com.portalevent.core.common.PageableObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ArlRegistrationListServiceImpl implements ArlRegistrationListService {
    @Autowired
    private ArlParticipantRepository repository;

    //    Hàm lấy ra danh sách người tham gia
    @Override
    public PageableObject<ArlEventParticipantResponse> getAllParticipant(ArlEventParticipantRequest request, String idEvent) {
        return new PageableObject<>(repository.getAllParticipant(PageRequest.of(request.getPage(), request.getSize()), request, idEvent));
    }

}
