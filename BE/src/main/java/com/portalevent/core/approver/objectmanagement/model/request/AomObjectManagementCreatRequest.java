package com.portalevent.core.approver.objectmanagement.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * Class này để chứa dữ liệu bên FE để add object
 */

@Getter
@Setter
public class AomObjectManagementCreatRequest {

    @NotBlank(message = "Không để trống tên thể loại!!!")
    private String name;
}
