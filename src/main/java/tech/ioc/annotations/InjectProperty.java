package tech.ioc.annotations;

import tech.ioc.configurator.InjectPropertyAnnotationObjectConfigurator;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Это аннотация используется в полях {@link Component}'ов в котороые надо проинициализировать данные из настроек.
 *
 * @see InjectPropertyAnnotationObjectConfigurator
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface InjectProperty {
    /**
     * Имя настройки из файла конфигураций. Если не указано то будет взято настройка по имени поля.
     */
    String value() default "";
}
