import {EditOutlined, PlusCircleOutlined, PlusOutlined} from '@ant-design/icons';
import {Button, Col, Form, Input, Modal, Row, Select, Switch, Tooltip} from 'antd';
import {Option} from 'antd/es/mentions';
import React, {useEffect, useState} from 'react'
import {APMajorManagermentApi} from '../APMajorManagermentApi';
import {Alert, Snackbar} from '@mui/material';

function MajorModal({isUpdate, majorId, onSuccess}) {
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [typeMajor, setTypeMajor] = useState(false);
    const [form] = Form.useForm();

    const [parentMajorList, setParentMajorList] = useState([]);
    const [major, setMajor] = useState({});

    const [openAlert, setOpenAlert] = useState(false);

    const [messageAlert, setMessageAlert] = useState('');
    const [isSuccess, setIsSuccess] = useState(true);
    const handleCloseAlert = (event, reason) => {
        if (reason === 'clickaway') {
            return;
        }
        setOpenAlert(false);
    };
    const loadData = (majorId) => {
        APMajorManagermentApi.getOneMajor(majorId).then(response => {
            const data = response.data.data;
            setMajor(data);
        }).catch(e => {
            console.log(e);
        })
    }
    const loadParentMajor = () => {
        APMajorManagermentApi.fetchAllParentMajor().then(response => {
            setParentMajorList(response.data);
        }).catch(e => {
            console.log(e);
        })
    }
    useEffect(() => {
        loadParentMajor();
    }, [])
    useEffect(() => {
        loadData(majorId);
    }, [majorId])

    const showModal = () => {
        isUpdate && loadData(majorId);
        loadParentMajor();
        setIsModalOpen(true);
    };
    const handleCancel = () => {
        setIsModalOpen(false);
    };



    const handleSubmitForm = (data) => {
        Modal.confirm({
            title: "Xác nhận",
            maskClosable: true,
            content: `Xác nhận ${isUpdate ? 'cập nhật' : 'thêm'} chuyên ngành ?`,
            okText: "Ok",
            cancelText: "Cancel",
            onOk: () => {
                if (isUpdate) {
                    APMajorManagermentApi.update(majorId, data).then(response => {
                        console.log(response);
                        onSuccess();
                        setIsModalOpen(false);
                        setIsSuccess(true);
                        setMessageAlert("Cập nhật thành công!");
                        form.resetFields();
                    }).catch(e => {
                        setIsSuccess(false);
                        setMessageAlert(e.response.data.message);
                    })
                    console.log(data.mainMajor);
                } else {
                    APMajorManagermentApi.register(data).then(response => {
                        console.log(response);
                        onSuccess();
                        setIsModalOpen(false);
                        setIsSuccess(true);
                        setMessageAlert("Thêm thành công!");
                        form.resetFields();
                    }).catch(e => {
                        setIsSuccess(false);
                        setMessageAlert(e.response.data.message)
                    })
                }
                setOpenAlert(true);
            },
        });
    }

    const validateWhitespace = (rule, value, callback, message) => {
        if (value && value.trim().length === 0) {
            callback(message || 'Vui lòng không nhập toàn khoảng trắng!');
        } else {
            callback();
        }
    };
    return (
        <>
            <Tooltip placement="top" title={isUpdate ? "Sửa chuyên ngành" : "Thêm chuyên ngành"}>
                <Button type="primary" shape={isUpdate ? "circle" : "square"} size={"middle"} onClick={showModal}>
                    {isUpdate === true ? <EditOutlined/> : <><PlusCircleOutlined/> <span>Thêm chuyên ngành</span></>}
                </Button>
            </Tooltip>
            <Modal title={`${isUpdate === true ? "Cập nhật" : "Thêm"} chuyên ngành`} form={form} open={isModalOpen}
                   onCancel={handleCancel} footer={""} key={majorId}>
                <Form layout='vertical' onFinish={handleSubmitForm} initialValues={isUpdate && {
                    code: major.code,
                    name: major.name,
                    email: major.mailOfManager,
                    mainMajor: parentMajorList.filter(item => item.code === major?.mainMajorCode)[0]?.id
                }} form={form}>
                    <Row gutter={12}>
                        <Col xl={12}>
                            <Form.Item label={"Mã chuyên ngành"} name={"code"}
                                       rules={[{ validator: (rule, value, callback) =>
                                               validateWhitespace(rule, value, callback, "Mã chuyên ngành không được để trống!")}
                                       ]}>
                                <Input placeholder='Nhập mã chuyên ngành...'/>
                            </Form.Item>
                        </Col>
                        <Col xl={12}>
                            <Form.Item label={"Tên chuyên ngành"} name={"name"}
                                       rules={[{ validator: (rule, value, callback) =>
                                               validateWhitespace(rule, value, callback, "Tên chuyên ngành không được để trống!")}
                                       ]}>
                                <Input placeholder='Nhập tên chuyên ngành...'/>
                            </Form.Item>
                        </Col>
                        <Col xl={12}>
                            <Form.Item label={"Email trưởng bộ môn"} name={"email"}
                                       rules={[{ validator: (rule, value, callback) =>
                                               validateWhitespace(rule, value, callback, "Email trưởng bộ môn không được để trống!")}
                                       ]}>
                                <Input placeholder='Nhập email...'/>
                            </Form.Item>
                        </Col>
                        <Col xl={12}>
                            {isUpdate && major?.mainMajorCode !== null && (
                                <Form.Item name={"mainMajor"} label={"Chuyên ngành cha"}
                                           rules={[{required: true, message: "Chuyên ngành cha không được để trống!"}]}>
                                    <Select className="me-2" showSearch optionFilterProp="children"
                                            style={{width: '100%'}}
                                            defaultValue={parentMajorList.filter(item => item.code === major?.mainMajorCode).id}>
                                        <Option value="">-- Chọn chuyên ngành cha --</Option>
                                        {parentMajorList.map((item) => (
                                            <Option key={item.id} value={item.id}>
                                                {item.name}
                                            </Option>
                                        ))}
                                    </Select>
                                </Form.Item>
                            )}
                            {typeMajor && (
                                <Form.Item name={"mainMajor"} label={"Chuyên ngành cha"} rules={[{
                                    required: typeMajor,
                                    message: "Chuyên ngành cha không được để trống!"
                                }]}>
                                    <Select className="me-2" showSearch optionFilterProp="children"
                                            style={{width: '100%'}}>
                                        <Option value="">-- Chọn chuyên ngành cha --</Option>
                                        {parentMajorList.map((item) => (
                                            <Option key={item.id} value={item.id}>
                                                {item.name}
                                            </Option>
                                        ))}
                                    </Select>
                                </Form.Item>
                            )}
                        </Col>
                        <Col xl={24} style={{marginBottom: "10px"}}>
                            {!isUpdate && (
                                <>
                                    <Switch defaultChecked={false} size='small' onChange={setTypeMajor}/> là chuyên
                                    ngành hẹp ?
                                </>
                            )}
                        </Col>
                    </Row>
                    <Button htmlType='submit' type='primary'>{isUpdate ? (<><EditOutlined/> Cập nhật</>) : (<>{
                        <PlusOutlined/>} Thêm</>)}</Button>
                </Form>
            </Modal>

            <Snackbar open={openAlert} autoHideDuration={2000} onClose={handleCloseAlert}>
                <Alert onClose={handleCloseAlert} severity={isSuccess ? 'success' : 'error'} sx={{width: '100%'}}>
                    {messageAlert}
                </Alert>
            </Snackbar>
        </>
    )
}

export default MajorModal;