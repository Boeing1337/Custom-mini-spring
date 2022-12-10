package tech.ioc.casting;

public class ToLongClassFromStringTypeCaster implements FromStringTypeCaster {

    @Override
    public Object cast(String variable) {
        return Long.parseLong(variable);
    }

    @Override
    public Class<?> fromType() {
        return Long.class;
    }

}
