package com.portalevent.core.approver.majormanagement.service.impl;

import com.portalevent.core.approver.majormanagement.model.request.AmmMajorRequest;
import com.portalevent.core.approver.majormanagement.model.response.AmmMajorResponse;
import com.portalevent.core.approver.majormanagement.repository.AmmMajorRepository;
import com.portalevent.core.approver.majormanagement.service.AmmMajorService;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.entity.Major;
import com.portalevent.infrastructure.constant.Message;
import com.portalevent.infrastructure.exeption.rest.RestApiException;
import com.portalevent.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AmmMajorServiceImpl implements AmmMajorService {
    @Autowired
    private AmmMajorRepository majorRepository;

    @Autowired
    private LoggerUtil loggerUtil;

    /**
     *
     * @param value mã, tên, email trưởng bộ môn
     * @param mainMajor check xem có phải là chuyên ngành cha hay k
     * @return danh sách chuyên ngành
     */
    @Override
    public List<AmmMajorResponse> getMajorList(String value, String mainMajor) {
        return majorRepository.getMajorList(value, mainMajor);
    }

    /**
     *
     * @return danh sách chuyên ngành cha
     */
    @Override
    public List<AmmMajorResponse> getMajorParentList() {
        return majorRepository.getMajorParentList();
    }

    /***
     *
     * @param id id chuyên ngành
     * @return object chuyên ngành có id là id truyền vào
     */
    @Override
    public ResponseObject getMajor(String id) {
        return new ResponseObject(majorRepository.getMajor(id));
    }

    /**
     *
     * @param request dữ liệu từ FE
     * @return
     */
    @Override
    public ResponseObject createMajor(AmmMajorRequest request) {
        Major major = new Major();
        if (majorRepository.existsByCodeIgnoreCaseAndCodeIgnoreCaseNot(request.getCode(), "")) {
            throw new RestApiException(Message.MAJOR_CODE_ALREADY_EXISTS);
        }
        if (majorRepository.existsByMailOfManagerIgnoreCaseAndMailOfManagerIgnoreCaseNot(request.getEmail(), "")) {
            throw new RestApiException(Message.EMAIL_ALREADY_EXISTS);
        }
        if (majorRepository.existsByNameIgnoreCaseAndNameIgnoreCaseNot(request.getName(), "")) {
            throw new RestApiException(Message.MAJOR_NAME_ALREADY_EXISTS);
        }
        if (request.getMainMajor() != null) {
            major.setMainMajorId(request.getMainMajor().trim());
        }
        major.setCode(request.getCode().trim());
        major.setName(request.getName().trim());
        major.setMailOfManager(request.getEmail().trim());
        loggerUtil.sendLog("Đã tạo chuyên ngành " + request.getCode().trim() + "-" + request.getName().trim(), null);
        return new ResponseObject(majorRepository.save(major));
    }

    /**
     *
     * @param id id của chuyên ngành cần cập nhật
     * @param request dữ liệu lấy từ FE
     * @return
     */
    @Override
    public ResponseObject updateMajor(String id, AmmMajorRequest request) {
        Major major = majorRepository.findById(id).get();
        if (majorRepository.existsByCodeIgnoreCaseAndCodeIgnoreCaseNot(request.getCode(), major.getCode())) {
            throw new RestApiException(Message.MAJOR_CODE_ALREADY_EXISTS);
        }
        if (majorRepository.existsByMailOfManagerIgnoreCaseAndMailOfManagerIgnoreCaseNot(request.getEmail(), major.getMailOfManager())) {
            throw new RestApiException(Message.EMAIL_ALREADY_EXISTS);
        }
        if (majorRepository.existsByNameIgnoreCaseAndNameIgnoreCaseNot(request.getName(), major.getName())) {
            throw new RestApiException(Message.MAJOR_NAME_ALREADY_EXISTS);
        }
        if (request.getMainMajor() != null) {
            major.setMainMajorId(request.getMainMajor().trim());
        }
        if (!major.getName().equals(request.getName())) {
            loggerUtil.sendLog("Đã cập nhật tên nguyên ngành từ " + major.getName() + " thành " + request.getName(), "");
        }
        if (!major.getCode().equals(request.getCode())) {
            loggerUtil.sendLog("Đã cập nhật mã nguyên ngành từ " + major.getCode() + " thành " + request.getCode(), "");
        }
        if (!major.getMailOfManager().equals(request.getEmail())) {
            loggerUtil.sendLog("Đã cập nhật email trưởng môn từ " + major.getMailOfManager() + " thành " + request.getEmail(), "");
        }
        if (major.getMainMajorId() != null && request.getMainMajor() != null &&
                !major.getMainMajorId().equals(request.getMainMajor())) {
            Major majorFatherOld = majorRepository.findById(major.getMainMajorId()).get();
            Major majorFatherNew = majorRepository.findById(request.getMainMajor()).get();
            loggerUtil.sendLog("Đã cập nhật chuyên ngành cha của chuyên ngành "
                    + major.getCode() + "-" + major.getName() + " từ " +
                    majorFatherOld.getName() + " thành " + majorFatherNew.getName(), "");
        }
        if (major.getMainMajorId() != null && request.getMainMajor() == null) {
            loggerUtil.sendLog("Đã cập nhật chuyên ngành "
                    + major.getCode() + "-" + major.getName()
                    + " từ chuyên ngành con thành chuyên ngành cha", "");
        }
        major.setCode(request.getCode().trim());
        major.setName(request.getName().trim());
        major.setMailOfManager(request.getEmail().trim());
        return new ResponseObject(majorRepository.save(major));
    }

    /**
     *
     * @param id id của chuyên ngành cần xóa
     * @return trả về thông báo
     */
    @Override
    public String deleteMajor(String id) {
        Optional<Major> majorFind = majorRepository.findById(id);
        if (!majorFind.isPresent()) {
            throw new RestApiException(Message.MAJOR_NOT_EXIST);
        }
        majorRepository.deleteMajor(id);
        if (majorRepository.countParentMajor(id) > 0) {
            throw new RestApiException(Message.CANNOT_DELETED_PARENT_MAJOR);
        }
        if (majorRepository.countMajorInEvent(id) > 0) {
            throw new RestApiException(Message.CANNOT_DELETED_MAJOR_IN_EVENT);
        }
        loggerUtil.sendLog("Đã xóa chuyên ngành " + majorFind.get().getCode() + "-" + majorFind.get().getName(), "");
        return "Xóa thành công!";
    }
}
