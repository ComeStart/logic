package cn.comestart.trinity.utils;

import java.util.concurrent.TimeUnit;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：线程休眠辅助工具类
 */
public class SleepTools {
	
	/**
	 * 按秒休眠
	 * @param seconds 秒数
	 */
    public static final void seconds(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
        }
    }
    
    /**
     * 按毫秒数休眠
     * @param millis 毫秒数
     */
    public static final void ms(int millis) {
        try {
            TimeUnit.MILLISECONDS.sleep(millis);
        } catch (InterruptedException e) {
        }
    }
}
