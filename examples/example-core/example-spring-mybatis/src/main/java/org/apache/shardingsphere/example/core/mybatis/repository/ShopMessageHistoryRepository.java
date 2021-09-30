package org.apache.shardingsphere.example.core.mybatis.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.shardingsphere.example.core.api.entity.ShopMessageHistory;
import org.apache.shardingsphere.example.core.api.repository.ShopRepository;

import java.util.List;

/**
 *
 * @author liguoping
 * @since 2021/9/29 19:25
 */
@Mapper
public interface ShopMessageHistoryRepository extends ShopRepository {

    List<ShopMessageHistory> fetchShopMessageSendHistoryList(
            @Param("shopId") Long shopId, @Param("statusId") String statusId);

    List<ShopMessageHistory> fetchShopMessageSendHistorysss(
            @Param("shopId") Long shopId, @Param("statusId") String statusId);
}
