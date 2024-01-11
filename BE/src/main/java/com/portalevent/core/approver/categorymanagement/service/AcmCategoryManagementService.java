package com.portalevent.core.approver.categorymanagement.service;

import com.portalevent.core.approver.categorymanagement.model.request.AcmCategoryManagementCreateRequest;
import com.portalevent.core.approver.categorymanagement.model.request.AcmCategoryManagementListRequest;
import com.portalevent.core.approver.categorymanagement.model.response.AcmCategoryManagementListReponse;
import com.portalevent.core.common.PageableObject;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.entity.Category;
import jakarta.validation.Valid;

public interface AcmCategoryManagementService {

    //hiển thị thể loại có phân trang và tìm kiếm
    PageableObject<AcmCategoryManagementListReponse> getListCategory(AcmCategoryManagementListRequest request);

    //hiển thị 1 thể loại trong list thể loại
    ResponseObject getDetailCategory(String id);

    //thêm thể loại
    Category postCategory(@Valid AcmCategoryManagementCreateRequest request);

    //cập nhật thể loại
    Category updateCategory(String id, @Valid AcmCategoryManagementCreateRequest request);

    //xóa thể loại
    Boolean deleteCategory(String id);

}
