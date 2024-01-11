import React, { useEffect, useState } from 'react';
import { Button, Col, Form, Input, Row, Select, Table } from 'antd';
import Title from 'antd/es/typography/Title';
import { FileExcelOutlined, FilterOutlined } from '@ant-design/icons';
import { Option } from 'antd/es/mentions';
import { APRegistrationListApi } from './APRegistrationListApi';
import * as XLSX from 'xlsx/xlsx.mjs';
import { useParams } from "react-router-dom";
import { dateTimeFromLong } from '../../../utils/Converter';

export default function APRegistrationList() {
    const [params, setParams] = useState({});
    const [listParticipant, setListParticipant] = useState([]);
    const [totalPages, setTotalPages] = useState(0);
    const [currentPage, setCurrentPage] = useState(1);// Trang hiện tại
    const [pageSize, setPageSize] = useState(10); // Số mục trên mỗi trang
    const [listExport, setListExport] = useState([]);
    const [listGetAll, setListGetAll] = useState([]);
    const { id } = useParams();
    // const id = '8c7e8006-366a-4f4e-a5bc-ea62c06ac4e5';
    useEffect(() => {
        APRegistrationListApi.fetchAll(id, {}).then(response => {
            setListParticipant(response.data.data);
            setTotalPages(response.data.totalPages);
        }).catch(e => {
            console.log(e);
        })
        APRegistrationListApi.getListExport(id, {}).then(response => {
            setListGetAll(response.data.data);
        }).catch(e => {
            console.log(e);
        })
    }, [])
    useEffect(() => {
        APRegistrationListApi.fetchAll(id, params).then(response => {
            setListParticipant(response.data.data);
            setTotalPages(response.data.totalPages);
        }).catch(e => {
            console.log(e);
        })
        APRegistrationListApi.getListExport(id, params).then(response => {
            setListExport(response.data.data);
        }).catch(e => {
            console.log(e);
        })
    }, [params])

    const columns = [
        {
            title: '#',
            dataIndex: 'index',
            key: 'index',
        },
        {
            title: 'Email',
            dataIndex: 'email',
            key: 'email',
        },
        {
            title: 'Lớp',
            dataIndex: 'className',
            key: 'className',
        },
        {
            title: 'Câu hỏi',
            dataIndex: 'question',
            key: 'question',
            render: (question, record) => (
                <span>
                    {question === null ? (<p style={{ textAlign: "center" }}>-</p>) : question}
                </span>
            ),
        },
        {
            title: 'Giảng viên',
            dataIndex: 'lecturerName',
            key: 'lecturerName'
        },
        {
            title: 'Ngày đăng ký',
            dataIndex: 'createDate',
            key: 'createDate',
            render: (x) => dateTimeFromLong(x)
        },
    ];

    const handleExportExcel = () => {
        console.log(listExport);
        const excelData = listExport.map(item => ({
            'STT': item.index,
            'Lớp': item.className,
            'Email': item.email,
            'Giảng viên': item.lecturerName,
            'Câu hỏi': item.question || '',
        }));
        const ws = XLSX.utils.json_to_sheet(excelData);
        ws['!auto'] = 1;
        const wb = XLSX.utils.book_new();
        XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');
        XLSX.writeFile(wb, 'data.xlsx');
    }

    return (
        <>
            <div className="container" style={{ backgroundColor: "#FFF", padding: "10px", borderRadius: "10px", marginBottom: "10px", boxShadow: "0 2px 4px rgba(0, 0, 0, 0.1)" }}>
                <Title level={5}><FilterOutlined /> Bộ lọc</Title>
                <Form layout='vertical' onFinish={(data) => setParams(data)}>
                    <Row gutter={12} style={{ marginBottom: "10px" }}>
                        <Col flex="1 1 200px">
                            <Form.Item name={"email"} label="Email">
                                <Select showSearch placeholder="Nhập email người đăng ký..." optionFilterProp="children"
                                    filterOption={(input, option) => (option?.children ?? "").toLowerCase().includes(input.toLowerCase())}
                                >
                                    <Option value=''>Tất cả</Option>
                                    {listGetAll
                                        .filter((item, index, self) => self.findIndex(i => i.email === item.email) === index)
                                        .map((item, index) => (
                                            <Option key={index} value={item.email}>
                                                {item.email}
                                            </Option>
                                        ))}
                                </Select>
                            </Form.Item>
                        </Col>
                        <Col flex="1 1 50px">
                            <Form.Item name={"className"} label="Tên lớp">
                                <Select
                                    showSearch
                                    placeholder="Nhập tên lớp..."
                                    optionFilterProp="children"
                                    filterOption={(input, option) => (option?.children ?? "").toLowerCase().includes(input.toLowerCase())}
                                >
                                    <Option value=''>Tất cả</Option>
                                    {listGetAll
                                        .filter((item, index, self) => self.findIndex(i => i.className === item.className) === index)
                                        .map((item, index) => (
                                            <Option key={index} value={item.className}>
                                                {item.className}
                                            </Option>
                                        ))}
                                </Select>
                            </Form.Item>
                        </Col>
                        <Col flex="1 1 50px">
                            <Form.Item name={"lecturer"} label="Tên giảng viên">
                                <Select
                                    showSearch
                                    placeholder="Nhập tên giảng viên..."
                                    optionFilterProp="children"
                                    filterOption={(input, option) => (option?.lecturerName ?? "").toLowerCase().includes(input.toLowerCase())}
                                >
                                    <Option value=''>Tất cả</Option>
                                    {listGetAll
                                        .filter((item, index, self) => self.findIndex(i => i.lecturerName === item.lecturerName) === index)
                                        .map((item, index) => (
                                            <Option key={index} value={item.lecturerName}>
                                                {item.lecturerName}
                                            </Option>
                                        ))}
                                </Select>
                            </Form.Item>
                        </Col>
                        <Col flex="0 1 ">
                            <Form.Item label="​">
                                <Button type='primary' htmlType='submit' icon={<FilterOutlined />}>Lọc</Button>
                            </Form.Item>
                        </Col>
                    </Row>
                </Form>
            </div>
            <div className="container" style={{ backgroundColor: "#FFF", padding: "10px", borderRadius: "10px" }}>
                <Row gutter={12}>
                    <Col flex="8 1"><Title level={4}>Danh sách người tham gia</Title></Col>
                    <Col flex="1 1">
                        <Button type='primary' style={{ backgroundColor: "#1E6E43" }} onClick={() => handleExportExcel()}>
                            <FileExcelOutlined /> Export Excel
                        </Button>
                    </Col>
                </Row>
                <Table
                    id='my-table'
                    style={{ marginTop: "10px" }}
                    dataSource={listParticipant}
                    columns={columns}
                    pagination={{
                        showSizeChanger: true,
                        current: currentPage,
                        pageSize: pageSize,
                        pageSizeOptions: [10, 20, 50, 100],
                        showQuickJumper: true,
                        total: totalPages * 10,
                        onChange: (page, pageSize) => {
                            const data = { ...params }
                            data.size = pageSize
                            data.page = page - 1
                            setParams(data);
                            setCurrentPage(page);
                            setPageSize(pageSize);
                        },
                    }}
                />
            </div>
        </>
    )
}