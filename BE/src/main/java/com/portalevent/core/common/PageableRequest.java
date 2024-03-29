package com.portalevent.core.common;

import com.portalevent.infrastructure.constant.PaginationConstant;
import lombok.Getter;
import lombok.Setter;

/**
 * @author SonPT
 */

@Getter
@Setter
public abstract class PageableRequest {

    private int page = PaginationConstant.DEFAULT_PAGE;

    private int size = PaginationConstant.DEFAULT_SIZE;

}
