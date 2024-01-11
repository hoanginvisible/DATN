import React, {useEffect, useState} from "react";
import {Table,} from "antd";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faList} from "@fortawesome/free-solid-svg-icons";
import APStatisticsEventApi from "../APStatisticsEventApi";


const APSEListLecturerTop = ({idSemester}) => {
    const [listOrganizer, setListOrganizer] = useState([]);

    useEffect(() => {
        APStatisticsEventApi.getListOrganizer(idSemester).then(
            (res) => {
                setListOrganizer(res.data.data)
            },
            (err) => {
                console.log(err);
            }
        );
    }, [idSemester]);

    const columns = [
        {
            title: "STT",
            dataIndex: "recordNumber",
            key: "recordNumber",
            render: (text, record, index) => <span>{index + 1}</span>,
        },
        {
            title: "Họ tên",
            dataIndex: "name",
            key: "name",
        },
        {
            title: "Email",
            dataIndex: "email",
            key: "email",
        },
        {
            title: "Số lượng sự kiện",
            key: "quantityEvent",
            dataIndex: "quantityEvent",
        }
    ];

    return (
        <div>
            <h4 className="title-comment">
                <FontAwesomeIcon icon={faList} style={{color: "#172b4d"}}/>
                <span style={{marginLeft: "7px"}}>Danh sách giảng viên tham gia nhiều sự kiện</span>
            </h4>
            <Table
                dataSource={listOrganizer}
                columns={columns}
                bordered
                pagination={false}
            />
        </div>
    )
}

export default APSEListLecturerTop;
