package com.company.project.common.enums;

import lombok.Getter;

//订单状态（0:待支付；1:已支付；2:已发货；3:已完成；4:已取消）
@Getter
public enum OrderEnums {

    WAIT_PAY(0),

    PAYED(1),

    SENDED(2),

    SUCCESS(3),

    CANCEL(4),


    ;

    private int value;

    private OrderEnums(int value) {
        this.value = value;
    }
}
