package com.portalevent.core.approver.majormanagement.service;

import com.portalevent.core.approver.majormanagement.model.request.AmmMajorRequest;
import com.portalevent.core.approver.majormanagement.model.response.AmmMajorResponse;
import com.portalevent.core.common.ResponseObject;

import java.util.List;

public interface AmmMajorService {
    List<AmmMajorResponse> getMajorList(String value, String mainMajor);

    List<AmmMajorResponse> getMajorParentList();

    ResponseObject getMajor(String id);

    ResponseObject createMajor(AmmMajorRequest request);

    ResponseObject updateMajor(String id, AmmMajorRequest request);

    String deleteMajor(String id);
}
