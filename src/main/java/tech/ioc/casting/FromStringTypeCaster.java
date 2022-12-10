package tech.ioc.casting;

public interface FromStringTypeCaster {
    Object cast(String variable);

    Class<?> fromType();
}
