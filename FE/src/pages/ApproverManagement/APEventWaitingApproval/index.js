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
    Table,
    message, Tooltip,
} from "antd";
import React, {useEffect, useState} from "react";
import {Link} from "react-router-dom";
import {APEventWaitingApprovalApi} from "./APRegistrationListApi";
import moment from "moment/moment";
// import ExportCSV from './ExportExcel';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faEye, faFilter} from "@fortawesome/free-solid-svg-icons";
import "./index.css";

const {Option} = Select;

export default function EventWaitingApproval() {
    let [listCategory, setListCategory] = useState([]);
    let [listEventApproved, setListEventApproved] = useState([]);
    let [name, setName] = useState("");
    let [formality, setFormality] = useState("");
    let [status, setStatus] = useState([]);
    let [startTime, setStartTime] = useState();
    let [endTime, setEndTime] = useState();
    let [idCategory, setIdCategory] = useState([]);
    let [idEventMajor, setIdEventMajor] = useState([]);
    let [currentPage, setCurrentPage] = useState(1);
    let [totalPages, setToTalPages] = useState(0);
    let [pageSize, setPageSize] = useState(10); // Số mục trên mỗi trang
    let [dataSource, setDataSource] = useState([]);
    let {RangePicker} = DatePicker;

    useEffect(() => {
        loadCategory();
    }, []);

  useEffect(() => {
    loadListEvent();
  }, [currentPage]);

    const loadCategory = () => {
        APEventWaitingApprovalApi.fetchListCategory()
            .then((response) => {
                setListCategory(response.data.data);
            })
            .catch((error) => {
            });
    };

  const loadListEvent = () => {
    APEventWaitingApprovalApi.fetchListEventWaiting({
      page: currentPage - 1,
      size: 5,
      name: null,
      eventGroup: null,
      categoryId: null,
      startTime: null,
      endTime: null,
      formality: null,
      status: null,
    })
      .then((response) => {
        setListEventApproved(response.data.data);
        setCurrentPage(response.data.currentPage + 1);
        setToTalPages(response.data.totalPages);
      })
      .catch((error) => {
        console.log(error.message);
      });
  };


    const handleSearch = () => {
        let startDate = new Date(startTime).getTime();
        let endDate = new Date(endTime).getTime();
        APEventWaitingApprovalApi.fetchListEventWaiting({
            page: currentPage - 1,
            size: 5,
            name: name,
            majorId: Array.isArray(idEventMajor)
                ? idEventMajor.length === 0
                    ? null
                    : idEventMajor.join(",")
                : null,
            categoryId: Array.isArray(idCategory)
                ? idCategory.length === 0
                    ? null
                    : idCategory.join(",")
                : null,
            startTime: startDate === 0 ? null : startDate,
            endTime: endDate === 0 ? null : endDate,
            formality: formality === "null" ? null : formality,
            status: Array.isArray(status)
                ? status.length === 0
                    ? null
                    : status.join(",")
                : null,
        })
            .then((response) => {
                setListEventApproved(response.data.data);
                setCurrentPage(response.data.currentPage + 1);
                setToTalPages(response.data.totalPages);
            })
            .catch((error) => {
            });
    };

    const [form] = Form.useForm();
    const handleResetForm = () => {
        form.resetFields();
        setName(null);
        setFormality(null);
        setStatus(null);
        setStartTime(null);
        setEndTime(null);
        loadListEvent();
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
                        : "";
    };

    const renderDateTime = (startTime) => {
        const momentObject = moment(startTime);
        const formattedDateTime = momentObject.format("HH:mm DD/MM/YYYY");
        return formattedDateTime;
    };

    const renderAction = (record) => (
        <div class="textCenter">
            <Tooltip title="Xem chi tiết" placement="top">
                <Link to={`/approver-management/event-detail/${record.id}`}>
                    <Button size={"middle"} shape={"circle"} type="primary"><FontAwesomeIcon icon={faEye}/></Button>
                </Link>
            </Tooltip>
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

    return (
        <div className="rounded-lg shadow-md w-full bg-white p-8">
            <div style={{marginTop: 5}}>
                <Card
                    title={
                        <span>
              <FontAwesomeIcon icon={faFilter} style={{marginRight: "8px"}}/>
              Bộ lọc
            </span>
                    }
                    bordered={false}
                >
                    <Form labelCol={{span: 8}} wrapperCol={{span: 15}} form={form}>
                        <Row gutter={16}>
                            <Col span={12}>
                                <Form.Item className="formItem" label="Tên sự kiện" name="name">
                                    <Input
                                        placeholder="Nhập tên sự kiện ..."
                                        value={name}
                                        onChange={(e) => {
                                            setName(e.target.value);
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
                                        value={idCategory}
                                        onChange={(e) => {
                                            setIdCategory(e);
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
                                        value={status}
                                        onChange={(e) => {
                                            setStatus(e);
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
                                        value={[startTime, endTime]}
                                        onChange={(e) => {
                                            setStartTime(e[0]);
                                            setEndTime(e[1]);
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
                            total: pageSize * totalPages,
                            onChange: (current, pageSize) => {
                                setCurrentPage(current);
                                setPageSize(pageSize);
                            },
                        }}
                    />
                </Card>
            </div>
        </div>
    );
}
