package tech.ioc.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Это аннотация используется в {@link Component} над полем для которого вы хотите получить зависимость
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface InjectObject {
    /**
     * Имя объекста. Если не указывать то создасться по- умолчанию по полному имени класса.
     */
    String value() default "";
}
