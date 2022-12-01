package tech.ioc.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Это аннотация помечает класс как {@link Component} который будет включен в контекст приложения.
 * Позволяет автоматически подложить такой класс как зависимость в другой {@link Component}
 */
@Retention(RUNTIME)
@Target(ElementType.TYPE)
public @interface Component {
    String name() default "";

    boolean isMain() default false;
}
