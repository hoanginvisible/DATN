package com.portalevent.entity;

import com.portalevent.entity.base.PrimaryEntity;
import com.portalevent.infrastructure.constant.EntityProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author SonPT
 */

/**
 * Object: Major (Chuyên ngành)
 * Description: Lưu các chuyên ngành, chuyên ngành hẹp
 * - Lưu theo đệ quy. VD: Chuyên ngành hẹp(bộ môn) JAVA thuộc chuyên ngành PTPM thuộc ngành UDPM (không lưu ngành UDPM)
 * -> ptpm = {id: <UUID1>; mainMajorId: null; code: "PTPM"; name: "Phát triển phần mềm"; mailOfManager: null}
 *   java = {id: <UUID2>; mainMajorId: <UUID1>; code: "JAVA"; name: "Bộ môn Java"; mailOfManager: <email trưởng bộ môn Java>}
 *   cSharp = {id: <UUID2>; mainMajorId: <UUID1>; code: "C#"; name: "Bộ môn C Sharp"; mailOfManager: <email trưởng bộ môn C#}
 */

@Entity
@Table(name = "major")
@Getter
@Setter
public class Major extends PrimaryEntity implements Serializable {

    @Column(length = EntityProperties.LENGTH_ID)
    private String mainMajorId;

    @Column(length = EntityProperties.LENGTH_CODE)
    private String code;

    @Column(length = EntityProperties.LENGTH_NAME)
    private String name;

    @Column(length = EntityProperties.LENGTH_EMAIL)
    private String mailOfManager;

}
