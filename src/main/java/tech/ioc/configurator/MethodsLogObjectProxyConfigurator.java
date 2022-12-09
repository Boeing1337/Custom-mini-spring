package tech.ioc.configurator;

import lombok.extern.log4j.Log4j2;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;
import tech.ioc.ApplicationContext;
import tech.ioc.configurator.interfaces.ProxyObjectConfigurator;
import tech.ioc.infrastucture.BeanContainer;

import java.lang.reflect.Method;
import java.util.Arrays;

@Log4j2
public class MethodsLogObjectProxyConfigurator implements ProxyObjectConfigurator {

    @Override
    public void configure(BeanContainer container, ApplicationContext context) {
        Object oldProxy = container.getProxy();
        container.setProxy(Enhancer.create(container.getImplClass(), new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        log.debug(method.getName() + " " + Arrays.toString(args));
                        return method.invoke(oldProxy, args);
                    }
                }
        ));
    }

}
