package org.apache.shardingsphere.example.core.api.repository;

import org.apache.shardingsphere.example.core.api.entity.ShopMessageHistory;

import java.util.List;

/**
 *
 * @author liguoping
 * @since 2021/9/29 19:49
 */
public interface ShopRepository {

    List<ShopMessageHistory> fetchShopMessageSendHistoryList(
            Long shopId, String statusId);

    List<ShopMessageHistory> fetchShopMessageSendHistorysss(
            Long shopId, String statusId);
}
