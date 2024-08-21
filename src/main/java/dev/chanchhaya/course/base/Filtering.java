package dev.chanchhaya.course.base;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class Filtering {
    List<Filter> filterList = new ArrayList<>();

    public void addFilter(String key, Operator operator, Object value) {
        filterList.add(new Filter(key, operator, value));
    }

    public enum Operator {
        eq("eq"),
        gt("gt"),
        gte("gte"),
        in("in"),
        lt("lt"),
        lte("lte"),
        ne("ne"),
        nin("nin"),
        regex("regex");

        private final String operator;

        Operator(String operator) {
            this.operator = operator;
        }

        @Override
        public String toString() {
            return this.operator;
        }

        public static Operator fromString(String operator) {
            for (Operator op : Operator.values()) {
                if (op.operator.equalsIgnoreCase(operator)) {
                    return op;
                }
            }
            return null;
        }
    }

    @Getter
    public static class Filter {
        String key;
        Operator operator;
        Object value;

        public Filter(String key, Operator operator, Object value) {
            this.key = key;
            this.operator = operator;
            this.value = value;
        }

        @Override
        public String toString() {
            return getKey() + getOperator() + getValue();
        }
    }
}
