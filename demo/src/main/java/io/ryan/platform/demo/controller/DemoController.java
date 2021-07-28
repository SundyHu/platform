package io.ryan.platform.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * DemoController
 *
 * @author Ryan
 * @version 1.0.0
 * @date: 2021-07-28 22:09
 */
@RestController
@Slf4j
public class DemoController {

    private static Lock lock = null;

    static {
        lock = new ReentrantLock();
    }

    @GetMapping(value = "/demo")
    public Map<String, Object> demo() throws Exception {
        log.info("尝试获取锁");
        lock.lock();
        try {
            log.info("获取到锁了");
            Map<String, Object> result = new ConcurrentHashMap<>(2);
            result.put("id", 1);
            result.put("msg", "success");
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            lock.unlock();
        }

        throw new IllegalArgumentException("获取锁失败");
    }
}
