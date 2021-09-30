/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.example.core.mybatis.service;

import org.apache.shardingsphere.example.core.api.entity.ShopMessageHistory;
import org.apache.shardingsphere.example.core.api.repository.OrderRepository;
import org.apache.shardingsphere.example.core.api.repository.ShopRepository;
import org.apache.shardingsphere.example.core.api.service.ExampleService;
import org.apache.shardingsphere.example.core.mybatis.repository.ShopMessageHistoryRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

@Service
@Primary
public class ShopMessageHistoryServiceImpl implements ExampleService {
    
    @Resource
    private ShopRepository shopRepository;

    @Resource
    private OrderRepository orderRepository;

    @Override
    public void initEnvironment() throws SQLException {
    }
    


    @Override
    public void cleanEnvironment() throws SQLException {
    }
    
    @Override
    @Transactional
    public void processSuccess() throws SQLException {
        System.out.println("-------------- Process Success Begin ---------------");
        List<ShopMessageHistory> shopMessageHistories = shopRepository.fetchShopMessageSendHistoryList(1L, "1");
        shopMessageHistories.forEach(System.out::println);
        System.out.println("-------------- Process Success Finish --------------");
    }
    
    @Override
    @Transactional
    public void processFailure() throws SQLException {
        System.out.println("-------------- Process Failure Begin ---------------");
        System.out.println("-------------- Process Failure Finish --------------");
        throw new RuntimeException("Exception occur for transaction test.");
    }


    
    @Override
    public void printData() throws SQLException {

    }
}
