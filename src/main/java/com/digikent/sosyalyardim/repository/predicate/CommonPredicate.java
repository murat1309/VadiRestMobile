package com.digikent.sosyalyardim.repository.predicate;

import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.path.*;

import java.util.Date;

import static org.hibernate.jpa.criteria.ValueHandlerFactory.isNumeric;

/**
 * Created by gunayb on 29.07.2016.
 */
public class CommonPredicate<T> {

    private SearchCriteria criteria;
    final Class<T> typeParameterClass;

    public CommonPredicate(SearchCriteria criteria, Class<T> typeParameterClass) {
        this.criteria = criteria;
        this.typeParameterClass = typeParameterClass;
    }

    public BooleanExpression getPredicate() {
        PathBuilder<T> entityPath = new PathBuilder<T>(typeParameterClass, typeParameterClass.getSimpleName().toLowerCase());

        if(criteria.getOperation().equalsIgnoreCase(":")) {
            if(isNumeric(criteria.getValue().toString())) {
                NumberPath <Integer> path = entityPath.getNumber(criteria.getKey(), Integer.class);
                int value = Integer.parseInt(criteria.getValue().toString());
                return path.eq(value);
            } else if(Date.class.isInstance(criteria.getValue())) {
                DatePath<Date> path = entityPath.getDate(criteria.getKey(), Date.class);
                Date value = Date.class.cast(criteria.getValue());
                return path.eq(value);
            } else {
                StringPath path = entityPath.getString(criteria.getKey());
                if(!criteria.getValue().toString().isEmpty()) {
                    return path.equalsIgnoreCase(criteria.getValue().toString());
                }
            }

        } else if (criteria.getOperation().equalsIgnoreCase(">")) {
            if(isNumeric(criteria.getValue().toString())) {
                NumberPath <Integer> path = entityPath.getNumber(criteria.getKey(), Integer.class);
                int value = Integer.parseInt(criteria.getValue().toString());
                return path.goe(value);
            } else if(Date.class.isInstance(criteria.getValue())) {
                DatePath<Date> path = entityPath.getDate(criteria.getKey(), Date.class);
                Date value = Date.class.cast(criteria.getValue());
                return path.goe(value);
            }
        } else if (criteria.getOperation().equalsIgnoreCase("<")) {
            if(isNumeric(criteria.getValue().toString())) {
                NumberPath <Integer> path = entityPath.getNumber(criteria.getKey(), Integer.class);
                int value = Integer.parseInt(criteria.getValue().toString());
                return path.loe(value);
            } else if(Date.class.isInstance(criteria.getValue())) {
                DatePath<Date> path = entityPath.getDate(criteria.getKey(), Date.class);
                Date value = Date.class.cast(criteria.getValue());
                return path.loe(value);
            }
        } else if(criteria.getOperation().equalsIgnoreCase("NULLOR")) {
            BooleanPath path = entityPath.getBoolean(criteria.getKey());
            return path.eq(false).or(path.isNull());
        }

        return null;
    }

}
