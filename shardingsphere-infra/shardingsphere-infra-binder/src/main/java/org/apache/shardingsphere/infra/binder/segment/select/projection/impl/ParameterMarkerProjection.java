package org.apache.shardingsphere.infra.binder.segment.select.projection.impl;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.apache.shardingsphere.infra.binder.segment.select.projection.Projection;

import java.util.Optional;

/**
 * todo
 *
 * @author liguoping
 * @since 2021/10/15 18:08
 */
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class ParameterMarkerProjection implements Projection {

    private final int parameterMarkerIndex;

    private final String alias;

    @Override
    public String getExpression() {
        return String.valueOf(parameterMarkerIndex);
    }

    @Override
    public String getColumnLabel() {
        return getAlias().orElse(String.valueOf(parameterMarkerIndex));
    }

    @Override
    public Optional<String> getAlias() {
        return Optional.ofNullable(alias);
    }

    /**
     * Get expression with alias.
     *
     * @return expression with alias
     */
    public String getExpressionWithAlias() {
        return getExpression() + (null == alias ? "" : " AS " + alias);
    }
}
