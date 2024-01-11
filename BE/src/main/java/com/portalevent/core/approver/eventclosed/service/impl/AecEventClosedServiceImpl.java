package com.portalevent.core.approver.eventclosed.service.impl;

import com.portalevent.core.approver.eventclosed.model.request.AecEventCloseRequest;
import com.portalevent.core.approver.eventclosed.model.response.AecEventCloseResponse;
import com.portalevent.core.approver.eventclosed.model.response.AecPropsResponse;
import com.portalevent.core.approver.eventclosed.repository.AecCategoryRepository;
import com.portalevent.core.approver.eventclosed.repository.AecEventCloseRepository;
import com.portalevent.core.approver.eventclosed.repository.AecMajorRepository;
import com.portalevent.core.approver.eventclosed.repository.AecObjectRepository;
import com.portalevent.core.approver.eventclosed.repository.AecSemesterRepository;
import com.portalevent.core.approver.eventclosed.service.AecEventClosedService;
import com.portalevent.core.common.PageableObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AecEventClosedServiceImpl implements AecEventClosedService {
    @Autowired
    private AecEventCloseRepository repository;
    @Autowired
    private AecMajorRepository majorRepository;
    @Autowired
    private AecObjectRepository objectRepository;
    @Autowired
    private AecCategoryRepository categoryRepository;
    @Autowired
    private AecSemesterRepository semesterRepository;

    /**
     *
     * @param request tên, thể loại, đối tượng, chuyên ngành, học kỳ
     * @return danh sách sự kiện đã đóng
     */
    @Override
    public PageableObject<AecEventCloseResponse> getAllEventClose(AecEventCloseRequest request) {
        return new PageableObject<>(repository.getAllEventClose(PageRequest.of(request.getPage(), request.getSize()), request));
    }

    /**
     *
     * @return danh sách chuyên ngành
     */
    @Override
    public List<AecPropsResponse> getAllMajor() {
        return majorRepository.getAllMajor();
    }

    /**
     *
     * @return danh sách đối tượng
     */
    @Override
    public List<AecPropsResponse> getAllObject() {
        return objectRepository.getAllObject();
    }

    /**
     *
     * @return danh sách thể loại
     */
    @Override
    public List<AecPropsResponse> getAllCategory() {
        return categoryRepository.getAllCategory();
    }

    /**
     *
     * @return danh sách học kỳ
     */
    @Override
    public List<AecPropsResponse> getAllSemester() {
        return semesterRepository.getAllSemester();
    }
}
