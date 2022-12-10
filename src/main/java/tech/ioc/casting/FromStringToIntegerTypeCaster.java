package tech.ioc.casting;

public class FromStringToIntegerTypeCaster implements FromStringTypeCaster {

    @Override
    public Integer cast(String variable) {
        return Integer.parseInt(variable);
    }

    @Override
    public Class<?> fromType() {
        return Integer.class;
    }

}
