package com.portalevent.core.approver.semestermanagement.service;

import com.portalevent.core.approver.semestermanagement.model.request.AewaSemesterRequest;
import com.portalevent.core.approver.semestermanagement.model.respone.AewaSemesterResponse;
import com.portalevent.core.common.PageableObject;
import com.portalevent.entity.Semester;
import jakarta.validation.Valid;

import java.util.List;

public interface AewaSemesterService {

    List<Semester> getAll1();

    PageableObject<AewaSemesterResponse> getListSemester(Integer page, String request);

    Semester getOne(String id);

    Semester add(@Valid AewaSemesterRequest semester);

    Semester update(@Valid AewaSemesterRequest semester, String id);

    boolean remove(String id);
}
