package tech.ioc.infrastucture;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class BeanContainer {

    private final String beanName;
    private final Class<?> implClass;
    private Object original;
    @Setter
    private Object proxy;

    public void initBean(Object bean) {
        this.original = bean;
        this.proxy = bean;
    }
}
