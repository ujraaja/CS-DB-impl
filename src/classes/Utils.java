package classes;

import storageManager.Field;
import storageManager.FieldType;
import storageManager.Tuple;

public class Utils {
    
    public static boolean checkCondition(SearchCondition condition, Tuple tuple) {
        if(condition.searchCondition == null)
            return checkCondition(condition.booleanTerm, tuple);
        return checkCondition(condition.booleanTerm, tuple) || checkCondition(condition.searchCondition, tuple);
    }
    
    public static boolean checkCondition(BooleanTerm booleanTerm, Tuple tuple) {
        if(booleanTerm.booleanTerm == null)
            return checkCondition(booleanTerm.booleanFactor, tuple);
        return checkCondition(booleanTerm.booleanFactor, tuple) && checkCondition(booleanTerm.booleanTerm, tuple);
    }
    
    public static boolean checkCondition(BooleanFactor booleanFactor, Tuple tuple) {
        Object v1 = evaluate(booleanFactor.expression1, tuple);
        Object v2 = evaluate(booleanFactor.expression2, tuple);
        if(v1 instanceof java.lang.Integer && v2 instanceof java.lang.Integer) {
            switch (booleanFactor.operator) {
                case '<':
                    return (int) v1 < (int) v2;
                case '>':
                    return (int) v1 > (int) v2;
                case '=':
                    return (int) v1 == (int) v2;
            }
        } else {
            assert(booleanFactor.operator == '=');
            return v1.toString().equals(v2.toString());
        }
        return false;
    }
    
    public static Object evaluate(Expression expression, Tuple tuple) {
        Object v1 = evaluate(expression.term1, tuple);
        if(expression.operator == ' ')
            return v1;
        Object v2 = evaluate(expression.term2, tuple);
        switch (expression.operator) {
            case '+':
                return (int) v1 + (int) v2;
            case '-':
                return (int) v1 - (int) v2;
            case '*':
                return (int) v1 * (int) v2;
        }
        return null;
    }
    
    public static Object evaluate(Term term, Tuple tuple) {
        if(term instanceof ColumnName) {
            String attribute;
            if(tuple.getSchema().getFieldNames().contains(term.toString()))
                attribute = term.toString();
            else
                attribute = ((ColumnName) term).attribute;
            Field field = tuple.getField(attribute);
            if(field.type == FieldType.INT)
                return field.integer;
            return field.str;
        }
        if(term instanceof Integer)
            return ((Integer) term).value;
        return ((Literal) term).value;
    }
}
