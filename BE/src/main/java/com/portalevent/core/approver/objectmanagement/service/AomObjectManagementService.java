package com.portalevent.core.approver.objectmanagement.service;

import com.portalevent.core.approver.objectmanagement.model.request.AomObjectManagementCreatRequest;
import com.portalevent.core.approver.objectmanagement.model.request.AomObjectManagementListRequest;
import com.portalevent.core.approver.objectmanagement.model.response.AomObjectManagementListResponse;
import com.portalevent.core.common.PageableObject;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.entity.Object;
import jakarta.validation.Valid;

public interface AomObjectManagementService {

    //Hiển thị list object và tìm kiếm
    PageableObject<AomObjectManagementListResponse> getListObject(AomObjectManagementListRequest request);

    //Hiển thị 1 object trong list object
    ResponseObject getDetailObject(String id);

    //Add object vào list object
    Object postObject(@Valid AomObjectManagementCreatRequest request);

    //Update object
    Object updateObject(String id, @Valid AomObjectManagementCreatRequest request);

    //Delete object
    Boolean deleteObject(String id);

}
