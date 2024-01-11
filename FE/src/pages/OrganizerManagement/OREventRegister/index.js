import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Button, Col, DatePicker, Form, Input, Modal, Row, Select, message, Table } from 'antd';
import React, { useContext, useEffect, useMemo, useState } from 'react';
import JoditEditor, { Jodit } from 'jodit-react';
import FormItem from 'antd/es/form/FormItem';
import { faCheck, faPlus, faTrashCan } from '@fortawesome/free-solid-svg-icons';
import TextArea from 'rc-textarea';
import dayjs from 'dayjs';
import { AppContextregister } from './context';
import OREventRegisterApi from './OREventRegisterApi';
import { render } from '@testing-library/react';
import { Option } from 'rc-select';
import OREventRegisterWrapper from './OREventRegisterWrapper';

export default function OREventRegister() {
    const [name, setName] = useState("");
    const [nameBlock, setNameBlock] = useState("");
    const [nameSemester, setNameSemester] = useState("");
    const [formality, setFormality] = useState();
    const [startTime, setStartTime] = useState("");
    const [endTime, setEndTime] = useState("");
    const [idCategory, setIdCategory] = useState("");
    const [idSemester, setIdSemester] = useState("");
    const [idObject, setIdObject] = useState([]);
    const [idMajor, setIdMajor] = useState([]);
    const [location, setLocation] = useState("");
    const [contentEditor, setContentEditor] = useState("");
    const [listResources, setListResources] = useState([]);
    const [bannerPath, setBannerPath] = useState("");
    const [backfroundPath, setBackgroundPath] = useState("");
    const [listSemester, setListSemester] = useState([]);
    const [listObject, setListObject] = useState([]);
    const [listMajor, setListMajor] = useState([]);
    const [listCategory, setListCategory] = useState([]);
    const [listAgenda, setListAgenda] = useState([]);
    const [listLocation, setListLocation] = useState([]);
    const [expectedParticipants, setExpectedParticipants] = useState(null);
    const [eventType, setEventType] = useState(null);
    const [standeePath, setStandeePath] = useState('');
    const [blockNumber, setBlockNumber] = useState(false);

    return (
        <AppContextregister.Provider
            value={{
                name, setName,
                idCategory, setIdCategory,
                startTime, setStartTime,
                endTime, setEndTime,
                formality, setFormality,
                idSemester, setIdSemester,
                idObject, setIdObject,
                idMajor, setIdMajor,
                contentEditor, setContentEditor,
                bannerPath, setBannerPath,
                backfroundPath, setBackgroundPath,
                listResources, setListResources,
                listSemester, setListSemester,
                listObject, setListObject,
                listMajor, setListMajor,
                listCategory, setListCategory,
                location, setLocation,
                listAgenda, setListAgenda,
                listLocation, setListLocation,
                eventType, setEventType,
                standeePath, setStandeePath,
                blockNumber, setBlockNumber,
                expectedParticipants, setExpectedParticipants,
            }}>
            <OREventRegisterWrapper />
        </AppContextregister.Provider>
    );
}
