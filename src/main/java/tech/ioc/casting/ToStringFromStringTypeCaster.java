package tech.ioc.casting;

public class ToStringFromStringTypeCaster implements FromStringTypeCaster {

    @Override
    public Object cast(String variable) {
        return variable;
    }

    @Override
    public Class<?> fromType() {
        return String.class;
    }

}
