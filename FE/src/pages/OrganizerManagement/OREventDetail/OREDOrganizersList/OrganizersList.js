import React, { useEffect, useState } from "react";
import {
    Button,
    Input,
    Popconfirm,
    Select,
    Space,
    Table,
    message,
} from "antd";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faList, faPencilAlt, faPlus, faTrash } from "@fortawesome/free-solid-svg-icons";
import OREventDetailApi from "../../OREventDetail/OREventDetailApi";
import { useParams } from "react-router-dom";
import { DeleteOutlined } from "@ant-design/icons";
import ModalAddOrganizer from "./modal-add-organizer/ModalAddOrganizer";
import { organizerCurent } from "../../../../helper/UserCurrent";
import ModalUpdateOrganizer from "./modal-update-organizer/ModalUpdateOrganizer";

const OrganizersList = ({ isDisable, checkRole }) => {
    let [listOrganizer, setListOrganizer] = useState([]);
    let [listAgenda, setListAgenda] = useState([]);
    let [organizer, setOrganizer] = useState({});
    const { id } = useParams();

    useEffect(() => {
        loadOrganizersByIdEvent();
        loadDataAgenda();
    }, [id]);

    // Lấy danh sách người tổ chức
    const loadOrganizersByIdEvent = () => {
        OREventDetailApi.getOrganizersByIdEvent(id).then(
            (res) => {
                setListOrganizer(res.data.data);
            }
        ).catch((err) => {
            message.error('Lỗi hệ thống. Không thể lấy danh sách người tổ chức của sự kiện');
        });
    }

    // Delete người tổ chức
    const deleteEventOrganizer = (idEventOrganizer, organizerId) => {
        let data = {
            id: idEventOrganizer,
            organizerId: organizerId,
            eventId: id,
            idUserCurrent: organizerCurent.id,
        };
        OREventDetailApi.deleteOrganizer(data).then(
            (res) => {
                let updateListOrganizer = listOrganizer.filter(
                    (record) => record.id !== res.data.data
                );
                setListOrganizer(updateListOrganizer);
                message.error("Xóa thành công");
            }
        ).catch((err) => {
            message.error(err.response.data.message);
        });
    };

    // Lấy danh sách agenda
    const loadDataAgenda = () => {
        OREventDetailApi.getAgendasByIdEvent(id).then(
            (res) => {
                setListAgenda(res.data.data);
            }
        ).catch((err) => {
            message.error('Lỗi hệ thống. Không thể lấy danh sách agenda của sự kiện');
        });
    };

    // Đóng / mở của modal add người tổ chức
    const [isModalAddOrganizer, setIsModalAddOrganizer] = useState(false);
    const openModalAddOrganizer = () => {
        setIsModalAddOrganizer(true);
    };
    const onCancelModalAddOrganizer = () => {
        setIsModalAddOrganizer(false);
    };

    // Đóng / mở của modal update người tổ chức
    const [isModalUpdateOrganizer, setIsModalUpdateOrganizer] = useState(false);
    const openModalUpdateOrganizer = (record) => {
        setOrganizer(record);
        setIsModalUpdateOrganizer(true);
    };
    const onCancelModalUpdateOrganizer = () => {
        loadOrganizersByIdEvent();
        setIsModalUpdateOrganizer(false);
    };

    // Các cột của bảng người tổ chức
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
            title: "Tên tài khoản",
            dataIndex: "username",
            key: "username",
        },
        {
            title: "Email",
            dataIndex: "email",
            key: "email",
        },
        {
            title: "Vai trò",
            dataIndex: "eventRole",
            key: "eventRole",
            render: (text, record) => (
                <Space>
                    {record.eventRole === 0 && <span>Host</span>}
                    {record.eventRole === 1 && <span>Co-Host</span>}
                </Space>
            ),
        },
        {
            title: "Hành động",
            dataIndex: "actions",
            key: "actions",
            render: (text, record) => (
                <Space size="middle">
                    <FontAwesomeIcon
                        className="custom-icon"
                        icon={faPencilAlt}
                        style={{ marginRight: "15px" }}
                        onClick={() => {
                            openModalUpdateOrganizer(record);
                        }}
                    />
                    {record.eventRole !== 0 && (
                        <Popconfirm
                            title="Bạn chắc chắn muốn xóa người tổ chức này không?"
                            onConfirm={() => {
                                deleteEventOrganizer(record.id, record.organizerId);
                            }}
                            okText="Có"
                            cancelText="Không"
                        >
                            <Button type="link" icon={<DeleteOutlined />} />
                        </Popconfirm>
                    )}
                </Space>
            ),
        },
    ];
    const dynamicColumns = isDisable
        ? columns.filter((column) => column.key !== "actions")
        : columns;

    // Thêm dòng trong bảng agenda
    const handleAddRow = () => {
        let randomNumber = '';
        let check = true;
        while (check) {
            randomNumber = (Math.floor(Math.random() * 1000) + 1) + '';
            // eslint-disable-next-line no-loop-func
            let obj = listAgenda.find((item) => {
                return item.index === randomNumber;
            });
            if (typeof obj === 'undefined') {
                check = false;
            }
        }
        if (listAgenda.length < 1) {
            const newRow = {
                index: randomNumber,
                id: '',
                eventId: id,
                organizerId: '',
                key: listAgenda.length,
                startTime: null,
                endTime: null,
                description: '',
            };
            setListAgenda([...listAgenda, newRow]);
        } else {
            const newRow = {
                index: randomNumber,
                id: '',
                eventId: id,
                organizerId: '',
                key: listAgenda.length,
                startTime: listAgenda[listAgenda.length - 1].endTime,
                endTime: null,
                description: '',
            };
            setListAgenda([...listAgenda, newRow]);
        }
    };

    // Bảng Agenda
    const handleInputChange = (e, key, dataIndex) => {
        const updatedData = [...listAgenda];
        const record = updatedData.find((item) => item.index === key);
        if (record) {
            record[dataIndex] = e.target.value;
            setListAgenda(updatedData);
        }
    };

    const handleSelectChange = (e, key, dataIndex) => {
        const updatedData = [...listAgenda];
        const record = updatedData.find((item) => item.index === key);
        if (record) {
            record[dataIndex] = e;
            setListAgenda(updatedData);
        }
    };

    const columnsAgenda = [
        {
            title: "STT",
            dataIndex: "index",
            key: "index",
            render: (text, record, index) => <span>{index + 1}</span>,
            width: "3%",
        },
        {
            title: "Thời gian bắt đầu",
            dataIndex: "startTime",
            key: "startTime",
            width: "23%",
            editable: true,
            render: (text, record, index) => (
                <Input
                    readOnly={isDisable ? true : false}
                    value={text}
                    onChange={(e) => handleInputChange(e, record.index, "startTime")}
                />
            ),
        },
        {
            title: "Thời gian kết thúc",
            dataIndex: "endTime",
            key: "endTime",
            width: "23%",
            editable: true,
            render: (text, record, index) => (
                <Input
                    readOnly={isDisable ? true : false}
                    value={text}
                    onChange={(e) => handleInputChange(e, record.index, "endTime")}
                />
            ),
        },
        {
            title: "Mô tả",
            dataIndex: "description",
            key: "description",
            width: "25%",
            render: (text, record, index) => (
                <Input
                    readOnly={isDisable ? true : false}
                    value={text}
                    onChange={(e) => handleInputChange(e, record.index, "description")}
                />
            ),
        },
        {
            title: "Người phụ trách",
            dataIndex: "organizerId",
            key: "organizerId",
            width: "20%",
            render: (text, record, index) => (
                <Select
                    style={{ width: "100%", pointerEvents: isDisable ? "none" : "" }}
                    value={text}
                    options={listOrganizer.map((organizer) => ({
                        value: organizer.organizerId,
                        label: organizer.name,
                    }))}
                    onChange={(e) => handleSelectChange(e, record.index, "organizerId")}
                    placeholder="Select Item..."
                    maxTagCount="responsive"
                />
            ),
        },
        {
            title: "Hành động",
            dataIndex: "actions",
            key: "actions",
            render: (text, record, index) => (
                <Space size="middle">
                    <Popconfirm
                        title="Bạn chắc chắn muốn xóa agenda này không?"
                        onConfirm={() => {
                            deleteAgenda(record.id, record.index);
                        }}
                        okText="Có"
                        cancelText="Không"
                    >
                        <FontAwesomeIcon icon={faTrash} className="custom-icon"/>
                    </Popconfirm>

                    {index + 1 === listAgenda.length &&
                        <FontAwesomeIcon
                            icon={faPlus}
                            className="custom-icon"
                            style={{ marginRight: "15px" }}
                            onClick={() => {
                                handleAddRow();
                            }}
                        />
                    }
                </Space>
            ),
            width: "15%",
        },
    ];

    const dynamicColumnsAgenda = isDisable
        ? columnsAgenda.filter((column) => column.key !== "actions")
        : columnsAgenda;
    // End Bảng Agenda

    // Lưu danh sách agenda
    const saveListAgenda = () => {
        let hasEmptyFields = false;
        const listAgendaNew = listAgenda.map((item, index) => {
            const newItem = {
                id: item.id,
                eventId: item.eventId,
                organizerId: item.organizerId,
                startTime: item.startTime,
                endTime: item.endTime,
                description: item.description,
            };
    
            if (newItem.startTime == null || newItem.startTime === "") {
                message.error(`Vui lòng nhập thời gian bắt đầu cho agenda ở dòng ${index + 1}.`);
                hasEmptyFields = true;
            }
            if (newItem.endTime == null || newItem.endTime === "") {
                message.error(`Vui lòng nhập thời gian kết thúc cho agenda ở dòng ${index + 1}.`);
                hasEmptyFields = true;
            }
            if (newItem.description == null || newItem.description === "") {
                message.error(`Vui lòng nhập mô tả cho agenda ở dòng ${index + 1}.`);
                hasEmptyFields = true;
            }
            if (newItem.organizerId == null || newItem.organizerId === "") {
                message.error(`Vui lòng chọn người phụ trách cho agenda ở dòng ${index + 1}.`);
                hasEmptyFields = true;
            }
            return newItem;
        });
        if (hasEmptyFields) {
            return;
        }

        OREventDetailApi.saveListAgenda(listAgendaNew).then(
            (res) => {
                message.success("Lưu thông tin thành công");
                loadDataAgenda();
            }
        ).catch((err) => {
            message.error(err.response.data.message);
        });
    }

    // Delete agenda
    const deleteAgenda = (id, index) => {
        if (id === '') {
            let updateListAgenda = listAgenda.filter(
                (record) => record.index !== index
            );
            setListAgenda(updateListAgenda);
            message.error("Xóa thành công");
        } else {
            OREventDetailApi.deleteAgenda(id).then(
                (res) => {
                    let updateListAgenda = listAgenda.filter(
                        (record) => record.id !== res.data.data
                    );
                    setListAgenda(updateListAgenda);
                    message.error("Xóa thành công");
                }
            ).catch((err) => {
                message.error(err.response.data.message);
            });
        }
    };

    return (
        <>
            {/* ************ Bảng người tổ chức ************** */}
            <div
                style={{
                    display: "flex",
                    alignItems: "center",
                    justifyContent: "space-between",
                }}
            >
                <h4 className="title-comment">
                    <FontAwesomeIcon icon={faList} style={{ color: "#172b4d" }} />
                    <span style={{ marginLeft: "7px" }}>Danh sách người tổ chức</span>
                </h4>
                <Button
                    onClick={openModalAddOrganizer}
                    style={{
                        color: "white",
                        backgroundColor: "rgb(38, 144, 214)",
                        display: isDisable ? "none" : "",
                    }}
                >
                    Thêm người tổ chức
                </Button>
            </div>
            <br/>
            <Table
                dataSource={listOrganizer}
                columns={dynamicColumns}
                pagination={false}
                rowKey="id"
            />
            <ModalAddOrganizer
                visible={isModalAddOrganizer}
                onCancel={onCancelModalAddOrganizer}
                loadOrganizers={loadOrganizersByIdEvent}
            />
            <ModalUpdateOrganizer
                Organizer={organizer}
                visible={isModalUpdateOrganizer}
                onCancel={onCancelModalUpdateOrganizer}
                checkRole={() => checkRole()}
            />
            {/* ************ END Bảng người tổ chức ************ */}
            
            {/* ************ Bảng Agenda ************ */}
            <div
                style={{
                    display: "flex",
                    alignItems: "center",
                    justifyContent: "space-between",
                    marginTop: "20px",
                }}
            >
                <h4 className="title-comment">
                    <FontAwesomeIcon icon={faList} style={{ color: "#172b4d" }} />
                    <span style={{ marginLeft: "7px" }}>Danh sách Agenda</span>
                </h4>
                <Button
                    onClick={() => saveListAgenda()}
                    style={{
                        color: "white",
                        backgroundColor: "rgb(38, 144, 214)",
                        display: isDisable ? "none" : "",
                    }}
                >
                    Lưu
                </Button>
            </div>
            <br />
                <Table
                    dataSource={listAgenda}
                    columns={dynamicColumnsAgenda}
                    rowKey="id"
                    pagination={false}
                    locale={{
                        emptyText: (
                          <div>
                            <Button type="primary" 
                                onClick={() => {
                                    handleAddRow();
                                }}
                            >
                                <FontAwesomeIcon
                                    icon={faPlus}
                                />
                            </Button>
                          </div>
                        ),
                    }}
                />
             {/* ************ END Bảng Agenda ************ */}
        </>
    );
};

export default OrganizersList;
