import React, { useState, useEffect } from "react";
import * as XLSX from "xlsx";
import { APAttendanceListApi } from "./APAttendanceListApi";
import { useParams } from "react-router-dom";
import { message, Button } from "antd";
import dayjs from "dayjs";
import { faFileCsv } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

const APExportExcel = ({ list }) => {
    const exportToExcel = () => {
        if (list.length < 1) {
            message.warning("Danh sách điểm danh trống!");
            return;
        }
        const filteredData = list.map((item) => ({
            STT: item.index,
            ClassName: item.className,
            Email: item.email,
            AttendanceTime: dayjs(item.attendanceTime).format("DD-MM-YYYY HH:mm:ss"),
            Rate: item.rate,
            Feedback: item.feedback,
        }));
        const ws = XLSX.utils.json_to_sheet(filteredData);
        const wb = XLSX.utils.book_new();
        XLSX.utils.book_append_sheet(wb, ws, "Sheet1");

        // Cài đặt tiêu đề cho các cột
        ws["A1"] = { v: "STT", t: "s" };
        ws["B1"] = { v: "Lớp học", t: "s" };
        ws["C1"] = { v: "Email", t: "s" };
        ws["D1"] = { v: "Thời gian tham dự", t: "s" };
        ws["E1"] = { v: "Rate", t: "s" };
        ws["F1"] = { v: "Feedback", t: "s" };

        const headerStyle = {
            font: { bold: true, sz: 14 },
            fill: {
                fgColor: { rgb: "FFFF00" }, // Mã màu vàng
            },
        };

        ws["A1"].s = headerStyle;
        ws["B1"].s = headerStyle;
        ws["C1"].s = headerStyle;
        ws["D1"].s = headerStyle;
        ws["E1"].s = headerStyle;
        ws["F1"].s = headerStyle;
        XLSX.utils.sheet_add_aoa(ws, [], { origin: -1, extend: true });
        XLSX.writeFile(wb, `${"Danh sách người tham gia sự kiện"}.xlsx`);
    };

    // return <button onClick={exportToExcel}>Xuất Excel</button>;
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
                icon={faFileCsv}
                style={{ color: "#ffffff", marginLeft: "7px" }}
            />
        </Button>
    );
};

export default APExportExcel;
