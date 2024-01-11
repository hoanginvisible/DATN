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
 * Object: Event major (Chuyên ngành của sự kiện)
 * Description: Lưu chuyên ngành, chuyên ngành hẹp của sự kiện
 * VD: Sự kiện A thuộc chuyên ngành PTPM.
 * 		Sự kiện B thuộc chuyên ngành hẹp JAVA.và C#
 */

@Getter
@Setter
@Table(name = "event_major")
@Entity
public class EventMajor extends PrimaryEntity implements Serializable {


    @Column(length = EntityProperties.LENGTH_ID, nullable = false)
    private String eventId;

    @Column(length = EntityProperties.LENGTH_ID, nullable = false)
    private String majorId;

}
