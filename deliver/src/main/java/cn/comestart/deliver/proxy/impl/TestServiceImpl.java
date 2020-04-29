package cn.comestart.deliver.proxy.impl;

import cn.comestart.deliver.proxy.TestService;
import org.springframework.stereotype.Service;

@Service("testService")
public class TestServiceImpl implements TestService {
    @Override
    public void foo() {
        System.out.println("TestServiceImpl");
    }
}
