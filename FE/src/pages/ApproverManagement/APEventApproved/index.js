import {SearchOutlined, UndoOutlined} from "@ant-design/icons";
import {
    Col,
    Form,
    Input,
    Row,
    Select,
    DatePicker,
    Button,
    Card,
    Space,
    Table, Tooltip,
} from "antd";
import React, {useEffect, useState} from "react";
import {Link} from "react-router-dom";
import moment from "moment/moment";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faEye, faFilter} from "@fortawesome/free-solid-svg-icons";
import "./index.css";
import {APEventApprovedApi} from "./APEventApprovedApi";

const {Option} = Select;

export default function EventApproved() {
    const [request, setRequest] = useState({});
    const [listCategory, setListCategory] = useState([]);
    const [listEventApproved, setListEventApproved] = useState([]);
    const [currentPage, setCurrentPage] = useState(1); // Trang hiện tại
    const [pageSize, setPageSize] = useState(10); // Số mục trên mỗi trang
    const [totalPages, setTotalPages] = useState(0);
    const {RangePicker} = DatePicker;

    const [form] = Form.useForm();

    useEffect(() => {
        loadListEvent(currentPage);
    }, [currentPage]);

    useEffect(() => {
        loadCategory();
    }, []);

    const handleResetForm = (currentPage) => {
        form.resetFields();
        setRequest({
            name: null,
            idCategory: null,
            startTime: null,
            endTime: null,
            status: null,
        });
        loadListEvent(currentPage);
    };

    const loadListEvent = (currentPage) => {
        APEventApprovedApi.fetchAll({
            page: currentPage - 1,
            name: null,
            categoryId: null,
            startTime: null,
            endTime: null,
            status: null,
        })
            .then((response) => {
                setListEventApproved(response.data.data.data);
                setTotalPages(response.data.data.totalPages);
            })
            .catch((error) => {
            });
    };

    const handleSearch = () => {
        let startDate = new Date(request.startTime).getTime();
        let endDate = new Date(request.endTime).getTime();
        APEventApprovedApi.fetchAll({
            page: currentPage - 1,
            name: request.name,
            categoryId: Array.isArray(request.idCategory)
                ? request.idCategory.length === 0
                    ? null
                    : request.idCategory.join(",")
                : null,
            startTime: startDate === 0 ? null : startDate,
            endTime: endDate === 0 ? null : endDate,
            status: Array.isArray(request.status)
                ? request.status.length === 0
                    ? null
                    : request.status.join(",")
                : null,
        })
            .then((response) => {
                setListEventApproved(response.data.data.data);
                setTotalPages(response.data.data.totalPages);
            })
            .catch((error) => {
            });
    };

    const loadCategory = () => {
        APEventApprovedApi.fetchListCategory()
            .then((response) => {
                setListCategory(response.data.data);
            })
            .catch((error) => {
            });
    };

    const renderStatus = (status) => {
        return status === "1"
            ? "Vừa đăng ký"
            : status === "2"
                ? "Cần sửa"
                : status === "3"
                    ? "Chờ phê duyệt"
                    : status === "4"
                        ? "Đã phê duyệt"
                        : status === "5"
                            ? "Đã tổ chức"
                            : "";
    };

    const renderDateTime = (startTime) => {
        const momentObject = moment(startTime);
        const formattedDateTime = momentObject.format("HH:mm DD/MM/YYYY");
        return formattedDateTime;
    };

    const renderAction = (record) => (
        <div className="textCenter">
            <Link to={`/approver-management/event-detail/${record.id}`}>
                <Tooltip placement="top" title={"Xem chi tiết"}>
                    <Button shape="circle" size={"middle"} type="primary"><FontAwesomeIcon icon={faEye}/></Button>
                </Tooltip>
            </Link>
        </div>
    );

    const columnsEvent = [
        {
            title: "#",
            dataIndex: "index",
            key: "index",
        },
        {
            title: "Tên sự kiện",
            dataIndex: "name",
            key: "name",
        },
        {
            title: "Ngày bắt đầu",
            dataIndex: "startTime",
            key: "startTime",
            render: renderDateTime,
        },
        {
            title: "Ngày kết thúc",
            key: "endTime",
            dataIndex: "endTime",
            render: renderDateTime,
        },
        {
            title: "Danh mục",
            key: "nameCategory",
            dataIndex: "nameCategory",
        },
        {
            title: "Trạng thái",
            key: "status",
            dataIndex: "status",
            render: renderStatus,
        },
        {
            title: "Thao tác",
            key: "action",
            width: 90,
            render: renderAction,
        },
    ];

    const handleTableChange = (pagination) => {
        // pagination có chứa currentPage và pageSize mới
        setCurrentPage(pagination.current);
        setPageSize(pagination.pageSize);
        APEventApprovedApi.fetchAll({
            page: pagination.current - 1,
            size: pagination.pageSize,
            name: null,
            categoryId: null,
            startTime: null,
            endTime: null,
            status: null,
        })
            .then((response) => {
                setListEventApproved(response.data.data.data);
                setTotalPages(response.data.data.totalPages);
            })
            .catch((error) => {
            });
    };

    return (
        <div className="rounded-lg shadow-md w-full bg-white p-8">
            <div style={{marginTop: 5}}>
                <Card
                    title={
                        <span>
                            <FontAwesomeIcon
                                icon={faFilter}
                                style={{marginRight: "8px"}}
                            />
                            Bộ lọc
                        </span>
                    }
                    bordered={false}
                >
                    <Form labelCol={{span: 8}} wrapperCol={{span: 15}} form={form}>
                        <Row gutter={16}>
                            <Col span={12}>
                                <Form.Item
                                    className="formItem"
                                    label="Tên sự kiện"
                                    name="name"
                                >
                                    <Input
                                        placeholder="Nhập tên sự kiện ..."
                                        value={request.name}
                                        onChange={(e) => {
                                            setRequest({
                                                ...request,
                                                name: e.target.value,
                                            });
                                        }}
                                    />
                                </Form.Item>

                                <Form.Item
                                    className="formItem"
                                    label="Danh mục sự kiện"
                                    name="categoryName"
                                >
                                    <Select
                                        mode="multiple"
                                        value={request.idCategory}
                                        onChange={(e) => {
                                            setRequest({
                                                ...request,
                                                idCategory: e,
                                            });
                                        }}
                                        placeholder="Chọn danh mục sự kiện ..."
                                    >
                                        {listCategory.map((category, index) => (
                                            <Option key={index} value={category.id}>
                                                {category.name}
                                            </Option>
                                        ))}
                                    </Select>
                                </Form.Item>
                            </Col>

                            <Col span={12}>
                                <Form.Item
                                    className="formItem"
                                    label="Trạng thái phê duyệt"
                                    name="approvalStatus"
                                >
                                    <Select
                                        mode="multiple"
                                        value={request.status}
                                        onChange={(e) => {
                                            setRequest({
                                                ...request,
                                                status: e,
                                            });
                                        }}
                                        placeholder="Chọn trạng thái ..."
                                    >
                                        <Option key="1" value="1">
                                            Dự kiến tổ chức
                                        </Option>
                                        <Option key="3" value="3">
                                            Chờ phê duyệt
                                        </Option>
                                        <Option key="2" value="2">
                                            Cần sửa
                                        </Option>
                                        <Option key="4" value="4">
                                            Đã phê duyệt
                                        </Option>
                                    </Select>
                                </Form.Item>
                                <Form.Item
                                    label="Thời gian sự kiện"
                                    rules={[{required: true}]}
                                >
                                    <RangePicker
                                        showTime={{
                                            format: "HH:mm",
                                        }}
                                        style={{
                                            width: "100%"
                                        }}
                                        format="YYYY-MM-DD HH:mm"
                                        value={[request.startTime, request.endTime]}
                                        onChange={(e) => {
                                            setRequest({
                                                startTime: e[0],
                                                endTime: e[1],
                                            });
                                        }}
                                        // onOk={onOk}
                                    />
                                </Form.Item>
                            </Col>
                        </Row>

                        <Form.Item
                            wrapperCol={{
                                offset: 9,
                                span: 15,
                            }}
                            style={{margin: 0}}
                        >
                            <Button
                                type="primary"
                                style={{margin: 5}}
                                htmlType="submit"
                                onClick={() => handleSearch()}
                                icon={<SearchOutlined fontSize="20px"/>}
                            >
                                Tìm kiếm
                            </Button>
                            <Button
                                type="primary"
                                danger
                                onClick={() => handleResetForm()}
                                htmlType="button"
                                icon={<UndoOutlined/>}
                            >
                                Làm mới bộ lọc
                            </Button>
                        </Form.Item>
                    </Form>
                </Card>
            </div>

            <div style={{marginTop: 20}}>
                <Card
                    title="Danh sách sự kiện"
                    // extra={<ExportCSV csvData={listEventApproved} fileName="sample_data" />}
                    bordered={false}
                >
                    <Table
                        columns={columnsEvent}
                        dataSource={listEventApproved}
                        rowKey="index"
                        pagination={{
                            current: currentPage,
                            pageSize: pageSize,
                            total: totalPages * pageSize,
                            showSizeChanger: true,
                            showQuickJumper: true,
                        }}
                        onChange={handleTableChange}
                    />
                </Card>
            </div>
        </div>
    );
}
