import { Button, Col, DatePicker, Form, Input, InputNumber, message, Row, Select, Space } from 'antd';
import React, { useContext, useEffect, useState } from 'react';
import { AppContextregister } from './context';
import OREventRegisterApi from './OREventRegisterApi';
import { Option } from 'rc-select';
import { useNavigate } from "react-router-dom";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faBan, faPenToSquare } from '@fortawesome/free-solid-svg-icons';
import dayjs from 'dayjs';
import FormJoditEditor from './Jodit';
import { dateTimeFromLong } from '../../../utils/Converter';
import { DatePickerProps, RangePickerProps } from "antd/es/date-picker";

const FormComponent = (props) => {
    const navigate = useNavigate();
    let [nameSemester, setNameSemester] = useState("");
    const { RangePicker } = DatePicker;
    const {
        name, setName,
        idCategory, setIdCategory,
        startTime, setStartTime,
        endTime, setEndTime,
        idSemester, setIdSemester,
        idObject, setIdObject,
        idMajor, setIdMajor,
        contentEditor, setContentEditor,
        listSemester, setListSemester,
        listObject, setListObject,
        listMajor, setListMajor,
        listCategory, setListCategory,
        eventType, setEventType,
        blockNumber, setBlockNumber,
        expectedParticipants, setExpectedParticipants

    } = useContext(AppContextregister);
    const [nameErrorMessage, setNameErrorMessage] = useState('');
    const [timeErrorMessage, setTimeErrorMessage] = useState('');

    const findSemester = () => {
        let startDate = startTime ? new Date(startTime).getTime() : null;
        let endDate = endTime ? new Date(endTime).getTime() : null;

        let foundSemester = null;
        let foundStartBlock = null; // Biến để theo dõi block cho ngày bắt đầu
        let foundEndBlock = null; // Biến để theo dõi block cho ngày kết thúc

        for (const semester of listSemester) {

            if (startDate >= semester.startTime && endDate <= semester.endTime) {
                setIdSemester(semester.id);
                foundSemester = semester.id;
                setNameSemester(semester.name);

                if (
                    startDate >= semester.startTimeFirstBlock &&
                    startDate <= semester.endTimeFirstBlock
                ) {
                    foundStartBlock = false; // Tìm thấy block cho ngày bắt đầu
                } else if (
                    startDate >= semester.startTimeSecondBlock &&
                    startDate <= semester.endTimeSecondBlock
                ) {
                    foundStartBlock = true; // Tìm thấy block cho ngày bắt đầu
                }

                if (
                    endDate >= semester.startTimeFirstBlock &&
                    endDate <= semester.endTimeFirstBlock
                ) {
                    foundEndBlock = false; // Tìm thấy block cho ngày kết thúc
                } else if (
                    endDate >= semester.startTimeSecondBlock &&
                    endDate <= semester.endTimeSecondBlock
                ) {
                    foundEndBlock = true; // Tìm thấy block cho ngày kết thúc
                }
                break;
            }
        }
        if (startDate !== null && endDate !== null) {
            if (foundSemester === null) {
                setIdSemester(null);
                setBlockNumber(null);
                message.error("Không tìm thấy học kỳ phù hợp");
            } else {
                setIdSemester(foundSemester);
                if (foundStartBlock !== null && foundEndBlock !== null) {
                    if (foundStartBlock !== foundEndBlock) {
                        message.error("Không tìm thấy block phù hợp");
                    } else {
                        // Ngày bắt đầu và kết thúc ở cùng một block
                        setBlockNumber(foundStartBlock);
                    }
                }
            }
        }
    };

    useEffect(() => {
        findSemester();
    }, [startTime, endTime]);

    const [formRegister] = Form.useForm();

    function compareTime(startTime, endTime) {
        var startTimeStap = Math.floor(new Date(startTime).getTime());
        var endTimeStap = Math.floor(new Date(endTime).getTime());
        if (startTimeStap > endTimeStap || dateTimeFromLong(startTimeStap) === dateTimeFromLong(endTimeStap)) {
            return false;
        }
        return true;
    }

    function cleanData() {
        setIdSemester('');
        setIdCategory('');
        setIdObject([]);
        setIdMajor([]);
        setName('');
        setStartTime('');
        setEndTime('');
        setContentEditor('');
        setEventType(true);
        setBlockNumber(false);
        setExpectedParticipants(0);
        setNameSemester("");
    }

    const disabledDate = (current) => {
        const today = dayjs().startOf("day");
        return current < today;
    };

    function convertdateTimeFromLong(time) {
        const dateObject = new Date(time);
        const hours = dateObject.getHours();
        const minutes = dateObject.getMinutes();
        const day = dateObject.getDate();
        const month = dateObject.getMonth() + 1;
        const year = dateObject.getFullYear();

        const formattedDate = `${year}-${month.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}`;
        return formattedDate;
    }

    const onFinish = () => {
        let data = {
            idCategory: idCategory,
            idSemester: idSemester,
            listObject: idObject,
            name: name,
            startTime: startTime,
            endTime: endTime,
            eventType: eventType,
            blockNumber: blockNumber,
            description: contentEditor,
            listMajor: idMajor,
            expectedParticipants: Number(expectedParticipants),
        }
        let check = true;
        if (data.name.trim() === '') {
            setNameErrorMessage('Vui lòng nhập tên sự kiện');
            check = false;
        } else {
            setNameErrorMessage('');
        }
        if (data.startTime === '' || data.endTime === '') {
            setTimeErrorMessage('Vui lòng chọn thời gian diễn ra sự kiện');
            check = false;
        } else if (!compareTime(data.startTime, data.endTime)) {
            setTimeErrorMessage('Thời gian bắt đầu không thể lớn hơn, hoặc bằng thời gian kết thúc!!!');
            check = false
        } else {
            setTimeErrorMessage('');
        }
        if (check) {
            OREventRegisterApi.register(data)
                .then((response) => {
                    message.success('Đăng ký thành công');
                    navigate('/organizer-management/event-detail/' + response.data.data.id);
                }).catch((error) => {
                    message.error(error.response.data);
                });
        }
        // formRegister.resetFields();
        cleanData();
    };

    const clearFormInformations = () => {
        console.log("clear");
        cleanData();
        formRegister.resetFields();
    }

    return (
        <div>
            <Form
                form={formRegister}
            >
                <Space direction="vertical" size="middle" style={{ display: 'flex', marginTop: '20px' }}>
                    <Row style={{ justifyContent: 'space-between' }}>
                        <Col span={10} offset={-3}>
                            <label><span style={{ color: "red" }}>*</span> Tên sự kiện</label>
                            <Form.Item
                                name="name"
                                style={{
                                    margin: '0'
                                }}
                            >
                                <Input maxLength="500" placeholder='Nhập tên sự kiện' onChange={(e) => setName(e.target.value)} />
                            </Form.Item>
                            <span style={{ color: "red" }}>{nameErrorMessage}</span>
                        </Col>
                        <Col span={11} offset={-3}>
                            <label>Sự kiện dành cho</label>
                            <Form.Item
                                name="eventType"
                                style={{
                                    margin: '0'
                                }}
                            >
                                <Select style={{ width: "100%" }} onChange={(value) => setEventType(value)}>
                                    <Option value={0}>Sinh viên</Option>
                                    <Option value={1}>Giảng viên</Option>
                                    <Option value={2}>Tất cả</Option>
                                </Select>
                            </Form.Item>
                        </Col>
                    </Row>
                    <Row style={{ justifyContent: 'space-between' }}>
                        <Col span={10} offset={-3}>
                            <label><span style={{ color: "red" }}>*</span> Thời gian diễn ra</label>
                            <Form.Item
                                name="time"
                                style={{
                                    margin: '0'
                                }}
                            >
                                <RangePicker
                                    disabledDate={disabledDate}
                                    showTime={{ format: 'HH:mm' }}
                                    format="YYYY-MM-DD HH:mm"
                                    popupStyle={{ maxHeight: '300px' }}
                                    value={[startTime !== '' ? dayjs(new Date(startTime)) : '', endTime !== '' ? dayjs(new Date(endTime)) : '']}
                                    style={{ width: '100%' }}
                                    onChange={(value: DatePickerProps['value'] | RangePickerProps['value'], dateString: [string, string] | string
                                    ) => {
                                        setStartTime(dateString[0] !== '' ? new Date(dateString[0]).valueOf() : '');
                                        setEndTime(dateString[1] !== '' ? new Date(dateString[1]).valueOf() : '');
                                    }}
                                />
                            </Form.Item>
                            <span style={{ color: "red" }}>{timeErrorMessage}</span>
                        </Col>
                        <Col span={11} offset={-3}>
                            <label>Đối tượng</label>
                            <Form.Item
                                name="object"
                                style={{
                                    margin: '0'
                                }}
                            >
                                <Select style={{ width: "100%" }} onChange={(value) => setIdObject(value)} mode='multiple'>
                                    {listObject.map((item) => (
                                        <Select.Option key={item.id} value={item.id}>
                                            {item.name}
                                        </Select.Option>
                                    ))}
                                </Select>
                            </Form.Item>
                        </Col>
                    </Row>
                    <Row style={{ justifyContent: 'space-between' }}>
                        <Col span={10}>
                            <label>Thể loại</label>
                            <Form.Item
                                name="category"
                                style={{
                                    margin: '0'
                                }}
                            >
                                <Select style={{ width: "100%" }} onChange={(value) => setIdCategory(value)}>
                                    {listCategory.map((item) => (
                                        <Select.Option key={item.id} value={item.id}>
                                            {item.name}
                                        </Select.Option>
                                    ))}
                                </Select>
                            </Form.Item>
                        </Col>
                        <Col span={11} offset={-3}>
                            <label>Chuyên ngành</label>
                            <Form.Item
                                name="major"
                                style={{
                                    margin: '0'
                                }}
                            >
                                <Select style={{ width: "100%" }} onChange={(value) => setIdMajor(value)} mode='multiple'>
                                    {listMajor.map((item) => (
                                        <Select.Option key={item.id} value={item.id}>
                                            {item.name}
                                        </Select.Option>
                                    ))}
                                </Select>
                            </Form.Item>
                        </Col>
                    </Row>
                    <Row style={{ justifyContent: 'space-between' }}>
                        <Col span={10} offset={-3}>
                            <label>Học kỳ</label>
                            <Input value={nameSemester} readOnly />
                        </Col>
                        <Col span={11} offset={-3}>
                            <label>Block</label>
                            <Input readOnly value={blockNumber === false ? "Block 1" : "Block 2"} />
                        </Col>

                    </Row>
                    <Row style={{ justifyContent: 'space-between' }}>
                        <Col span={10} offset={-3}>
                            <label>Số người tham gia dự kiến</label>
                            <Form.Item
                                name="expectedParticipants"
                                style={{
                                    margin: '0'
                                }}
                            >
                                <InputNumber type='number' style={{ width: "100%" }} rootClassName={"mb-5"} min={0} max={32760} value={expectedParticipants} onChange={(e) => setExpectedParticipants(e)} />
                            </Form.Item>
                        </Col>
                    </Row>

                    <Row>
                        <Col span={24}>
                            <label>Mô tả</label>
                            <Form.Item
                                name="description"
                                style={{
                                    margin: '0'
                                }}
                            >
                                <FormJoditEditor
                                    // maxLength="10"
                                    value={contentEditor}
                                    onChange={(description) => setContentEditor(description)}
                                >
                                </FormJoditEditor>
                            </Form.Item>
                        </Col>
                    </Row>

                    <Row justify="center">
                        <Button type="primary" htmlType="button" style={{ marginBottom: '0.8rem', marginRight: '10px' }}
                            onClick={onFinish}>
                            <FontAwesomeIcon icon={faPenToSquare} style={{ marginRight: '0.5rem' }} />
                            Đăng ký sự kiện
                        </Button>
                        <Button type="primary" style={{ backgroundColor: '#dc3545' }}
                            htmlType="reset"
                            onClick={clearFormInformations}
                        >
                            <FontAwesomeIcon icon={faBan} style={{ marginRight: '0.5rem' }} />
                            Clear form
                        </Button>
                    </Row>
                </Space>
            </Form>
        </div>
    );
}

export default FormComponent;