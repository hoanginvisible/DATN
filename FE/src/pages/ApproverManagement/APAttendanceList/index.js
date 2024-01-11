import { SearchOutlined, UndoOutlined } from "@ant-design/icons";
import { useEffect, useState } from "react";
import moment from "moment/moment";
import { Button, Card, Col, Form, Input, Row, Select, Space, Table } from "antd";
import { Link, useParams } from "react-router-dom";
import { APAttendanceListApi } from "./APAttendanceListApi";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faFilter, faStar } from "@fortawesome/free-solid-svg-icons";
import APExportExcel from "./APExportExcel";

const { Option } = Select;

export default function AttendanceList() {
    const [listAttendance, setListAttendance] = useState([]);
    const [attendance, setAttendance] = useState({});
    const [currentPage, setCurrentPage] = useState(1); // Trang hiện tại
    const [pageSize, setPageSize] = useState(10); // Số mục trên mỗi trang
    const [totalPages, setTotalPages] = useState(0);

    const { id } = useParams();
    const [form] = Form.useForm();

    useEffect(() => {
        handleResetForm();
    }, [currentPage]);

    const handleResetForm = () => {
        form.resetFields();
        setAttendance({
            email: null,
            className: null,
            startTime: null,
            rate: null,
            feedback: null,
        });
        handleSearch();
    };

    const handleSearch = () => {
        APAttendanceListApi.fetchAttendanceList(
            {
                page: currentPage - 1,
                size: pageSize,
                email: attendance.email,
                className: attendance.className,
                rate: Array.isArray(attendance.rate)
                    ? attendance.rate.length === 0
                        ? null
                        : attendance.rate.join(",")
                    : null,
                feedback: attendance.feedback,
            },
            id
        )
            .then((response) => {
                setListAttendance(response.data.data.data);
                setTotalPages(response.data.data.totalPages);
                setCurrentPage(response.data.data.currentPage + 1);
            })
            .catch((error) => {});
    };

    const renderDateTime = (startTime) => {
        const momentObject = moment(startTime);
        const formattedDateTime = momentObject.format("HH:mm DD/MM/YYYY");
        return formattedDateTime;
    };

    const columnsAttendanceList = [
        {
            title: "STT",
            dataIndex: "index",
            key: "index",
        },
        {
            title: "Email",
            dataIndex: "email",
            key: "email",
        },
        {
            title: "Thời gian tham gia",
            dataIndex: "attendanceTime",
            key: "attendanceTime",
            render: renderDateTime,
        },
        {
            title: "Lớp",
            key: "className",
            dataIndex: "className",
        },
        {
            title: "Phản hồi",
            key: "feedback",
            dataIndex: "feedback",
        },
        {
            title: "Đánh giá",
            key: "rate",
            dataIndex: "rate",
        }
    ];

    const handleTableChange = (pagination) => {
        console.log("Current Page:", pagination.current);
        console.log("Page Size:", pagination.pageSize);

        setCurrentPage(pagination.current);
        setPageSize(pagination.pageSize);
        console.log("currentPage:", currentPage);
        console.log("pageSize:", pageSize);
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
                                            value={attendance.email}
                                            onChange={(e) => {
                                                setAttendance({
                                                    ...attendance,
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
                                            value={attendance.className}
                                            onChange={(e) => {
                                                setAttendance({
                                                    ...attendance,
                                                    className: e.target.value,
                                                });
                                            }}
                                        />
                                    </Form.Item>
                                </Col>

                                <Col span={12}>
                                    <Form.Item
                                        className="formItem"
                                        label="Đánh giá"
                                        name="rate"
                                    >
                                        <Select
                                            mode="multiple"
                                            value={attendance.rate}
                                            onChange={(e) => {
                                                setAttendance({
                                                    ...attendance,
                                                    rate: e,
                                                });
                                            }}
                                            placeholder="Chọn loại đánh giá ..."
                                        >
                                            <Option key="5" value="5">
                                                5{" "}
                                                <FontAwesomeIcon
                                                    icon={faStar}
                                                    style={{ color: "#ffde0a" }}
                                                />
                                            </Option>
                                            <Option key="4" value="4">
                                                4{" "}
                                                <FontAwesomeIcon
                                                    icon={faStar}
                                                    style={{ color: "#ffde0a" }}
                                                />
                                            </Option>
                                            <Option key="3" value="3">
                                                3{" "}
                                                <FontAwesomeIcon
                                                    icon={faStar}
                                                    style={{ color: "#ffde0a" }}
                                                />
                                            </Option>
                                            <Option key="2" value="2">
                                                2{" "}
                                                <FontAwesomeIcon
                                                    icon={faStar}
                                                    style={{ color: "#ffde0a" }}
                                                />
                                            </Option>
                                            <Option key="1" value="1">
                                                1{" "}
                                                <FontAwesomeIcon
                                                    icon={faStar}
                                                    style={{ color: "#ffde0a" }}
                                                />
                                            </Option>
                                        </Select>
                                    </Form.Item>
                                    <Form.Item
                                        className="formItem"
                                        label="Phản hồi"
                                        name="feedback"
                                    >
                                        <Input
                                            placeholder="Nhập phản hồi ..."
                                            value={attendance.feedback}
                                            onChange={(e) => {
                                                setAttendance({
                                                    ...attendance,
                                                    feedback: e.target.value,
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
                        title="Danh sách điểm danh"
                        extra={<APExportExcel list={listAttendance} />}
                        bordered={false}
                    >
                        <Table
                            columns={columnsAttendanceList}
                            dataSource={listAttendance}
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
