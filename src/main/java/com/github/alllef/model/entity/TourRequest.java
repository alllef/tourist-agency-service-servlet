package com.github.alllef.model.entity;

import com.github.alllef.utils.enums.RequestStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class TourRequest {
    private Long tourRequestId;
    private Long tourId;
    private Long userId;
    private RequestStatus requestStatus;
    private Integer discount;
}
