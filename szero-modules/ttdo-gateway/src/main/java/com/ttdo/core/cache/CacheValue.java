package com.ttdo.core.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheValue {
    String key();

    String primaryKey() default "";

    String primaryKeyAlias() default "";

    String searchKey() default "";

    DataStructure structure() default CacheValue.DataStructure.VALUE;

    int db() default -1;

    String dbAlias() default "";

    public static enum DataStructure {
        VALUE,
        OBJECT,
        MAP_VALUE,
        MAP_OBJECT,
        LIST_OBJECT;

        private DataStructure() {
        }
    }
}