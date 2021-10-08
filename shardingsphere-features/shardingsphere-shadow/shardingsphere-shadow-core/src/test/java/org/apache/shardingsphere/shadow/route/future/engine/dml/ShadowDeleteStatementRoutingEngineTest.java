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

package org.apache.shardingsphere.shadow.route.future.engine.dml;

import org.apache.shardingsphere.infra.binder.statement.dml.DeleteStatementContext;
import org.apache.shardingsphere.infra.route.context.RouteContext;
import org.apache.shardingsphere.infra.route.context.RouteMapper;
import org.apache.shardingsphere.infra.route.context.RouteUnit;
import org.apache.shardingsphere.shadow.algorithm.config.AlgorithmProvidedShadowRuleConfiguration;
import org.apache.shardingsphere.shadow.algorithm.shadow.column.ColumnRegexMatchShadowAlgorithm;
import org.apache.shardingsphere.shadow.api.config.datasource.ShadowDataSourceConfiguration;
import org.apache.shardingsphere.shadow.api.config.table.ShadowTableConfiguration;
import org.apache.shardingsphere.shadow.api.shadow.column.ShadowOperationType;
import org.apache.shardingsphere.shadow.route.future.engine.determiner.ShadowColumnCondition;
import org.apache.shardingsphere.shadow.rule.ShadowRule;
import org.apache.shardingsphere.shadow.spi.ShadowAlgorithm;
import org.apache.shardingsphere.sql.parser.sql.common.segment.dml.column.ColumnSegment;
import org.apache.shardingsphere.sql.parser.sql.common.segment.dml.expr.BinaryOperationExpression;
import org.apache.shardingsphere.sql.parser.sql.common.segment.dml.expr.simple.LiteralExpressionSegment;
import org.apache.shardingsphere.sql.parser.sql.common.segment.dml.predicate.WhereSegment;
import org.apache.shardingsphere.sql.parser.sql.common.segment.generic.CommentSegment;
import org.apache.shardingsphere.sql.parser.sql.common.segment.generic.table.SimpleTableSegment;
import org.apache.shardingsphere.sql.parser.sql.common.segment.generic.table.TableNameSegment;
import org.apache.shardingsphere.sql.parser.sql.common.value.identifier.IdentifierValue;
import org.apache.shardingsphere.sql.parser.sql.dialect.statement.mysql.dml.MySQLDeleteStatement;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ShadowDeleteStatementRoutingEngineTest {

    private ShadowDeleteStatementRoutingEngine shadowDeleteStatementRoutingEngine;

    @Before
    public void init() {
        shadowDeleteStatementRoutingEngine = new ShadowDeleteStatementRoutingEngine(createDeleteStatementContext(), createParameters());
    }

    private List<Object> createParameters() {
        return null;
    }

    private DeleteStatementContext createDeleteStatementContext() {
        DeleteStatementContext result = mock(DeleteStatementContext.class);
        Collection<SimpleTableSegment> allTables = new LinkedList<>();
        allTables.add(new SimpleTableSegment(new TableNameSegment(20, 25, new IdentifierValue("t_order"))));
        when(result.getAllTables()).thenReturn(allTables);
        BinaryOperationExpression binaryOperationExpression = mock(BinaryOperationExpression.class);
        when(binaryOperationExpression.getLeft()).thenReturn(new ColumnSegment(0, 0, new IdentifierValue("user_id")));
        when(binaryOperationExpression.getRight()).thenReturn(new LiteralExpressionSegment(0, 0, "1"));
        WhereSegment whereSegment = new WhereSegment(0,0, binaryOperationExpression);
        when(result.getWhere()).thenReturn(Optional.of(whereSegment));
        MySQLDeleteStatement mySQLDeleteStatement = new MySQLDeleteStatement();
        Collection<CommentSegment> commentSegments = new LinkedList<>();
        commentSegments.add(new CommentSegment("/*shadow:true,foo:bar*/", 0, 20));
        commentSegments.add(new CommentSegment("/*aaa:bbb*/", 21, 30));
        mySQLDeleteStatement.setCommentSegments(commentSegments);
        when(result.getSqlStatement()).thenReturn(mySQLDeleteStatement);
        return result;
    }

    @Test
    public void assertRouteAndParseShadowColumnConditions() {
        RouteContext routeContext = mock(RouteContext.class);
        Collection<RouteUnit> routeUnits = new LinkedList<>();
        routeUnits.add(new RouteUnit(new RouteMapper("ds", "ds_shadow"), new LinkedList<>()));
        when(routeContext.getRouteUnits()).thenReturn(routeUnits);
        shadowDeleteStatementRoutingEngine.route(routeContext, new ShadowRule(createAlgorithmProvidedShadowRuleConfiguration()));
        Optional<Collection<ShadowColumnCondition>> shadowColumnConditions = shadowDeleteStatementRoutingEngine.parseShadowColumnConditions();
        assertThat(shadowColumnConditions.isPresent(), is(true));
        Collection<ShadowColumnCondition> shadowColumns = shadowColumnConditions.get();
        assertThat(shadowColumns.size(), is(1));
        Iterator<ShadowColumnCondition> iterator = shadowColumns.iterator();
        ShadowColumnCondition userId = iterator.next();
        assertThat(userId.getColumn(), is("user_id"));
        assertThat(userId.getTable(), is("t_order"));
        assertThat(userId.getValues().iterator().next(), is("1"));
        Optional<Collection<String>> sqlNotes = shadowDeleteStatementRoutingEngine.parseSqlNotes();
        assertThat(sqlNotes.isPresent(), is(true));
        assertThat(sqlNotes.get().size(), is(2));
        Iterator<String> sqlNotesIt = sqlNotes.get().iterator();
        assertThat(sqlNotesIt.next(), is("/*shadow:true,foo:bar*/"));
        assertThat(sqlNotesIt.next(), is("/*aaa:bbb*/"));
    }

    private AlgorithmProvidedShadowRuleConfiguration createAlgorithmProvidedShadowRuleConfiguration() {
        AlgorithmProvidedShadowRuleConfiguration result = new AlgorithmProvidedShadowRuleConfiguration("shadow", Arrays.asList("ds", "ds1"), Arrays.asList("shadow_ds", "shadow_ds1"));
        result.setEnable(true);
        result.setDataSources(createDataSources());
        result.setTables(createTables());
        result.setShadowAlgorithms(createShadowAlgorithms());
        return result;
    }

    private Map<String, ShadowAlgorithm> createShadowAlgorithms() {
        Map<String, ShadowAlgorithm> result = new LinkedHashMap<>();
        result.put("user-id-delete-regex-algorithm", createColumnShadowAlgorithm());
        return result;
    }

    private ShadowAlgorithm createColumnShadowAlgorithm() {
        final ColumnRegexMatchShadowAlgorithm columnRegexMatchShadowAlgorithm = new ColumnRegexMatchShadowAlgorithm();
        Properties properties = new Properties();
        properties.setProperty("column", "user_id");
        properties.setProperty("operation", "delete");
        properties.setProperty("regex", "[1]");
        columnRegexMatchShadowAlgorithm.setProps(properties);
        columnRegexMatchShadowAlgorithm.init();
        return columnRegexMatchShadowAlgorithm;
    }

    private Map<String, ShadowTableConfiguration> createTables() {
        Map<String, ShadowTableConfiguration> result = new LinkedHashMap<>();
        Collection<String> shadowAlgorithmNames = new LinkedList<>();
        shadowAlgorithmNames.add("user-id-delete-regex-algorithm");
        result.put("t_order", new ShadowTableConfiguration(Collections.singletonList("shadow-data-source-0"), shadowAlgorithmNames));
        return result;
    }

    private Map<String, ShadowDataSourceConfiguration> createDataSources() {
        Map<String, ShadowDataSourceConfiguration> result = new LinkedHashMap<>();
        result.put("shadow-data-source-0", new ShadowDataSourceConfiguration("ds", "ds_shadow"));
        return result;
    }

    @Test
    public void assertCreateShadowDetermineCondition() {
        assertThat(shadowDeleteStatementRoutingEngine.createShadowDetermineCondition().getShadowOperationType(), is(ShadowOperationType.DELETE));
    }

    @Test
    public void assertGetAllTables() {
        Collection<SimpleTableSegment> allTables = shadowDeleteStatementRoutingEngine.getAllTables();
        assertThat(allTables.size(), is(1));
        assertThat(allTables.iterator().next().getTableName().getIdentifier().getValue(), is("t_order"));
    }
}
