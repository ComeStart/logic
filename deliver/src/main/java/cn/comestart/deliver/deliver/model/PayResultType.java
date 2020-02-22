package cn.comestart.deliver.deliver.model;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

public enum PayResultType {
    L1 {
        @Override
        public PayResultModel createPayResultModelProxy() {
            return createPayResultModelProxy((o, method, objects, methodProxy) -> {
                if ("getPhase".equals(method.getName())) {
                    return 3;
                } else if ("getFeeType".equals(method.getName())) {
                    return "L1";
                }
                return methodProxy.invokeSuper(o, objects);
            });
        }
    },
    VACCOUNT {
        @Override
        public PayResultModel createPayResultModelProxy() {
            return createPayResultModelProxy((o, method, objects, methodProxy) -> {
                if ("getPhase".equals(method.getName())) {
                    return 1;
                } else if ("getFeeType".equals(method.getName())) {
                    return "PAY";
                }
                return methodProxy.invokeSuper(o, objects);
            });
        }
    },
    BANKCARD {
        @Override
        public PayResultModel createPayResultModelProxy() {
            return createPayResultModelProxy((o, method, objects, methodProxy) -> {
                if ("getPhase".equals(method.getName())) {
                    return 2;
                } else if ("getFeeType".equals(method.getName())) {
                    return "PAY";
                }
                return methodProxy.invokeSuper(o, objects);
            });
        }
    },
    FEE {
        @Override
        public PayResultModel createPayResultModelProxy() {
            return createPayResultModelProxy((o, method, objects, methodProxy) -> {
                if ("getPhase".equals(method.getName())) {
                    return 3;
                } else if ("getFeeType".equals(method.getName())) {
                    return "FEE";
                }
                return methodProxy.invokeSuper(o, objects);
            });
        }
    };

    public abstract PayResultModel createPayResultModelProxy();

    protected PayResultModel createPayResultModelProxy(MethodInterceptor methodInterceptor) {
        Enhancer enhancer = new Enhancer();
        //2.2设置增强器的类加载器
        enhancer.setClassLoader(PayResultModel.class.getClassLoader());
        //2.3设置代理对象父类类型
        enhancer.setSuperclass(PayResultModel.class);
        //2.4设置回调函数
        enhancer.setCallback(methodInterceptor);

        return (PayResultModel) enhancer.create();
    }
}
