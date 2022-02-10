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

grammar RQLStatement;

import Keyword, Literals, Symbol;

showResources
    : SHOW SCHEMA RESOURCES (FROM schemaName)?
    ;

showSingleTableRules
    : SHOW SINGLE TABLE RULES (FROM schemaName)?
    ;

showSingleTable
    : SHOW SINGLE (table | TABLES) (FROM schemaName)?
    ;

showShardingTableRulesUsedKeyGenerator
    : SHOW SHARDING TABLE RULES USED KEY GENERATOR keyGeneratorName (FROM schemaName)?
    ;

countSchemaRules
    : COUNT SCHEMA RULES (FROM schemaName)?
    ;

showRulesUsedResource
    : SHOW RULES USED RESOURCE resourceName (FROM schemaName)?
    ;

resourceName
    : IDENTIFIER | STRING
    ;

schemaName
    : IDENTIFIER
    ;

table
    : TABLE tableName
    ;

tableName
    : IDENTIFIER
    ;
    
keyGeneratorName
    : IDENTIFIER | STRING
    ;