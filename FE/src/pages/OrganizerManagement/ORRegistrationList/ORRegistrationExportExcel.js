import React from "react";
import * as XLSX from "xlsx";
import { message, Button } from "antd";
import dayjs from "dayjs";
import { faFileExcel } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

const ORRegistrationExportExcel = ({ list }) => {
    const exportToExcel = () => {
        if (list.length < 1) {
            message.warning("Danh sách tham gia trống!");
            return;
        }
        const filteredData = list.map((item) => ({
            STT: item.stt,
            ClassName: item.className,
            Email: item.email,
            CreateDate: dayjs(item.createDate).format("HH:mm DD-MM-YYYY"),
            LecturerName: item.lecturerName,
            Question: item.question,
        }));
        const ws = XLSX.utils.json_to_sheet(filteredData);
        const wb = XLSX.utils.book_new();
        XLSX.utils.book_append_sheet(wb, ws, "RegistrationList");

        const headerStyle = {
            font: {
                bold: true,
                sz: 16,
                color: { rgb: "FFFFFF00" }, // Màu vàng
            },
            fill: {
                fgColor: { rgb: "FF0000FF" }, // Màu đỏ
            },
        };

        // Cài đặt tiêu đề cho các cột
        ws["A1"] = { v: "STT", t: "s" };
        ws["B1"] = { v: "Lớp học", t: "s" };
        ws["C1"] = { v: "Email", t: "s" };
        ws["D1"] = { v: "Thời gian đăng ký", t: "s" };
        ws["E1"] = { v: "Tên giảng viên", t: "s" };
        ws["F1"] = { v: "Câu hỏi", t: "s" };

        ws["A1"].s = headerStyle;
        ws["B1"].s = headerStyle;
        ws["C1"].s = headerStyle;
        ws["D1"].s = headerStyle;
        ws["E1"].s = headerStyle;
        ws["F1"].s = headerStyle;

        ws['!cols'] = [
            { wch: 5 },   // Độ rộng cột A (STT)
            { wch: 15 },  // Độ rộng cột B (Lớp học)
            { wch: 20 },  // Độ rộng cột C (Email)
            { wch: 20 },  // Độ rộng cột D (Thời gian tham dự)
            { wch: 25 },  // Độ rộng cột E (Tên giảng viên)
            { wch: 50 },  // Độ rộng cột F (Câu hỏi)
        ];        
        XLSX.utils.sheet_add_aoa(ws, [], { origin: -1, extend: true });
        XLSX.writeFile(wb, `${"Danh sách tham gia"}.xlsx`);
    };

    return (
        <Button
            type="primary"
            className="btn-form-event"
            onClick={exportToExcel}
            style={{
                backgroundColor: "#217346",
            }}
        >
            Xuất Excel
            <FontAwesomeIcon
                icon={faFileExcel}
                style={{ color: "#ffffff", marginLeft: "7px" }}
            />
        </Button>
    );
};

export default ORRegistrationExportExcel;
