package tech.ioc.casting;

public class ToDoubleFromStringTypeCaster implements FromStringTypeCaster {

    @Override
    public Object cast(String variable) {
        return Double.parseDouble(variable);
    }

    @Override
    public Class<?> fromType() {
        return Double.TYPE;
    }

}
