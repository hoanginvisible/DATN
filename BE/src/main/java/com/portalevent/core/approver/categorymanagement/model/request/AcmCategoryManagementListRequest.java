package com.portalevent.core.approver.categorymanagement.model.request;

import com.portalevent.core.common.PageableRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * class này để hứng data từ input tìm kiếm
 */
@Getter
@Setter
public class AcmCategoryManagementListRequest extends PageableRequest {

    private String name;

}
