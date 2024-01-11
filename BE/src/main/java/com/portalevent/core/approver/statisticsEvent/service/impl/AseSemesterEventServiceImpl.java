package com.portalevent.core.approver.statisticsEvent.service.impl;

import com.portalevent.core.approver.statisticsEvent.model.response.AseCategory;
import com.portalevent.core.approver.statisticsEvent.model.response.AseEventInMajor;
import com.portalevent.core.approver.statisticsEvent.model.response.AseEventInMajorResponse;
import com.portalevent.core.approver.statisticsEvent.model.response.AseEventInSemesterResponse;
import com.portalevent.core.approver.statisticsEvent.model.response.AseLecturerInEvent;
import com.portalevent.core.approver.statisticsEvent.model.response.AseListOrganizer;
import com.portalevent.core.approver.statisticsEvent.model.response.AseListOrganizerResponse;
import com.portalevent.core.approver.statisticsEvent.model.response.AseMajorResponse;
import com.portalevent.core.approver.statisticsEvent.model.response.AseParticipantInEvent;
import com.portalevent.core.approver.statisticsEvent.model.response.AseSemesterResponse;
import com.portalevent.core.approver.statisticsEvent.model.response.AseTopEventResponse;
import com.portalevent.core.approver.statisticsEvent.repository.AseCategoryRepository;
import com.portalevent.core.approver.statisticsEvent.repository.AseEventRepository;
import com.portalevent.core.approver.statisticsEvent.repository.AseMajorRepository;
import com.portalevent.core.approver.statisticsEvent.repository.AseSemesterRepository;
import com.portalevent.core.approver.statisticsEvent.service.AseStatisticEventService;
import com.portalevent.core.common.SimpleResponse;
import com.portalevent.infrastructure.session.PortalEventsSession;
import com.portalevent.util.CallApiIdentity;
import com.portalevent.util.mail.EmailUtils;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author HoangDV
 */
@Service
public class AseSemesterEventServiceImpl implements AseStatisticEventService {
    @Autowired
    private AseSemesterRepository semesterRepository;
    @Autowired
    private AseEventRepository eventRepository;
    @Autowired
    private AseMajorRepository majorRepository;
    @Autowired
    private AseCategoryRepository categoryRepository;
    @Autowired
    private CallApiIdentity callApiIdentity;
    @Autowired
    private EmailUtils emailUtils;
    @Autowired
    private PortalEventsSession session;

    /**
     * @return List semester
     * @description Lấy ra tất cả học kỳ
     */
    @Override
    public List<AseSemesterResponse> getAllSemester() {
        return semesterRepository.getAllSemester();
    }

    /**
     * @param idSemester: Id của học kỳ
     * @return Tổng số sự kiện đã đăng ký trong kỳ ở tất cả các trạng thái
     */
    @Override
    public Integer getSumEventBySemester(String idSemester) {
        return eventRepository.getSumEventBySemester(idSemester);
    }

    /**
     * @param idSemester: Id của học kỳ
     * @return Trạng thái của các sự kiện và số lượng tương ứng
     * @description Ví dụ lấy ra trạng thái 0 là đã đóng với số lượng 2
     */
    @Override
    public List<AseEventInSemesterResponse> getEventBySemester(String idSemester) {
        return eventRepository.getEventBySemester(idSemester);
    }

    /**
     * @param idSemester: Id của học kỳ
     * @return Thông tin của top 3 sự kiện có nhiều người tham gia nhất
     */
    @Override
    public List<AseTopEventResponse> getTopEvent(String idSemester) {
        return eventRepository.getTopEvent(idSemester);
    }

    /**
     * @param idSemester: Id của học kỳ
     * @return List người tổ chức sự kiện
     * @description Trả về thông tin của người tổ chức và số lượng sự kiện đã đăng kí trong kì
     */
    @Override
    public List<AseListOrganizerResponse> getListOrganizer(String idSemester) {
        // List object gồm 2 field idOrganizer and quantityEvent
        List<AseListOrganizer> listOrganizer = eventRepository.getListOrganizer(idSemester);
        // List response gồm 3 field nameOrganizer, email, quantityEvent
        List<AseListOrganizerResponse> listOrganizerResponses = new ArrayList<>();

        List<String> listIdOrganizer = new ArrayList<>();
        for (AseListOrganizer o : listOrganizer) {
            listIdOrganizer.add(o.getOrganizerId());
        }
        List<SimpleResponse> listSimpleResponse = callApiIdentity.handleCallApiGetListUserByListId(listIdOrganizer);

        for (AseListOrganizer o : listOrganizer) {
            for (SimpleResponse x : listSimpleResponse) {
                if (o.getOrganizerId().equals(x.getId())) {
                    AseListOrganizerResponse response = new AseListOrganizerResponse();
                    response.setName(x.getName());
                    response.setEmail(x.getEmail());
                    response.setQuantityEvent(o.getQuantityEvent());
                    listOrganizerResponses.add(response);
                }
            }
        }
        return listOrganizerResponses;
    }

    /**
     * @param idSemester: Id học kỳ
     * @return Danh sách chuyên ngành kèm sô sự kiện đã tổ chức trong truyên ngành trong học kỳ
     */
    @Override
    public List<AseEventInMajorResponse> getEventInMajorByIdSemester(String idSemester) {
        List<AseEventInMajor> listEventInMajor = eventRepository.getEventInMajorByIdSemester(idSemester);
        List<AseMajorResponse> listMajor = majorRepository.getAllMajor();
        // List real trả về được kết hợp thông tin từ hai list trên
        List<AseEventInMajorResponse> listResponses = new ArrayList<>();

        for (AseMajorResponse x : listMajor) {
            int check = 0;
            AseEventInMajorResponse response = new AseEventInMajorResponse();
            response.setCode(x.getCode());
            response.setName(x.getName());
            for (AseEventInMajor y : listEventInMajor) {
                if (y.getId().equals(x.getId())) {
                    response.setQuantity(y.getQuantity());
                    check = 1;
                }
            }
            if (check == 0) {
                response.setQuantity(0);
            }
            listResponses.add(response);
        }
        return listResponses;
    }

    /**
     * @param idSemester: Id học kỳ
     * @return Trả về danh sách gồm các sự kiện gồm số lượng người dự kiện tham gia,
     * người tham gia thực tế, người đăng kí
     */
    @Override
    public List<AseParticipantInEvent> getListParticipantInEvent(String idSemester) {
        return eventRepository.getListParticipantInEvent(idSemester);
    }

    /**
     * @param idSemester: Id học kỳ
     * @return Trả về danh sách sự kiện gồm số lượng người dự kiện tham gia,
     * người tham gia thực tế, người đăng kí theo thể loại
     */
    @Override
    public List<AseParticipantInEvent> getListParticipantInEventByCategory(String idSemester, String idCategory) {
        return eventRepository.getListParticipantInEventByCategory(idSemester, idCategory);
    }

    /**
     * @param idSemester: Id học kỳ
     * @return Trả về danh sách sự kiện gồm số lượng đăng ký, số lượng tham gia thực tế
     */
    @Override
    public List<AseLecturerInEvent> getListLecturerInEvent(String idSemester) {
        return eventRepository.getListLecturerInEvent(idSemester);
    }

    /**
     * @return Danh sách thể loại sự kiện
     */
    @Override
    public List<AseCategory> getAllCategory() {
        return categoryRepository.getAllCategory();
    }
}
