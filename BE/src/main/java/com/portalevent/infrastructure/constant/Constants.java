package com.portalevent.infrastructure.constant;

/**
 * @author SonPT
 */
public final class Constants {

    private Constants() {
    }

    public static final String VERSION = "v1.0";

    public static final String ENCODING_UTF8 = "UTF-8";

    public class FileProperties {
        private FileProperties() {
        }

        public static final String PROPERTIES_APPLICATION = "application.properties";
        public static final String VERSION_PROPERTIES = "version.properties";
    }

    public class DisplayName {
        public DisplayName() {}

        /**
         * Người phê duyệt
         */
        public static final String APPROVER_CATEGORY_MANAGEMENT = "quan.ly.the.loai";
        public static final String APPROVER_EVENT_APPROVED = "su.kien.da.phe.duyet";
        public static final String APPROVER_ATTENDANCE_LIST = "danh.sach.diem.danh";
        public static final String APPROVER_EVENT_CLOSED = "su.kien.da.dong";
        public static final String APPROVER_EVENT_DETAIL = "chi.tiet.su.kien";
        public static final String APPROVER_EVENT_WAITING_APPROVAL = "su.kien.cho.phe.duyet";
        public static final String APPROVER_MAJOR_MANAGEMENT = "quan.ly.chuyen.nganh";
        public static final String APPROVER_OBJECT_MANAGEMENT = "quan.doi.tuong";
        public static final String APPROVER_PERIODIC_EVENT = "su.kien.trong.ky";
        public static final String APPROVER_REGISTRATION_LIST = "danh.sach.dang.ky";
        public static final String APPROVER_SEMESTER_MANAGEMENT = "quan.ly.hoc.ky";
        public static final String APPROVER_STATISTICS_EVENT = "thong.ke.nguoi.phe.duyet";

        /***************************
         * Người tổ chức
         */
        public static final String ORGANIZER_ATTENDANCE_LIST = "danh.sach.diem.danh";
        public static final String ORGANIZER_EVENT_DETAIL = "chi.tiet.su.kien";
        public static final String ORGANIZER_EVENT_REGISTER = "dang.ky.su.kien";
        public static final String ORGANIZER_EVENT_REGISTERED = "su.kien.da.dang.ky";
        public static final String ORGANIZER_IMPORT_TUTORIAL = "import.lich.tutorial";
        public static final String ORGANIZER_MULTIPLE_REGISTER = "dang.ky.hang.loat";
        public static final String ORGANIZER_PERIODIC_EVENT = "su.kien.hang.ky";
        public static final String ORGANIZER_REGISTRATION_LIST = "danh.sach.diem.danh";
        public static final String ORGANIZER_STATISTIC_EVENT = "thong.ke.nguoi.to.chuc";
        /**
         * Hành chính
         */
        public static final String ADMINISTRATIVE_EVENT_IN_SEMESTER = "su.kien.trong.ky";
        public static final String ADMINISTRATIVE_HIRE_DESIGN_LIST = "su.kien.can.booking";

    }

    public class UrlPath {
        private UrlPath() {}

        public static final String URL_API_PARTICIPANT = "/api/home";

        public static final String URL_API_ORGANIZER_MANAGEMENT = "/api/organizer-management";

        public static final String URL_API_APPROVER_MANAGEMENT = "/api/approver-management";

        public static final String URL_API_ADMINISTRATIVE = "/api/administrative";

        /*****************************
         * Các API của người phê duyệt
         */
        //Màn hình quản lý thể loại
        public static final String URL_API_APPROVER_CATEGORY_MANAGEMENT = URL_API_APPROVER_MANAGEMENT + "/category-management";
        //Màn hình sự kiện đã phê duyệt
        public static final String URL_API_APPROVER_EVENT_APPROVED = URL_API_APPROVER_MANAGEMENT + "/event-approved";
        //Màn hình danh sách điểm danh
        public static final String URL_API_APPROVER_ATTENDANCE_LIST = URL_API_APPROVER_MANAGEMENT + "/attendance-list";
        //Màn hình sự kiện đã đóng
        public static final String URL_API_APPROVER_EVENT_CLOSED = URL_API_APPROVER_MANAGEMENT + "/event-closed";
        //Màn hình chi tiết sự kiện
        public static final String URL_API_APPROVER_EVENT_DETAIL = URL_API_APPROVER_MANAGEMENT + "/event-detail";
        //Màn hình sự kiện chờ phê duyệt
        public static final String URL_API_APPROVER_EVENT_WAITING_APPROVAL = URL_API_APPROVER_MANAGEMENT + "/event-approval";
        //Màn hình quản lý chuyên ngành
        public static final String URL_API_APPROVER_MAJOR_MANAGEMENT = URL_API_APPROVER_MANAGEMENT + "/major-manager";
        //Màn hình quản lý đối tượng
        public static final String URL_API_APPROVER_OBJECT_MANAGEMENT = URL_API_APPROVER_MANAGEMENT + "/object-management";
        //Màn hình sự kiện hằng kỳ
        public static final String URL_API_APPROVER_PERIODIC_EVENT = URL_API_APPROVER_MANAGEMENT + "/periodic-event";
        //Màn hình danh sách đăng ký
        public static final String URL_API_APPROVER_REGISTRATION_LIST = URL_API_APPROVER_MANAGEMENT + "/registration-list";
        //Màn hình quản lý học kỳ
        public static final String URL_API_APPROVER_SEMESTER_MANAGEMENT = URL_API_APPROVER_MANAGEMENT + "/semester";
        //Màn hình thống kê của Người phê duyệt
        public static final String URL_APO_APPROVER_STATISTICS_EVENT = URL_API_APPROVER_MANAGEMENT + "/statistic-event";

        /***************************
         * Các API của người tổ chức
         */
        //Màn hình danh sách điểm danh
        public static final String URL_API_ORGANIZER_ATTENDANCE_LIST = URL_API_ORGANIZER_MANAGEMENT + "/attendance-list";
        //Màn hình chi tiết sự kiện
        public static final String URL_API_ORGANIZER_EVENT_DETAIL = URL_API_ORGANIZER_MANAGEMENT + "/event-detail";
        //Màn hình đăng ký sự kiện
        public static final String URL_API_ORGANIZER_EVENT_REGISTER = URL_API_ORGANIZER_MANAGEMENT + "/event-register";
        //Màn hình sự kiện đã đăng ký
        public static final String URL_API_ORGANIZER_EVENT_REGISTERED = URL_API_ORGANIZER_MANAGEMENT + "/event-registered";
        //Màn hình import danh sách buổi tutorial
        public static final String URL_API_ORGANIZER_IMPORT_TUTORIAL = URL_API_ORGANIZER_MANAGEMENT + "/import-tutorials";
        //Màn hình đăng ký sự kiện hàng loạt
        public static final String URL_API_ORGANIZER_MULTIPLE_REGISTER = URL_API_ORGANIZER_MANAGEMENT + "/multiple-register";
        //Màn hình sự kiện hằng kỳ
        public static final String URL_API_ORGANIZER_PERIODIC_EVENT = URL_API_ORGANIZER_MANAGEMENT + "/periodic-event";
        //Màn hình danh sách điểm danh
        public static final String URL_API_ORGANIZER_REGISTRATION_LIST = URL_API_ORGANIZER_MANAGEMENT + "/registration-list";
        //Màn hình thống kê
        public static final String URL_API_ORGANIZER_STATISTIC_EVENT = URL_API_ORGANIZER_MANAGEMENT + "/statistic-event";
        /**
         * Các API của hành chính
         */
        //Màn hình sự kiện trong kỳ
        public static final String URL_API_ADMINISTRATIVE_EVENT_IN_SEMESTER = URL_API_ADMINISTRATIVE + "/event-in-semester";
        //Màn hình sự kiện cần booking
        public static final String URL_API_ADMINISTRATIVE_HIRE_DESIGN_LIST = URL_API_ADMINISTRATIVE + "/hire-design";

        /**
         * API hệ thống
         */
        public static final String URL_API_HISTORY = "/api/system";

    }

    public static final String REGEX_EMAIL_FPT = "\\w+@fpt.edu.vn";

    public static final String REGEX_PHONE_NUMBER = "(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}";

    public static final String REGEX_DATE ="^(0[1-9]|1[012])/(0[1-9]|[12][0-9]|[3][01])/\\\\d{4}$";

}