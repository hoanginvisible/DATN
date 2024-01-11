package com.portalevent.core.organizer.eventDetail.service.impl;

import com.portalevent.core.common.HoneyCategoryResponse;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.core.common.SimpleResponse;
import com.portalevent.core.organizer.attendanceList.model.response.OalAttendanceResponse;
import com.portalevent.core.organizer.eventDetail.model.request.OedAddEventLocationRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedAttendanceRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedCreateAgendaRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedCreateEventOrganizerRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedCreateResourceRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedDeleteCommentRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedDeleteEventOrganizerRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedEventCloseRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedEvidenceRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedInvitationTimeRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedMailRequestApprovalEvent;
import com.portalevent.core.organizer.eventDetail.model.request.OedPostCommentRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedRegisterRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedReplyCommentRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedSendConversionRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedSendMailEvidenceRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedUpdateAgendaRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedUpdateEventLocationRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedUpdateEventOrganizer;
import com.portalevent.core.organizer.eventDetail.model.request.OedUpdateEventRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedUpdateResourceRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedWaitApprovalPeriodicallyRequest;
import com.portalevent.core.organizer.eventDetail.model.response.OedEventOverlapOrganizer;
import com.portalevent.core.organizer.eventDetail.model.response.OedAgendaItemCustom;
import com.portalevent.core.organizer.eventDetail.model.response.OedAgendaItemResponse;
import com.portalevent.core.organizer.eventDetail.model.response.OedAllCommentResponse;
import com.portalevent.core.organizer.eventDetail.model.response.OedCategoryResponse;
import com.portalevent.core.organizer.eventDetail.model.response.OedCommentResponse;
import com.portalevent.core.organizer.eventDetail.model.response.OedEventDetailCustom;
import com.portalevent.core.organizer.eventDetail.model.response.OedEventEvidenceResponse;
import com.portalevent.core.organizer.eventDetail.model.response.OedEventLocationResponse;
import com.portalevent.core.organizer.eventDetail.model.response.OedEventOrganizerCustom;
import com.portalevent.core.organizer.eventDetail.model.response.OedEventOrganizerResponse;
import com.portalevent.core.organizer.eventDetail.model.response.OedEventResponse;
import com.portalevent.core.organizer.eventDetail.model.response.OedInvitationTimeResponse;
import com.portalevent.core.organizer.eventDetail.model.response.OedMajorResponse;
import com.portalevent.core.organizer.eventDetail.model.response.OedObjectResponse;
import com.portalevent.core.organizer.eventDetail.model.response.OedReplyCommentResponse;
import com.portalevent.core.organizer.eventDetail.model.response.OedResourceResponse;
import com.portalevent.core.organizer.eventDetail.model.response.OedSemesterResponse;
import com.portalevent.core.organizer.eventDetail.repository.OdeAgendaItemRepository;
import com.portalevent.core.organizer.eventDetail.repository.OdeEventOrganizerRepository;
import com.portalevent.core.organizer.eventDetail.repository.OedCategoryRepository;
import com.portalevent.core.organizer.eventDetail.repository.OedCommentRepository;
import com.portalevent.core.organizer.eventDetail.repository.OedEventLocationRepository;
import com.portalevent.core.organizer.eventDetail.repository.OedEventMajorRepository;
import com.portalevent.core.organizer.eventDetail.repository.OedEventObjectRepository;
import com.portalevent.core.organizer.eventDetail.repository.OedEventRepository;
import com.portalevent.core.organizer.eventDetail.repository.OedEvidenceRepository;
import com.portalevent.core.organizer.eventDetail.repository.OedInvitationTimeRepository;
import com.portalevent.core.organizer.eventDetail.repository.OedMajorRepository;
import com.portalevent.core.organizer.eventDetail.repository.OedObjectRepository;
import com.portalevent.core.organizer.eventDetail.repository.OedParticipantRepository;
import com.portalevent.core.organizer.eventDetail.repository.OedResourceRepository;
import com.portalevent.core.organizer.eventDetail.repository.OedSemesterRepository;
import com.portalevent.core.organizer.eventDetail.repository.OedSystemOptionRepository;
import com.portalevent.core.organizer.eventDetail.service.OedEventDetailService;
import com.portalevent.entity.AgendaItem;
import com.portalevent.entity.Category;
import com.portalevent.entity.Comment;
import com.portalevent.entity.Event;
import com.portalevent.entity.EventLocation;
import com.portalevent.entity.EventMajor;
import com.portalevent.entity.EventObject;
import com.portalevent.entity.EventOrganizer;
import com.portalevent.entity.Evidence;
import com.portalevent.entity.InvitationTime;
import com.portalevent.entity.Resource;
import com.portalevent.entity.Semester;
import com.portalevent.entity.SystemOption;
import com.portalevent.infrastructure.apiconstant.ActorConstants;
import com.portalevent.infrastructure.backgroundjob.sendinvitationmail.SendInvitationJob;
import com.portalevent.infrastructure.constant.ApprovalPeriodicallyStatus;
import com.portalevent.infrastructure.constant.EventRole;
import com.portalevent.infrastructure.constant.EventStatus;
import com.portalevent.infrastructure.constant.EventType;
import com.portalevent.infrastructure.constant.EvidenceType;
import com.portalevent.infrastructure.constant.MailConstant;
import com.portalevent.infrastructure.constant.Message;
import com.portalevent.infrastructure.exeption.rest.ForbiddenException;
import com.portalevent.infrastructure.exeption.rest.RestApiException;
import com.portalevent.infrastructure.session.PortalEventsSession;
import com.portalevent.repository.EventOrganizerRepository;
import com.portalevent.util.CallApiIdentity;
import com.portalevent.util.CallPortalHoney;
import com.portalevent.util.CloudinaryUtils;
import com.portalevent.util.CompareUtils;
import com.portalevent.util.LoggerUtil;
import com.portalevent.util.mail.EmailUtils;
import com.portalevent.util.mail.MailCustomRequest;
import com.portalevent.util.mail.MailEventOrganizerCustom;
import com.portalevent.util.mail.MailEventUpdateWhenApproved;
import com.portalevent.util.mail.MailEvidenceRequest;
import com.portalevent.util.mail.MailEvidenceResponce;
import com.portalevent.util.mail.MailObjectResponse;
import com.portalevent.util.mail.MailOrganizerSendApproval;
import com.portalevent.util.mail.MailOrgenizer;
import com.portalevent.util.mail.MailResourceRequest;
import com.portalevent.util.mail.MailResourceResponce;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.Valid;

import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Synchronized;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author SonPT
 */

@Service
@Validated
public class OedDetailEventServiceImpl implements OedEventDetailService {

    @Autowired
    private OedCategoryRepository categoryRepository;

    @Autowired
    private EmailUtils emailUtils;

    @Autowired
    private OedEventRepository eventRepository;

    @Autowired
    private OedResourceRepository resourceRepository;

    @Autowired
    private OedCommentRepository commentRepository;

    @Autowired
    private OedSemesterRepository semesterRepository;

    @Autowired
    private OedMajorRepository majorRepository;

    @Autowired
    private OedEventMajorRepository eventMajorRepository;

    @Autowired
    private OedEventObjectRepository eventObjectRepository;

    @Autowired
    private OedEventLocationRepository eventLocationRepository;

    @Autowired
    private OdeEventOrganizerRepository odeEventOrganizerRepository;

    @Autowired
    private OdeAgendaItemRepository odeAgendaItemRepository;

    @Autowired
    private OedEvidenceRepository oedEvidenceRepository;

    @Autowired
    private OedObjectRepository objectRepository;

    @Autowired
    private OedParticipantRepository participantRepository;

    @Autowired
    private OedInvitationTimeRepository invitationTimeRepository;

    @Autowired
    private CloudinaryUtils cloudinaryUtils;

    @Autowired
    private CallApiIdentity callApiIdentity;

    @Autowired
    PortalEventsSession portalEventsSession;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private LoggerUtil loggerUtil;

    private final CallPortalHoney callPortalHoney;

    @Value("${spring.mail.username}")
    private String sender;

    @Autowired
    private OedSystemOptionRepository systemOptionRepository;

    @Autowired
    public OedDetailEventServiceImpl(CallPortalHoney callPortalHoney) {
        this.callPortalHoney = callPortalHoney;
    }

    @Override
    public List<OedCategoryResponse> getAll() {
        return categoryRepository.getAll();
    }

    /***
     *
     * @param id: Id của sự kiện
     * @return Thông tin chi tiết của một sự kiện
     */
    @Override
    public OedEventDetailCustom detail(String id) {
        OedEventResponse e = eventRepository.detail(id);
        if (e == null) {
            throw new RestApiException(Message.EVENT_NOT_EXIST);
        }
        OedEventDetailCustom eventCustom = new OedEventDetailCustom();
        eventCustom.setId(e.getId());
        eventCustom.setSemesterId(e.getSemesterId());
        eventCustom.setSemester(e.getSemester());
        eventCustom.setSemesterStart(e.getSemesterStart());
        eventCustom.setSemesterEnd(e.getSemesterEnd());
        eventCustom.setStartTimeFirstBlock(e.getStartTimeFirstBlock());
        eventCustom.setEndTimeFirstBlock(e.getEndTimeFirstBlock());
        eventCustom.setStartTimeSecondBlock(e.getStartTimeSecondBlock());
        eventCustom.setEndTimeSecondBlock(e.getEndTimeSecondBlock());
        eventCustom.setName(e.getName());
        eventCustom.setCategory(e.getCategory());
        eventCustom.setExpectedParticipants(e.getExpectedParticipants());
        eventCustom.setBlock(e.getBlock());
        eventCustom.setStartTime(e.getStartTime());
        eventCustom.setEndTime(e.getEndTime());
        eventCustom.setDescription(e.getDescription());
        eventCustom.setStatus(e.getStatus());
        eventCustom.setReason(e.getReason());
        eventCustom.setBackground(e.getBackground());
        eventCustom.setBanner(e.getBanner());
        eventCustom.setStandee(e.getStandee());
        eventCustom.setEventType(e.getEventType());
        eventCustom.setCreatedDate(e.getCreatedDate());
        eventCustom.setNumberParticipant(e.getNumberparticipant()); // Số người điểm danh khi báo cáo
        eventCustom.setCountNumberParticipant(participantRepository.countRateParticipant(id)); // Số người điểm danh hệ thống
        eventCustom.setCountParticipant(participantRepository.countByEventId(id)); // Số người đăng ký
        eventCustom.setIsHireLocation(e.getIsHireLocation());
        eventCustom.setIsHireDesignBg(e.getIsHireDesignBG());
        eventCustom.setIsHireDesignBanner(e.getIsHireDesignBanner());
        eventCustom.setIsHireDesignStandee(e.getIsHireDesignStandee());
        eventCustom.setIsAttendance(e.getIsAttendance());
        eventCustom.setIsOpenRegistration(e.getIsOpenRegistration());
        eventCustom.setIsWaitApprovalPeriodically(e.getIsWaitApprovalPeriodically());
        eventCustom.setApprovalPeriodicallyReason(e.getApprovalPeriodicallyReason());
        eventCustom.setIsConversionHoneyRequest(e.getIsConversionHoneyRequest());

        Integer sumRate45 = participantRepository.sumRate45ByEventId(id);
        Integer sumRate = participantRepository.sumRateByEventId(id);
        if (sumRate45 != null && sumRate != null && sumRate45 > 0 && sumRate > 0 ) {
            double percentage = (sumRate45 / sumRate) * 100;
            DecimalFormat df = new DecimalFormat("#");
            eventCustom.setPercentage(df.format(percentage));
        } else {
            eventCustom.setPercentage("0");
        }
        if (e.getApprover() != null) {
            SimpleResponse responseApprover = callApiIdentity.handleCallApiGetUserById(e.getApprover());
            eventCustom.setApproveName(responseApprover.getName());
        }
        return eventCustom;
    }

    /***
     *
     * @param id: Id của sự kiện
     * @return Yêu cầu phê duyệt sk và chuyển thành Chờ phê duyệt
     */
    @Override
    public Event updateStatusEventByIdEvent(String id) {
        SimpleDateFormat sdfDateTime = new SimpleDateFormat("hh:mm dd/MM/yyyy");
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm");
        StringBuffer message = new StringBuffer();
        Boolean checkRole = checkOrganizerRole(id);
        StringBuilder time = new StringBuilder("");
        Optional<Event> event = eventRepository.findById(id);
        if (event.isEmpty()) {
            throw new RestApiException(Message.EVENT_NOT_EXIST); //event không tồn tại
        }
        if (!checkRole) {
            throw new RestApiException(Message.YOU_HAVE_NO_RIGHTS); // Check quền chỉnh sửa
        }
        int check = 0;
        if (event.get().getCategoryId() == null || event.get().getCategoryId().equals("")
                || event.get().getSemesterId() == null || event.get().getSemesterId().equals("")
                || event.get().getName() == null || event.get().getName().equals("")
                || event.get().getStartTime() == null
                || event.get().getEndTime() == null
                || event.get().getBlockNumber() == null
                || event.get().getExpectedParticipants() == null
                || event.get().getEventType() == null
                || event.get().getDescription() == null || event.get().getDescription().equals("")) {
            throw new RestApiException(Message.UPDATE_ALL_EVENT_INFORMATION); //Yêu cầu cập nhật thông tin cho sự kiện
        }
        Long now = new Date().getTime();
		if (event.get().getStartTime() < now || event.get().getEndTime() < now) {
            throw new RestApiException(Message.INVALID_START_TIME);
        }
        Optional<SystemOption> systemOption = systemOptionRepository.findById(1L);

        // Kiểm tra sự kiện cần gửi YC phê duyệt có trùng thời gian diễn ra với sự kiện khác không
        if (systemOption.get().getIsAllowNotEnoughTimeApproval()) {
            List<Event> events = eventRepository.getAllEventBySemesterId(event.get().getSemesterId(), portalEventsSession.getCurrentIdUser());
            events.forEach(e -> {
                if (e.getStartTime() <= event.get().getEndTime() && e.getEndTime() >= event.get().getStartTime()) {
                    throw new RestApiException("Sự kiện `" + e.getName() + Message.APPROVED_EVENT_EXIST_DURING_TIME_PERIOD.getMessage());
                }
            });
        }
        List<OedEventOrganizerResponse> listOrganizer = odeEventOrganizerRepository.getAllEventOrganizer(id);
        listOrganizer.forEach(organizer -> {
            List<OedEventOverlapOrganizer> eventDuplicateOrganizers = eventRepository.getEventOverlapOrganizer(organizer.getOrganizerId(),
                    event.get().getStartTime(), event.get().getEndTime());
            if (!eventDuplicateOrganizers.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm dd/MM/yyyy");
                SimpleResponse simpleResponse = callApiIdentity.handleCallApiGetUserById(organizer.getOrganizerId());
                Message.OVERLAP_ORGANIZER.setMessage("Người tổ chức " + simpleResponse.getName()
                        + " đã tham gia trong sự kiện "
                        + eventDuplicateOrganizers.get(0).getName()
                        + " đã được phê duyệt tổ chức vào " + sdf.format(new Date(eventDuplicateOrganizers.get(0).getStartTime())) + " - "
                        + sdf.format(new Date(eventDuplicateOrganizers.get(0).getEndTime())));
                throw new RestApiException(Message.OVERLAP_ORGANIZER);
            }
        });

        Integer dateDiff = (int) Math.floor((event.get().getStartTime() - (new Date().getTime())) / (1000 * 60 * 60 * 24));
        if (dateDiff < systemOption.get().getMandatoryApprovalDays()) {
            if (systemOption.isPresent()) {
                if (!systemOption.get().getIsAllowNotEnoughTimeApproval()) {
                    throw new RestApiException(Message.APPROVAL_TIME_INVALID);
                } else {
                    event.get().setIsNotEnoughTimeApproval(true);
                }
            }
        }
        Byte lastetOrderApprovalPriotiry = eventRepository.getLastestOrderApprovalPriority();
        if (lastetOrderApprovalPriotiry != null) {
            event.get().setOrderApprovalPriority((lastetOrderApprovalPriotiry + 1));
        } else {
            event.get().setOrderApprovalPriority(1);
        }

        event.get().setStatus(EventStatus.WAITING_APPROVAL);
        Event e = eventRepository.save(event.get());

        // Gửi mail thông báo
        Category category = categoryRepository.findById(e.getCategoryId()).get();
        List<OedEventOrganizerCustom> lecturers = getAllOrganizerByIdEvent(event.get().getId());
        List<OedEventLocationResponse> locations = getEventLocationByIdEvent(event.get().getId());
        List<OedObjectResponse> objects = getObjectByIdEvent(event.get().getId());
        List<OedResourceResponse> responses = getAllResource(event.get().getId());
        List<OedAgendaItemCustom> agendas = getAllAgendaItem(event.get().getId());

        Date startTime = new Date(event.get().getStartTime());
        Date endTime = new Date(event.get().getEndTime());
        if (new Date(sdfDate.format(startTime)).compareTo(new Date(sdfDate.format(endTime))) == 0) {
            time.append("<li>Thời gian: ").append(sdfTime.format(startTime)).append(" - ").append(sdfTime.format(endTime)).append("</li><li>Ngày: ").append(sdfDate.format(startTime)).append("</li>");
        } else {
            time.append("<li>Thời gian: ").append(sdfDateTime.format(startTime)).append(" - ").append(sdfDateTime.format(endTime)).append("</li>");
        }
        OedMailRequestApprovalEvent request = new OedMailRequestApprovalEvent();
        List<SimpleResponse> listApprover = callApiIdentity.handleCallApiGetUserByRoleAndModule(ActorConstants.ACTOR_APPROVER);
        String[] mailApprover = listApprover.stream()
                .map(SimpleResponse::getEmail)
                .filter(Objects::nonNull)
                .distinct()
                .toArray(String[]::new);

        request.setMails(mailApprover);
        request.setSubject("[Portal Event] Thư gửi yêu cầu phê duyệt sự kiện " + event.get().getName());
        request.setEventName(event.get().getName());
        request.setNameHost(portalEventsSession.getCurrentName());
        request.setEmailHost(portalEventsSession.getCurrentEmail());
        request.setLecturers(lecturers);
        request.setContent(new String[]{event.get().getDescription()});
        request.setDate(time.toString());
        request.setTypeEvent(event.get().getEventType() + "");
        request.setCategory(category.getName());
        request.setObjects(objects);
        request.setLocations(locations);
        request.setResources(responses);
        request.setAgendas(agendas);
        request.setExpectedParticipants(event.get().getExpectedParticipants() + "");
        request.setLinkBackground(event.get().getBackgroundPath() == null ? "" : event.get().getBackgroundPath());
        request.setLinkBanner(event.get().getBannerPath() == null ? "" : event.get().getBannerPath());
        sendEmailApprovalEvent(request);
        if (event.get().getStatus() != EventStatus.WAITING_APPROVAL) {
            message.append("Gửi yêu cầu phê duyệt sự kiện: ")
                    .append(event.get().getName())
                    .append(" - Diễn ra vào: ")
                    .append(sdfDateTime.format(new Date(event.get().getStartTime())))
                    .append(" - ")
                    .append(sdfDateTime.format(new Date(event.get().getEndTime())))
                    .append(". Gửi mail thông báo tới: ");
            listApprover.forEach(item -> {
                message.append(item.getEmail()).append(", ");
            });
            loggerUtil.sendLog(message.toString(), event.get().getId());
        }
        return e;
    }

    /**
     * Gửi mail yêu cầu phê duyệt
     * @param request: Thông tin của một sự kiện cần phê duyệt
     */
    @Async
    public void sendEmailApprovalEvent(OedMailRequestApprovalEvent request) {
        String htmlBody = MailConstant.HEADER.replace("{title}", "");
        String lectuerList = "";
        String contentList = "";
        String objectList = "";
        String locationList = "";
        String resourceList = "";
        String tableAgenda = """
                            <table style="margin-top: 10px; margin-bottom: 10px;"><tr>
                                    <th>STT</th>
                                    <th>Thời gian bắt đầu</th>
                                    <th>Thời gian kết thúc</th>
                                    <th>Mô tả</th>
                                    <th>Người phụ trách</th>
                                </tr>
                """;
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.toString());
            ClassPathResource resource = new ClassPathResource(MailConstant.LOGO_PATH);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setBcc(request.getMails());
            mimeMessageHelper.setSubject(request.getSubject());
            for (OedEventOrganizerCustom lecturer : request.getLecturers()) {
                lectuerList += "<li>" + lecturer.getName() + " - " + lecturer.getUsername() + "</li>";
            }
            for (String content : request.getContent()) {
                contentList += "<li>" + content + "</li>";
            }
            for (OedObjectResponse object : request.getObjects()) {
                objectList += "<li>" + object.getName() + "</li>";
            }
            for (OedEventLocationResponse location : request.getLocations()) {
                if (location.getFormality() == 0) {
                    locationList += "<li> Online - "+ location.getName() + ": <a href='" + location.getPath() + "'>Tại đây</a></li>";
                } else {
                    locationList += "<li> Offline -"+ location.getName() + ": " + location.getPath() + "</li>";
                }
            }
            for (OedResourceResponse res : request.getResources()) {
                resourceList += "<li>" + res.getName() + ": <a href='" + res.getPath() + "'>Tại đây</a></li>";
            }
            for (OedAgendaItemCustom agenda : request.getAgendas()) {
                tableAgenda += "<tr>";
                tableAgenda += "<td>" + (agenda.getIndex() + 1) + "</td>";
                tableAgenda += "<td>" + agenda.getStartTime() + "</td>";
                tableAgenda += "<td>" + agenda.getEndTime() + "</td>";
                tableAgenda += "<td>" + agenda.getDescription() + "</td>";
                tableAgenda += "<td>" + agenda.getUsername() + "</td>";
                tableAgenda += "</tr>";
            }

            mimeMessageHelper.setText(htmlBody + MailConstant.CONTENT_APPROVAL_EVENT_MAIL
                    .replace("{eventName}", request.getEventName())
                    .replace("{nameHost}", request.getNameHost())
                    .replace("{lecturerList}", lectuerList)
                    .replace("{contentList}", contentList)
                    .replace("{date}", request.getDate())
                    .replace("{category}", request.getCategory())
                    .replace("{typeEvent}", request.getTypeEvent())
                    .replace("{objects}", objectList)
                    .replace("{locations}", locationList)
                    .replace("{resources}", resourceList)
                    .replace("{agendas}", tableAgenda)
                    .replace("{expectedParticipants}", request.getExpectedParticipants())
                    .replace("{emailHost}", request.getEmailHost())
                    .replace("{linkBackground}", request.getLinkBackground() == null ? "" : request.getLinkBackground())
                    .replace("{linkBanner}", request.getLinkBanner() == null ? "" : request.getLinkBanner()) + MailConstant.FOOTER, true);
            mimeMessageHelper.addInline("logoImage", resource);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     *
     * @param request : Các thông tin cập nhật
     * @return Một sự kiện đã được cập nhật
     */
    @Override
    public Event update(@Valid OedUpdateEventRequest request) {
        StringBuilder message = new StringBuilder();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        Optional<Event> eventFind = eventRepository.findById(request.getId());
        if (!eventFind.isPresent()) {
            throw new RestApiException(Message.EVENT_NOT_EXIST); // Event ko tồn tại
        }
        Boolean checkRole = checkOrganizerRole(request.getId());
        if (!checkRole) {
            throw new RestApiException(Message.YOU_HAVE_NO_RIGHTS);// Check quền chỉnh sửa
        }
        if (eventFind.get().getStatus() != EventStatus.EDITING && eventFind.get().getStatus() != EventStatus.SCHEDULED_HELD
                && eventFind.get().getStatus() != EventStatus.APPROVED) {
            throw new RestApiException(Message.INVALID_STATUS);
        }
        try {
            Date startTime = new Date(request.getStartTime());
            Date endTime = new Date(request.getEndTime());
            if (startTime.before(new Date())) {
                throw new RestApiException(Message.INVALID_START_TIME); // Ngày bắt đầu không hợp lệ
            }
            if (startTime.after(endTime)) {
                throw new RestApiException(Message.INVALID_END_TIME); // Ngày kết thúc không hợp lệ
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RestApiException(Message.INVALID_START_TIME); // Ngày BD hoặc KT không hợp lệ
        }
        eventMajorRepository.deleteEventMajorByIdEvent(request.getId());
        List<EventMajor> newEventMajor = new ArrayList<>();
        for (String major : request.getIdMajor()) {
            EventMajor eventMajor = new EventMajor();
            eventMajor.setEventId(request.getId());
            eventMajor.setMajorId(major);
            newEventMajor.add(eventMajor);
        }
        if (!newEventMajor.isEmpty()) {
            eventMajorRepository.saveAll(newEventMajor);
        }

        //Gửi mail cho người phê duyệt khi sk đã phê duyệt
        if (eventFind.get().getStatus() == EventStatus.APPROVED) {
            Category category = categoryRepository.findById(request.getIdCategory()).get();
            List<OedMajorResponse> majorResponses = getMajorByIdEvent(eventFind.get().getId());
            String[] major = majorResponses.stream()
                    .map(OedMajorResponse::getName)
                    .toArray(String[]::new);
            List<SimpleResponse> simpleResponses = callApiIdentity.handleCallApiGetUserByRoleAndModule(ActorConstants.ACTOR_APPROVER);
            String[] mailApprover = simpleResponses.stream()
                    .map(SimpleResponse::getEmail)
                    .filter(Objects::nonNull)
                    .distinct()
                    .toArray(String[]::new);
            MailEventUpdateWhenApproved x = new MailEventUpdateWhenApproved();
            x.setMails(mailApprover);
            x.setSubject("[Portal Event] Thư gửi thông báo cập nhật thông tin sự kiện " + eventFind.get().getName());
            if (eventFind.get().getDescription() != null && !eventFind.get().getDescription().equals(request.getDescription())) {
                x.setContent(new String[] {request.getDescription()});
            }
            if (eventFind.get().getName() != null && !eventFind.get().getName().equals(request.getName())){
                x.setEventName(request.getName());
            } else {
                x.setEventName(eventFind.get().getName() == null ? "Rỗng" : eventFind.get().getName());
            }
            x.setUserNameChange(portalEventsSession.getCurrentUserName());
            if (eventFind.get().getStartTime() != null && (eventFind.get().getStartTime() != request.getStartTime()
                    || eventFind.get().getEndTime() != request.getEndTime())){
                x.setDate(dateFormat.format(new Date(request.getStartTime())) + " - " +
                    dateFormat.format(new Date(request.getEndTime())));
            }
            if (eventFind.get().getCategoryId() != null && !eventFind.get().getCategoryId().equals(request.getIdCategory())){
                x.setCategory(category.getName());
            }
            if (eventFind.get().getEventType() != null && eventFind.get().getEventType().ordinal() != request.getEventType()) {
                x.setTypeEvent(eventFind.get().getEventType() + "");
            }
            if (eventFind.get().getExpectedParticipants() != null && eventFind.get().getExpectedParticipants() != request.getExpectedParticipants()){
                x.setExpectedParticipants(eventFind.get().getExpectedParticipants() + "");
            }

            x.setMajor(major);
            emailUtils.sendEmailEventUpdateWhenApproved(x);
        }

        Optional<Semester> semesterFind = semesterRepository.findById(request.getSemesterId());
        if (!semesterFind.isPresent()) {
            throw new RestApiException(Message.SEMESTER_NOT_EXISTS);
        }
        eventFind.get().setSemesterId(semesterFind.get().getId());
        eventFind.get().setBlockNumber(request.getBlock());
        Long startTimeBlock = null;
        Long endTimeBlock = null;

        if (request.getBlock() == false) {
            startTimeBlock = semesterFind.get().getStartTimeFirstBlock();
            endTimeBlock = semesterFind.get().getEndTimeFirstBlock();
        } else {
            startTimeBlock = semesterFind.get().getStartTimeSecondBlock();
            endTimeBlock = semesterFind.get().getEndTimeSecondBlock();
        }
        if (request.getStartTime() < startTimeBlock) {
            throw new RestApiException(Message.TIME_OF_EVENT_MUST_IN_TIME_OF_BLOCK);
        }
        if (request.getEndTime() > endTimeBlock) {
            throw new RestApiException(Message.TIME_OF_EVENT_MUST_IN_TIME_OF_BLOCK);
        }
        message.append(CompareUtils.getMessageProperyChange("Thể loại",
                eventFind.get().getCategoryId(), request.getIdCategory(), " rỗng "));
        message.append(CompareUtils.getMessageProperyChange("Mô tả",
                eventFind.get().getDescription(), request.getDescription(), " rỗng "));

        eventFind.get().setId(request.getId());
        eventFind.get().setCategoryId(request.getIdCategory());
        eventFind.get().setDescription(request.getDescription());
        if (request.getEventType() == null && eventFind.get().getEventType() == null) {

        } else if (request.getEventType() == null && eventFind.get().getEventType() != null) {
            message.append("kiểu sự kiện thành Chưa xác định; ");
        } else if (request.getEventType() == 0) {
            if (eventFind.get().getEventType() != EventType.STUDENT_EVENT) {
                message.append("kiểu sự kiện thành Sự kiện dành cho sinh viên; ");
            }
            eventFind.get().setEventType(EventType.STUDENT_EVENT);
        } else if (request.getEventType() == 1) {
            if (eventFind.get().getEventType() != EventType.LECTURER_EVENT) {
                message.append("kiểu sự kiện thành Sự kiện dành cho giảng viên; ");
            }
            eventFind.get().setEventType(EventType.LECTURER_EVENT);
        } else if (request.getEventType() == 2) {
            if (eventFind.get().getEventType() != EventType.COMBINED_EVNT) {
                message.append("kiểu sự kiện thành Sự kiện dành cho cả sinh viên và giảng viên; ");
            }
            eventFind.get().setEventType(EventType.COMBINED_EVNT);
        } else {
            if (eventFind.get().getEventType() != EventType.TUTORIAL) {
                message.append("kiểu sự kiện thành Sự kiện Tutorial; ");
            }
            eventFind.get().setEventType(EventType.TUTORIAL);
        }
        message.append(CompareUtils.getMessageProperyChange("Tên sự kiện",
                eventFind.get().getName(), request.getName(), " rỗng "));
        message.append(CompareUtils.getMessageProperyChange("thời gian bắt đầu",
                eventFind.get().getStartTime(), request.getStartTime(), " rỗng "));
        message.append(CompareUtils.getMessageProperyChange("Thời gian kết thúc",
                eventFind.get().getEndTime(), request.getEndTime(), " rỗng "));
        message.append(CompareUtils.getMessageProperyChange("Background",
                eventFind.get().getBackgroundPath(), request.getBackgroundPath(), " rỗng "));
        message.append(CompareUtils.getMessageProperyChange("Banner",
                eventFind.get().getBannerPath(), request.getBannerPath(), ""));
        message.append(CompareUtils.getMessageProperyChange("Standee",
                eventFind.get().getStandeePath(), request.getStandeePath(), ""));
        message.append(CompareUtils.getMessageProperyChange("số người dự kiến tham gia",
                eventFind.get().getExpectedParticipants(), request.getExpectedParticipants(), ""));
        message.append(CompareUtils.getMessageProperyChange("trạng thái đặt địa điểm",
                eventFind.get().getIsHireLocation(), request.getIsHireLocation(), ""));
        message.append(CompareUtils.getMessageProperyChange("trạng thái Book thiết kế Banner",
                eventFind.get().getIsHireDesignBanner(), request.getIsHireDesignBanner(), ""));
        message.append(CompareUtils.getMessageProperyChange("trạng thái Book thiết kế Background",
                eventFind.get().getIsHireDesignBg(), request.getIsHireDesignBg(), ""));
        message.append(CompareUtils.getMessageProperyChange("trạng thái Book thiết kế Standee",
                eventFind.get().getIsHireDesignStandee(), request.getIsHireDesignStandee(), ""));

        eventFind.get().setName(request.getName().trim());
        eventFind.get().setStartTime(request.getStartTime());
        eventFind.get().setEndTime(request.getEndTime());
        eventFind.get().setBackgroundPath(request.getBackgroundPath());
        eventFind.get().setBannerPath(request.getBannerPath());
        eventFind.get().setStandeePath(request.getStandeePath());
        eventFind.get().setExpectedParticipants((short) request.getExpectedParticipants());

        eventFind.get().setIsHireLocation(request.getIsHireLocation());
        eventFind.get().setIsHireDesignBg(request.getIsHireDesignBg());
        eventFind.get().setIsHireDesignBanner(request.getIsHireDesignBanner());
        eventFind.get().setIsHireDesignStandee(request.getIsHireDesignStandee());

        eventObjectRepository.deleteEventMajorByIdEvent(request.getId());
        for (String object : request.getIdObject()) {
            EventObject eventObject = new EventObject();
            eventObject.setEventId(request.getId());
            eventObject.setObjectId(object);
            eventObjectRepository.save(eventObject);
        }
        if (!message.isEmpty()) {
            loggerUtil.sendLog("Đã sửa ".concat(message.toString()), eventFind.get().getId());
        }


        return eventFind.get();
    }

    /***
     *
     * @param idEvent: Id sự kiện
     * @return Danh sách tài nguyên của một sự kiện
     */
    @Override
    public List<OedResourceResponse> getAllResource(String idEvent) {
        return resourceRepository.getAllResource(idEvent);
    }

    /***
     *
     * @param id: Id sự kiện
     * @return Danh sách địa điểm của một sự kiện
     */
    @Override
    public List<OedEventLocationResponse> getEventLocationByIdEvent(String id) {
        return eventLocationRepository.getEventLocationByIdEvent(id);
    }

    /***
     *
     * @param request: Những thông tin khi thêm một tài nguyên
     * @return Một đối tượng Resource sau khi thêm vào DB
     */
    @Override
    @Synchronized
    public Resource create(@Valid OedCreateResourceRequest request) {
        Optional<Event> event = eventRepository.findById(request.getIdEvent());
        if (!event.isPresent()) {
            throw new RestApiException(Message.EVENT_NOT_EXIST);
        }
        Boolean checkRole = checkOrganizerRole(request.getIdEvent());
        if (!checkRole) {
            throw new RestApiException(Message.YOU_HAVE_NO_RIGHTS);// Check quền chỉnh sửa
        }
        List<OedResourceResponse> responses = getAllResource(event.get().getId());
        for (OedResourceResponse x: responses) {
            if (request.getName().equals(x.getName())) {
                throw new RestApiException(Message.RESOURCE_NAME_ALREADY_EXIST);
            }
        }
        Resource resource = new Resource();
        resource.setName(request.getName());
        resource.setPath(request.getPath());
        resource.setDescription(request.getDescription());
        resource.setEventId(request.getIdEvent());
        loggerUtil.sendLog("Đã thêm mới một Tài nguyên trong sự kiện", event.get().getId());
        return resourceRepository.save(resource);
    }

    /***
     *
     * @param request: Những thông tin khi cập nhật một tài nguyên
     * @return: Một đối tượng Resource sau khi được cập nhật
     */
    @Override
    @Synchronized
    public Resource update(@Valid OedUpdateResourceRequest request) {
        StringBuilder message = new StringBuilder();
        Optional<Resource> resourceFind = resourceRepository.findById(request.getId());
        if (!resourceFind.isPresent()) {
            throw new RestApiException(Message.RESOURCE_NOT_EXIST); // Resource ko tồn tại
        }
        message.append(CompareUtils.getMessageProperyChange("tên của tài nguyên",
                resourceFind.get().getName(), request.getName(), ""));
        message.append(CompareUtils.getMessageProperyChange("mô tả của tài nguyên",
                resourceFind.get().getDescription(), request.getDescription(), ""));
        message.append(CompareUtils.getMessageProperyChange("dường dẫn của tài nguyên",
                resourceFind.get().getPath(), request.getPath(), ""));

        resourceFind.get().setName(request.getName());
        resourceFind.get().setPath(request.getPath());
        resourceFind.get().setDescription(request.getDescription());
        if (!message.equals("")) {
            loggerUtil.sendLog("Đã sửa ".concat(message.toString()), resourceFind.get().getEventId());
            resourceRepository.save(resourceFind.get());
        }
        return resourceFind.get();
    }

    /***
     *
     * @param id: Id của Resource
     * @return: Trả về Id của Resource
     */
    @Override
    @Synchronized
    public String deleteResource(String id) {
        resourceRepository.deleteById(id);
        loggerUtil.sendLog("Đã xóa một Tài nguyên", "");
        return id;
    }

    /***
     *
     * @return: Danh sách học kỳ
     */
    @Override
    public List<OedSemesterResponse> getSemesters() {
        return semesterRepository.getAll();
    }

    /***
     *
     * @return: Danh sách chuyên ngành
     */
    @Override
    public List<OedMajorResponse> getMajors() {
        return majorRepository.getAll();
    }

    /***
     *
     * @param id: ID của sự kiện
     * @return: Danh sách Chuyên ngành trong sự kiện
     */
    @Override
    public List<OedMajorResponse> getMajorByIdEvent(String id) {
        return majorRepository.getMajorByIdEvent(id);
    }

    /***
     *
     * @param idEvent
     * @return Danh sách Người tổ chức của sự kiện
     */
    @Override
    public List<OedEventOrganizerCustom> getAllOrganizerByIdEvent(String idEvent) {
        List<OedEventOrganizerResponse> oedEventOrganizerResponseList = odeEventOrganizerRepository.getAllEventOrganizer(idEvent);
        List<String> listIdOrganizer = oedEventOrganizerResponseList.stream()
                .map(OedEventOrganizerResponse::getOrganizerId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        List<SimpleResponse> responseOrganizer = callApiIdentity.handleCallApiGetListUserByListId(listIdOrganizer);

        List<OedEventOrganizerCustom> oedEventOrganizerCustomList = new ArrayList<>();
        for (OedEventOrganizerResponse xx : oedEventOrganizerResponseList) {
            OedEventOrganizerCustom oedEventOrganizerCustom = new OedEventOrganizerCustom();
            oedEventOrganizerCustom.setId(xx.getId());
            oedEventOrganizerCustom.setEventId(xx.getEventId());
            oedEventOrganizerCustom.setOrganizerId(xx.getOrganizerId());
            oedEventOrganizerCustom.setEventRole(xx.getEventRole());
            for (SimpleResponse simpleResponse : responseOrganizer) {
                if (xx.getOrganizerId().equals(simpleResponse.getId())) {
                    oedEventOrganizerCustom.setName(simpleResponse.getName());
                    oedEventOrganizerCustom.setUsername(simpleResponse.getUserName());
                    oedEventOrganizerCustom.setEmail(simpleResponse.getEmail());
                    break;
                }
            }
            oedEventOrganizerCustomList.add(oedEventOrganizerCustom);
        }
        return oedEventOrganizerCustomList;
    }

    /***
     *
     * @param idEvent
     * @return Người tổ chức là true - Host, false - Co-host
     */
    @Override
    public Boolean checkOrganizerRole(String idEvent) {
        String idOrganizer = portalEventsSession.getCurrentIdUser();
        Optional<EventOrganizer> eventOrganizer = odeEventOrganizerRepository.findByOrganizerIdAndEventId(idOrganizer, idEvent);
        if (!eventOrganizer.isPresent()) {
            List<String> lstCurrentRole = portalEventsSession.getCurrentUserRole();
            if (lstCurrentRole.contains(ActorConstants.ACTOR_ADMINISTRATIVE)) {
                return false;
            }
            throw new ForbiddenException();
        } else {
            if (eventOrganizer.get().getEventRole().equals(EventRole.HOST)) {
                return true;
            } else {
                return false;
            }
        }
    }

    /***
     *
     * @param idEvent
     * @return Danh sách AgendaItem của 1 sự kiện
     */
    @Override
    public List<OedAgendaItemCustom> getAllAgendaItem(String idEvent) {
        List<OedAgendaItemResponse> oedAgendaItemResponses = odeAgendaItemRepository.getAllAgendaItems(idEvent);
        List<OedAgendaItemCustom> oedAgendaItemCustoms = new ArrayList<>();
        int stt = 0;
        for (OedAgendaItemResponse xx : oedAgendaItemResponses) {
            OedAgendaItemCustom oedAgendaItemCustom = new OedAgendaItemCustom();
            oedAgendaItemCustom.setIndex(stt);
            oedAgendaItemCustom.setId(xx.getId());
            oedAgendaItemCustom.setEventId(xx.getEventId());
            oedAgendaItemCustom.setOrganizerId(xx.getOrganizerId());
            oedAgendaItemCustom.setStartTime(xx.getStartTime());
            oedAgendaItemCustom.setEndTime(xx.getEndTime());
            oedAgendaItemCustom.setDescription(xx.getDescription());
            if (xx.getOrganizerId() != null) {
                SimpleResponse simpleResponse = callApiIdentity.handleCallApiGetUserById(xx.getOrganizerId());
                oedAgendaItemCustom.setName(simpleResponse.getName());
                oedAgendaItemCustom.setUsername(simpleResponse.getUserName());
            } else {
                oedAgendaItemCustom.setName("");
                oedAgendaItemCustom.setUsername("");
            }
            oedAgendaItemCustoms.add(oedAgendaItemCustom);
            stt++;
        }
        return oedAgendaItemCustoms;
    }

    /***
     *
     * @param idEvent
     * @return Danh sách Người phê duyệt không có trong sự kiện
     */
    @Override
    public List<SimpleResponse> getAllOrganizerNotInEvent(String idEvent) {
        List<OedEventOrganizerResponse> oedEventOrganizerResponseList = odeEventOrganizerRepository.getAllEventOrganizer(idEvent);
        List<String> listIdOrganizer = oedEventOrganizerResponseList.stream()
                .map(OedEventOrganizerResponse::getOrganizerId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        List<SimpleResponse> listSimple = callApiIdentity.handleCallApiGetUserByRoleAndModule(ActorConstants.ACTOR_ORGANIZER);

        List<SimpleResponse> responseOrganizer = listSimple;
        responseOrganizer.removeIf(xx -> listIdOrganizer.contains(xx.getId()));
        return responseOrganizer;
    }

    /***
     *
     * @param request
     * @return Đối tượng EventOrganizer vừa được tạo
     */
    @Override
    public EventOrganizer createEventOrganizer(@Valid OedCreateEventOrganizerRequest request) {
        Boolean checkRole = checkOrganizerRole(request.getEventId());
        if (!checkRole) {
            throw new RestApiException(Message.YOU_HAVE_NO_RIGHTS);// Check quền chỉnh sửa
        }
        Optional<Event> eventFind = eventRepository.findById(request.getEventId());
        if (!eventFind.isPresent()) {
            throw new RestApiException(Message.EVENT_NOT_EXIST);
        }
        List<OedEventOrganizerResponse> organizerResponseList = odeEventOrganizerRepository.getAllEventOrganizer(request.getEventId());
        for (OedEventOrganizerResponse x: organizerResponseList) {
            if (request.getOrganizerId().equals(x.getOrganizerId())) {
                throw new RestApiException(Message.THE_ORGANIER_ALREADY_EXISTS);
            }
        }

        EventOrganizer eventOrganizer = new EventOrganizer();
        eventOrganizer.setOrganizerId(request.getOrganizerId());
        eventOrganizer.setEventId(request.getEventId());
        if (request.getEventRole() == 1) {
            eventOrganizer.setEventRole(EventRole.CO_HOST);
        } else {
            eventOrganizer.setEventRole(EventRole.HOST);
        }

        EventOrganizer organizer = odeEventOrganizerRepository.save(eventOrganizer);

        // Gửi mail thông báo đến người tổ chức
        SimpleResponse simpleResponse = callApiIdentity.handleCallApiGetUserById(organizer.getOrganizerId());
        Category category = categoryRepository.findById(eventFind.get().getCategoryId()).get();
        List<OedEventOrganizerCustom> lecturers = getAllOrganizerByIdEvent(eventFind.get().getId());
        List<OedObjectResponse> objects = getObjectByIdEvent(eventFind.get().getId());
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");

        List<MailEventOrganizerCustom> lectureList = new ArrayList<>();
        for (OedEventOrganizerCustom x: lecturers) {
            MailEventOrganizerCustom custom = new MailEventOrganizerCustom(x.getId(), x.getEventId(), x.getOrganizerId(), x.getEventRole(), x.getName(), x.getUsername(), x.getEmail());
            lectureList.add(custom);
        }
        List<MailObjectResponse> objectResponseList = new ArrayList<>();
        for (OedObjectResponse x: objects) {
            MailObjectResponse custom = new MailObjectResponse(x.getId(), x.getName());
            objectResponseList.add(custom);
        }
        String eventType = "";
        if (eventFind.get().getEventType() == EventType.STUDENT_EVENT) {
            eventType = "Sinh viên";
        } else if (eventFind.get().getEventType() == EventType.LECTURER_EVENT) {
            eventType = "Giảng viên";
        } else if (eventFind.get().getEventType() == EventType.COMBINED_EVNT) {
            eventType = "Cả Giảng viên và Sinh viên";
        }
        MailOrgenizer req = new MailOrgenizer();
        req.setEventName(eventFind.get().getName());
        req.setMails(simpleResponse.getEmail());
        req.setSubject("[Portal Event] Thư gửi thông báo trở thành người tổ chức sự kiện " + eventFind.get().getName());
        req.setRole(organizer.getEventRole() + "");
        req.setUserNameAdd(portalEventsSession.getCurrentUserName());
        req.setDate(dateFormat.format(new Date(eventFind.get().getStartTime())) + " - " +
                dateFormat.format(new Date(eventFind.get().getEndTime())));
        req.setTypeEvent(eventType);
        req.setCategory(category.getName());
        req.setObjects(objectResponseList);
        req.setLecturerList(lectureList);
        req.setStatus("thêm vào danh sách");
        emailUtils.sendEmailAddOrganizer(req);

        // Gửi mail thông báo đến người phê duyệt khi sk đã phê duyệt
        if (eventFind.get().getStatus() == EventStatus.APPROVED) {
            List<SimpleResponse> simpleResponses = callApiIdentity.handleCallApiGetUserByRoleAndModule(ActorConstants.ACTOR_APPROVER);
            String[] mailApprover = simpleResponses.stream()
                    .map(SimpleResponse::getEmail)
                    .filter(Objects::nonNull)
                    .distinct()
                    .toArray(String[]::new);
            MailOrganizerSendApproval org = new MailOrganizerSendApproval();
            String mess = "Thông báo" + portalEventsSession.getCurrentUserName() + " đã thêm " + simpleResponse.getUserName() +
                    " vào danh sách người tổ chức của sự kiện <strong>" + eventFind.get().getName() + "</strong>";
            org.setMessage(mess);
            org.setMails(mailApprover);
            org.setSubject("[Portal Event] Thư gửi thông náo thêm người tổ chức vào sự kiện " + eventFind.get().getName());
            org.setLecturerList(lectureList);
            emailUtils.sendEmailChangeOrganizer(org);
        }
        loggerUtil.sendLog("Đã Thêm một Người tổ chức " + simpleResponse.getUserName() + " vào sự kiện", eventFind.get().getId());
        return organizer;
    }

    /***
     *
     * @param request
     * @return Đối tượng EventOrganizer vừa được cập nhật
     */
    @Override
    public EventOrganizer updateEventOrganizer(@Valid OedUpdateEventOrganizer request) {
        StringBuilder message = new StringBuilder();
        Optional<Event> eventFind = eventRepository.findById(request.getEventId());
        if (!eventFind.isPresent()) {
            throw new RestApiException(Message.EVENT_NOT_EXIST);
        }
        Boolean checkRole = checkOrganizerRole(request.getEventId());
        if (!checkRole) {
            throw new RestApiException(Message.YOU_HAVE_NO_RIGHTS);// Check quền chỉnh sửa
        }

        Optional<EventOrganizer> eventOrganizer = odeEventOrganizerRepository.findById(request.getId());
        if (!eventOrganizer.isPresent()) {
            throw new RestApiException(Message.EVENT_ORGANIZER_NOT_EXIST);
        }
        if (request.getEventRole() == 1) {
            if (eventOrganizer.get().getEventRole() != EventRole.CO_HOST) {
                message.append("vai trò của người tổ chức từ HOST thành CO-HOST; ");
            }
            eventOrganizer.get().setEventRole(EventRole.CO_HOST);
        } else {
            if (eventOrganizer.get().getEventRole() != EventRole.HOST) {
                message.append("vai trò của người tổ chức từ CO-HOST thành HOST; ");
            }
            eventOrganizer.get().setEventRole(EventRole.HOST);
        }
        if (!message.equals("")) {
            loggerUtil.sendLog("Đã sửa ".concat(message.toString()), eventFind.get().getId());
            EventOrganizer organizer = odeEventOrganizerRepository.save(eventOrganizer.get());

            // Gửi mail thông báo
            SimpleResponse simpleResponse = callApiIdentity.handleCallApiGetUserById(organizer.getOrganizerId());
            Category category = categoryRepository.findById(eventFind.get().getCategoryId()).get();
            List<OedEventOrganizerCustom> lecturers = getAllOrganizerByIdEvent(eventFind.get().getId());
            List<OedObjectResponse> objects = getObjectByIdEvent(eventFind.get().getId());
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");

            List<MailEventOrganizerCustom> lectureList = new ArrayList<>();
            for (OedEventOrganizerCustom x: lecturers) {
                MailEventOrganizerCustom custom = new MailEventOrganizerCustom(x.getId(), x.getEventId(), x.getOrganizerId(), x.getEventRole(), x.getName(), x.getUsername(), x.getEmail());
                lectureList.add(custom);
            }
            List<MailObjectResponse> objectResponseList = new ArrayList<>();
            for (OedObjectResponse x: objects) {
                MailObjectResponse custom = new MailObjectResponse(x.getId(), x.getName());
                objectResponseList.add(custom);
            }
            String eventType = "";
            if (eventFind.get().getEventType() == EventType.STUDENT_EVENT) {
                eventType = "Sinh viên";
            } else if (eventFind.get().getEventType() == EventType.LECTURER_EVENT) {
                eventType = "Giảng viên";
            } else if (eventFind.get().getEventType() == EventType.COMBINED_EVNT) {
                eventType = "Cả Giảng viên và Sinh viên";
            }
            MailOrgenizer req = new MailOrgenizer();
            req.setEventName(eventFind.get().getName());
            req.setMails(simpleResponse.getEmail());
            req.setSubject("[Portal Event] Thư gửi thông báo cập nhật vai trò người tổ chức sự kiện " + eventFind.get().getName());
            req.setRole(organizer.getEventRole() + "");
            req.setUserNameAdd(portalEventsSession.getCurrentUserName());
            req.setDate(dateFormat.format(new Date(eventFind.get().getStartTime())) + " - " +
                    dateFormat.format(new Date(eventFind.get().getEndTime())));
            req.setTypeEvent(eventType);
            req.setCategory(category.getName());
            req.setObjects(objectResponseList);
            req.setLecturerList(lectureList);
            req.setStatus("thay đổi quyền");
            emailUtils.sendEmailAddOrganizer(req);

            // Gửi mail thông báo đến người phê duyệt khi sk đã phê duyệt
            if (eventFind.get().getStatus() == EventStatus.APPROVED) {
                MailOrganizerSendApproval org = new MailOrganizerSendApproval();
                List<SimpleResponse> simpleResponses = callApiIdentity.handleCallApiGetUserByRoleAndModule(ActorConstants.ACTOR_APPROVER);
                String[] mailApprover = simpleResponses.stream()
                        .map(SimpleResponse::getEmail)
                        .filter(Objects::nonNull)
                        .distinct()
                        .toArray(String[]::new);
                String mess = "Thông báo" + portalEventsSession.getCurrentUserName() + " đã cập nhật người tổ chức " + simpleResponse.getUserName() +
                        " của sự kiện <strong>" + eventFind.get().getName() + "</strong> từ vai trò" +
                        (organizer.getEventRole() == EventRole.HOST ? "Co-Host" : "Host") + " thành " + organizer.getEventRole();
                org.setMessage(mess);
                org.setMails(mailApprover);
                org.setSubject("[Portal Event] Thư gửi thông báo cập nhật người tổ chức của sự kiện " + eventFind.get().getName());
                org.setLecturerList(lectureList);
                emailUtils.sendEmailChangeOrganizer(org);
            }
            return organizer;
        } else {
            return eventOrganizer.get();
        }
    }

    /***
     * Xóa EventOrganizer
     * @param request
     * @return ID của EventOrganizer vừa được xóa
     */
    @Override
    public String deleteEventOrganizer(@Valid OedDeleteEventOrganizerRequest request) {
        Optional<Event> eventFind = eventRepository.findById(request.getEventId());
        if (!eventFind.isPresent()) {
            throw new RestApiException(Message.EVENT_NOT_EXIST);
        }
        Boolean checkRole = checkOrganizerRole(request.getEventId());
        if (!checkRole) {
            throw new RestApiException(Message.YOU_HAVE_NO_RIGHTS);// Check quền chỉnh sửa
        }

        Optional<EventOrganizer> eventOrganizerFind = odeEventOrganizerRepository.findById(request.getId());
        if (!eventOrganizerFind.isPresent()) {
            throw new RestApiException(Message.EVENT_ORGANIZER_NOT_EXIST);
        }
        List<String> listIdAgenda = odeAgendaItemRepository.checkAgendaOrganizer(request.getEventId(), request.getOrganizerId());
        if (listIdAgenda != null && !listIdAgenda.isEmpty()) {
            throw new RestApiException(Message.ORGANIZER_HAVE_AGENDA);
        }
        SimpleResponse simpleResponse = callApiIdentity.handleCallApiGetUserById(eventOrganizerFind.get().getOrganizerId());
        odeEventOrganizerRepository.deleteById(request.getId());

        // Gửi mail thông báo
        Category category = categoryRepository.findById(eventFind.get().getCategoryId()).get();
        List<OedEventOrganizerCustom> lecturers = getAllOrganizerByIdEvent(eventFind.get().getId());
        List<OedObjectResponse> objects = getObjectByIdEvent(eventFind.get().getId());
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");

        List<MailEventOrganizerCustom> lectureList = new ArrayList<>();
        for (OedEventOrganizerCustom x: lecturers) {
            MailEventOrganizerCustom custom = new MailEventOrganizerCustom(x.getId(), x.getEventId(), x.getOrganizerId(), x.getEventRole(), x.getName(), x.getUsername(), x.getEmail());
            lectureList.add(custom);
        }
        List<MailObjectResponse> objectResponseList = new ArrayList<>();
        for (OedObjectResponse x: objects) {
            MailObjectResponse custom = new MailObjectResponse(x.getId(), x.getName());
            objectResponseList.add(custom);
        }
        String eventType = "";
        if (eventFind.get().getEventType() == EventType.STUDENT_EVENT) {
            eventType = "Sinh viên";
        } else if (eventFind.get().getEventType() == EventType.LECTURER_EVENT) {
            eventType = "Giảng viên";
        } else if (eventFind.get().getEventType() == EventType.COMBINED_EVNT) {
            eventType = "Cả Giảng viên và Sinh viên";
        }
        MailOrgenizer req = new MailOrgenizer();
        req.setEventName(eventFind.get().getName());
        req.setMails(simpleResponse.getEmail());
        req.setSubject("[Portal Event] Thư gửi thông xóa khỏi danh sách người tổ chức sự kiện " + eventFind.get().getName());
        req.setUserNameAdd(portalEventsSession.getCurrentUserName());
        req.setDate(dateFormat.format(new Date(eventFind.get().getStartTime())) + " - " +
                dateFormat.format(new Date(eventFind.get().getEndTime())));
        req.setTypeEvent(eventType);
        req.setCategory(category.getName());
        req.setObjects(objectResponseList);
        req.setLecturerList(lectureList);
        emailUtils.sendEmailDeleteOrganizer(req);

        // Gửi mail thông báo đến người phê duyệt khi sk đã phê duyệt
        if (eventFind.get().getStatus() == EventStatus.APPROVED) {
            MailOrganizerSendApproval org = new MailOrganizerSendApproval();
            List<SimpleResponse> simpleResponses = callApiIdentity.handleCallApiGetUserByRoleAndModule(ActorConstants.ACTOR_APPROVER);
            String[] mailApprover = simpleResponses.stream()
                    .map(SimpleResponse::getEmail)
                    .filter(Objects::nonNull)
                    .distinct()
                    .toArray(String[]::new);
            String mess = "Thông báo" + portalEventsSession.getCurrentUserName() + " đã xóa người tổ chức " + simpleResponse.getUserName() +
                    " khỏi danh sách người tổ chức của sự kiện <strong>" + eventFind.get().getName() + "</strong>";
            org.setMessage(mess);
            org.setMails(mailApprover);
            org.setSubject("[Portal Event] Thư gửi thông báo cập nhật danh sách người tổ chức của sự kiện " + eventFind.get().getName());
            org.setLecturerList(lectureList);
            emailUtils.sendEmailChangeOrganizer(org);
        }

        loggerUtil.sendLog("Đã xóa một Người tổ chức " + simpleResponse.getUserName() + " khỏi sự kiện", eventFind.get().getId());
        return request.getId();
    }

    /***
     *
     * @param request
     * @return Đối tượng Agenda vừa được tạo
     */
    @Override
    public AgendaItem createAgenda(@Valid OedCreateAgendaRequest request) {
        Optional<Event> eventFind = eventRepository.findById(request.getEventId());
        if (!eventFind.isPresent()) {
            throw new RestApiException(Message.EVENT_NOT_EXIST);
        }
        Boolean checkRole = checkOrganizerRole(request.getEventId());
        if (!checkRole) {
            throw new RestApiException(Message.YOU_HAVE_NO_RIGHTS);// Check quền chỉnh sửa
        }
        AgendaItem agendaItem = new AgendaItem();
        agendaItem.setEventId(request.getEventId());
        agendaItem.setDescription(request.getDescription());
        agendaItem.setOrganizerId(request.getOrganizerId());
        agendaItem.setStartTime(request.getStartTime());
        agendaItem.setEndTime(request.getEndTime());
        loggerUtil.sendLog("Đã thêm một Agenda item vào sự kiện", eventFind.get().getId());
        return odeAgendaItemRepository.save(agendaItem);
    }

    /***
     *
     * @param request
     * @return Đối tượng Agenda vừa được cập nhật
     */
    @Override
    public AgendaItem updateAgenda(@Valid OedUpdateAgendaRequest request) {
        StringBuilder message = new StringBuilder();
        Optional<Event> eventFind = eventRepository.findById(request.getEventId());
        if (!eventFind.isPresent()) {
            throw new RestApiException(Message.EVENT_NOT_EXIST);
        }
        Boolean checkRole = checkOrganizerRole(request.getEventId());
        if (!checkRole) {
            throw new RestApiException(Message.YOU_HAVE_NO_RIGHTS);// Check quền chỉnh sửa
        }
        AgendaItem agendaItem = odeAgendaItemRepository.findById(request.getId()).get();
        if (agendaItem == null) {
            throw new RestApiException(Message.AGENDA_NOT_EXIST);
        }
        agendaItem.setEventId(request.getEventId());
        agendaItem.setDescription(request.getDescription());
        agendaItem.setOrganizerId(request.getOrganizerId());
        agendaItem.setStartTime(request.getStartTime());
        agendaItem.setEndTime(request.getEndTime());
        if (!message.equals("")) {
            loggerUtil.sendLog(message.toString(), eventFind.get().getId());
        }
        return odeAgendaItemRepository.save(agendaItem);
    }

    /***
     *
     * @param id
     * @return ID của Agenda vừa được xóa
     */
    @Override
    public String deleteAgenda(String id) {
        AgendaItem agendaItem = odeAgendaItemRepository.findById(id).get();
        if (agendaItem == null) {
            throw new RestApiException(Message.AGENDA_NOT_EXIST);
        }
        odeAgendaItemRepository.deleteById(id);
        loggerUtil.sendLog("Đã xóa một AgendaItem", agendaItem.getEventId());
        return id;
    }

    /***
     *
     * @param id
     * @return Danh sách Object của 1 sự kiện
     */
    @Override
    public List<OedObjectResponse> getObjectByIdEvent(String id) {
        return objectRepository.getObjectByIdEvent(id);
    }

    /***
     *
     * @param eventId: Id sự kiện
     * @param pageNumber: số trang
     * @return Danh sách Comment
     */
    @Override
    public List<OedAllCommentResponse> getComment(String eventId, int pageNumber) {
        long a = new Date().getTime();
        if (!eventRepository.existsById(eventId)) {
            throw new RestApiException(Message.EVENT_NOT_EXIST);
        }
        if (pageNumber < 0) {
            throw new RestApiException(Message.PAGE_NOT_EXIST);
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        Pageable pageable = PageRequest.of(pageNumber, 10);
        //List chứa ID các user cần lấy thông tin
        List<String> lstUserIdToGet = new ArrayList<>();
        List<OedAllCommentResponse> listComment = new ArrayList<>();
        List<OedReplyCommentResponse> listReplyComment = new ArrayList<>();
        List<String> listReplyCommentId = new ArrayList<>();
        Page<Comment> commentFromDB = commentRepository.findByEventIdAndReplyIdIsNullOrderByLastModifiedDateDesc(eventId, pageable);

        for (Comment comment : commentFromDB) {
            OedAllCommentResponse commentResponse = new OedAllCommentResponse();
            commentResponse.setTotalPages(commentFromDB.getTotalPages());
            commentResponse.setCurrentPage(commentFromDB.getNumber());
            commentResponse.setId(comment.getId());
            commentResponse.setLastModifiedDate(dateFormat.format(new Date(comment.getLastModifiedDate())));
            commentResponse.setComment(comment.getComment());
            commentResponse.setUserId(comment.getUserId());
            commentResponse.setIsReply(false);
            commentResponse.setListReply(new ArrayList<>());
            lstUserIdToGet.add(comment.getUserId());
            listReplyCommentId.add(comment.getId());
            listComment.add(commentResponse);
        }
        List<OedCommentResponse> listReply = commentRepository.getReplyCommentByReplyIdAndEventId(eventId, listReplyCommentId);
        listReply.forEach(comment -> {
            OedReplyCommentResponse replyCommentResponse = new OedReplyCommentResponse();
            replyCommentResponse.setId(comment.getId());
            replyCommentResponse.setLastModifiedDate(dateFormat.format(new Date(comment.getLastModifiedDate())));
            replyCommentResponse.setComment(comment.getComment());
            replyCommentResponse.setReplyId(comment.getReplyId());
            replyCommentResponse.setUserId(comment.getUserId());

            lstUserIdToGet.add(comment.getUserId());
            listReplyComment.add(replyCommentResponse);
        });
        listReplyComment.forEach(replyComment -> {
            listComment.forEach(comment -> {
                if (comment.getId().equals(replyComment.getReplyId())) {
                    comment.addElementToListReply(replyComment);
                }
            });
        });

        if (lstUserIdToGet.size() > 0) {
            //Lấy thông tin user từ list phía trên
            List<SimpleResponse> listUser = callApiIdentity.handleCallApiGetListUserByListId(lstUserIdToGet);
            //Map chứa User
            Map<String, SimpleResponse> mapUser = new HashMap<>();
            listUser.forEach(user -> {
                mapUser.put(user.getId(), user);
            });
            listComment.forEach(comment -> {
                //Duyệt qua các phần tử comment và reply comment để gán các thông tin user được lấy từ Map thông qua UserId
                SimpleResponse commentUser = mapUser.get(comment.getUserId());
                if (commentUser != null) {
                    comment.setUserId(commentUser.getId());
                    comment.setEmail(commentUser.getEmail());
                    comment.setAvatar(commentUser.getPicture());
                }
                if (comment.getListReply() != null) {
                    for (OedReplyCommentResponse replyComment : comment.getListReply()) {
                        SimpleResponse replyCommentUser = mapUser.get(replyComment.getUserId());
                        if (replyCommentUser != null) {
                            replyComment.setUserId(replyCommentUser.getId());
                            replyComment.setEmail(replyCommentUser.getName());
                            replyComment.setAvatar(replyCommentUser.getPicture());
                        }
                    }
                }
            });
        }
        Collections.reverse(listComment);
        return listComment;
    }

    /***
     *
     * @param req
     * @return Comment vừa được tạo
     */
    @Override
    public OedAllCommentResponse postComment(OedPostCommentRequest req) {
        String userId = portalEventsSession.getCurrentIdUser();
        SimpleResponse responseEntity = callApiIdentity.handleCallApiGetUserById(userId);
        Optional<Event> event = eventRepository.findById(req.getEventId());
        if (!event.isPresent()) {
            throw new RestApiException(Message.EVENT_NOT_EXIST);
        }
        Comment comment = new Comment();
        comment.setEventId(req.getEventId());
        comment.setUserId(responseEntity.getId());
        comment.setComment(req.getComment());
        comment = commentRepository.save(comment);

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        OedAllCommentResponse newComment = new OedAllCommentResponse();
        newComment.setId(comment.getId());
        newComment.setLastModifiedDate(dateFormat.format(new Date(comment.getLastModifiedDate())));
        newComment.setComment(comment.getComment());
        newComment.setListReply(new ArrayList<>());
        newComment.setUserId(responseEntity.getId());
        newComment.setEmail(responseEntity.getEmail());
        newComment.setAvatar(responseEntity.getPicture());
        loggerUtil.sendLog(responseEntity.getUserName() + " đã thêm một bình luận cho sự kiện", event.get().getId());
        return newComment;
    }

    @Override
    public Boolean deleteComment(OedDeleteCommentRequest req) {
        String userId = portalEventsSession.getCurrentIdUser();
        Integer check = 0;
        List<SimpleResponse> simpleResponses = callApiIdentity.handleCallApiGetUserByRoleAndModule(ActorConstants.ACTOR_ORGANIZER);
        for (SimpleResponse x : simpleResponses) {
            if (userId.equals(x.getId())) {
                check++;
                break;
            }
        }
        if (check == 0) {
            throw new RestApiException(Message.YOU_HAVE_NO_RIGHTS);
        }
        Optional<Comment> comment = commentRepository.findById(req.getCommentId());
        if (!comment.isPresent()) {
            throw new RestApiException(Message.COMMENT_NOT_EXIST);
        }
        if (comment.get().getReplyId() == null) {
            List<Comment> listReply = commentRepository.getReplyCommentByReplyId(comment.get().getId());
            commentRepository.deleteAll(listReply);
        }
        commentRepository.delete(comment.get());
        loggerUtil.sendLog("Đã xóa một bình luận trong sự kiện", comment.get().getEventId());
        return true;
    }

    /***
     *
     * @param req
     * @return Comment reply vừa tạo
     */
    @Override
    public OedReplyCommentResponse replyComment(OedReplyCommentRequest req) {
        String userId = portalEventsSession.getCurrentIdUser();
        SimpleResponse responseEntity = callApiIdentity.handleCallApiGetUserById(userId);

        Optional<Event> event = eventRepository.findById(req.getEventId());
        if (!event.isPresent()) {
            throw new RestApiException(Message.EVENT_NOT_EXIST);
        }
        if (!commentRepository.existsById(req.getReplyId())) {
            throw new RestApiException(Message.COMMENT_NOT_EXIST);
        }
        Comment replyComment = new Comment();
        replyComment.setEventId(req.getEventId());
        replyComment.setComment(req.getComment());
        replyComment.setUserId(responseEntity.getId());
        replyComment.setReplyId(req.getReplyId());
        replyComment = commentRepository.save(replyComment);

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        OedReplyCommentResponse commentResponse = new OedReplyCommentResponse();
        commentResponse.setId(replyComment.getId());
        commentResponse.setLastModifiedDate(dateFormat.format(new Date(replyComment.getLastModifiedDate())));
        commentResponse.setComment(replyComment.getComment());
        commentResponse.setReplyId(replyComment.getReplyId());
        commentResponse.setUserId(responseEntity.getId());
        commentResponse.setEmail(responseEntity.getEmail());
        commentResponse.setAvatar(responseEntity.getPicture());
        loggerUtil.sendLog(responseEntity.getUserName() + " đã thêm một phản hồi bình luận", event.get().getId());
        return commentResponse;
    }

    /***
     * Đóng sự kiện
     * @param req
     * @return
     */
    @Override
    public ResponseObject closeEvent(OedEventCloseRequest req) {
        if (req.getIdEvent() == null) throw new RestApiException(Message.ID_MUST_NOT_EMPTY);
        Event event = eventRepository.findById(req.getIdEvent())
                .orElseThrow(() -> new RestApiException(Message.EVENT_NOT_EXIST));
        Boolean checkRole = checkOrganizerRole(req.getIdEvent());
        if (!checkRole) {
            throw new RestApiException(Message.YOU_HAVE_NO_RIGHTS);// Check quền chỉnh sửa
        }

        //REASON_IS_EMPTY -> Lý do từ chối trống
        if ("".equals(req.getReason())) throw new RestApiException(Message.REASON_MUST_NOT_EMPTY);
        event.setReason(req.getReason());
        event.setStatus(EventStatus.CLOSE);

        ResponseObject responseObject = new ResponseObject(eventRepository.save(event));
        responseObject.setMessage("update success");

        // Gửi mail thông báo
        Category category = categoryRepository.findById(event.getCategoryId()).get();
        List<OedEventOrganizerCustom> lecturers = getAllOrganizerByIdEvent(event.getId());
        List<OedObjectResponse> objects = getObjectByIdEvent(event.getId());
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");

        List<MailEventOrganizerCustom> lectureList = new ArrayList<>();
        for (OedEventOrganizerCustom x: lecturers) {
            MailEventOrganizerCustom custom = new MailEventOrganizerCustom(x.getId(), x.getEventId(),
                    x.getOrganizerId(), x.getEventRole(), x.getName(), x.getUsername(), x.getEmail());
            lectureList.add(custom);
        }
        List<MailObjectResponse> objectResponseList = new ArrayList<>();
        for (OedObjectResponse x: objects) {
            MailObjectResponse custom = new MailObjectResponse(x.getId(), x.getName());
            objectResponseList.add(custom);
        }
        String eventType = "";
        if (event.getEventType() == EventType.STUDENT_EVENT) {
            eventType = "Sinh viên";
        } else if (event.getEventType() == EventType.LECTURER_EVENT) {
            eventType = "Giảng viên";
        } else if (event.getEventType() == EventType.COMBINED_EVNT) {
            eventType = "Cả Giảng viên và Sinh viên";
        }
        MailCustomRequest request = new MailCustomRequest();
        List<SimpleResponse> simpleResponses = callApiIdentity.handleCallApiGetUserByRoleAndModule(ActorConstants.ACTOR_APPROVER);
        List<SimpleResponse> administrators = callApiIdentity.handleCallApiGetUserByRoleAndModule(ActorConstants.ACTOR_ADMINISTRATIVE);
        String[] mailApprover = simpleResponses.stream()
                .map(SimpleResponse::getEmail)
                .filter(Objects::nonNull)
                .distinct()
                .toArray(String[]::new);
        String[] mailLecturers = lecturers.stream()
                .map(OedEventOrganizerCustom::getEmail)
                .filter(Objects::nonNull)
                .distinct()
                .toArray(String[]::new);
        String[] mailAdministrative = simpleResponses.stream()
                .map(SimpleResponse::getEmail)
                .filter(Objects::nonNull)
                .distinct()
                .toArray(String[]::new);
        String[] combinedArray = Stream.concat(Arrays.stream(mailApprover), Arrays.stream(mailLecturers))
                .distinct()
                .toArray(String[]::new);
        combinedArray = Stream.concat(Arrays.stream(combinedArray), Arrays.stream(mailAdministrative)).distinct().toArray(String[]::new);

        request.setMails(combinedArray);
        request.setSubject("[Portal Event] Thư gửi thông báo đóng sự kiện " + event.getName());
        request.setEventName(event.getName());
        request.setOganizerName(portalEventsSession.getCurrentUserName());
        request.setDate(dateFormat.format(new Date(event.getStartTime())) + " - " +
                dateFormat.format(new Date(event.getEndTime())));
        request.setTypeEvent(eventType);
        request.setCategory(category.getName());
        request.setObjects(objectResponseList);
        request.setLecturerList(lectureList);
        request.setReason(req.getReason());
        emailUtils.sendEmailCloseEvent(request);
        String message = "Đã đóng sự kiện " + event.getName() + ". Gửi mail thông báo tới ";
        for (String item : combinedArray) {
            message += item + ", ";
        }
        loggerUtil.sendLog(message, event.getId());
        return responseObject;
    }

    /***
     * Lưu danh sách agenda
     * @param requests
     * @return Danh sách agendaItem đã được cập nhật
     */
    @Override
    public List<AgendaItem> saveListAgenda(List<OedUpdateAgendaRequest> requests) {
        StringBuilder message = new StringBuilder();
        Optional<Event> eventFind = eventRepository.findById(requests.get(0).getEventId());
        if (!eventFind.isPresent()) {
            throw new RestApiException(Message.EVENT_NOT_EXIST);
        }
        Boolean checkRole = checkOrganizerRole(requests.get(0).getEventId());
        if (!checkRole) {
            throw new RestApiException(Message.YOU_HAVE_NO_RIGHTS);// Check quền chỉnh sửa
        }
        List<AgendaItem> agendaItems = new ArrayList<>();
        for (OedUpdateAgendaRequest x : requests) {
            AgendaItem agendaItem = new AgendaItem();

            // Nếu id đã tồn tại thì cập nhật thông tin và chưa tồn tại thì thêm mới
            if (x.getId() == null || x.getId().equals("")) {
                agendaItem.setEventId(x.getEventId());
                agendaItem.setOrganizerId(x.getOrganizerId());
                agendaItem.setStartTime(x.getStartTime());
                agendaItem.setEndTime(x.getEndTime());
                agendaItem.setDescription(x.getDescription());
                loggerUtil.sendLog("Đã thêm một agendaItem vào sự kiện", eventFind.get().getId());
            } else {
                agendaItem = odeAgendaItemRepository.findById(x.getId()).get();
                message.append(CompareUtils.getMessageProperyChange("mô tả Agenda item",
                        agendaItem.getDescription(), x.getDescription(), " rỗng "));
                message.append(CompareUtils.getMessageProperyChange("thời gian bắt đầu Agenda item",
                        agendaItem.getStartTime(), x.getEndTime(), " rỗng "));
                message.append(CompareUtils.getMessageProperyChange("thời gian kết thúc của Agenda item",
                        agendaItem.getEndTime(), x.getEndTime(), " rỗng "));
                message.append(CompareUtils.getMessageProperyChange("Người phụ trách của Agenda item",
                        agendaItem.getOrganizerId(), x.getOrganizerId(), " rỗng "));

                agendaItem.setEventId(x.getEventId());
                agendaItem.setOrganizerId(x.getOrganizerId());
                agendaItem.setStartTime(x.getStartTime());
                agendaItem.setEndTime(x.getEndTime());
                agendaItem.setDescription(x.getDescription());
                if (!message.equals("")) {
                    loggerUtil.sendLog("Đã sửa".concat(message.toString()), eventFind.get().getId());
                }
            }
            agendaItems.add(agendaItem);
        }
        return odeAgendaItemRepository.saveAll(agendaItems);
    }

    /***
    /***
     *
     * @param file: File ảnh
     * @param type: Thể loại của ảnh là Background or Banner or Standee
     * @param idEvent
     * @return: Đường dẫn ảnh
     */
    @Override
    public String uploadImage(MultipartFile file, int type, String idEvent) {
        String responseUrl = cloudinaryUtils.uploadImage(file, idEvent, type);
        if (type == 0) {
            loggerUtil.sendLog("Đã tải lên ảnh Background cho sự kiện", idEvent);
        } else if (type == 1) {
            loggerUtil.sendLog("Đã tải lên ảnh Banner cho sự kiện", idEvent);
        } else if (type == 2) {
            loggerUtil.sendLog("Đã tải lên ảnh Standee cho sự kiện", idEvent);
        }
        return responseUrl;
    }

    /***
     *
     * @param id: Id ảnh
     * @return
     */
    @Override
    public boolean deleteImage(String id) {
        return cloudinaryUtils.deleteImage(0, id);
    }

    /***
     *
     * @param idEvent
     * @return Danh sách evidence của sự kiện
     */
    @Override
    public List<OedEventEvidenceResponse> getAllEventEvidence(String idEvent) {
        return oedEvidenceRepository.getAllEventEvidence(idEvent);
    }

    /**
     * Gửi mail báo cáo sau sự kiện
     * @param request
     */
    @Override
    public String sendMailEvidence(OedSendMailEvidenceRequest request) {
        Optional<Event> event = eventRepository.findById(request.getIdEvent());
        if (!event.isPresent()) {
            throw new RestApiException(Message.EVENT_NOT_EXIST);
        }
        if (!(event.get().getStatus() != EventStatus.APPROVED || event.get().getStatus() != EventStatus.ORGANIZED)) {
            throw new RestApiException(Message.INCORRECT_STATUS);
        }
        List<MailEvidenceResponce> evidences = new ArrayList<>();
        List<OedEventEvidenceResponse> listEvidence = getAllEventEvidence(event.get().getId());
        for (OedEventEvidenceResponse x: listEvidence) {
            MailEvidenceResponce mailEvidenceResponce = new MailEvidenceResponce(x.getEventId(), x.getName(), x.getPath(), x.getDescription(), x.getEvidenceType());
            evidences.add(mailEvidenceResponce);
        }
        Category category = categoryRepository.findById(event.get().getCategoryId()).get();

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        String dateStart = dateFormat.format(new Date(event.get().getStartTime()));
        String dateEnd = dateFormat.format(new Date(event.get().getStartTime()));
        String[] dateStartString = dateStart.split("\\s");
        String[] dateEndString = dateEnd.split("\\s");

        List<SimpleResponse> simpleResponses = callApiIdentity.handleCallApiGetUserByRoleAndModule(ActorConstants.ACTOR_APPROVER);
        String[] mailApprover = simpleResponses.stream()
                .map(SimpleResponse::getEmail)
                .filter(Objects::nonNull)
                .distinct()
                .toArray(String[]::new);
        MailEvidenceRequest req = new MailEvidenceRequest();
        req.setMails(mailApprover);
        req.setSubject("[Portal Event] Thư gửi báo cáo kết quả sau sự kiện " + event.get().getName());
        req.setEventName(event.get().getName());
        req.setEvidences(evidences);
        req.setEventType(category.getName() + "");
        req.setNumberParticipants(event.get().getNumberParticipants() + "");
        req.setCountParticipant(request.getCountParticipant() + "");
        req.setPercentage(request.getPercentage());
        req.setTime(dateStartString[0] + " - " + dateEndString[0]);
        req.setDate(dateStartString[1]);
        try {
    		emailUtils.sendEmailEvidence(req);
            if (event.get().getStatus() == EventStatus.APPROVED && event.get().getStatus() != EventStatus.ORGANIZED) {
                event.get().setStatus(EventStatus.ORGANIZED);
                eventRepository.save(event.get());
                loggerUtil.sendLog("Đã gửi mail báo cáo kết quả sau sự kiện và cập nhật trạng thái sự kiện thành `Đã tổ chức`.", event.get().getId());
            } else {
                loggerUtil.sendLog("Đã gửi mail báo cáo kết quả sau sự kiện", event.get().getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RestApiException(Message.SEND_MAIL_FAILED);
        }
        return "Gửi thành công";
    }

    /***
     *
     * @param request
     * @return Evidence vừa đc tạo
     */
    @Override
    public Evidence createEvidence(@Valid OedEvidenceRequest request) {
        Optional<Event> event = eventRepository.findById(request.getIdEvent());
        if (!event.isPresent()) {
            throw new RestApiException(Message.EVENT_NOT_EXIST);
        }
        List<OedEventEvidenceResponse> responses = getAllEventEvidence(request.getIdEvent());
        for (OedEventEvidenceResponse x: responses) {
            if (request.getName().equals(x.getName())) {
                throw new RestApiException(Message.EVIDENCE_NAME_ALREADY_EXIST);
            }
        }
        Evidence evidence = new Evidence();
        evidence.setEventId(request.getIdEvent().trim());
        evidence.setName(request.getName().trim());
        evidence.setPath(request.getPath().trim());
        evidence.setDescription(request.getDescription().trim());
        if (request.getEvidenceType() == 0) {
            evidence.setEvidenceType(EvidenceType.LINK);
        } else if (request.getEvidenceType() == 1) {
            evidence.setEvidenceType(EvidenceType.IMAGE);
        }
        loggerUtil.sendLog("Đã thêm mới một Evidence", event.get().getId());
        return oedEvidenceRepository.save(evidence);
    }

    /***
     *
     * @param request
     * @return Evidence vừa đc cập nhật
     */
    @Override
    public Evidence updateEvidence(String id,@Valid OedEvidenceRequest request) {
        Optional<Event> event = eventRepository.findById(request.getIdEvent());
        if (!event.isPresent()) {
            throw new RestApiException(Message.EVENT_NOT_EXIST);
        }
        Optional<Evidence> evidence = oedEvidenceRepository.findById(id);
        if (!evidence.isPresent()) {
            throw new RestApiException(Message.EVIDENCE_NOT_EXIST);
        }
        StringBuilder message = new StringBuilder();
        message.append(CompareUtils.getMessageProperyChange("tên Evidence",
                evidence.get().getName(), request.getName(), " rỗng "));
        message.append(CompareUtils.getMessageProperyChange("mô tả Evidence",
                evidence.get().getDescription(), request.getDescription(), " rỗng "));

        evidence.get().setName(request.getName());
        evidence.get().setDescription(request.getDescription());
        if (request.getEvidenceType() == 0) {
            if (evidence.get().getEvidenceType() != EvidenceType.LINK) {
                message.append("thể loại Evidence từ Ảnh thành đường dẫn; ");
//                loggerUtil.sendLog(message, event.get().getId());
            }
            evidence.get().setEvidenceType(EvidenceType.LINK);
        } else if (request.getEvidenceType() == 1) {
            if (evidence.get().getEvidenceType() != EvidenceType.IMAGE) {
                message.append("thể loại Evidence từ đường dẫn thành ảnh; ");
//                loggerUtil.sendLog(message, event.get().getId());
            }
            evidence.get().setEvidenceType(EvidenceType.IMAGE);
        }
        message.append(CompareUtils.getMessageProperyChange("đường dẫn của Evidence",
                evidence.get().getPath(), request.getPath(), " rỗng "));

        evidence.get().setPath(request.getPath());
        if (!message.equals("")) {
            loggerUtil.sendLog("Đã sửa".concat(message.toString()), event.get().getId());
            return oedEvidenceRepository.save(evidence.get());
        } else {
            return evidence.get();
        }
    }

    /***
     * Xóa evidence
     * @param id
     * @return
     */
    @Override
    public String deleteEvidence(String id) {
        Evidence evidence = oedEvidenceRepository.findById(id).get();
        if (evidence == null) {
            throw new RestApiException(Message.EVIDENCE_NOT_EXIST);
        }
        oedEvidenceRepository.deleteById(id);
        loggerUtil.sendLog("Đã xóa một Evidence", evidence.getEventId());
        return id;
    }

    /***
     * Đóng / Mở đăng ký
     * @param req
     * @return:
     */
    @Override
    public Boolean openOrCloseRegister(@Valid OedRegisterRequest req) {
        if (req.getId() == null) throw new RestApiException(Message.ID_MUST_NOT_EMPTY);
        Event event = eventRepository.findById(req.getId())
                .orElseThrow(() -> new RestApiException(Message.EVENT_NOT_EXIST));
        if (event.getStatus() != EventStatus.APPROVED) {
            throw new RestApiException(Message.INCORRECT_STATUS);
        } else {
            if (req.getIsOpenRegistration()) {
                loggerUtil.sendLog("Đã MỞ đăng ký", event.getId());
            } else {
                loggerUtil.sendLog("Đã ĐÓNG đăng ký", event.getId());
            }
            event.setIsOpenRegistration(req.getIsOpenRegistration());
            eventRepository.save(event);
            return req.getIsOpenRegistration();
        }
    }

    /***
     * Đóng / Mở điểm danh
     * @param req
     * @return:
     */
    @Override
    public Boolean openOrCloseAttendance(@Valid OedAttendanceRequest req) { // Đóng / mở điểm danh
        if (req.getId() == null) throw new RestApiException(Message.ID_MUST_NOT_EMPTY);
        Event event = eventRepository.findById(req.getId())
                .orElseThrow(() -> new RestApiException(Message.EVENT_NOT_EXIST));
        long openTime = event.getStartTime();
        long closeTime = event.getEndTime();
        long currentTime = System.currentTimeMillis();
        if (currentTime >= openTime && currentTime <= closeTime) {
            if (req.getIsAttendance()) {
                loggerUtil.sendLog("Đã MỞ điểm danh", event.getId());
            } else {
                loggerUtil.sendLog("Đã ĐÓNG điểm danh", event.getId());
            }
            event.setIsAttendance(req.getIsAttendance());
            eventRepository.save(event);
            return req.getIsAttendance();
        } else {
            throw new RestApiException(Message.EVENT_HAS_NOT_STARTED);
        }

    }

    /***
     * Lưu evidence
     * @param files: Danh sách file ảnh
     * @param idEvent
     * @return
     */
    @Override
    public List<Evidence> addEvidence(List<MultipartFile> files, String idEvent) {
        List<Evidence> evidences = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            String fileName = file.getOriginalFilename();

            String imgPath = uploadImage(file, 3, idEvent + fileName);
            Evidence evidence = new Evidence();
            evidence.setEventId(idEvent);
            evidence.setPath(imgPath);
            evidence.setName(fileName);
            evidence.setEvidenceType(EvidenceType.IMAGE);
            evidences.add(evidence);
        }
        loggerUtil.sendLog("Đã thêm Evidence", idEvent);
        return oedEvidenceRepository.saveAll(evidences);
    }

    /***
     * Cập nhật số người tham gia sự kiện
     * @param numberParticipant: số người tham gia sự kiện
     * @param idEvent
     * @return
     */
    @Override
    public ResponseObject updateNumberParticipant(int numberParticipant, String idEvent) {
        Event event = eventRepository.findById(idEvent)
                .orElseThrow(() -> new RestApiException(Message.EVENT_NOT_EXIST));
        if (event.getNumberParticipants() == null) {
            loggerUtil.sendLog("Đã cập nhật số người tham gia cao nhất: " + event.getNumberParticipants() + " người", event.getId());
            event.setNumberParticipants((short) numberParticipant);
            eventRepository.save(event);
        } else if (event.getNumberParticipants() != (short) numberParticipant) {
            loggerUtil.sendLog("Đã cập nhật số người tham gia cao nhất: " + event.getNumberParticipants() + " người", event.getId());
        	event.setNumberParticipants((short) numberParticipant);
            eventRepository.save(event);
        } else {
            throw new RestApiException(Message.NOTHING_TO_SAVE);
        }
        return new ResponseObject(event);
    }

    /**
     * Gửi yêu cầu phê duyệt hàng kỳ
     * @param req
     * @return
     */
    @Override
    public String approvePeriodicEvents(@Valid OedWaitApprovalPeriodicallyRequest req) {
        if (req.getId() == null) throw new RestApiException(Message.ID_MUST_NOT_EMPTY);
        Event event = eventRepository.findById(req.getId())
                .orElseThrow(() -> new RestApiException(Message.EVENT_NOT_EXIST));
        if (event.getStatus() != EventStatus.ORGANIZED) {
            throw new RestApiException(Message.INCORRECT_STATUS);
        } else {
            loggerUtil.sendLog("Đã gửi yêu cầu phê duyệt sự kiện hàng kỳ sự kiện `" + event.getName() + "` thành sự kiện hàng kỳ ", event.getId());
            event.setIsWaitApprovalPeriodically(ApprovalPeriodicallyStatus.DA_GUI_YEU_CAU);
            eventRepository.save(event);
            return "Đã gửi yêu cầu phê duyệt hàng kỳ";
        }
    }

    /**
     * Tái tổ chức một sự kiện
     * @param idEvent
     * @param values: List giá trị Integer value
     * @return Event vừa đc tạo
     */
    @Override
    public Event eventReorganization(String idEvent, List<Integer> values) {
        Event event = eventRepository.findById(idEvent)
                .orElseThrow(() -> new RestApiException(Message.EVENT_NOT_EXIST));
        List<OedMajorResponse> majorResponses = getMajorByIdEvent(event.getId());
        List<OedEventOrganizerCustom> organizers =  getAllOrganizerByIdEvent(event.getId());
        List<OedObjectResponse> objects = getObjectByIdEvent(event.getId());
        List<OedAgendaItemCustom> agendas = getAllAgendaItem(event.getId());

        Event e = new Event();
        if (values.contains(1)) {
            e.setName(event.getName());
        }
        if (values.contains(2)){
            e.setEventType(event.getEventType());
        }
        if (values.contains(6)){
            e.setDescription(event.getDescription());
        }
        if (values.contains(4)){
            e.setCategoryId(event.getCategoryId());
        }
        e.setStatus(EventStatus.SCHEDULED_HELD);
        e = eventRepository.save(e);
        if (values.contains(3)){
            for (OedMajorResponse x: majorResponses) {
                EventMajor eventMajor = new EventMajor();
                eventMajor.setEventId(e.getId());
                eventMajor.setMajorId(x.getId());
                eventMajorRepository.save(eventMajor);
            }
        }
        if (values.contains(5)){
            for (OedObjectResponse x: objects) {
                EventObject eventObject = new EventObject();
                eventObject.setEventId(e.getId());
                eventObject.setObjectId(x.getId());
                eventObjectRepository.save(eventObject);
            }
        }
        if (values.contains(7)){
            for (OedEventOrganizerCustom x: organizers) {
                EventOrganizer organizer = new EventOrganizer();
                organizer.setEventId(e.getId());
                organizer.setOrganizerId(x.getOrganizerId());
                if (x.getEventRole() == 0) {
                    organizer.setEventRole(EventRole.HOST);
                } else if (x.getEventRole() == 1) {
                    organizer.setEventRole(EventRole.CO_HOST);
                }
                odeEventOrganizerRepository.save(organizer);
            }
        } else {
            EventOrganizer organizer = new EventOrganizer();
            organizer.setEventId(e.getId());
            organizer.setOrganizerId(portalEventsSession.getCurrentIdUser());
            organizer.setEventRole(EventRole.HOST);
            odeEventOrganizerRepository.save(organizer);
        }
        if (values.contains(8)){
            for (OedAgendaItemCustom x: agendas) {
                AgendaItem agendaItem = new AgendaItem();
                agendaItem.setEventId(e.getId());
                agendaItem.setDescription(x.getDescription());
                agendaItem.setStartTime(x.getStartTime());
                agendaItem.setEndTime(x.getEndTime());
                agendaItem.setOrganizerId(x.getOrganizerId());
                odeAgendaItemRepository.save(agendaItem);
            }
        }
        loggerUtil.sendLog("Đã tái tổ chức sự kiện" + event.getName(), event.getId());
        return e;
    }

    /**
     *
     * @param idEvent
     * @return Gửi mail tài nguyên sau khi tổ chức sự kiện cho sinh viên
     */
    @Override
    public String sendMailResource(String idEvent) {
        Event event = eventRepository.findById(idEvent)
                .orElseThrow(() -> new RestApiException(Message.EVENT_NOT_EXIST));
        if (event.getStatus() != EventStatus.ORGANIZED) {
            throw new RestApiException(Message.INCORRECT_STATUS);
        }
        List<OalAttendanceResponse> attendanceResponses = participantRepository.getAllAttendanceByEventId(event.getId());
        String[] mailAttendance = attendanceResponses.stream()
                .map(OalAttendanceResponse::getEmail)
                .filter(Objects::nonNull)
                .distinct()
                .toArray(String[]::new);

        List<OedResourceResponse> responses = getAllResource(event.getId());
        List<MailResourceResponce> resourceResponces = new ArrayList<>();
        for (OedResourceResponse x: responses) {
            MailResourceResponce mailResourceResponce = new MailResourceResponce(event.getId(), x.getName(), x.getPath(), x.getDescription());
            resourceResponces.add(mailResourceResponce);
        }
        Category category = categoryRepository.findById(event.getCategoryId()).get();
        MailResourceRequest req = new MailResourceRequest();
        req.setMails(mailAttendance);
        req.setSubject("[Portal Event] Tài liệu sau buổi " + category.getName() + " '" + event.getName() +"'");
        req.setEventName(event.getName());
        req.setResources(resourceResponces);
        req.setCategory(category.getName());
        emailUtils.sendEmailResource(req);
        loggerUtil.sendLog("Đã gửi mail tài nguyên sau sự kiện cho sinh viên", event.getId());
        return "Gửi mail thành công";
    }

    /**
     * Lưu danh sách Thời gian đặt trước để gửi mail
     * @param requests
     * @return
     */
    @Override
    public List<InvitationTime> saveListInvitationTime(List<OedInvitationTimeRequest> requests) {
        if (requests.isEmpty()) {
            throw new RestApiException(Message.NOTHING_TO_SAVE);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm dd/MM/yyyy");
        StringBuilder messageLog = new StringBuilder("Đã lưu thông tin Thời gian đặt trước vào: ");
        Optional<Event> eventFind = eventRepository.findById(requests.get(0).getEventId());
        if (!eventFind.isPresent()) {
            throw new RestApiException(Message.EVENT_NOT_EXIST);
        }
        List<InvitationTime> lstInvitationTime = invitationTimeRepository.getAllByEventId(eventFind.get().getId());
        Map<String, InvitationTime> mapInvitationTime = new HashMap<>();
        lstInvitationTime.forEach(item -> {
            mapInvitationTime.put(item.getId(), item);
        });
        List<InvitationTime> invitationTimes = new ArrayList<>();
        Scheduler scheduler = null;
        try {
            scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RestApiException(Message.SCHEDULE_FAILURE);
        }
        for (OedInvitationTimeRequest item : requests) {
            if (item.getTime() > eventFind.get().getStartTime()) {
                throw new RestApiException(Message.EXCEEDING_START_TIME);
            }
            if (item.getTime() < new Date().getTime()) {
                throw new RestApiException(Message.SCHEDULE_FEATURE_ONLY);
            }
            InvitationTime invitationTime = new InvitationTime();
            invitationTime.setId(item.getId());
            if (!lstInvitationTime.isEmpty() && !item.getId().isBlank()) {
                if (mapInvitationTime.get(item.getId()) == null) {
                    throw new RestApiException(Message.INVITATION_TIME_NOT_EXIST);
                } else if (!mapInvitationTime.get(item.getId()).getStatus()) {
                    //Nếu không có thay đổi thì tiếp tục vòng lặp
                    System.out.println("------------------" + mapInvitationTime.get(item.getId()).getTime());
                    System.out.println("------------------" + item.getTime());
                    System.out.println(!mapInvitationTime.get(item.getId()).getTime().equals(item.getTime()));
                    if (!mapInvitationTime.get(item.getId()).getTime().equals(item.getTime())) {
                        InvitationTime invitationTimeInDB = mapInvitationTime.get(item.getId());
                        try {
                            if (scheduler.checkExists(new JobKey(invitationTimeInDB.getJobId(), "sendInvitationMail"))) {
                                scheduler.deleteJob(new JobKey(invitationTimeInDB.getJobId(), "sendInvitationMail"));
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            throw new RestApiException(Message.SCHEDULE_FAILURE);
                        }
                    } else {
                        continue;
                    }
                } else {
                    throw new RestApiException(Message.INVITATION_HAS_BEEN_SENT + " tại " + sdf.format(invitationTime.getTime()) + ". Không thể thay đổi");
                }
            }
            invitationTime.setEventId(item.getEventId());
            invitationTime.setTime(item.getTime());
            invitationTime.setJobId(UUID.randomUUID().toString());
            invitationTime.setTriggerId(UUID.randomUUID().toString());
            invitationTime.setStatus(false);
            invitationTimes.add(invitationTime);
            messageLog.append(sdf.format(new Date(item.getTime()))).append("; ");
        }
        List<SimpleResponse> listSimpleReponse = callApiIdentity.handleCallApiGetUserByRoleAndModule(ActorConstants.ACTOR_ORGANIZER);
        invitationTimes = invitationTimeRepository.saveAll(invitationTimes);
        try {
            for (InvitationTime invitationTime :invitationTimes) {
                // Tao job
                if (invitationTime.getStatus()) {
                    continue;
                }
                JobDetail job = JobBuilder.newJob(SendInvitationJob.class)
                        .withIdentity(invitationTime.getJobId(), "sendInvitationMail")
                        .build();
                job.getJobDataMap().put("eventId", eventFind.get().getId());
                job.getJobDataMap().put("listOganizer", listSimpleReponse);
                job.getJobDataMap().put("eventRepository", eventRepository);
                job.getJobDataMap().put("participantRepository", participantRepository);
                job.getJobDataMap().put("invitationTime", invitationTime);
                job.getJobDataMap().put("invitationTimeRepository", invitationTimeRepository);
                // Tạo trigger và lập lịch
                Trigger trigger = TriggerBuilder.newTrigger()
                        .withIdentity(invitationTime.getTriggerId(), "sendInvitationMail")
                        .startAt(new Date(invitationTime.getTime())) // Thiết lập thời điểm gửi mail
                        .build();

                // Lập lịch công việc với trigger
                scheduler.scheduleJob(job, trigger);
            }
            for (String groupName : scheduler.getJobGroupNames()) {
                for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                    System.out.println(jobKey.getName());
                }
            }
        } catch (Exception e) {
            throw new RestApiException(Message.SCHEDULE_FAILURE);
        }
        return invitationTimes;
    }

    /**
     *
     * @param idEvent
     * @return Danh sách invitationTime
     */
    @Override
    public List<OedInvitationTimeResponse> getAllInvitationTime(String idEvent) {
        return invitationTimeRepository.getAllInvitationTime(idEvent);
    }

    /**
     * Xóa InvitationTime
     * @param id
     * @return
     */
    @Override
    public String deleteInvitationTime(String id) {
        InvitationTime invitationTime = invitationTimeRepository.findById(id).get();
        if (invitationTime == null) {
            throw new RestApiException(Message.INVITATION_TIME_NOT_EXIST);
        }
        if (invitationTime.getStatus()) {
            throw new RestApiException(Message.EMAIL_HAS_BEEN_SEND);
        }
        try {
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            if (scheduler.checkExists(new JobKey(invitationTime.getJobId(), "sendInvitationMail"))) {
                // Xóa job gửi mail đã tạo trước đó
                scheduler.deleteJob(new JobKey(invitationTime.getJobId(), "sendInvitationMail"));
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm dd/MM/yyyy");
        loggerUtil.sendLog("Đã xóa một lịch gửi mail thư mời sự kiện vào lúc " + sdf.format(new Date(invitationTime.getTime())), invitationTime.getEventId());
        invitationTimeRepository.deleteById(id);
        return id;
    }

    @Override
    public void sendEmailUpdateEventWhenApproved(OedMailRequestApprovalEvent request) {
        String htmlBody = MailConstant.HEADER.replace("{title}", "");
        String eventList = "";
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.toString());
            ClassPathResource resource = new ClassPathResource(MailConstant.LOGO_PATH);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setBcc(request.getMails());
            mimeMessageHelper.setSubject(request.getSubject());
            eventList += """
                        <ul type="circle">
                    """;
            if (request.getLecturers().size() != 0) {
                eventList += """
                        <li>GV tham gia:
                            <ul>
                        """;
                for (OedEventOrganizerCustom lecturer : request.getLecturers()) {
                    eventList += "<li>" + lecturer.getName() + " - " + lecturer.getUsername() + "</li>";
                }
                eventList += """
                            </ul>
                        </li>
                        """;
            }
            if (request.getContent().length != 0) {
                eventList += """
                    <li>Nội dung:
                        <ul>
                    """;
                for (String content : request.getContent()) {
                    eventList += "<li>" + content + "</li>";
                }
                eventList += """
                         </ul>
                     </li>
                     """;
            }
            if (request.getCategory() != null || !request.getCategory().isBlank()) {
                eventList += "<li>Thể loại: " + request.getCategory() + "</li>";
            }
            if (request.getTypeEvent() != null) {
                eventList += "<li>Sự kiện cho: " + request.getTypeEvent() + "</li>";
            }
            if (request.getDate() != null || !request.getDate().isBlank()) {
                eventList += "<li>Thời gian: " + request.getDate() + "</li>";
            }
            if (request.getExpectedParticipants() != null || !request.getExpectedParticipants().isBlank()) {
                eventList += "<li>Dự kiến số người tham gia: " + request.getExpectedParticipants() + "</li>";
            }
            if (request.getObjects().size() != 0) {
                eventList += """
                    <li>Đối tượng:
                        <ul>
                    """;
                for (OedObjectResponse object : request.getObjects()) {
                    eventList += "<li>" + object.getName() + "</li>";
                }
                eventList += """
                            </ul>
                       </li>
                     """;
            }
            if (request.getLocations().size() != 0) {
                eventList += """
                    <li>Địa điểm:
                        <ul>
                    """;
                for (OedEventLocationResponse location : request.getLocations()) {
                    if (location.getFormality() == 0) {
                        eventList += "<li> Online - "+ location.getName() + ": <a href='" + location.getPath() + "'>Tại đây</a></li>";
                    } else {
                        eventList += "<li> Offline -"+ location.getName() + ": " + location.getPath() + "</li>";
                    }
                }
                eventList += """
                            </ul>
                       </li>
                     """;
            }
            if (request.getResources().size() != 0) {
                eventList += """
                        <li>Tài nguyên:
                            <ul>
                        """;
                for (OedResourceResponse res : request.getResources()) {
                    eventList += "<li>" + res.getName() + ": <a href='" + res.getPath() + "'>Tại đây</a></li>";
                }
                eventList += """
                                </ul>
                           </li>
                         """;
            }
            if (!request.getLinkBackground().isBlank() || request.getLinkBackground() != null) {
                eventList += "<li>Link Background: <a href='" + request.getLinkBackground() + "'>Tại đây</a></li>";
            }
            if (!request.getLinkBanner().isBlank() || request.getLinkBanner() != null) {
                eventList += "<li>Link Banner: <a href='" + request.getLinkBanner() + "'>Tại đây</a></li>";
            }
            if (request.getAgendas().size() != 0) {
                eventList += """
                        <li>Agenda sự kiện:
                            <table style="margin-top: 10px; margin-bottom: 10px;"><tr>
                                    <th>STT</th>
                                    <th>Thời gian bắt đầu</th>
                                    <th>Thời gian kết thúc</th>
                                    <th>Mô tả</th>
                                    <th>Người phụ trách</th>
                                </tr>
                """;
                for (OedAgendaItemCustom agenda : request.getAgendas()) {
                    eventList += "<tr>";
                    eventList += "<td>" + (agenda.getIndex() + 1) + "</td>";
                    eventList += "<td>" + agenda.getStartTime() + "</td>";
                    eventList += "<td>" + agenda.getEndTime() + "</td>";
                    eventList += "<td>" + agenda.getDescription() + "</td>";
                    eventList += "<td>" + agenda.getUsername() + "</td>";
                    eventList += "</tr>";
                }
                eventList += """
                            </table>
                        </li>
                        """;
            }
            eventList += """
                            </ul>
                        """;

            mimeMessageHelper.setText(htmlBody + MailConstant.CONTENT_APPROVAL_EVENT_MAIL
                    .replace("{eventName}", request.getEventName())
                    .replace("{event}", eventList)+ MailConstant.FOOTER, true);
            mimeMessageHelper.addInline("logoImage", resource);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Boolean sendConversionRequest(OedSendConversionRequest request) {
        Event event = eventRepository.findById(request.getEventId())
                .orElseThrow(() -> new RestApiException(Message.EVENT_NOT_EXIST));
        if (event.getStatus() != EventStatus.ORGANIZED) {
            throw new RestApiException(Message.INCORRECT_STATUS);
        }
        Boolean checkSend = callPortalHoney.sendConversionHoney(request.getEventId(), request.getNumberHoney(), request.getHoneyCategoryId());
        if (!checkSend) {
            throw new RestApiException(Message.SEND_CONVERSION_REQUEST_FAIL);
        }
        return true;
    }

    @Override
    public List<HoneyCategoryResponse> getHoneyCategory() {
        List<HoneyCategoryResponse> honeyCategories = callPortalHoney.getHoneyCategory();
        return honeyCategories;
    }

    /***
     * Xóa Địa điểm của sự kiện
     * @param id
     * @return
     */
    @Override
    public String deleteEventLocationByIdEvent(String id) {
        eventLocationRepository.deleteEventLocationByIdEvent(id);
        loggerUtil.sendLog("Đã xóa một địa điểm của sự kiện", "");
        return id;
    }

    /***
     *
     * @param request
     * @return Địa điểm vừa được cập nhật
     */
    @Override
    public EventLocation updateEventLocation(@Valid OedUpdateEventLocationRequest request) {
        StringBuilder message = new StringBuilder();
        Optional<EventLocation> eventLocation = eventLocationRepository.findById(request.getId());
        if (eventLocation.isEmpty()) {
            throw new RestApiException(Message.EVENT_LOCATION_NOT_EXIT);
        }
        Boolean checkRole = checkOrganizerRole(eventLocation.get().getEventId());
        if (!checkRole) {
            throw new RestApiException(Message.YOU_HAVE_NO_RIGHTS);// Check quền chỉnh sửa
        }
        message.append(CompareUtils.getMessageProperyChange("tên của địa điểm",
                eventLocation.get().getName(), request.getName(), " rỗng "));
        message.append(CompareUtils.getMessageProperyChange("đường dẫn của địa điểm",
                eventLocation.get().getPath(), request.getPath(), " rỗng "));
        message.append(CompareUtils.getMessageProperyChange("trạng thái tổ chức của địa điểm",
                eventLocation.get().getFormality(), request.getFormality(), " rỗng "));

        eventLocation.get().setName(request.getName());
        eventLocation.get().setPath(request.getPath());
        eventLocation.get().setFormality(request.getFormality());
        if (!message.equals("")) {
            loggerUtil.sendLog("Đã sửa".concat(message.toString()), eventLocation.get().getEventId());
            eventLocationRepository.save(eventLocation.get());
        }
        return eventLocation.get();
    }

    /***
     *
     * @param request
     * @return Địa điểm vừa được thêm mới
     */
    @Override
    public EventLocation addEventLocation(@Valid OedAddEventLocationRequest request) {
        Optional<Event> event = eventRepository.findById(request.getIdEvent());
        if (!event.isPresent()) {
            throw new RestApiException(Message.EVENT_NOT_EXIST);
        }
        Boolean checkRole = checkOrganizerRole(request.getIdEvent());
        if (!checkRole) {
            throw new RestApiException(Message.YOU_HAVE_NO_RIGHTS);// Check quền chỉnh sửa
        }
        List<OedEventLocationResponse> locations = getEventLocationByIdEvent(event.get().getId());
        for (OedEventLocationResponse x: locations) {
            if (request.getName().equals(x.getName())) {
                throw new RestApiException(Message.LOCATION_NAME_ALREADY_EXIST);
            }
        }
        EventLocation eventLocation = new EventLocation();
        eventLocation.setName(request.getName());
        eventLocation.setPath(request.getPath());
        eventLocation.setFormality(request.getFormality());
        eventLocation.setEventId(event.get().getId());
        loggerUtil.sendLog("Đã thêm một địa điểm trong sự kiện", event.get().getId());
        return eventLocationRepository.save(eventLocation);
    }

    /***
     *
     * @return Danh sách Object
     */
    @Override
    public List<OedObjectResponse> getAllObject() {
        return objectRepository.getAll();
    }


}
