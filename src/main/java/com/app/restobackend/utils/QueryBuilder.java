package com.app.restobackend.utils;


import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import com.app.restobackend.commons.GenericEntity;
import com.app.restobackend.commons.PagingRequest;

import java.util.Map;

public class QueryBuilder<T extends GenericEntity> {

    private Specification<T> specification;
    private PagingRequest pagingRequest;

    public QueryBuilder(PagingRequest pagingRequest) {
        this.pagingRequest = pagingRequest;
        this.specification = (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("id"), 0);
    }

    public Specification<T> buildSpecification() {
        if (null == pagingRequest.getDtSearch())
            return this.specification;

        final Map<String, PagingRequest.FilterObject> filterObjectMap = pagingRequest.getDtSearch();

        for (String key : filterObjectMap.keySet()) {
            final PagingRequest.FilterObject filterObject = filterObjectMap.get(key);

            switch (filterObject.getMatchMode()) {
                case CONTAINS:
                    this.specification.and((root, query, criteriaBuilder) ->
                            criteriaBuilder.like(root.get(key), "%" + filterObject.getValue() + "%"));
                    break;
                case EQUALS:
                    this.specification.and((root, query, criteriaBuilder) ->
                            criteriaBuilder.equal(root.get(key), this.predictValueType(filterObject.getType(), filterObject.getValue())));
                    break;
            }
        }

        return this.specification;
    }

    /**
     *
     * @param type
     * @param value
     * @return
     */
    private Object predictValueType(PagingRequest.FilterObjectType type, String value) {
        switch (type) {
            case NUMERIC:
                return Integer.parseInt(value);
            default:
                return value;
        }
    }

}
