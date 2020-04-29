package cn.comestart.deliver.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class TestServiceInvocationHandler implements InvocationHandler {
    private TestService target;

    public TestServiceInvocationHandler(TestService target) {
        this.target=target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("proxy");
        return method.invoke(target, args);
    }

}