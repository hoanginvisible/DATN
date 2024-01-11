package com.tool;

import com.portalevent.entity.*;
import com.portalevent.entity.Object;
import com.portalevent.infrastructure.constant.*;
import com.portalevent.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Date;

/**
 * @author SonPT
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.portalevent.repository")
public class DBGenerator implements CommandLineRunner {

	@Autowired
    private AgendaItemRepository agendaItemRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private EventLocationRepository eventLocationRepository;
    @Autowired
    private EventMajorRepository eventMajorRepository;
    @Autowired
    private EventObjectRepository eventObjectRepository;
    @Autowired
    private EventOrganizerRepository eventOrganizerRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private EvidenceRepository evidenceRepository;
    @Autowired
    private MajorRepository majorRepository;
    @Autowired
    private ObjectRepository objectRepository;
    @Autowired
    private OrganizerMajorRepository organizerMajorRepository;
    @Autowired
    private ParticipantRepository participantRepository;
    @Autowired
    private PeriodicEventMajorRepository periodicEventMajorRepository;
    @Autowired
    private PeriodicEventObjectRepository periodicEventObjectRepository;
    @Autowired
    private PeriodicEventRepository periodicEventRepository;
    @Autowired
    private ResourceRepository resourceRepository;
    @Autowired
    private SemesterRepository semesterRepository;
    @Autowired
    private SystemOptionRepository systemOptionRepository;


    @Override
    public void run(String... args) throws Exception {

        //Bảng category
        Category seminar = new Category();
        seminar.setName("Seminar");
        seminar.setId(categoryRepository.save(seminar).getId());
        Category workshop = new Category();
        workshop.setName("Workshop");
        workshop.setId(categoryRepository.save(workshop).getId());
        Category talkshow = new Category();
        talkshow.setName("Talkshow");
        talkshow.setId(categoryRepository.save(talkshow).getId());

        //Bảng Semester
        Semester fall2023 = new Semester();
        fall2023.setName("Fall 2023");
        fall2023.setStartTime(1693501200000L);
        fall2023.setEndTime(1703955600000L);
        fall2023.setStartTimeFirstBlock(1693587600000L);
        fall2023.setEndTimeFirstBlock(1698598800000L);
        fall2023.setStartTimeSecondBlock(1698858000000L);
        fall2023.setEndTimeSecondBlock(1703869200000L);
        fall2023.setId(semesterRepository.save(fall2023).getId());

        Semester spring2024 = new Semester();
        spring2024.setName("Spring 2024");
        spring2024.setStartTime(1641049200000L);
        spring2024.setEndTime(1648731600000L);
        spring2024.setStartTimeFirstBlock(1641049200000L);
        spring2024.setEndTimeFirstBlock(1646041200000L);
        spring2024.setStartTimeSecondBlock(1646149200000L);
        spring2024.setEndTimeSecondBlock(1648731600000L);
        spring2024.setId(semesterRepository.save(spring2024).getId());

        //Bảng Object
        Object ky1 = new Object();
        ky1.setName("Kỳ 1");
        ky1.setId(objectRepository.save(ky1).getId());
        Object ky2 = new Object();
        ky2.setName("Kỳ 2");
        ky2.setId(objectRepository.save(ky2).getId());
        Object ky3 = new Object();
        ky3.setName("Kỳ 3");
        ky3.setId(objectRepository.save(ky3).getId());
        Object ky4 = new Object();
        ky4.setName("Kỳ 4");
        ky4.setId(objectRepository.save(ky4).getId());
        Object ky5 = new Object();
        ky5.setName("Kỳ 5");
        ky5.setId(objectRepository.save(ky5).getId());
        Object ky6 = new Object();
        ky6.setName("Kỳ 6");
        ky6.setId(objectRepository.save(ky6).getId());
        Object ky7 = new Object();
        ky7.setName("Kỳ 7");
        ky7.setId(objectRepository.save(ky7).getId());

        // Bảng Major (chuyên ngành)
		Major udpm = new Major();
        udpm.setCode("UDPM");
        udpm.setMainMajorId(null);
        udpm.setName("Ứng dụng phần mềm");
        udpm.setMailOfManager("truongson.dev@gmail.com");
        udpm.setId(majorRepository.save(udpm).getId());

//        Major ptpm = new Major();
//        ptpm.setCode("PTPM");
//        ptpm.setMainMajorId(null);
//        ptpm.setName("Phát triển phần mềm");
//        ptpm.setMailOfManager("truongson.dev@gmail.com");
//        ptpm.setId(majorRepository.save(ptpm).getId());
//
//        Major xldl = new Major();
//        xldl.setCode("XLDL");
//        xldl.setMainMajorId(null);
//        xldl.setName("Xử lý dữ liệu");
//        xldl.setMailOfManager("truongson.dev@gmail.com");
//        xldl.setId(majorRepository.save(xldl).getId());

        Major javaMajor = new Major();
        javaMajor.setCode("PTPM_Java");
        javaMajor.setMainMajorId(udpm.getId());
        javaMajor.setName("Chuyên ngành hẹp java");
        javaMajor.setMailOfManager("truongson.dev@gmail.com");
        javaMajor.setId(majorRepository.save(javaMajor).getId());

        Major cSharpMajor = new Major();
        cSharpMajor.setCode("PTPM_C");
        cSharpMajor.setMainMajorId(udpm.getId());
        cSharpMajor.setName("Phát triển phần mềm C Sharp");
        cSharpMajor.setMailOfManager("truongson.dev@gmail.com");
        cSharpMajor.setId(majorRepository.save(cSharpMajor).getId());

        //Bảng Event
        Event event1 = new Event();
        event1.setApproverId("2EB57F85-2D45-44CB-C621-08DBFD8FC28C".toLowerCase());
        event1.setCategoryId(seminar.getId());
        event1.setSemesterId(fall2023.getId());
        event1.setName("Seminar Coding Convention");
//        event1.setStartTime(new Date().getTime() + 1728000000L);
        event1.setStartTime(1703487600000L);
//        event1.setEndTime(event1.getStartTime() + 7200000L);
        event1.setEndTime(1703494800000L);
        event1.setBlockNumber(false);
        event1.setExpectedParticipants((short) 300);
        event1.setNumberParticipants((short) 250);
        event1.setIsAttendance(false);
        event1.setIsOpenRegistration(false);
        event1.setEventType(EventType.STUDENT_EVENT);
        event1.setReason(null);
        event1.setStatus(EventStatus.ORGANIZED);
        event1.setIsHireLocation(false);
        event1.setIsHireDesignBanner(false);
        event1.setIsHireDesignBg(false);
        event1.setIsHireDesignStandee(false);
        event1.setIsHireDesignBanner(false);
        event1.setBackgroundPath("https://res.cloudinary.com/dowwnfkhb/image/upload/v1693827217/d63151ff-9578-4186-ba48-83bcb9d76620_BG.png");
        event1.setBannerPath("https://res.cloudinary.com/dowwnfkhb/image/upload/v1693827077/d63151ff-9578-4186-ba48-83bcb9d76620_BN.png");
        event1.setStandeePath(null);
        event1.setDescription("Một sự kiện không thể bỏ qua của các bạn JAVA 4");
        event1.setId(eventRepository.save(event1).getId());

        Event event2 = new Event();
        event2.setApproverId(null);
        event2.setCategoryId(workshop.getId());
        event2.setSemesterId(fall2023.getId());
        event2.setName("Workshop Chinh phục cấu trúc dữ liệu và giải thuật");
//        event2.setStartTime(new Date().getTime() + 1296000000L);
//        event2.setEndTime(event2.getStartTime() + 7200000L);
        event2.setStartTime(1703581200000L);
        event2.setEndTime(1703588400000L);
        event2.setBlockNumber(false);
        event2.setExpectedParticipants((short) 400);
        event2.setIsAttendance(false);
        event2.setIsOpenRegistration(false);
        event2.setEventType(EventType.STUDENT_EVENT);
        event2.setReason(null);
        event2.setStatus(EventStatus.SCHEDULED_HELD);
        event2.setIsHireLocation(false);
        event2.setIsHireDesignBanner(false);
        event2.setIsHireDesignBg(false);
        event2.setIsHireDesignStandee(false);
        event2.setBackgroundPath("https://res.cloudinary.com/dowwnfkhb/image/upload/v1693827639/f05c6349-f66b-4b80-88f4-51ce9ac91185_BG.png");
        event2.setBannerPath("https://res.cloudinary.com/dowwnfkhb/image/upload/v1693827634/f05c6349-f66b-4b80-88f4-51ce9ac91185_BN.png");
        event2.setStandeePath(null);
        event2.setDescription("Code sạch là gì. Chúng ta hãy cũng tìm hiểu về nó");
        event2.setId(eventRepository.save(event2).getId());

        Event event3 = new Event();
        event3.setApproverId("2EB57F85-2D45-44CB-C621-08DBFD8FC28C".toLowerCase());
        event3.setCategoryId(seminar.getId());
        event3.setSemesterId(fall2023.getId());
        event3.setName("Seminar EzModeling - Khách hàng trong Phần mềm Bán hàng");
        event3.setStartTime(1703044800000L);
        event3.setEndTime(1703048400000L);
        event3.setBlockNumber(false);
        event3.setExpectedParticipants((short) 500);
        event3.setIsAttendance(false);
        event3.setIsOpenRegistration(false);
        event3.setEventType(EventType.STUDENT_EVENT);
        event3.setReason(null);
        event3.setStatus(EventStatus.APPROVED);
        event3.setIsHireLocation(false);
        event3.setIsHireDesignBanner(false);
        event3.setIsHireDesignBg(false);
        event3.setIsHireDesignStandee(false);
        event3.setBackgroundPath("https://res.cloudinary.com/dowwnfkhb/image/upload/v1693827639/f05c6349-f66b-4b80-88f4-51ce9ac91185_BG.png");
        event3.setBannerPath("https://res.cloudinary.com/dowwnfkhb/image/upload/v1693827634/f05c6349-f66b-4b80-88f4-51ce9ac91185_BN.png");
        event3.setStandeePath(null);
        event3.setDescription("Tiếp nối chuỗi seri về EZMODELING  thì tiếp theo đây chúng ta sẽ tiếp tục đến với “GIẢM GIÁ TRONG PHẦN MỀM BÁN HÀNG”  -  một trong các sự kiện ĐÁNG MONG CHỜ NHẤT dành cho các bạn SV");
        event3.setId(eventRepository.save(event3).getId());

        Event event4 = new Event();
        event4.setApproverId("2EB57F85-2D45-44CB-C621-08DBFD8FC28C".toLowerCase());
        event4.setCategoryId(workshop.getId());
        event4.setSemesterId(fall2023.getId());
        event4.setName("Workshop Nghiệp vụ dự án");
        event4.setStartTime(1697880000000L);
        event4.setEndTime(1697887200000L);
        event4.setBlockNumber(false);
        event4.setExpectedParticipants((short) 500);
        event4.setIsAttendance(false);
        event4.setIsOpenRegistration(false);
        event4.setEventType(EventType.STUDENT_EVENT);
        event4.setReason(null);
        event4.setStatus(EventStatus.ORGANIZED);
        event4.setIsHireLocation(false);
        event4.setIsHireDesignBanner(false);
        event4.setIsHireDesignBg(false);
        event4.setIsHireDesignStandee(false);
        event4.setBackgroundPath("https://res.cloudinary.com/dowwnfkhb/image/upload/v1693827639/f05c6349-f66b-4b80-88f4-51ce9ac91185_BG.png");
        event4.setBannerPath("https://res.cloudinary.com/dowwnfkhb/image/upload/v1693827634/f05c6349-f66b-4b80-88f4-51ce9ac91185_BN.png");
        event4.setStandeePath(null);
        event4.setDescription("Sự Kiện GIẢI ĐÁP NGHIỆP VỤ DỰ ÁN - ĐA CHIỀU NGHIỆP VỤ");
        event4.setId(eventRepository.save(event4).getId());

        Event event5 = new Event();
        event5.setApproverId("2EB57F85-2D45-44CB-C621-08DBFD8FC28C".toLowerCase());
        event5.setCategoryId(seminar.getId());
        event5.setSemesterId(fall2023.getId());
        event5.setName("Seminar Clean Code trong - Đừng biến code thành rác");
        event5.setStartTime(1697709600000L);
        event5.setEndTime(1697716800000L);
        event5.setBlockNumber(true);
        event5.setExpectedParticipants((short) 500);
        event5.setIsAttendance(false);
        event5.setIsOpenRegistration(false);
        event5.setEventType(EventType.STUDENT_EVENT);
        event5.setReason("Chỉnh lại kế hoạch");
        event5.setStatus(EventStatus.ORGANIZED);
        event5.setIsHireLocation(false);
        event5.setIsHireDesignBanner(false);
        event5.setIsHireDesignBg(false);
        event5.setIsHireDesignStandee(false);
        event5.setBackgroundPath("https://res.cloudinary.com/dowwnfkhb/image/upload/v1693827639/f05c6349-f66b-4b80-88f4-51ce9ac91185_BG.png");
        event5.setBannerPath("https://res.cloudinary.com/dowwnfkhb/image/upload/v1693827634/f05c6349-f66b-4b80-88f4-51ce9ac91185_BN.png");
        event5.setStandeePath(null);
        event5.setDescription("Sự Kiện GIẢI ĐÁP NGHIỆP VỤ DỰ ÁN - ĐA CHIỀU NGHIỆP VỤ");
        event5.setId(eventRepository.save(event5).getId());

        Event event6 = new Event();
        event6.setApproverId("2EB57F85-2D45-44CB-C621-08DBFD8FC28C".toLowerCase());
        event6.setCategoryId(workshop.getId());
        event6.setSemesterId(fall2023.getId());
        event6.setName("Workshop Chinh phục cấu trúc dữ liệu và giải thuật");
        event6.setStartTime(1697094000000L);
        event6.setEndTime(1697101200000L);
        event6.setBlockNumber(true);
        event6.setExpectedParticipants((short) 500);
        event6.setIsAttendance(false);
        event6.setIsOpenRegistration(false);
        event6.setEventType(EventType.STUDENT_EVENT);
        event6.setReason(null);
        event6.setStatus(EventStatus.ORGANIZED);
        event6.setIsHireLocation(false);
        event6.setIsHireDesignBanner(false);
        event6.setIsHireDesignBg(false);
        event6.setIsHireDesignStandee(false);
        event6.setBackgroundPath("https://res.cloudinary.com/dowwnfkhb/image/upload/v1693827639/f05c6349-f66b-4b80-88f4-51ce9ac91185_BG.png");
        event6.setBannerPath("https://res.cloudinary.com/dowwnfkhb/image/upload/v1693827634/f05c6349-f66b-4b80-88f4-51ce9ac91185_BN.png");
        event6.setStandeePath(null);
        event6.setDescription("Sự Kiện GIẢI ĐÁP NGHIỆP VỤ DỰ ÁN - ĐA CHIỀU NGHIỆP VỤ");
        event6.setId(eventRepository.save(event6).getId());

        Event event7 = new Event();
        event7.setApproverId("2EB57F85-2D45-44CB-C621-08DBFD8FC28C".toLowerCase());
        event7.setCategoryId(seminar.getId());
        event7.setSemesterId(fall2023.getId());
        event7.setName("Seminar Phân biệt JDBC/Hibernate/JPA");
        event7.setStartTime(1697274000000L);
        event7.setEndTime(1697281200000L);
        event7.setBlockNumber(true);
        event7.setExpectedParticipants((short) 500);
        event7.setIsAttendance(false);
        event7.setIsOpenRegistration(false);
        event7.setEventType(EventType.STUDENT_EVENT);
        event7.setReason(null);
        event7.setStatus(EventStatus.ORGANIZED);
        event7.setIsHireLocation(false);
        event7.setIsHireDesignBanner(false);
        event7.setIsHireDesignBg(false);
        event7.setIsHireDesignStandee(false);
        event7.setBackgroundPath("https://res.cloudinary.com/dowwnfkhb/image/upload/v1693827639/f05c6349-f66b-4b80-88f4-51ce9ac91185_BG.png");
        event7.setBannerPath("https://res.cloudinary.com/dowwnfkhb/image/upload/v1693827634/f05c6349-f66b-4b80-88f4-51ce9ac91185_BN.png");
        event7.setStandeePath(null);
        event7.setDescription("Sự Kiện GIẢI ĐÁP NGHIỆP VỤ DỰ ÁN - ĐA CHIỀU NGHIỆP VỤ");
        event7.setId(eventRepository.save(event7).getId());

        Event event8 = new Event();
        event8.setApproverId("2EB57F85-2D45-44CB-C621-08DBFD8FC28C".toLowerCase());
        event8.setCategoryId(seminar.getId());
        event8.setSemesterId(fall2023.getId());
        event8.setName("Seminar EZModeling");
        event8.setStartTime(1697547600000L);
        event8.setEndTime(1697554800000L);
        event8.setBlockNumber(true);
        event8.setExpectedParticipants((short) 500);
        event8.setIsAttendance(false);
        event8.setIsOpenRegistration(false);
        event8.setEventType(EventType.STUDENT_EVENT);
        event8.setReason(null);
        event8.setStatus(EventStatus.ORGANIZED);
        event8.setIsHireLocation(false);
        event8.setIsHireDesignBanner(false);
        event8.setIsHireDesignBg(false);
        event8.setIsHireDesignStandee(false);
        event8.setBackgroundPath("https://res.cloudinary.com/dowwnfkhb/image/upload/v1693827639/f05c6349-f66b-4b80-88f4-51ce9ac91185_BG.png");
        event8.setBannerPath("https://res.cloudinary.com/dowwnfkhb/image/upload/v1693827634/f05c6349-f66b-4b80-88f4-51ce9ac91185_BN.png");
        event8.setStandeePath(null);
        event8.setDescription("Sự Kiện GIẢI ĐÁP NGHIỆP VỤ DỰ ÁN - ĐA CHIỀU NGHIỆP VỤ");
        event8.setId(eventRepository.save(event8).getId());

        Event event9 = new Event();
        event9.setApproverId("2EB57F85-2D45-44CB-C621-08DBFD8FC28C".toLowerCase());
        event9.setCategoryId(seminar.getId());
        event9.setSemesterId(fall2023.getId());
        event9.setName("Seminar KHÁM PHÁ HIBERNATE - LÀM QUEN VỚI KẾT NỐI DỮ LIỆU");
        event9.setStartTime(1682794800000L);
        event9.setEndTime(1682802000000L);
        event9.setBlockNumber(false);
        event9.setExpectedParticipants((short) 500);
        event9.setIsAttendance(false);
        event9.setIsOpenRegistration(false);
        event9.setEventType(EventType.STUDENT_EVENT);
        event9.setReason(null);
        event9.setStatus(EventStatus.ORGANIZED);
        event9.setIsHireLocation(false);
        event9.setIsHireDesignBanner(false);
        event9.setIsHireDesignBg(false);
        event9.setIsHireDesignStandee(false);
        event9.setBackgroundPath("https://res.cloudinary.com/dowwnfkhb/image/upload/v1693827639/f05c6349-f66b-4b80-88f4-51ce9ac91185_BG.png");
        event9.setBannerPath("https://res.cloudinary.com/dowwnfkhb/image/upload/v1693827634/f05c6349-f66b-4b80-88f4-51ce9ac91185_BN.png");
        event9.setStandeePath(null);
        event9.setDescription("Sự Kiện GIẢI ĐÁP NGHIỆP VỤ DỰ ÁN - ĐA CHIỀU NGHIỆP VỤ");
        event9.setId(eventRepository.save(event9).getId());

        Event event10 = new Event();
        event10.setApproverId("2EB57F85-2D45-44CB-C621-08DBFD8FC28C".toLowerCase());
        event10.setCategoryId(workshop.getId());
        event10.setSemesterId(fall2023.getId());
        event10.setName("Workshop Coding Convention");
        event10.setStartTime(1682214000000L);
        event10.setEndTime(1682221200000L);
        event10.setBlockNumber(false);
        event10.setExpectedParticipants((short) 500);
        event10.setIsAttendance(false);
        event10.setIsOpenRegistration(false);
        event10.setEventType(EventType.STUDENT_EVENT);
        event10.setReason(null);
        event10.setStatus(EventStatus.ORGANIZED);
        event10.setIsHireLocation(false);
        event10.setIsHireDesignBanner(false);
        event10.setIsHireDesignBg(false);
        event10.setIsHireDesignStandee(false);
        event10.setBackgroundPath("https://res.cloudinary.com/dowwnfkhb/image/upload/v1693827639/f05c6349-f66b-4b80-88f4-51ce9ac91185_BG.png");
        event10.setBannerPath("https://res.cloudinary.com/dowwnfkhb/image/upload/v1693827634/f05c6349-f66b-4b80-88f4-51ce9ac91185_BN.png");
        event10.setStandeePath(null);
        event10.setDescription("Sự Kiện GIẢI ĐÁP NGHIỆP VỤ DỰ ÁN - ĐA CHIỀU NGHIỆP VỤ");
        event10.setId(eventRepository.save(event10).getId());

        Event event11 = new Event();
        event11.setApproverId("2EB57F85-2D45-44CB-C621-08DBFD8FC28C".toLowerCase());
        event11.setCategoryId(workshop.getId());
        event11.setSemesterId(fall2023.getId());
        event11.setName("Workshop CRUD trên Swing sử dụng JDBC có join bảng");
        event11.setStartTime(1683058800000L);
        event11.setEndTime(1683066000000L);
        event11.setBlockNumber(false);
        event11.setExpectedParticipants((short) 500);
        event11.setIsAttendance(false);
        event11.setIsOpenRegistration(false);
        event11.setEventType(EventType.STUDENT_EVENT);
        event11.setReason(null);
        event11.setStatus(EventStatus.ORGANIZED);
        event11.setIsHireLocation(true);
        event11.setIsHireDesignBanner(false);
        event11.setIsHireDesignBg(false);
        event11.setIsHireDesignStandee(false);
        event11.setBackgroundPath("https://res.cloudinary.com/dowwnfkhb/image/upload/v1693827639/f05c6349-f66b-4b80-88f4-51ce9ac91185_BG.png");
        event11.setBannerPath("https://res.cloudinary.com/dowwnfkhb/image/upload/v1693827634/f05c6349-f66b-4b80-88f4-51ce9ac91185_BN.png");
        event11.setStandeePath(null);
        event11.setDescription("Sự Kiện GIẢI ĐÁP NGHIỆP VỤ DỰ ÁN - ĐA CHIỀU NGHIỆP VỤ");
        event11.setId(eventRepository.save(event11).getId());

        Event event12 = new Event();
        event12.setApproverId("2EB57F85-2D45-44CB-C621-08DBFD8FC28C".toLowerCase());
        event12.setCategoryId(workshop.getId());
        event12.setSemesterId(fall2023.getId());
        event12.setName("Workshop Swing và Spring Data Jpa: CRUD");
        event12.setStartTime(1692255600000L);
        event12.setEndTime(1692262800000L);
        event12.setBlockNumber(true);
        event12.setExpectedParticipants((short) 600);
        event12.setIsAttendance(false);
        event12.setIsOpenRegistration(false);
        event12.setEventType(EventType.STUDENT_EVENT);
        event12.setReason(null);
        event12.setStatus(EventStatus.ORGANIZED);
        event12.setIsHireLocation(true);
        event12.setIsHireDesignBanner(false);
        event12.setIsHireDesignBg(false);
        event12.setIsHireDesignStandee(false);
        event12.setBackgroundPath("https://res.cloudinary.com/dowwnfkhb/image/upload/v1693827639/f05c6349-f66b-4b80-88f4-51ce9ac91185_BG.png");
        event12.setBannerPath("https://res.cloudinary.com/dowwnfkhb/image/upload/v1693827634/f05c6349-f66b-4b80-88f4-51ce9ac91185_BN.png");
        event12.setStandeePath(null);
        event12.setDescription("Sự Kiện GIẢI ĐÁP NGHIỆP VỤ DỰ ÁN - ĐA CHIỀU NGHIỆP VỤ");
        event12.setId(eventRepository.save(event12).getId());

        Event event13 = new Event();
        event13.setApproverId("2EB57F85-2D45-44CB-C621-08DBFD8FC28C".toLowerCase());
        event13.setCategoryId(workshop.getId());
        event13.setSemesterId(fall2023.getId());
        event13.setName("Seminar Xây dựng cơ sở dữ liệu lưu trữ sản phẩm bán hàng");
        event13.setStartTime(1691943600000L);
        event13.setEndTime(1691950800000L);
        event13.setBlockNumber(true);
        event13.setExpectedParticipants((short) 600);
        event13.setIsAttendance(false);
        event13.setIsOpenRegistration(false);
        event13.setEventType(EventType.STUDENT_EVENT);
        event13.setReason(null);
        event13.setStatus(EventStatus.ORGANIZED);
        event13.setIsHireLocation(true);
        event13.setIsHireDesignBanner(false);
        event13.setIsHireDesignBg(false);
        event13.setIsHireDesignStandee(false);
        event13.setBackgroundPath("https://res.cloudinary.com/dowwnfkhb/image/upload/v1693827639/f05c6349-f66b-4b80-88f4-51ce9ac91185_BG.png");
        event13.setBannerPath("https://res.cloudinary.com/dowwnfkhb/image/upload/v1693827634/f05c6349-f66b-4b80-88f4-51ce9ac91185_BN.png");
        event13.setStandeePath(null);
        event13.setDescription("Sự Kiện GIẢI ĐÁP NGHIỆP VỤ DỰ ÁN - ĐA CHIỀU NGHIỆP VỤ");
        event13.setId(eventRepository.save(event13).getId());

        Event event14 = new Event();
        event14.setApproverId(null);
        event14.setCategoryId(workshop.getId());
        event14.setSemesterId(fall2023.getId());
        event14.setName("Truyền thông sự kiện đăng ký member xưởng - tuyển member");
        event14.setStartTime(1695798000000L);
        event14.setEndTime(1695798000000L);
        event14.setBlockNumber(true);
        event14.setExpectedParticipants((short) 150);
        event14.setIsAttendance(false);
        event14.setIsOpenRegistration(false);
        event14.setEventType(EventType.STUDENT_EVENT);
        event14.setReason(null);
        event14.setStatus(EventStatus.SCHEDULED_HELD);
        event14.setIsHireLocation(true);
        event14.setIsHireDesignBanner(false);
        event14.setIsHireDesignBg(false);
        event14.setIsHireDesignStandee(false);
        event14.setBackgroundPath(null);
        event14.setBannerPath(null);
        event14.setStandeePath(null);
        event14.setDescription("Sự Kiện GIẢI ĐÁP NGHIỆP VỤ DỰ ÁN - ĐA CHIỀU NGHIỆP VỤ");
        event14.setId(eventRepository.save(event14).getId());

        Event event15 = new Event();
        event15.setApproverId("2EB57F85-2D45-44CB-C621-08DBFD8FC28C".toLowerCase());
        event15.setCategoryId(workshop.getId());
        event15.setSemesterId(fall2023.getId());
        event15.setName("Workshop Làm Quen Với Menu");
        event15.setStartTime(1703842800000L);
        event15.setEndTime(1703850000000L);
        event15.setBlockNumber(true);
        event15.setExpectedParticipants((short) 150);
        event15.setIsAttendance(false);
        event15.setIsOpenRegistration(false);
        event15.setEventType(EventType.STUDENT_EVENT);
        event15.setReason(null);
        event15.setStatus(EventStatus.APPROVED);
        event15.setIsHireLocation(true);
        event15.setIsHireDesignBanner(false);
        event15.setIsHireDesignBg(false);
        event15.setIsHireDesignStandee(false);
        event15.setBackgroundPath(null);
        event15.setBannerPath(null);
        event15.setStandeePath(null);
        event15.setDescription("Bạn đang học Dự án 1 ngành UDPM ? Làm thế nào để đảm bảo nghiệp vụ cơ bản của dự án?");
        event15.setId(eventRepository.save(event15).getId());

        Event event16 = new Event();
        event16.setApproverId("2EB57F85-2D45-44CB-C621-08DBFD8FC28C".toLowerCase());
        event16.setCategoryId(workshop.getId());
        event16.setSemesterId(fall2023.getId());
        event16.setName("Workshop Làm Quen Với Menu");
        event16.setStartTime(1695721200000L);
        event16.setEndTime(1695728400000L);
        event16.setBlockNumber(true);
        event16.setExpectedParticipants((short) 150);
        event16.setIsAttendance(false);
        event16.setIsOpenRegistration(false);
        event16.setEventType(EventType.STUDENT_EVENT);
        event16.setReason("Sửa lại tiêu đề");
        event16.setStatus(EventStatus.EDITING);
        event16.setIsHireLocation(true);
        event16.setIsHireDesignBanner(false);
        event16.setIsHireDesignBg(false);
        event16.setIsHireDesignStandee(false);
        event16.setBackgroundPath(null);
        event16.setBannerPath(null);
        event16.setStandeePath(null);
        event16.setDescription("Bạn đang học Dự án 1 ngành UDPM ? Làm thế nào để đảm bảo nghiệp vụ cơ bản của dự án?");
        event16.setId(eventRepository.save(event16).getId());

        Event event17 = new Event();
        event17.setApproverId("2EB57F85-2D45-44CB-C621-08DBFD8FC28C".toLowerCase());
        event17.setCategoryId(workshop.getId());
        event17.setSemesterId(fall2023.getId());
        event17.setName("Con đường tester chuyên nghiệp");
        event17.setStartTime(1703311200000L);
        event17.setEndTime(1703318400000L);
        event17.setBlockNumber(true);
        event17.setExpectedParticipants((short) 150);
        event17.setIsAttendance(false);
        event17.setIsOpenRegistration(false);
        event17.setEventType(EventType.STUDENT_EVENT);
        event17.setReason(null);
        event17.setStatus(EventStatus.APPROVED);
        event17.setIsHireLocation(true);
        event17.setIsHireDesignBanner(false);
        event17.setIsHireDesignBg(false);
        event17.setIsHireDesignStandee(false);
        event17.setBackgroundPath(null);
        event17.setBannerPath(null);
        event17.setStandeePath(null);
        event17.setDescription("Đến với Workshop, sinh viên sẽ được cung cấp các kỹ năng và phương pháp xây dựng lộ trình nghề Tester");
        event17.setId(eventRepository.save(event17).getId());

        //Bảng evidence
        Evidence evidence = new Evidence();
        evidence.setEventId(event1.getId());
        evidence.setEvidenceType(EvidenceType.IMAGE);
        evidence.setName("Ảnh chụp Google meets");
        evidence.setPath("https://res.cloudinary.com/dowwnfkhb/image/upload/v1693827067/5d9dcadc-23df-4188-b026-0c007489aa83.png");
        evidence.setDescription(null);
        evidence.setId(evidenceRepository.save(evidence).getId());

        Evidence evidence1 = new Evidence();
        evidence1.setEventId(event2.getId());
        evidence1.setEvidenceType(EvidenceType.IMAGE);
        evidence1.setName("Ảnh chụp Google meets");
        evidence1.setPath("https://res.cloudinary.com/dowwnfkhb/image/upload/v1693827067/5d9dcadc-23df-4188-b026-0c007489aa83.png");
        evidence1.setDescription(null);
        evidence1.setId(evidenceRepository.save(evidence1).getId());

        Evidence evidence2 = new Evidence();
        evidence2.setEventId(event3.getId());
        evidence2.setEvidenceType(EvidenceType.IMAGE);
        evidence2.setName("Ảnh chụp Zoom");
        evidence2.setPath("https://res.cloudinary.com/dowwnfkhb/image/upload/v1693827067/5d9dcadc-23df-4188-b026-0c007489aa83.png");
        evidence2.setDescription(null);
        evidence2.setId(evidenceRepository.save(evidence2).getId());

        Evidence evidence3 = new Evidence();
        evidence3.setEventId(event4.getId());
        evidence3.setEvidenceType(EvidenceType.IMAGE);
        evidence3.setName("Ảnh chụp Zoom");
        evidence3.setPath("https://res.cloudinary.com/dowwnfkhb/image/upload/v1693827067/5d9dcadc-23df-4188-b026-0c007489aa83.png");
        evidence3.setDescription(null);
        evidence3.setId(evidenceRepository.save(evidence3).getId());

        Evidence evidence4 = new Evidence();
        evidence4.setEventId(event5.getId());
        evidence4.setEvidenceType(EvidenceType.IMAGE);
        evidence4.setName("Ảnh chụp Zoom");
        evidence4.setPath("https://res.cloudinary.com/dowwnfkhb/image/upload/v1693827067/5d9dcadc-23df-4188-b026-0c007489aa83.png");
        evidence4.setDescription(null);
        evidence4.setId(evidenceRepository.save(evidence4).getId());

        Evidence evidence5 = new Evidence();
        evidence5.setEventId(event6.getId());
        evidence5.setEvidenceType(EvidenceType.IMAGE);
        evidence5.setName("Ảnh chụp Zoom");
        evidence5.setPath("https://res.cloudinary.com/dowwnfkhb/image/upload/v1693827067/5d9dcadc-23df-4188-b026-0c007489aa83.png");
        evidence5.setDescription(null);
        evidence5.setId(evidenceRepository.save(evidence5).getId());

        Evidence evidence6 = new Evidence();
        evidence6.setEventId(event7.getId());
        evidence6.setEvidenceType(EvidenceType.IMAGE);
        evidence6.setName("Ảnh chụp Zoom");
        evidence6.setPath("https://res.cloudinary.com/dowwnfkhb/image/upload/v1693827067/5d9dcadc-23df-4188-b026-0c007489aa83.png");
        evidence6.setDescription(null);
        evidence6.setId(evidenceRepository.save(evidence6).getId());

        Evidence evidence7 = new Evidence();
        evidence7.setEventId(event8.getId());
        evidence7.setEvidenceType(EvidenceType.IMAGE);
        evidence7.setName("Ảnh chụp Zoom");
        evidence7.setPath("https://res.cloudinary.com/dowwnfkhb/image/upload/v1693827067/5d9dcadc-23df-4188-b026-0c007489aa83.png");
        evidence7.setDescription(null);
        evidence7.setId(evidenceRepository.save(evidence7).getId());

        Evidence evidence8 = new Evidence();
        evidence8.setEventId(event9.getId());
        evidence8.setEvidenceType(EvidenceType.IMAGE);
        evidence8.setName("Ảnh chụp Zoom");
        evidence8.setPath("https://res.cloudinary.com/dowwnfkhb/image/upload/v1693827067/5d9dcadc-23df-4188-b026-0c007489aa83.png");
        evidence8.setDescription(null);
        evidence8.setId(evidenceRepository.save(evidence8).getId());

        Evidence evidence9 = new Evidence();
        evidence9.setEventId(event10.getId());
        evidence9.setEvidenceType(EvidenceType.IMAGE);
        evidence9.setName("Ảnh chụp Zoom");
        evidence9.setPath("https://res.cloudinary.com/dowwnfkhb/image/upload/v1693827067/5d9dcadc-23df-4188-b026-0c007489aa83.png");
        evidence9.setDescription(null);
        evidence9.setId(evidenceRepository.save(evidence9).getId());

        Evidence evidence10 = new Evidence();
        evidence10.setEventId(event11.getId());
        evidence10.setEvidenceType(EvidenceType.IMAGE);
        evidence10.setName("Ảnh chụp Zoom");
        evidence10.setPath("https://res.cloudinary.com/dowwnfkhb/image/upload/v1693827067/5d9dcadc-23df-4188-b026-0c007489aa83.png");
        evidence10.setDescription(null);
        evidence10.setId(evidenceRepository.save(evidence10).getId());

        //Bảng Resource (Tài nguyên)
        Resource github1 = new Resource();
        github1.setEventId(event1.getId());
        github1.setName("Source code Github");
        github1.setPath("github.com/...");
       	github1.setId(resourceRepository.save(github1).getId());
        Resource video1 = new Resource();
        video1.setEventId(event1.getId());
        video1.setName("Video record");
        video1.setPath("youtube.com/....");
        video1.setId(resourceRepository.save(video1).getId());

        Resource github2 = new Resource();
        github2.setEventId(event2.getId());
        github2.setName("Source code Github");
        github2.setPath("github.com/...");
        github2.setId(resourceRepository.save(github2).getId());
        Resource video2 = new Resource();
        video2.setEventId(event2.getId());
        video2.setName("Video record");
        video2.setPath("youtube.com/....");
        video2.setId(resourceRepository.save(video2).getId());

        Resource github3 = new Resource();
        github3.setEventId(event3.getId());
        github3.setName("Source code Github");
        github3.setPath("github.com/...");
        github3.setId(resourceRepository.save(github3).getId());
        Resource video3 = new Resource();
        video3.setEventId(event3.getId());
        video3.setName("Video record");
        video3.setPath("youtube.com/....");
        video3.setId(resourceRepository.save(video3).getId());

        Resource github4 = new Resource();
        github4.setEventId(event4.getId());
        github4.setName("Source code Github");
        github4.setPath("github.com/...");
        github4.setId(resourceRepository.save(github4).getId());
        Resource video4 = new Resource();
        video4.setEventId(event4.getId());
        video4.setName("Video record");
        video4.setPath("youtube.com/....");
        video4.setId(resourceRepository.save(video4).getId());

        Resource github5 = new Resource();
        github5.setEventId(event5.getId());
        github5.setName("Source code Github");
        github5.setPath("github.com/...");
        github5.setId(resourceRepository.save(github5).getId());
        Resource video5 = new Resource();
        video5.setEventId(event5.getId());
        video5.setName("Video record");
        video5.setPath("youtube.com/....");
        video5.setId(resourceRepository.save(video5).getId());

        Resource github6 = new Resource();
        github6.setEventId(event6.getId());
        github6.setName("Source code Github");
        github6.setPath("github.com/...");
        github6.setId(resourceRepository.save(github6).getId());
        Resource video6 = new Resource();
        video6.setEventId(event6.getId());
        video6.setName("Video record");
        video6.setPath("youtube.com/....");
        video6.setId(resourceRepository.save(video6).getId());

        Resource github7 = new Resource();
        github7.setEventId(event7.getId());
        github7.setName("Source code Github");
        github7.setPath("github.com/...");
        github7.setId(resourceRepository.save(github7).getId());
        Resource video7 = new Resource();
        video7.setEventId(event7.getId());
        video7.setName("Tìm hiểu về JDBC");
        video7.setPath("https://topdev.vn/blog/huong-dan-ket-noi-co-so-du-lieu-voi-java-jdbc/");
        video7.setId(resourceRepository.save(video7).getId());

        Resource github8 = new Resource();
        github8.setEventId(event8.getId());
        github8.setName("Source code Github");
        github8.setPath("github.com/...");
        github8.setId(resourceRepository.save(github8).getId());
        Resource video8 = new Resource();
        video8.setEventId(event8.getId());
        video8.setName("Video record");
        video8.setPath("youtube.com/....");
        video8.setId(resourceRepository.save(video8).getId());

        Resource github9 = new Resource();
        github9.setEventId(event9.getId());
        github9.setName("Source code Github");
        github9.setPath("github.com/...");
        github9.setId(resourceRepository.save(github9).getId());
        Resource video9 = new Resource();
        video9.setEventId(event9.getId());
        video9.setName("Video record");
        video9.setPath("youtube.com/....");
        video9.setId(resourceRepository.save(video9).getId());

        Resource github10 = new Resource();
        github10.setEventId(event10.getId());
        github10.setName("Source code Github");
        github10.setPath("github.com/...");
        github10.setId(resourceRepository.save(github10).getId());
        Resource video10 = new Resource();
        video10.setEventId(event10.getId());
        video10.setName("Video record");
        video10.setPath("youtube.com/....");
        video10.setId(resourceRepository.save(video10).getId());

        Resource github11 = new Resource();
        github11.setEventId(event11.getId());
        github11.setName("Source code Github");
        github11.setPath("github.com/...");
        github11.setId(resourceRepository.save(github11).getId());
        Resource video11 = new Resource();
        video11.setEventId(event11.getId());
        video11.setName("Video record");
        video11.setPath("youtube.com/....");
        video11.setId(resourceRepository.save(video11).getId());

        Resource github17 = new Resource();
        github17.setEventId(event17.getId());
        github17.setName("Tài liệu về Tester");
        github17.setPath("https://itviec.com/blog/tester-thanh-cong/");
        github17.setId(resourceRepository.save(github17).getId());

        // Bảng Agenda item
        AgendaItem agendaItem1 = new AgendaItem();
        agendaItem1.setEventId(event1.getId());
        agendaItem1.setOrganizerId("9EA5B27D-F183-4537-BAAE-BECACE5532EA".toLowerCase());
        agendaItem1.setStartTime("20:00");
        agendaItem1.setEndTime("20:20");
        agendaItem1.setDescription("Ổn định, giới thiệu sự kiện");
        agendaItem1.setId(agendaItemRepository.save(agendaItem1).getId());
        AgendaItem agendaItem2 = new AgendaItem();
        agendaItem2.setEventId(event1.getId());
        agendaItem2.setOrganizerId("9EA5B27D-F183-4537-BAAE-BECACE5532EA".toLowerCase());
        agendaItem2.setStartTime("20:20");
        agendaItem2.setEndTime("21:30");
        agendaItem2.setDescription("Giải thích về Coding convention là gì");
        agendaItem2.setId(agendaItemRepository.save(agendaItem2).getId());
        AgendaItem agendaItem3 = new AgendaItem();
        agendaItem3.setEventId(event1.getId());
        agendaItem3.setOrganizerId("AA3F9EE6-797D-4C90-C373-08DBE28ECB04".toLowerCase());
        agendaItem3.setStartTime("21:30");
        agendaItem3.setEndTime("22:30");
        agendaItem3.setDescription("Phần Q & A");
        agendaItem3.setId(agendaItemRepository.save(agendaItem3).getId());

        AgendaItem agendaItem5 = new AgendaItem();
        agendaItem5.setEventId(event2.getId());
        agendaItem5.setOrganizerId("AA3F9EE6-797D-4C90-C373-08DBE28ECB04".toLowerCase());
        agendaItem5.setStartTime("20:30");
        agendaItem5.setEndTime("21:00");
        agendaItem5.setDescription("Giới thiệu ổn định sự kiện");
        agendaItem5.setId(agendaItemRepository.save(agendaItem5).getId());
        AgendaItem agendaItem4 = new AgendaItem();
        agendaItem4.setId("237C2164-7463-47DE-3105-08DBE295E52C".toLowerCase());
        agendaItem4.setEventId(event2.getId());
        agendaItem4.setOrganizerId(null);
        agendaItem4.setStartTime("21:00");
        agendaItem4.setEndTime("22:00");
        agendaItem4.setDescription("Thuyết trình về sự kiện");
        agendaItem4.setId(agendaItemRepository.save(agendaItem4).getId());

        AgendaItem agendaItem6 = new AgendaItem();
        agendaItem6.setEventId(event3.getId());
        agendaItem6.setOrganizerId("AA3F9EE6-797D-4C90-C373-08DBE28ECB04".toLowerCase());
        agendaItem6.setStartTime("20:30");
        agendaItem6.setEndTime("21:00");
        agendaItem6.setDescription("Giới thiệu ổn định sự kiện");
        agendaItem6.setId(agendaItemRepository.save(agendaItem6).getId());
        AgendaItem agendaItem7 = new AgendaItem();
        agendaItem7.setEventId(event3.getId());
        agendaItem7.setOrganizerId("55EB2982-00E8-4DFE-C376-08DBE28ECB04".toLowerCase());
        agendaItem7.setStartTime("21:00");
        agendaItem7.setEndTime("22:00");
        agendaItem7.setDescription("Thuyết trình về sự kiện");
        agendaItem7.setId(agendaItemRepository.save(agendaItem7).getId());
        AgendaItem agendaItem8 = new AgendaItem();
        agendaItem8.setEventId(event3.getId());
        agendaItem8.setOrganizerId("55EB2982-00E8-4DFE-C376-08DBE28ECB04".toLowerCase());
        agendaItem8.setStartTime("22:00");
        agendaItem8.setEndTime("22:30");
        agendaItem8.setDescription("Phần Q & A");
        agendaItem8.setId(agendaItemRepository.save(agendaItem8).getId());

        AgendaItem agendaItem9 = new AgendaItem();
        agendaItem9.setEventId(event4.getId());
        agendaItem9.setOrganizerId("237C2164-7463-47DE-3105-08DBE295E52C".toLowerCase());
        agendaItem9.setStartTime("20:00");
        agendaItem9.setEndTime("20:30");
        agendaItem9.setDescription("Giới thiệu ổn định sự kiện");
        agendaItem9.setId(agendaItemRepository.save(agendaItem9).getId());
        AgendaItem agendaItem10 = new AgendaItem();
        agendaItem10.setEventId(event4.getId());
        agendaItem10.setOrganizerId("237C2164-7463-47DE-3105-08DBE295E52C".toLowerCase());
        agendaItem10.setStartTime("20:30");
        agendaItem10.setEndTime("22:00");
        agendaItem10.setDescription("Triển khai sự kiện");
        agendaItem10.setId(agendaItemRepository.save(agendaItem10).getId());

        AgendaItem agendaItem12 = new AgendaItem();
        agendaItem12.setEventId(event5.getId());
        agendaItem12.setOrganizerId("9EA5B27D-F183-4537-BAAE-BECACE5532EA".toLowerCase());
        agendaItem12.setStartTime("20:00");
        agendaItem12.setEndTime("20:30");
        agendaItem12.setDescription("Giới thiệu ổn định sự kiện");
        agendaItem12.setId(agendaItemRepository.save(agendaItem12).getId());
        AgendaItem agendaItem11 = new AgendaItem();
        agendaItem11.setEventId(event5.getId());
        agendaItem11.setOrganizerId("AA3F9EE6-797D-4C90-C373-08DBE28ECB04".toLowerCase());
        agendaItem11.setStartTime("20:30");
        agendaItem11.setEndTime("22:00");
        agendaItem11.setDescription("Triển khai sự kiện");
        agendaItem11.setId(agendaItemRepository.save(agendaItem11).getId());

        AgendaItem agendaItem13 = new AgendaItem();
        agendaItem13.setEventId(event6.getId());
        agendaItem13.setOrganizerId("55EB2982-00E8-4DFE-C376-08DBE28ECB04".toLowerCase());
        agendaItem13.setStartTime("20:00");
        agendaItem13.setEndTime("20:30");
        agendaItem13.setDescription("Giới thiệu ổn định sự kiện");
        agendaItem13.setId(agendaItemRepository.save(agendaItem13).getId());
        AgendaItem agendaItem14 = new AgendaItem();
        agendaItem14.setEventId(event6.getId());
        agendaItem14.setOrganizerId("55EB2982-00E8-4DFE-C376-08DBE28ECB04".toLowerCase());
        agendaItem14.setStartTime("20:30");
        agendaItem14.setEndTime("22:00");
        agendaItem14.setDescription("Triển khai sự kiện");
        agendaItem14.setId(agendaItemRepository.save(agendaItem14).getId());

        AgendaItem agendaItem15 = new AgendaItem();
        agendaItem15.setEventId(event7.getId());
        agendaItem15.setOrganizerId("237C2164-7463-47DE-3105-08DBE295E52C".toLowerCase());
        agendaItem15.setStartTime("20:00");
        agendaItem15.setEndTime("20:30");
        agendaItem15.setDescription("Giới thiệu ổn định sự kiện");
        agendaItem15.setId(agendaItemRepository.save(agendaItem15).getId());
        AgendaItem agendaItem16 = new AgendaItem();
        agendaItem16.setEventId(event7.getId());
        agendaItem16.setOrganizerId("237C2164-7463-47DE-3105-08DBE295E52C".toLowerCase());
        agendaItem16.setStartTime("20:30");
        agendaItem16.setEndTime("22:00");
        agendaItem16.setDescription("Triển khai sự kiện");
        agendaItem16.setId(agendaItemRepository.save(agendaItem16).getId());

        AgendaItem agendaItem17 = new AgendaItem();
        agendaItem17.setEventId(event8.getId());
        agendaItem17.setOrganizerId("237C2164-7463-47DE-3105-08DBE295E52C".toLowerCase());
        agendaItem17.setStartTime("20:00");
        agendaItem17.setEndTime("20:30");
        agendaItem17.setDescription("Giới thiệu ổn định sự kiện");
        agendaItem17.setId(agendaItemRepository.save(agendaItem15).getId());
        AgendaItem agendaItem18 = new AgendaItem();
        agendaItem18.setEventId(event8.getId());
        agendaItem18.setOrganizerId("9EA5B27D-F183-4537-BAAE-BECACE5532EA".toLowerCase());
        agendaItem18.setStartTime("20:30");
        agendaItem18.setEndTime("22:00");
        agendaItem18.setDescription("Triển khai sự kiện");
        agendaItem18.setId(agendaItemRepository.save(agendaItem18).getId());

        AgendaItem agendaItem19 = new AgendaItem();
        agendaItem19.setEventId(event9.getId());
        agendaItem19.setOrganizerId("55EB2982-00E8-4DFE-C376-08DBE28ECB04".toLowerCase());
        agendaItem19.setStartTime("20:00");
        agendaItem19.setEndTime("20:30");
        agendaItem19.setDescription("Giới thiệu ổn định sự kiện");
        agendaItem19.setId(agendaItemRepository.save(agendaItem19).getId());
        AgendaItem agendaItem20 = new AgendaItem();
        agendaItem20.setEventId(event9.getId());
        agendaItem20.setOrganizerId("9EA5B27D-F183-4537-BAAE-BECACE5532EA".toLowerCase());
        agendaItem20.setStartTime("20:30");
        agendaItem20.setEndTime("22:00");
        agendaItem20.setDescription("Triển khai sự kiện");
        agendaItem20.setId(agendaItemRepository.save(agendaItem20).getId());

        AgendaItem agendaItem21 = new AgendaItem();
        agendaItem21.setEventId(event10.getId());
        agendaItem21.setOrganizerId("55EB2982-00E8-4DFE-C376-08DBE28ECB04".toLowerCase());
        agendaItem21.setStartTime("20:00");
        agendaItem21.setEndTime("20:30");
        agendaItem21.setDescription("Giới thiệu ổn định sự kiện");
        agendaItem21.setId(agendaItemRepository.save(agendaItem21).getId());
        AgendaItem agendaItem22 = new AgendaItem();
        agendaItem22.setEventId(event10.getId());
        agendaItem22.setOrganizerId("55EB2982-00E8-4DFE-C376-08DBE28ECB04".toLowerCase());
        agendaItem22.setStartTime("20:30");
        agendaItem22.setEndTime("22:00");
        agendaItem22.setDescription("Triển khai sự kiện");
        agendaItem22.setId(agendaItemRepository.save(agendaItem22).getId());

        AgendaItem agendaItem23 = new AgendaItem();
        agendaItem23.setEventId(event17.getId());
        agendaItem23.setOrganizerId("55EB2982-00E8-4DFE-C376-08DBE28ECB04".toLowerCase());
        agendaItem23.setStartTime("11:00");
        agendaItem23.setEndTime("11:30");
        agendaItem23.setDescription("Giới thiệu ổn định sự kiện");
        agendaItem23.setId(agendaItemRepository.save(agendaItem23).getId());
        AgendaItem agendaItem24 = new AgendaItem();
        agendaItem24.setEventId(event17.getId());
        agendaItem24.setOrganizerId("55EB2982-00E8-4DFE-C376-08DBE28ECB04".toLowerCase());
        agendaItem24.setStartTime("11:30");
        agendaItem24.setEndTime("12:00");
        agendaItem24.setDescription("Triển khai sự kiện");
        agendaItem24.setId(agendaItemRepository.save(agendaItem24).getId());

        // Bảng Event Organizer
        EventOrganizer eventOrganizer1 = new EventOrganizer();
        eventOrganizer1.setEventId(event1.getId());
        eventOrganizer1.setOrganizerId("9EA5B27D-F183-4537-BAAE-BECACE5532EA".toLowerCase());
        eventOrganizer1.setEventRole(EventRole.HOST);
        eventOrganizer1.setId(eventOrganizerRepository.save(eventOrganizer1).getId());

        EventOrganizer eventOrganizer2 = new EventOrganizer();
        eventOrganizer2.setEventId(event1.getId());
        eventOrganizer2.setOrganizerId("AA3F9EE6-797D-4C90-C373-08DBE28ECB04".toLowerCase());
        eventOrganizer2.setEventRole(EventRole.CO_HOST);
        eventOrganizer2.setId(eventOrganizerRepository.save(eventOrganizer2).getId());

        EventOrganizer eventOrganizer4 = new EventOrganizer();
        eventOrganizer4.setEventId(event1.getId());
        eventOrganizer4.setOrganizerId("55EB2982-00E8-4DFE-C376-08DBE28ECB04".toLowerCase());
        eventOrganizer4.setEventRole(EventRole.CO_HOST);
        eventOrganizer4.setId(eventOrganizerRepository.save(eventOrganizer4).getId());

        EventOrganizer eventOrganizer3 = new EventOrganizer();
        eventOrganizer3.setEventId(event2.getId());
        eventOrganizer3.setOrganizerId("AA3F9EE6-797D-4C90-C373-08DBE28ECB04".toLowerCase());
        eventOrganizer3.setEventRole(EventRole.HOST);
        eventOrganizer3.setId(eventOrganizerRepository.save(eventOrganizer3).getId());
        EventOrganizer eventOrganizer6 = new EventOrganizer();
        eventOrganizer6.setEventId(event2.getId());
        eventOrganizer6.setOrganizerId("237C2164-7463-47DE-3105-08DBE295E52C".toLowerCase());
        eventOrganizer6.setEventRole(EventRole.HOST);
        eventOrganizer6.setId(eventOrganizerRepository.save(eventOrganizer6).getId());
//        EventOrganizer eventOrganizer5 = new EventOrganizer();
//        eventOrganizer5.setEventId(event2.getId());
//        eventOrganizer5.setOrganizerId("AA3F9EE6-797D-4C90-C373-08DBE28ECB04".toLowerCase());
//        eventOrganizer5.setEventRole(EventRole.CO_HOST);
//        eventOrganizer5.setId(eventOrganizerRepository.save(eventOrganizer5).getId());

        EventOrganizer eventOrganizer7 = new EventOrganizer();
        eventOrganizer7.setEventId(event3.getId());
        eventOrganizer7.setOrganizerId("55EB2982-00E8-4DFE-C376-08DBE28ECB04".toLowerCase());
        eventOrganizer7.setEventRole(EventRole.HOST);
        eventOrganizer7.setId(eventOrganizerRepository.save(eventOrganizer7).getId());
        EventOrganizer eventOrganizer8 = new EventOrganizer();
        eventOrganizer8.setEventId(event3.getId());
        eventOrganizer8.setOrganizerId("9EA5B27D-F183-4537-BAAE-BECACE5532EA".toLowerCase());
        eventOrganizer8.setEventRole(EventRole.CO_HOST);
        eventOrganizer8.setId(eventOrganizerRepository.save(eventOrganizer8).getId());
//        EventOrganizer eventOrganizer9 = new EventOrganizer();
//        eventOrganizer9.setEventId(event3.getId());
//        eventOrganizer9.setOrganizerId("5F291803-0CF5-4F68-C61E-08DBFD8FC28C".toLowerCase());
//        eventOrganizer9.setEventRole(EventRole.CO_HOST);
//        eventOrganizer9.setId(eventOrganizerRepository.save(eventOrganizer9).getId());

        EventOrganizer eventOrganizer10 = new EventOrganizer();
        eventOrganizer10.setEventId(event4.getId());
        eventOrganizer10.setOrganizerId("AA3F9EE6-797D-4C90-C373-08DBE28ECB04".toLowerCase());
        eventOrganizer10.setEventRole(EventRole.HOST);
        eventOrganizer10.setId(eventOrganizerRepository.save(eventOrganizer10).getId());
        EventOrganizer eventOrganizer11 = new EventOrganizer();
        eventOrganizer11.setEventId(event4.getId());
        eventOrganizer11.setOrganizerId("237C2164-7463-47DE-3105-08DBE295E52C".toLowerCase());
        eventOrganizer11.setEventRole(EventRole.CO_HOST);
        eventOrganizer11.setId(eventOrganizerRepository.save(eventOrganizer11).getId());
//        EventOrganizer eventOrganizer12 = new EventOrganizer();
//        eventOrganizer12.setEventId(event4.getId());
//        eventOrganizer12.setOrganizerId("9EA5B27D-F183-4537-BAAE-BECACE5532EA".toLowerCase());
//        eventOrganizer12.setEventRole(EventRole.CO_HOST);
//        eventOrganizer12.setId(eventOrganizerRepository.save(eventOrganizer12).getId());

        EventOrganizer eventOrganizer13 = new EventOrganizer();
        eventOrganizer13.setEventId(event5.getId());
        eventOrganizer13.setOrganizerId("AA3F9EE6-797D-4C90-C373-08DBE28ECB04".toLowerCase());
        eventOrganizer13.setEventRole(EventRole.HOST);
        eventOrganizer13.setId(eventOrganizerRepository.save(eventOrganizer13).getId());
        EventOrganizer eventOrganizer14 = new EventOrganizer();
        eventOrganizer14.setEventId(event5.getId());
        eventOrganizer14.setOrganizerId("9EA5B27D-F183-4537-BAAE-BECACE5532EA".toLowerCase());
        eventOrganizer14.setEventRole(EventRole.CO_HOST);
        eventOrganizer14.setId(eventOrganizerRepository.save(eventOrganizer14).getId());
//        EventOrganizer eventOrganizer15 = new EventOrganizer();
//        eventOrganizer15.setEventId(event5.getId());
//        eventOrganizer15.setOrganizerId("237C2164-7463-47DE-3105-08DBE295E52C".toLowerCase());
//        eventOrganizer15.setEventRole(EventRole.CO_HOST);
//        eventOrganizer15.setId(eventOrganizerRepository.save(eventOrganizer15).getId());

        EventOrganizer eventOrganizer16 = new EventOrganizer();
        eventOrganizer16.setEventId(event6.getId());
        eventOrganizer16.setOrganizerId("55EB2982-00E8-4DFE-C376-08DBE28ECB04".toLowerCase());
        eventOrganizer16.setEventRole(EventRole.HOST);
        eventOrganizer16.setId(eventOrganizerRepository.save(eventOrganizer16).getId());
        EventOrganizer eventOrganizer17 = new EventOrganizer();
        eventOrganizer17.setEventId(event6.getId());
        eventOrganizer17.setOrganizerId("AA3F9EE6-797D-4C90-C373-08DBE28ECB04".toLowerCase());
        eventOrganizer17.setEventRole(EventRole.CO_HOST);
        eventOrganizer17.setId(eventOrganizerRepository.save(eventOrganizer17).getId());
//        EventOrganizer eventOrganizer18 = new EventOrganizer();
//        eventOrganizer18.setEventId(event6.getId());
//        eventOrganizer18.setOrganizerId("55EB2982-00E8-4DFE-C376-08DBE28ECB04".toLowerCase());
//        eventOrganizer18.setEventRole(EventRole.CO_HOST);
//        eventOrganizer18.setId(eventOrganizerRepository.save(eventOrganizer18).getId());

        EventOrganizer eventOrganizer19 = new EventOrganizer();
        eventOrganizer19.setEventId(event7.getId());
        eventOrganizer19.setOrganizerId("55EB2982-00E8-4DFE-C376-08DBE28ECB04".toLowerCase());
        eventOrganizer19.setEventRole(EventRole.HOST);
        eventOrganizer19.setId(eventOrganizerRepository.save(eventOrganizer19).getId());
        EventOrganizer eventOrganizer20 = new EventOrganizer();
        eventOrganizer20.setEventId(event7.getId());
        eventOrganizer20.setOrganizerId("237C2164-7463-47DE-3105-08DBE295E52C".toLowerCase());
        eventOrganizer20.setEventRole(EventRole.CO_HOST);
        eventOrganizer20.setId(eventOrganizerRepository.save(eventOrganizer20).getId());
//        EventOrganizer eventOrganizer21 = new EventOrganizer();
//        eventOrganizer21.setEventId(event7.getId());
//        eventOrganizer21.setOrganizerId("47F3CA5F-FB7F-4733-3106-08DBE295E52C".toLowerCase());
//        eventOrganizer21.setEventRole(EventRole.CO_HOST);
//        eventOrganizer21.setId(eventOrganizerRepository.save(eventOrganizer21).getId());

        EventOrganizer eventOrganizer22 = new EventOrganizer();
        eventOrganizer22.setEventId(event8.getId());
        eventOrganizer22.setOrganizerId("55EB2982-00E8-4DFE-C376-08DBE28ECB04".toLowerCase());
        eventOrganizer22.setEventRole(EventRole.HOST);
        eventOrganizer22.setId(eventOrganizerRepository.save(eventOrganizer22).getId());
        EventOrganizer eventOrganizer23 = new EventOrganizer();
        eventOrganizer23.setEventId(event8.getId());
        eventOrganizer23.setOrganizerId("9EA5B27D-F183-4537-BAAE-BECACE5532EA".toLowerCase());
        eventOrganizer23.setEventRole(EventRole.CO_HOST);
        eventOrganizer23.setId(eventOrganizerRepository.save(eventOrganizer23).getId());
//        EventOrganizer eventOrganizer24 = new EventOrganizer();
//        eventOrganizer24.setEventId(event8.getId());
//        eventOrganizer24.setOrganizerId("47F3CA5F-FB7F-4733-3106-08DBE295E52C".toLowerCase());
//        eventOrganizer24.setEventRole(EventRole.CO_HOST);
//        eventOrganizer24.setId(eventOrganizerRepository.save(eventOrganizer24).getId());

        EventOrganizer eventOrganizer25 = new EventOrganizer();
        eventOrganizer25.setEventId(event9.getId());
        eventOrganizer25.setOrganizerId("55EB2982-00E8-4DFE-C376-08DBE28ECB04".toLowerCase());
        eventOrganizer25.setEventRole(EventRole.HOST);
        eventOrganizer25.setId(eventOrganizerRepository.save(eventOrganizer25).getId());
        EventOrganizer eventOrganizer26 = new EventOrganizer();
        eventOrganizer26.setEventId(event9.getId());
        eventOrganizer26.setOrganizerId("237C2164-7463-47DE-3105-08DBE295E52C".toLowerCase());
        eventOrganizer26.setEventRole(EventRole.CO_HOST);
        eventOrganizer26.setId(eventOrganizerRepository.save(eventOrganizer26).getId());
        EventOrganizer eventOrganizer27 = new EventOrganizer();
        eventOrganizer27.setEventId(event9.getId());
        eventOrganizer27.setOrganizerId("9EA5B27D-F183-4537-BAAE-BECACE5532EA".toLowerCase());
        eventOrganizer27.setEventRole(EventRole.CO_HOST);
        eventOrganizer27.setId(eventOrganizerRepository.save(eventOrganizer27).getId());

        EventOrganizer eventOrganizer28 = new EventOrganizer();
        eventOrganizer28.setEventId(event10.getId());
        eventOrganizer28.setOrganizerId("55EB2982-00E8-4DFE-C376-08DBE28ECB04".toLowerCase());
        eventOrganizer28.setEventRole(EventRole.HOST);
        eventOrganizer28.setId(eventOrganizerRepository.save(eventOrganizer28).getId());
        EventOrganizer eventOrganizer29 = new EventOrganizer();
        eventOrganizer29.setEventId(event10.getId());
        eventOrganizer29.setOrganizerId("AA3F9EE6-797D-4C90-C373-08DBE28ECB04".toLowerCase());
        eventOrganizer29.setEventRole(EventRole.CO_HOST);
        eventOrganizer29.setId(eventOrganizerRepository.save(eventOrganizer29).getId());
//        EventOrganizer eventOrganizer30 = new EventOrganizer();
//        eventOrganizer30.setEventId(event10.getId());
//        eventOrganizer30.setOrganizerId("47F3CA5F-FB7F-4733-3106-08DBE295E52C".toLowerCase());
//        eventOrganizer30.setEventRole(EventRole.CO_HOST);
//        eventOrganizer30.setId(eventOrganizerRepository.save(eventOrganizer30).getId());

        EventOrganizer eventOrganizer31 = new EventOrganizer();
        eventOrganizer31.setEventId(event17.getId());
        eventOrganizer31.setOrganizerId("55EB2982-00E8-4DFE-C376-08DBE28ECB04".toLowerCase());
        eventOrganizer31.setEventRole(EventRole.HOST);
        eventOrganizer31.setId(eventOrganizerRepository.save(eventOrganizer31).getId());
        EventOrganizer eventOrganizer32 = new EventOrganizer();
        eventOrganizer32.setEventId(event17.getId());
        eventOrganizer32.setOrganizerId("237C2164-7463-47DE-3105-08DBE295E52C".toLowerCase());
        eventOrganizer32.setEventRole(EventRole.CO_HOST);
        eventOrganizer32.setId(eventOrganizerRepository.save(eventOrganizer32).getId());
//        EventOrganizer eventOrganizer33 = new EventOrganizer();
//        eventOrganizer33.setEventId(event17.getId());
//        eventOrganizer33.setOrganizerId("47F3CA5F-FB7F-4733-3106-08DBE295E52C".toLowerCase());
//        eventOrganizer33.setEventRole(EventRole.CO_HOST);
//        eventOrganizer33.setId(eventOrganizerRepository.save(eventOrganizer33).getId());

        //Bảng Event Location
        EventLocation eventLocation1 = new EventLocation();
        eventLocation1.setEventId(event1.getId());
        eventLocation1.setFormality(Formality.OFFLINE);
        eventLocation1.setName("Tòa P");
        eventLocation1.setPath("P403");
        eventLocation1.setId(eventLocationRepository.save(eventLocation1).getId());
        EventLocation eventLocation2 = new EventLocation();
        eventLocation2.setEventId(event1.getId());
        eventLocation2.setFormality(Formality.ONLINE);
        eventLocation2.setName("Google Meets");
        eventLocation2.setPath("https://meet.google.com/fod-weje-gxg");
        eventLocation2.setId(eventLocationRepository.save(eventLocation2).getId());
        EventLocation eventLocation3 = new EventLocation();
        eventLocation3.setEventId(event2.getId());
        eventLocation3.setFormality(Formality.ONLINE);
        eventLocation3.setName("Google Meets");
        eventLocation3.setPath("https://meet.google.com/fod-weje-gxg");
        eventLocation3.setId(eventLocationRepository.save(eventLocation3).getId());

        EventLocation eventLocation4 = new EventLocation();
        eventLocation4.setEventId(event3.getId());
        eventLocation4.setFormality(Formality.OFFLINE);
        eventLocation4.setName("Phòng Zoom");
        eventLocation4.setPath("https://us02web.zoom.us/j/5071725482");
        eventLocation4.setId(eventLocationRepository.save(eventLocation4).getId());

        EventLocation eventLocation5 = new EventLocation();
        eventLocation5.setEventId(event4.getId());
        eventLocation5.setFormality(Formality.ONLINE);
        eventLocation5.setName("Phòng Zoom");
        eventLocation5.setPath("https://us02web.zoom.us/j/5071725482");
        eventLocation5.setId(eventLocationRepository.save(eventLocation5).getId());

        EventLocation eventLocation6 = new EventLocation();
        eventLocation6.setEventId(event5.getId());
        eventLocation6.setFormality(Formality.ONLINE);
        eventLocation6.setName("Phòng Zoom");
        eventLocation6.setPath("https://us02web.zoom.us/j/5071725482");
        eventLocation6.setId(eventLocationRepository.save(eventLocation6).getId());

        EventLocation eventLocation7 = new EventLocation();
        eventLocation7.setEventId(event6.getId());
        eventLocation7.setFormality(Formality.ONLINE);
        eventLocation7.setName("Phòng Zoom");
        eventLocation7.setPath("https://us02web.zoom.us/j/5071725482");
        eventLocation7.setId(eventLocationRepository.save(eventLocation7).getId());

        EventLocation eventLocation8 = new EventLocation();
        eventLocation8.setEventId(event7.getId());
        eventLocation8.setFormality(Formality.ONLINE);
        eventLocation8.setName("Phòng Zoom");
        eventLocation8.setPath("https://us02web.zoom.us/j/5071725482");
        eventLocation8.setId(eventLocationRepository.save(eventLocation8).getId());

        EventLocation eventLocation9 = new EventLocation();
        eventLocation9.setEventId(event8.getId());
        eventLocation9.setFormality(Formality.ONLINE);
        eventLocation9.setName("Phòng Zoom");
        eventLocation9.setPath("https://us02web.zoom.us/j/5071725482");
        eventLocation9.setId(eventLocationRepository.save(eventLocation9).getId());

        EventLocation eventLocation10 = new EventLocation();
        eventLocation10.setEventId(event9.getId());
        eventLocation10.setFormality(Formality.ONLINE);
        eventLocation10.setName("Phòng Zoom");
        eventLocation10.setPath("https://us02web.zoom.us/j/5071725482");
        eventLocation10.setId(eventLocationRepository.save(eventLocation10).getId());

        EventLocation eventLocation11 = new EventLocation();
        eventLocation11.setEventId(event10.getId());
        eventLocation11.setFormality(Formality.ONLINE);
        eventLocation11.setName("Phòng Zoom");
        eventLocation11.setPath("https://us02web.zoom.us/j/5071725482");
        eventLocation11.setId(eventLocationRepository.save(eventLocation11).getId());

        EventLocation eventLocation12 = new EventLocation();
        eventLocation12.setEventId(event17.getId());
        eventLocation12.setFormality(Formality.ONLINE);
        eventLocation12.setName("Phòng Zoom");
        eventLocation12.setPath("https://us02web.zoom.us/j/5071725482");
        eventLocation12.setId(eventLocationRepository.save(eventLocation12).getId());

        // Bảng EventMajor
        EventMajor eventMajor1 = new EventMajor();
        eventMajor1.setEventId(event1.getId());
        eventMajor1.setMajorId(javaMajor.getId());
        eventMajor1.setId(eventMajorRepository.save(eventMajor1).getId());
        EventMajor eventMajor2 = new EventMajor();
        eventMajor2.setEventId(event1.getId());
        eventMajor2.setMajorId(cSharpMajor.getId());
        eventMajor2.setId(eventMajorRepository.save(eventMajor2).getId());

        EventMajor eventMajor3 = new EventMajor();
        eventMajor3.setEventId(event2.getId());
        eventMajor3.setMajorId(javaMajor.getId());
        eventMajor3.setId(eventMajorRepository.save(eventMajor3).getId());
        EventMajor eventMajor4 = new EventMajor();
        eventMajor4.setEventId(event2.getId());
        eventMajor4.setMajorId(cSharpMajor.getId());
        eventMajor4.setId(eventMajorRepository.save(eventMajor4).getId());

        EventMajor eventMajor5 = new EventMajor();
        eventMajor5.setEventId(event3.getId());
        eventMajor5.setMajorId(javaMajor.getId());
        eventMajor5.setId(eventMajorRepository.save(eventMajor5).getId());
        EventMajor eventMajor6 = new EventMajor();
        eventMajor6.setEventId(event3.getId());
        eventMajor6.setMajorId(cSharpMajor.getId());
        eventMajor6.setId(eventMajorRepository.save(eventMajor6).getId());

        EventMajor eventMajor7 = new EventMajor();
        eventMajor7.setEventId(event4.getId());
        eventMajor7.setMajorId(javaMajor.getId());
        eventMajor7.setId(eventMajorRepository.save(eventMajor7).getId());
        EventMajor eventMajor8 = new EventMajor();
        eventMajor8.setEventId(event4.getId());
        eventMajor8.setMajorId(cSharpMajor.getId());
        eventMajor8.setId(eventMajorRepository.save(eventMajor8).getId());

//        EventMajor eventMajor9 = new EventMajor();
//        eventMajor9.setEventId(event5.getId());
//        eventMajor9.setMajorId(udpm.getId());
//        eventMajor9.setId(eventMajorRepository.save(eventMajor9).getId());
        EventMajor eventMajor10 = new EventMajor();
        eventMajor10.setEventId(event5.getId());
        eventMajor10.setMajorId(cSharpMajor.getId());
        eventMajor10.setId(eventMajorRepository.save(eventMajor10).getId());

//        EventMajor eventMajor11 = new EventMajor();
//        eventMajor11.setEventId(event6.getId());
//        eventMajor11.setMajorId(udpm.getId());
//        eventMajor11.setId(eventMajorRepository.save(eventMajor11).getId());
        EventMajor eventMajor12 = new EventMajor();
        eventMajor12.setEventId(event6.getId());
        eventMajor12.setMajorId(cSharpMajor.getId());
        eventMajor12.setId(eventMajorRepository.save(eventMajor12).getId());

//        EventMajor eventMajor13 = new EventMajor();
//        eventMajor13.setEventId(event7.getId());
//        eventMajor13.setMajorId(udpm.getId());
//        eventMajor13.setId(eventMajorRepository.save(eventMajor13).getId());
        EventMajor eventMajor14 = new EventMajor();
        eventMajor14.setEventId(event7.getId());
        eventMajor14.setMajorId(javaMajor.getId());
        eventMajor14.setId(eventMajorRepository.save(eventMajor14).getId());

        EventMajor eventMajor15 = new EventMajor();
        eventMajor15.setEventId(event8.getId());
        eventMajor15.setMajorId(javaMajor.getId());
        eventMajor15.setId(eventMajorRepository.save(eventMajor15).getId());
        EventMajor eventMajor16 = new EventMajor();
        eventMajor16.setEventId(event8.getId());
        eventMajor16.setMajorId(cSharpMajor.getId());
        eventMajor16.setId(eventMajorRepository.save(eventMajor16).getId());

        EventMajor eventMajor17 = new EventMajor();
        eventMajor17.setEventId(event9.getId());
        eventMajor17.setMajorId(javaMajor.getId());
        eventMajor17.setId(eventMajorRepository.save(eventMajor17).getId());
        EventMajor eventMajor18 = new EventMajor();
        eventMajor18.setEventId(event9.getId());
        eventMajor18.setMajorId(cSharpMajor.getId());
        eventMajor18.setId(eventMajorRepository.save(eventMajor18).getId());

//        EventMajor eventMajor19 = new EventMajor();
//        eventMajor19.setEventId(event10.getId());
//        eventMajor19.setMajorId(javaMajor.getId());
//        eventMajor19.setId(eventMajorRepository.save(eventMajor19).getId());
        EventMajor eventMajor20 = new EventMajor();
        eventMajor20.setEventId(event10.getId());
        eventMajor20.setMajorId(cSharpMajor.getId());
        eventMajor20.setId(eventMajorRepository.save(eventMajor20).getId());

        //Bảng EventObject
        EventObject eventObject1 = new EventObject();
        eventObject1.setEventId(event1.getId());
        eventObject1.setObjectId(ky1.getId());
        eventObject1.setId(eventObjectRepository.save(eventObject1).getId());
        EventObject eventObject2 = new EventObject();
        eventObject2.setEventId(event1.getId());
        eventObject2.setObjectId(ky2.getId());
        eventObject2.setId(eventObjectRepository.save(eventObject2).getId());

        EventObject eventObject3 = new EventObject();
        eventObject3.setEventId(event2.getId());
        eventObject3.setObjectId(ky5.getId());
        eventObject3.setId(eventObjectRepository.save(eventObject3).getId());
        EventObject eventObject4 = new EventObject();
        eventObject4.setEventId(event2.getId());
        eventObject4.setObjectId(ky6.getId());
        eventObject4.setId(eventObjectRepository.save(eventObject4).getId());

        EventObject eventObject5 = new EventObject();
        eventObject5.setEventId(event3.getId());
        eventObject5.setObjectId(ky5.getId());
        eventObject5.setId(eventObjectRepository.save(eventObject5).getId());
        EventObject eventObject6 = new EventObject();
        eventObject6.setEventId(event3.getId());
        eventObject6.setObjectId(ky6.getId());
        eventObject6.setId(eventObjectRepository.save(eventObject6).getId());
        EventObject eventObject7 = new EventObject();
        eventObject7.setEventId(event3.getId());
        eventObject7.setObjectId(ky7.getId());
        eventObject7.setId(eventObjectRepository.save(eventObject7).getId());

        EventObject eventObject8 = new EventObject();
        eventObject8.setEventId(event4.getId());
        eventObject8.setObjectId(ky2.getId());
        eventObject8.setId(eventObjectRepository.save(eventObject8).getId());
        EventObject eventObject9 = new EventObject();
        eventObject9.setEventId(event4.getId());
        eventObject9.setObjectId(ky3.getId());
        eventObject9.setId(eventObjectRepository.save(eventObject9).getId());
        EventObject eventObject10 = new EventObject();
        eventObject10.setEventId(event4.getId());
        eventObject10.setObjectId(ky4.getId());
        eventObject10.setId(eventObjectRepository.save(eventObject10).getId());

        EventObject eventObject11 = new EventObject();
        eventObject11.setEventId(event5.getId());
        eventObject11.setObjectId(ky2.getId());
        eventObject11.setId(eventObjectRepository.save(eventObject11).getId());
        EventObject eventObject12 = new EventObject();
        eventObject12.setEventId(event5.getId());
        eventObject12.setObjectId(ky3.getId());
        eventObject12.setId(eventObjectRepository.save(eventObject12).getId());

        EventObject eventObject13 = new EventObject();
        eventObject13.setEventId(event6.getId());
        eventObject13.setObjectId(ky2.getId());
        eventObject13.setId(eventObjectRepository.save(eventObject13).getId());
        EventObject eventObject14 = new EventObject();
        eventObject14.setEventId(event6.getId());
        eventObject14.setObjectId(ky1.getId());
        eventObject14.setId(eventObjectRepository.save(eventObject14).getId());

        EventObject eventObject15 = new EventObject();
        eventObject15.setEventId(event7.getId());
        eventObject15.setObjectId(ky5.getId());
        eventObject15.setId(eventObjectRepository.save(eventObject15).getId());
        EventObject eventObject16 = new EventObject();
        eventObject16.setEventId(event7.getId());
        eventObject16.setObjectId(ky6.getId());
        eventObject16.setId(eventObjectRepository.save(eventObject16).getId());

        EventObject eventObject18 = new EventObject();
        eventObject18.setEventId(event8.getId());
        eventObject18.setObjectId(ky5.getId());
        eventObject18.setId(eventObjectRepository.save(eventObject18).getId());
        EventObject eventObject19 = new EventObject();
        eventObject19.setEventId(event8.getId());
        eventObject19.setObjectId(ky6.getId());
        eventObject19.setId(eventObjectRepository.save(eventObject19).getId());

        EventObject eventObject20 = new EventObject();
        eventObject20.setEventId(event9.getId());
        eventObject20.setObjectId(ky5.getId());
        eventObject20.setId(eventObjectRepository.save(eventObject20).getId());
        EventObject eventObject21 = new EventObject();
        eventObject21.setEventId(event9.getId());
        eventObject21.setObjectId(ky6.getId());
        eventObject21.setId(eventObjectRepository.save(eventObject21).getId());

        EventObject eventObject22 = new EventObject();
        eventObject22.setEventId(event10.getId());
        eventObject22.setObjectId(ky5.getId());
        eventObject22.setId(eventObjectRepository.save(eventObject22).getId());
        EventObject eventObject23 = new EventObject();
        eventObject23.setEventId(event10.getId());
        eventObject23.setObjectId(ky6.getId());
        eventObject23.setId(eventObjectRepository.save(eventObject23).getId());

        EventObject eventObject24 = new EventObject();
        eventObject24.setEventId(event11.getId());
        eventObject24.setObjectId(ky3.getId());
        eventObject24.setId(eventObjectRepository.save(eventObject24).getId());
        EventObject eventObject25 = new EventObject();
        eventObject25.setEventId(event10.getId());
        eventObject25.setObjectId(ky4.getId());
        eventObject25.setId(eventObjectRepository.save(eventObject25).getId());
        EventObject eventObject26 = new EventObject();
        eventObject26.setEventId(event10.getId());
        eventObject26.setObjectId(ky5.getId());
        eventObject26.setId(eventObjectRepository.save(eventObject26).getId());

        EventObject eventObject27 = new EventObject();
        eventObject27.setEventId(event17.getId());
        eventObject27.setObjectId(ky3.getId());
        eventObject27.setId(eventObjectRepository.save(eventObject27).getId());
        EventObject eventObject28 = new EventObject();
        eventObject28.setEventId(event17.getId());
        eventObject28.setObjectId(ky4.getId());
        eventObject28.setId(eventObjectRepository.save(eventObject28).getId());
        EventObject eventObject29 = new EventObject();
        eventObject29.setEventId(event17.getId());
        eventObject29.setObjectId(ky5.getId());
        eventObject29.setId(eventObjectRepository.save(eventObject29).getId());

        //Bảng Participant (Người tham gia)
        Participant participant1 = new Participant();
        participant1.setIdUser("345A4EAD-3514-4B59-C375-08DBE28ECB04".toLowerCase());
        participant1.setEmail("duongnttph25958@fpt.edu.vn");
        participant1.setEventId(event1.getId());
        participant1.setQuestion("Thầy cô cho em hỏi coding convention là gì ạ?");
        participant1.setAttendanceTime(event1.getStartTime() + 3600000L);
        participant1.setRate((byte) 5);
        participant1.setClassName("SD17311");
        participant1.setLecturerName("HangNT169-Nguyễn Thúy Hằng");
        participant1.setFeedback("Sự kiện rất tuyệt vời ạ");
        participant1.setId(participantRepository.save(participant1).getId());

        Participant participant2 = new Participant();
        participant2.setIdUser("5DAAA022-28CA-4B49-C378-08DBE28ECB04".toLowerCase());
        participant2.setEventId(event1.getId());
        participant2.setEmail("anhltvph25818@fpt.edu.vn");
        participant2.setQuestion("Em có đọc nhưng vẫn chưa hiểu về coding convention là gì?");
        participant2.setAttendanceTime(event1.getStartTime() + 3660000L);
        participant2.setRate((byte) 4);
        participant2.setClassName("SD17312");
        participant2.setLecturerName("NguyenVV4-Vũ Văn Nguyên");
        participant2.setFeedback("Rất mong có sự kiện như vậy trong thời gian tới ạ.");
        participant2.setId(participantRepository.save(participant2).getId());

        Participant participant3 = new Participant();
        participant3.setEventId(event2.getId());
        participant3.setIdUser("9AD27C93-9FD5-4280-C379-08DBE28ECB04".toLowerCase());
        participant3.setEmail("hoantph26002@fpt.edu.vn");
        participant3.setQuestion("Giải thuật là gì vậy ạ?");
        participant3.setAttendanceTime(null);
        participant3.setRate(null);
        participant3.setClassName("SD17332");
        participant3.setLecturerName("HangNT169-Nguyễn Thúy Hằng");
        participant3.setFeedback(null);
        participant3.setId(participantRepository.save(participant3).getId());

        Participant participant4 = new Participant();
        participant4.setIdUser("6F5A7458-53E4-4118-08CF-08DBE2931518".toLowerCase());
        participant4.setEventId(event2.getId());
        participant4.setEmail("quanvpdph17996@fpt.edu.vn");
        participant4.setQuestion("Em chưa hiểu về thuật toán ạ?");
        participant4.setAttendanceTime(null);
        participant4.setRate(null);
        participant4.setClassName("SD17311");
        participant4.setLecturerName("HangNT169-Nguyễn Thúy Hằng");
        participant4.setFeedback(null);
        participant4.setId(participantRepository.save(participant4).getId());

        Participant participant5 = new Participant();
        participant5.setIdUser("5DAAA022-28CA-4B49-C378-08DBE28ECB04".toLowerCase());
        participant5.setEventId(event3.getId());
        participant5.setEmail("anhltvph25818@fpt.edu.vn");
        participant5.setQuestion("Em có đọc nhưng vẫn chưa hiểu về coding convention là gì?");
        participant5.setAttendanceTime(event3.getStartTime() + 3660000L);
        participant5.setRate((byte) 5);
        participant5.setClassName("SD17312");
        participant5.setLecturerName("PhongTT35-Trần Tuấn Phong");
        participant5.setFeedback("Rất mong có sự kiện như vậy trong thời gian tới ạ.");
        participant5.setId(participantRepository.save(participant5).getId());

        Participant participant6 = new Participant();
        participant6.setIdUser("D0B66031-321F-4ED1-5649-08DBE2941DBF".toLowerCase());
        participant6.setEventId(event3.getId());
        participant6.setEmail("hailvph13128@fpt.edu.vn");
        participant6.setQuestion("Sự kiện này có tổ chức thường xuyên không ạ?");
        participant6.setAttendanceTime(event3.getStartTime() + 3660000L);
        participant6.setRate((byte) 4);
        participant6.setClassName("SD17312");
        participant6.setLecturerName("HangNT169-Nguyễn Thúy Hằng");
        participant6.setFeedback("Rất mong có sự kiện như vậy trong thời gian tới ạ.");
        participant6.setId(participantRepository.save(participant6).getId());

        Participant participant7 = new Participant();
        participant7.setIdUser("B5D5167F-B023-4FC5-08D0-08DBE2931518".toLowerCase());
        participant7.setEventId(event3.getId());
        participant7.setEmail("tungttph24146@fpt.edu.vn");
        participant7.setQuestion("Sự kiện này có tổ chức thường xuyên không ạ?");
        participant7.setAttendanceTime(event3.getStartTime() + 3660000L);
        participant7.setRate((byte) 5);
        participant7.setClassName("SD17312");
        participant7.setLecturerName("HangNT169-Nguyễn Thúy Hằng");
        participant7.setFeedback("Rất mong có sự kiện như vậy trong thời gian tới ạ.");
        participant7.setId(participantRepository.save(participant7).getId());

        Participant participant8 = new Participant();
        participant8.setIdUser("B5D5167F-B023-4FC5-08D0-08DBE2931518".toLowerCase());
        participant8.setEventId(event4.getId());
        participant8.setEmail("tungttph24146@fpt.edu.vn");
        participant8.setQuestion("Tham gia sự kiện muộn có được điểm danh không ạ?");
        participant8.setAttendanceTime(event4.getStartTime() + 3660000L);
        participant8.setRate((byte) 3);
        participant8.setClassName("SD17333");
        participant8.setLecturerName("HangNT169-Nguyễn Thúy Hằng");
        participant8.setFeedback("Sự kiện rất hữu ích ạ");
        participant8.setId(participantRepository.save(participant8).getId());

        Participant participant9 = new Participant();
        participant9.setIdUser("AD19C4F8-9B1A-42C3-564B-08DBE2941DBF".toLowerCase());
        participant9.setEventId(event4.getId());
        participant9.setEmail("hieuhxph26626@fpt.edu.vn");
        participant9.setQuestion("Tham gia sự kiện muộn có được điểm danh không ạ?");
        participant9.setAttendanceTime(event4.getStartTime() + 3660000L);
        participant9.setRate((byte) 5);
        participant9.setClassName("SD17345");
        participant9.setLecturerName("NguyenVV4-Vũ Văn Nguyên");
        participant9.setFeedback(null);
        participant9.setId(participantRepository.save(participant9).getId());

        Participant participant10 = new Participant();
        participant10.setIdUser("AD19C4F8-9B1A-42C3-564B-08DBE2941DBF".toLowerCase());
        participant10.setEventId(event5.getId());
        participant10.setEmail("hieuhxph26626@fpt.edu.vn");
        participant10.setQuestion("Tham gia sự kiện muộn có được điểm danh không ạ?");
        participant10.setAttendanceTime(event5.getStartTime() + 3660000L);
        participant10.setRate((byte) 5);
        participant10.setClassName("SD17345");
        participant10.setLecturerName("NguyenVV4-Vũ Văn Nguyên");
        participant10.setFeedback(null);
        participant10.setId(participantRepository.save(participant10).getId());

        Participant participant11 = new Participant();
        participant11.setIdUser("D0C790F2-09CD-4879-1F88-08DBE29418E7".toLowerCase());
        participant11.setEventId(event5.getId());
        participant11.setEmail("hieundph25894@fpt.edu.vn");
        participant11.setQuestion("Sự kiện này có tổ chức thường xuyên không ạ?");
        participant11.setAttendanceTime(event5.getStartTime() + 3660000L);
        participant11.setRate((byte) 5);
        participant11.setClassName("SD17345");
        participant11.setLecturerName("NguyenVV4-Vũ Văn Nguyên");
        participant11.setFeedback(null);
        participant11.setId(participantRepository.save(participant11).getId());

        Participant participant12 = new Participant();
        participant12.setIdUser("D0C790F2-09CD-4879-1F88-08DBE29418E7".toLowerCase());
        participant12.setEventId(event6.getId());
        participant12.setEmail("hieundph25894@fpt.edu.vn");
        participant12.setQuestion("Sự kiện này có tổ chức thường xuyên không ạ?");
        participant12.setAttendanceTime(event6.getStartTime() + 3660000L);
        participant12.setRate((byte) 5);
        participant12.setClassName("SD17345");
        participant12.setLecturerName("NguyenVV4-Vũ Văn Nguyên");
        participant12.setFeedback("Khi nào thì được nhận tài nguyên ạ");
        participant12.setId(participantRepository.save(participant12).getId());

        Participant participant13 = new Participant();
        participant13.setIdUser("167CA279-0E43-429D-1F8A-08DBE29418E7".toLowerCase());
        participant13.setEventId(event6.getId());
        participant13.setEmail("huyvqph25924@fpt.edu.vn");
        participant13.setQuestion("Em vẫn chưa hiểu rõ về chủ đề của sự kiện?");
        participant13.setAttendanceTime(event6.getStartTime() + 3660000L);
        participant13.setRate((byte) 5);
        participant13.setClassName("SD17333");
        participant13.setLecturerName("TienNH21-Nguyễn Hoàng Tiến");
        participant13.setFeedback("Sự kiện rất hữu ích ạ");
        participant13.setId(participantRepository.save(participant13).getId());

        Participant participant14 = new Participant();
        participant14.setIdUser("9AD27C93-9FD5-4280-C379-08DBE28ECB04".toLowerCase());
        participant14.setEventId(event7.getId());
        participant14.setEmail("hoantph26002@fpt.edu.vn");
        participant14.setQuestion("Em vẫn chưa hiểu rõ về chủ đề của sự kiện?");
        participant14.setAttendanceTime(event7.getStartTime() + 3660000L);
        participant14.setRate((byte) 5);
        participant14.setClassName("SD17333");
        participant14.setLecturerName("TienNH21-Nguyễn Hoàng Tiến");
        participant14.setFeedback("Sự kiện rất hữu ích ạ");
        participant14.setId(participantRepository.save(participant14).getId());

        Participant participant15 = new Participant();
        participant15.setIdUser("345A4EAD-3514-4B59-C375-08DBE28ECB04".toLowerCase());
        participant15.setEventId(event7.getId());
        participant15.setEmail("duongnttph25958@fpt.edu.vn");
        participant15.setQuestion("Em vẫn chưa hiểu rõ về chủ đề của sự kiện?");
        participant15.setAttendanceTime(event7.getStartTime() + 3660000L);
        participant15.setRate((byte) 4);
        participant15.setClassName("SD17333");
        participant15.setLecturerName("TienNH21-Nguyễn Hoàng Tiến");
        participant15.setFeedback(null);
        participant15.setId(participantRepository.save(participant15).getId());

        Participant participant16 = new Participant();
        participant16.setIdUser("345A4EAD-3514-4B59-C375-08DBE28ECB04".toLowerCase());
        participant16.setEventId(event8.getId());
        participant16.setEmail("duongnttph25958@fpt.edu.vn");
        participant16.setQuestion("Em vẫn chưa hiểu rõ về chủ đề của sự kiện?");
        participant16.setAttendanceTime(event8.getStartTime() + 3660000L);
        participant16.setRate((byte) 4);
        participant16.setClassName("SD17789");
        participant16.setLecturerName("HangNT169-Nguyễn Thúy Hằng");
        participant16.setFeedback(null);
        participant16.setId(participantRepository.save(participant16).getId());

        Participant participant17 = new Participant();
        participant17.setIdUser("356F2422-D659-4B4F-1F8B-08DBE29418E7".toLowerCase());
        participant17.setEventId(event8.getId());
        participant17.setEmail("dungnpph25823@fpt.edu.vn");
        participant17.setQuestion("Em vẫn chưa hiểu rõ về chủ đề của sự kiện?");
        participant17.setAttendanceTime(event8.getStartTime() + 3660000L);
        participant17.setRate((byte) 5);
        participant17.setClassName("SD17789");
        participant17.setLecturerName("HangNT169-Nguyễn Thúy Hằng");
        participant17.setFeedback("Không có");
        participant17.setId(participantRepository.save(participant17).getId());

        Participant participant18 = new Participant();
        participant18.setIdUser("32DF0265-D150-48D0-C377-08DBE28ECB04".toLowerCase());
        participant18.setEventId(event9.getId());
        participant18.setEmail("anhdtnph25326@fpt.edu.vn");
        participant18.setQuestion("Em vẫn chưa hiểu rõ về chủ đề của sự kiện?");
        participant18.setAttendanceTime(event9.getStartTime() + 3660000L);
        participant18.setRate((byte) 5);
        participant18.setClassName("SD17541");
        participant18.setLecturerName("HangNT169-Nguyễn Thúy Hằng");
        participant18.setFeedback("Không có");
        participant18.setId(participantRepository.save(participant18).getId());

        Participant participant19 = new Participant();
        participant19.setIdUser("5DAAA022-28CA-4B49-C378-08DBE28ECB04".toLowerCase());
        participant19.setEventId(event9.getId());
        participant19.setEmail("anhltvph25818@fpt.edu.vn");
        participant19.setQuestion("Em vẫn chưa hiểu rõ về chủ đề của sự kiện?");
        participant19.setAttendanceTime(event9.getStartTime() + 3660000L);
        participant19.setRate((byte) 4);
        participant19.setClassName("SD17256");
        participant19.setLecturerName("HangNT169-Nguyễn Thúy Hằng");
        participant19.setFeedback(null);
        participant19.setId(participantRepository.save(participant19).getId());

        Participant participant20 = new Participant();
        participant20.setIdUser("32DF0265-D150-48D0-C377-08DBE28ECB04".toLowerCase());
        participant20.setEventId(event10.getId());
        participant20.setEmail("anhdtnph25326@fpt.edu.vn");
        participant20.setQuestion("Coding convention nghĩa là gì vậy ạ?");
        participant20.setAttendanceTime(event10.getStartTime() + 3660000L);
        participant20.setRate((byte) 5);
        participant20.setClassName("SD17256");
        participant20.setLecturerName("NguyenVV4-Vũ Văn Nguyên");
        participant20.setFeedback(null);
        participant20.setId(participantRepository.save(participant20).getId());

        Participant participant21 = new Participant();
        participant21.setIdUser("AD19C4F8-9B1A-42C3-564B-08DBE2941DBF".toLowerCase());
        participant21.setEventId(event10.getId());
        participant21.setEmail("hieuhxph26626@fpt.edu.vn");
        participant21.setQuestion("Coding convention nghĩa là gì vậy ạ?");
        participant21.setAttendanceTime(event10.getStartTime() + 3660000L);
        participant21.setRate((byte) 5);
        participant21.setClassName("SD17256");
        participant21.setLecturerName("NguyenVV4-Vũ Văn Nguyên");
        participant21.setFeedback("Khi nào tổ chức sự kiện này tiếp vậy ạ?");
        participant21.setId(participantRepository.save(participant21).getId());

        Participant participant22 = new Participant();
        participant22.setIdUser("AD19C4F8-9B1A-42C3-564B-08DBE2941DBF".toLowerCase());
        participant22.setEventId(event11.getId());
        participant22.setEmail("hieuhxph26626@fpt.edu.vn");
        participant22.setQuestion("Coding convention nghĩa là gì vậy ạ?");
        participant22.setAttendanceTime(event11.getStartTime() + 3660000L);
        participant22.setRate((byte) 5);
        participant22.setClassName("SD17333");
        participant22.setLecturerName("PhongTT35-Trần Tuấn Phong");
        participant22.setFeedback("Khi nào tổ chức sự kiện này tiếp vậy ạ?");
        participant22.setId(participantRepository.save(participant22).getId());

        Participant participant23 = new Participant();
        participant23.setIdUser("AD19C4F8-9B1A-42C3-564B-08DBE2941DBF".toLowerCase());
        participant23.setEventId(event17.getId());
        participant23.setEmail("hieuhxph26626@fpt.edu.vn");
        participant23.setQuestion("Tester là gì?");
        participant23.setAttendanceTime(event17.getStartTime() + 3660000L);
        participant23.setRate(null);
        participant23.setClassName("SD17333");
        participant23.setLecturerName("HangNT169-Nguyễn Thúy Hằng");
        participant23.setFeedback(null);
        participant23.setId(participantRepository.save(participant23).getId());

        Participant participant24 = new Participant();
        participant24.setIdUser("D0B66031-321F-4ED1-5649-08DBE2941DBF".toLowerCase());
        participant24.setEventId(event17.getId());
        participant24.setEmail("hailvph13128@fpt.edu.vn");
        participant24.setQuestion("Con đường phát triển của một tester là gì?");
        participant24.setAttendanceTime(event17.getStartTime() + 3660000L);
        participant24.setRate(null);
        participant24.setClassName("SD17333");
        participant24.setLecturerName("HangNT169-Nguyễn Thúy Hằng");
        participant24.setFeedback(null);
        participant24.setId(participantRepository.save(participant24).getId());

        Participant participant25 = new Participant();
        participant25.setIdUser("32DF0265-D150-48D0-C377-08DBE28ECB04".toLowerCase());
        participant25.setEventId(event17.getId());
        participant25.setEmail("anhdtnph25326@fpt.edu.vn");
        participant25.setQuestion("Con đường phát triển của một tester là gì?");
        participant25.setAttendanceTime(event17.getStartTime() + 3660000L);
        participant25.setRate(null);
        participant25.setClassName("SD17333");
        participant25.setLecturerName("HangNT169-Nguyễn Thúy Hằng");
        participant25.setFeedback(null);
        participant25.setId(participantRepository.save(participant24).getId());

        //Bảng Comment (commnent trong chi tiết sự kiện)
        Comment comment1 = new Comment();
        comment1.setUserId(participant1.getIdUser());
        comment1.setEventId(event1.getId());
        comment1.setComment("Tuy chưa hiểu lắm nhưng vui");
        comment1.setReplyId(null);
        comment1.setId(commentRepository.save(comment1).getId());

        Comment comment2 = new Comment();
        comment2.setUserId(participant2.getIdUser());
        comment2.setEventId(event1.getId());
        comment2.setComment("Bao giờ mới được buổi như này nữa nhỉ");
        comment2.setReplyId(null);
        comment2.setId(commentRepository.save(comment2).getId());

        Comment comment3 = new Comment();
        comment3.setUserId(participant1.getIdUser());
        comment3.setEventId(event1.getId());
        comment3.setComment("Sắp rồi nhé!");
        comment3.setReplyId(comment2.getId());
        comment3.setId(commentRepository.save(comment3).getId());

        Comment comment4 = new Comment();
        comment4.setUserId(participant3.getIdUser());
        comment4.setEventId(event2.getId());
        comment4.setComment("Em mong có một buổi như vậy tiếp ạ");
        comment4.setReplyId(null);
        comment4.setId(commentRepository.save(comment4).getId());

        Comment comment5 = new Comment();
        comment5.setUserId(participant4.getIdUser());
        comment5.setEventId(event2.getId());
        comment5.setComment("Xem rồi mới biết mình gà");
        comment5.setReplyId(null);
        comment5.setId(commentRepository.save(comment5).getId());

        Comment comment6 = new Comment();
        comment6.setUserId(participant24.getIdUser());
        comment6.setEventId(event17.getId());
        comment6.setComment("Rất mong chờ sự kiện");
        comment6.setReplyId(null);
        comment6.setId(commentRepository.save(comment6).getId());

        Comment comment7 = new Comment();
        comment7.setUserId(participant25.getIdUser());
        comment7.setEventId(event17.getId());
        comment7.setComment("Hóng hóng");
        comment7.setReplyId(null);
        comment7.setId(commentRepository.save(comment6).getId());

        Comment comment8 = new Comment();
        comment8.setUserId(participant25.getIdUser());
        comment8.setEventId(event17.getId());
        comment8.setComment("Em muốn trở thành một Tester chuyên nghiệp");
        comment8.setReplyId(null);
        comment8.setId(commentRepository.save(comment8).getId());

        /**
         * Bảng System Option
         */
        SystemOption systemOption = new SystemOption();
        systemOption.setId(1L);
        systemOption.setMandatoryApprovalDays((byte) 7);
        systemOption.setIsAllowNotEnoughTimeApproval(true);
        systemOption.setIsAllowCloseEvent(true);
        systemOption.setMinimumCloseTime(360000L);
		systemOptionRepository.save(systemOption);
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(DBGenerator.class);
        ctx.close();
    }
}
