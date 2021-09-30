package org.apache.shardingsphere.example.core.api.entity;

import lombok.Data;

import java.util.Date;

/**
 *
 * @author liguoping
 * @since 2021/9/29 19:27
 */
@Data
public class ShopMessageHistory {

    /**
     * id
     */
    private Long id;

    /**
     * shop_id
     */
    private Long shopId;

    /**
     * signin_name
     */
    private String signinName;

    /**
     * sms_content
     */
    private String smsContent;

    /**
     * sms_size
     */
    private Integer smsSize;

    /**
     * receive_status_id
     */
    private String receiveStatusId;

    /**
     * created_date_time
     */
    private Date createdDateTime;

    /**
     * mobile
     */
    private String mobile;

    /**
     * sms_balance_discount
     */
    private Integer smsBalanceDiscount;

    /**
     * external_id
     */
    private String externalId;

    /**
     * failure_des
     */
    private String failureDes;
}
