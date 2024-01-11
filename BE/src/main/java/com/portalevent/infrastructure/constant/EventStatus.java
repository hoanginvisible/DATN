package com.portalevent.infrastructure.constant;

/**
 * @author SonPT
 */
public enum EventStatus {
    CLOSE, // Đóng

    SCHEDULED_HELD, // dự kiến tổ chức

    EDITING, // Cần sửa

    WAITING_APPROVAL, // Chờ phê duyệt

    APPROVED, // Đã phê duyệt

    ORGANIZED // Đã tổ chức
}
