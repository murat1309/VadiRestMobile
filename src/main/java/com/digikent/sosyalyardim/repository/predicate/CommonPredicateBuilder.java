package com.digikent.sosyalyardim.repository.predicate;

import com.mysema.query.types.expr.BooleanExpression;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gunayb on 29.07.2016.
 */
public class CommonPredicateBuilder<T> {

    List<SearchCriteria> params;

    final Class<T> typeParameterClass;

    public CommonPredicateBuilder(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
        this.params = new ArrayList<>();
        //params.add(new SearchCriteria("deleteFlag", "NULLOR", false));
    }

    public CommonPredicateBuilder with(String key, String operation, Object value) {
        if(value != null) {
            params.add(new SearchCriteria(key, operation, value));
        }
        return this;
    }

    public BooleanExpression build() {

        List<BooleanExpression> predicates = new ArrayList<>();
        CommonPredicate<T> predicate;
        params.forEach(param->{
            BooleanExpression expression = new CommonPredicate<T>(param, typeParameterClass).getPredicate();
            if(expression != null) {
                predicates.add(expression);
            }
        });
        if(predicates.isEmpty()) {
            return null;
        }
        BooleanExpression result = predicates.get(0);
        for (int i = 1; i < predicates.size(); i++) {
            result = result.and(predicates.get(i));
        }
        return result;
    }
}
