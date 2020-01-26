package cn.comestart.trinity;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class TrinityTest extends Tester {
    @Autowired
    private TrinityService trinityService;

    @Test
    public void testTest() {
        System.out.println();
        trinityService.makeInvest();
        System.out.println();
    }
}
