--
-- Licensed to the Apache Software Foundation (ASF) under one or more
-- contributor license agreements.  See the NOTICE file distributed with
-- this work for additional information regarding copyright ownership.
-- The ASF licenses this file to You under the Apache License, Version 2.0
-- (the "License"); you may not use this file except in compliance with
-- the License.  You may obtain a copy of the License at
--
--     http://www.apache.org/licenses/LICENSE-2.0
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

CREATE USER 'root'@'%' IDENTIFIED BY '';
GRANT All privileges ON *.* TO 'root'@'%';

DROP DATABASE IF EXISTS verification_dataset;

CREATE DATABASE verification_dataset;

CREATE TABLE verification_dataset.t_order (order_id INT NOT NULL, user_id INT NOT NULL, status VARCHAR(45) NULL, PRIMARY KEY (order_id));
CREATE TABLE verification_dataset.t_order_item (item_id INT NOT NULL, order_id INT NOT NULL, user_id INT NOT NULL, status VARCHAR(45) NULL, creation_date DATE, PRIMARY KEY (item_id));
CREATE TABLE verification_dataset.t_single_table (single_id INT NOT NULL, id INT NOT NULL, status VARCHAR(45) NULL, PRIMARY KEY (single_id));
CREATE TABLE verification_dataset.t_broadcast_table (id INT NOT NULL, status VARCHAR(45) NULL, PRIMARY KEY (id));
CREATE TABLE verification_dataset.t_order_federate (order_id INT NOT NULL, user_id INT NOT NULL, status VARCHAR(45) NULL, PRIMARY KEY (order_id));
CREATE TABLE verification_dataset.t_order_item_federate (item_id INT NOT NULL, order_id INT NOT NULL, user_id INT NOT NULL, status VARCHAR(45) NULL, PRIMARY KEY (item_id));
CREATE TABLE verification_dataset.t_order_federate_sharding (order_id_sharding INT NOT NULL, user_id INT NOT NULL, status VARCHAR(45) NULL, PRIMARY KEY (order_id_sharding));
CREATE TABLE verification_dataset.t_order_item_federate_sharding (item_id INT NOT NULL, order_id INT NOT NULL, user_id INT NOT NULL, status VARCHAR(45) NULL, remarks VARCHAR(45) NULL, PRIMARY KEY (item_id));
CREATE TABLE verification_dataset.t_user (user_id INT NOT NULL, address_id INT NOT NULL, pwd VARCHAR(45) NULL, status VARCHAR(45) NULL, PRIMARY KEY (user_id));
CREATE TABLE verification_dataset.t_user_item (item_id INT NOT NULL, user_id INT NOT NULL, status VARCHAR(45) NULL, creation_date DATE, PRIMARY KEY (item_id));
CREATE TABLE verification_dataset.t_user_encrypt_federate (user_id INT NOT NULL, pwd VARCHAR(45) NULL, username VARCHAR(45) NULL, PRIMARY KEY (user_id));
CREATE TABLE verification_dataset.t_user_encrypt_federate_sharding (user_id INT NOT NULL, pwd VARCHAR(45) NULL, username VARCHAR(45) NULL, PRIMARY KEY (user_id));
CREATE TABLE verification_dataset.t_user_info (user_id INT NOT NULL,  information VARCHAR(45) NULL, PRIMARY KEY (user_id));

CREATE INDEX order_index_t_order ON verification_dataset.t_order (order_id);
CREATE INDEX user_index_t_user ON verification_dataset.t_user (user_id);


DROP DATABASE IF EXISTS write_dataset;

CREATE DATABASE write_dataset;

CREATE TABLE write_dataset.t_order (order_id INT NOT NULL, user_id INT NOT NULL, status VARCHAR(45) NULL, PRIMARY KEY (order_id));
CREATE TABLE write_dataset.t_order_item (item_id INT NOT NULL, order_id INT NOT NULL, user_id INT NOT NULL, status VARCHAR(45) NULL, creation_date DATE, PRIMARY KEY (item_id));
CREATE TABLE write_dataset.t_single_table (single_id INT NOT NULL, id INT NOT NULL, status VARCHAR(45) NULL, PRIMARY KEY (single_id));
CREATE TABLE write_dataset.t_broadcast_table (id INT NOT NULL, status VARCHAR(45) NULL, PRIMARY KEY (id));
CREATE TABLE write_dataset.t_order_federate (order_id INT NOT NULL, user_id INT NOT NULL, status VARCHAR(45) NULL, PRIMARY KEY (order_id));
CREATE TABLE write_dataset.t_order_item_federate (item_id INT NOT NULL, order_id INT NOT NULL, user_id INT NOT NULL, status VARCHAR(45) NULL, PRIMARY KEY (item_id));
CREATE TABLE write_dataset.t_order_federate_sharding (order_id_sharding INT NOT NULL, user_id INT NOT NULL, status VARCHAR(45) NULL, PRIMARY KEY (order_id_sharding));
CREATE TABLE write_dataset.t_order_item_federate_sharding (item_id INT NOT NULL, order_id INT NOT NULL, user_id INT NOT NULL, status VARCHAR(45) NULL, remarks VARCHAR(45) NULL, PRIMARY KEY (item_id));
CREATE TABLE write_dataset.t_user (user_id INT NOT NULL, address_id INT NOT NULL, pwd VARCHAR(45) NULL, status VARCHAR(45) NULL, PRIMARY KEY (user_id));
CREATE TABLE write_dataset.t_user_item (item_id INT NOT NULL, user_id INT NOT NULL, status VARCHAR(45) NULL, creation_date DATE, PRIMARY KEY (item_id));
CREATE TABLE write_dataset.t_user_encrypt_federate (user_id INT NOT NULL, pwd VARCHAR(45) NULL, username VARCHAR(45) NULL, PRIMARY KEY (user_id));
CREATE TABLE write_dataset.t_user_encrypt_federate_sharding (user_id INT NOT NULL, pwd VARCHAR(45) NULL, username VARCHAR(45) NULL, PRIMARY KEY (user_id));
CREATE TABLE write_dataset.t_user_info (user_id INT NOT NULL,  information VARCHAR(45) NULL, PRIMARY KEY (user_id));

CREATE INDEX order_index_t_order ON write_dataset.t_order (order_id);
CREATE INDEX user_index_t_user ON write_dataset.t_user (user_id);


DROP DATABASE IF EXISTS read_dataset;

CREATE DATABASE read_dataset;

CREATE TABLE read_dataset.t_order (order_id INT NOT NULL, user_id INT NOT NULL, status VARCHAR(45) NULL, PRIMARY KEY (order_id));
CREATE TABLE read_dataset.t_order_item (item_id INT NOT NULL, order_id INT NOT NULL, user_id INT NOT NULL, status VARCHAR(45) NULL, creation_date DATE, PRIMARY KEY (item_id));
CREATE TABLE read_dataset.t_single_table (single_id INT NOT NULL, id INT NOT NULL, status VARCHAR(45) NULL, PRIMARY KEY (single_id));
CREATE TABLE read_dataset.t_broadcast_table (id INT NOT NULL, status VARCHAR(45) NULL, PRIMARY KEY (id));
CREATE TABLE read_dataset.t_order_federate (order_id INT NOT NULL, user_id INT NOT NULL, status VARCHAR(45) NULL, PRIMARY KEY (order_id));
CREATE TABLE read_dataset.t_order_item_federate (item_id INT NOT NULL, order_id INT NOT NULL, user_id INT NOT NULL, status VARCHAR(45) NULL, PRIMARY KEY (item_id));
CREATE TABLE read_dataset.t_order_federate_sharding (order_id_sharding INT NOT NULL, user_id INT NOT NULL, status VARCHAR(45) NULL, PRIMARY KEY (order_id_sharding));
CREATE TABLE read_dataset.t_order_item_federate_sharding (item_id INT NOT NULL, order_id INT NOT NULL, user_id INT NOT NULL, status VARCHAR(45) NULL, remarks VARCHAR(45) NULL, PRIMARY KEY (item_id));
CREATE TABLE read_dataset.t_user (user_id INT NOT NULL, address_id INT NOT NULL, pwd VARCHAR(45) NULL, status VARCHAR(45) NULL, PRIMARY KEY (user_id));
CREATE TABLE read_dataset.t_user_item (item_id INT NOT NULL, user_id INT NOT NULL, status VARCHAR(45) NULL, creation_date DATE, PRIMARY KEY (item_id));
CREATE TABLE read_dataset.t_user_encrypt_federate (user_id INT NOT NULL, pwd VARCHAR(45) NULL, username VARCHAR(45) NULL, PRIMARY KEY (user_id));
CREATE TABLE read_dataset.t_user_encrypt_federate_sharding (user_id INT NOT NULL, pwd VARCHAR(45) NULL, username VARCHAR(45) NULL, PRIMARY KEY (user_id));
CREATE TABLE read_dataset.t_user_info (user_id INT NOT NULL,  information VARCHAR(45) NULL, PRIMARY KEY (user_id));

CREATE INDEX order_index_t_order ON read_dataset.t_order (order_id);
CREATE INDEX user_index_t_user ON read_dataset.t_user (user_id);


DROP DATABASE IF EXISTS prod_dataset;
CREATE DATABASE prod_dataset;

CREATE TABLE prod_dataset.t_shadow (order_id BIGINT NOT NULL, user_id INT NOT NULL, order_name VARCHAR(32) NOT NULL, type_char CHAR(1) NOT NULL, type_boolean BOOLEAN NOT NULL, type_smallint SMALLINT NOT NULL, type_enum ENUM('spring', 'summer', 'autumn', 'winter'), type_decimal DECIMAL(18,2) NOT NULL, type_date DATE NOT NULL, type_time TIME NOT NULL, type_timestamp TIMESTAMP NOT NULL, PRIMARY KEY (order_id));


DROP DATABASE IF EXISTS shadow_dataset;
CREATE DATABASE shadow_dataset;

CREATE TABLE shadow_dataset.t_shadow (order_id BIGINT NOT NULL, user_id INT NOT NULL, order_name VARCHAR(32) NOT NULL, type_char CHAR(1) NOT NULL, type_boolean BOOLEAN NOT NULL, type_smallint SMALLINT NOT NULL, type_enum ENUM('spring', 'summer', 'autumn', 'winter'), type_decimal DECIMAL(18,2) NOT NULL, type_date DATE NOT NULL, type_time TIME NOT NULL, type_timestamp TIMESTAMP NOT NULL, PRIMARY KEY (order_id));
