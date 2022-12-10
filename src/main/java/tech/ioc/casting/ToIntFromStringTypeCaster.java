package tech.ioc.casting;

public class ToIntFromStringTypeCaster implements FromStringTypeCaster {

    @Override
    public Object cast(String variable) {
        return Integer.parseInt(variable);
    }

    @Override
    public Class<?> fromType() {
        return Integer.TYPE;
    }

}
