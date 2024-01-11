import { SearchOutlined, UndoOutlined } from "@ant-design/icons";
import { useEffect, useState } from "react";
import moment from "moment/moment";
import { Button, Card, Col, Form, Input, Row, Table } from "antd";
import { useParams } from "react-router-dom";
import { ORRegistrationListApi } from "./ORRegistrationListApi";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faFilter } from "@fortawesome/free-solid-svg-icons";
import ORRegistrationExportExcel from "./ORRegistrationExportExcel";
import ShowHistoryModal from "../../../components/ShowHistoryModal";
import {ORGANIZER_REGISTRATION_LIST} from "../../../constants/DisplayName";

export default function ORERegistrationList() {
    const [listRegistration, setListRegistration] = useState([]);
    const [registration, setRegistration] = useState({});
    const [currentPage, setCurrentPage] = useState(1); // Trang hiện tại
    const [pageSize, setPageSize] = useState(10); // Số mục trên mỗi trang
    const [totalPages, setTotalPages] = useState(0);

    const { id } = useParams();
    const [form] = Form.useForm();

    useEffect(() => {
        handleResetForm();
        console.log(pageSize);
    }, [currentPage]);

    const handleResetForm = () => {
        form.resetFields();
        setRegistration({
            email: null,
            className: null,
            question: null,
            lecturer: null,
        });
        handleSearch();
    };

    const handleSearch = () => {
        const obj = {
            page: currentPage - 1,
            size: pageSize,
            idEvent: id,
            email: registration.email,
            className: registration.className,
            question: registration.question,
            lecturer: registration.lecturer,
        }
        console.log(obj);
        ORRegistrationListApi.fetchAll(obj)
            .then((response) => {
                console.log(response.data.data.data);
                setListRegistration(response.data.data.data);
                setTotalPages(response.data.data.totalPages);
                setCurrentPage(response.data.data.currentPage + 1);
            })
            .catch((error) => {});
    };

    const renderDateTime = (createDate) => {
        const momentObject = moment(createDate);
        const formattedDateTime = momentObject.format("HH:mm DD/MM/YYYY");
        return formattedDateTime;
    };

    const columnsRegistrationList = [
        {
            title: "STT",
            dataIndex: "stt",
            key: "stt",
        },
        {
            title: "Email",
            dataIndex: "email",
            key: "email",
        },
        {
            title: "Lớp",
            key: "className",
            dataIndex: "className",
        },
        {
            title: "Tên giảng viên",
            dataIndex: "lecturerName",
            key: "lecturerName",
        },
        {
            title: "Câu hỏi",
            key: "question",
            dataIndex: "question",
        },
        {
            title: "Thời gian đăng ký",
            key: "createDate",
            dataIndex: "createDate",
            render: renderDateTime,
        }
    ];

    const handleTableChange = (pagination) => {
        setCurrentPage(pagination.current);
        setPageSize(pagination.pageSize);
    };

    return (
        <>
            <div className="rounded-lg shadow-md w-full bg-white p-8">
                <div style={{ marginTop: 5 }}>
                    <Card
                        title={
                            <span>
                                <FontAwesomeIcon
                                    icon={faFilter}
                                    style={{ marginRight: "8px" }}
                                />
                                Bộ lọc
                            </span>
                        }
                        bordered={false}
                    >
                        <Form
                            labelCol={{ span: 7 }}
                            wrapperCol={{ span: 15 }}
                            form={form}
                        >
                            <Row gutter={16}>
                                <Col span={12}>
                                    <Form.Item
                                        className="formItem"
                                        label="Email"
                                        name="email"
                                    >
                                        <Input
                                            placeholder="Nhập email ..."
                                            value={registration.email}
                                            onChange={(e) => {
                                                setRegistration({
                                                    ...registration,
                                                    email: e.target.value,
                                                });
                                            }}
                                        />
                                    </Form.Item>
                                    <Form.Item
                                        className="formItem"
                                        label="Tên lớp"
                                        name="className"
                                    >
                                        <Input
                                            placeholder="Nhập tên lớp ..."
                                            value={registration.className}
                                            onChange={(e) => {
                                                setRegistration({
                                                    ...registration,
                                                    className: e.target.value,
                                                });
                                            }}
                                        />
                                    </Form.Item>
                                </Col>

                                <Col span={12}>
                                    <Form.Item
                                        className="formItem"
                                        label="Tên giảng viên"
                                        name="lecturer"
                                    >
                                       <Input
                                            placeholder="Nhập tên giảng viên ..."
                                            value={registration.lecturer}
                                            onChange={(e) => {
                                                setRegistration({
                                                    ...registration,
                                                    lecturer: e.target.value,
                                                });
                                            }}
                                        />
                                    </Form.Item>
                                    <Form.Item
                                        className="formItem"
                                        label="Câu hỏi"
                                        name="question"
                                    >
                                        <Input
                                            placeholder="Nhập câu hỏi ..."
                                            value={registration.question}
                                            onChange={(e) => {
                                                setRegistration({
                                                    ...registration,
                                                    question: e.target.value,
                                                });
                                            }}
                                        />
                                    </Form.Item>
                                </Col>
                            </Row>

                            <Form.Item
                                wrapperCol={{
                                    offset: 9,
                                    span: 15,
                                }}
                                style={{ margin: 0 }}
                            >
                                <Button
                                    type="primary"
                                    style={{ margin: 5 }}
                                    htmlType="submit"
                                    onClick={() => handleSearch()}
                                    icon={<SearchOutlined fontSize="20px" />}
                                >
                                    Tìm kiếm
                                </Button>
                                <Button
                                    type="primary"
                                    danger
                                    onClick={() => handleResetForm()}
                                    htmlType="button"
                                    icon={<UndoOutlined />}
                                >
                                    Làm mới bộ lọc
                                </Button>
                            </Form.Item>
                        </Form>
                    </Card>
                </div>

                <div style={{ marginTop: 20 }}>
                    <Card
                        title="Danh sách đăng ký"
                        extra={<>
                            <ORRegistrationExportExcel list={listRegistration} />
                            <ShowHistoryModal displayName={ORGANIZER_REGISTRATION_LIST} eventId={id}/>
                        </>}
                        bordered={false}
                    >
                        <Table
                            columns={columnsRegistrationList}
                            dataSource={listRegistration}
                            rowKey="index"
                            pagination={{
                                current: currentPage,
                                pageSize: pageSize,
                                total: totalPages,
                                showSizeChanger: true,
                            }}
                            onChange={handleTableChange}
                        />
                    </Card>
                </div>
            </div>
        </>
    );
}
