<?xml version="1.0" encoding="UTF-8" ?>
<!--
  ~ Licensed to the Apache Software Foundation (ASF) under one or more
  ~ contributor license agreements.  See the NOTICE file distributed with
  ~ this work for additional information regarding copyright ownership.
  ~ The ASF licenses this file to You under the Apache License, Version 2.0
  ~ (the "License"); you may not use this file except in compliance with
  ~ the License.  You may obtain a copy of the License at
  ~
  ~     http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  -->
<#assign package = feature?replace('-', '.')?replace(',', '.') />

<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="org.apache.shardingsphere.example.${package}.${framework?replace('-', '.')}.repository.AddressRepository">
    <resultMap id="baseResultMap" type="org.apache.shardingsphere.example.${package}.${framework?replace('-', '.')}.entity.Address">
        <result column="address_id" property="addressId" jdbcType="BIGINT"/>
        <result column="address_name" property="addressName" jdbcType="VARCHAR"/>
    </resultMap>

    <update id="createTableIfNotExists">
        CREATE TABLE IF NOT EXISTS t_address (address_id BIGINT NOT NULL, address_name VARCHAR(100) NOT NULL, PRIMARY KEY (address_id));
    </update>
    
    <update id="truncateTable">
        TRUNCATE TABLE t_address;
    </update>
    
    <update id="dropTable">
        DROP TABLE IF EXISTS t_address;
    </update>
    
    <insert id="insert">
        INSERT INTO t_address (address_id, address_name) VALUES (${r"#{addressId,jdbcType=BIGINT}, #{addressName,jdbcType=VARCHAR}"});
    </insert>
    
    <delete id="delete">
        DELETE FROM t_address WHERE address_id = ${r"#{addressId,jdbcType=BIGINT}"};
    </delete>
    
    <select id="selectAll" resultMap="baseResultMap">
        SELECT * FROM t_address;
    </select>
    
</mapper>
