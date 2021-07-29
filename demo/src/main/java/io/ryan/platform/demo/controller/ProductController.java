package io.ryan.platform.demo.controller;

import com.google.common.collect.Maps;
import io.ryan.platform.demo.annotations.RequestLimit;
import io.ryan.platform.demo.model.Product;
import io.ryan.platform.demo.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ProductController
 *
 * @author hkc
 * @version 1.0.0
 * @date 2021-07-29 10:52
 */
@RestController
@RequestMapping(value = "/products")
@Slf4j
public class ProductController {

    @Resource
    private ProductService productService;

    private static ReentrantLock lock = new ReentrantLock();

    @GetMapping(value = "/addProduct")
    public Map<String, Object> addProduct() {
        Product product = new Product();
        product.setProductName("Huawei P40");
        product.setTotalInventory(50000L);
        productService.save(product);

        ConcurrentMap<String, Object> result = Maps.newConcurrentMap();
        result.put("code", 1);
        result.put("message", "success");

        return result;
    }

    @RequestLimit(value = 1)
    @GetMapping(value = "/demo")
    public Map<String, Object> demo() {
        lock.lock();
        try {
            Product product = productService.getById(1420580224401170434L);
            if (null == product) {
                throw new IllegalArgumentException("产品不存在");
            }

            Product tmp = new Product();
            tmp.setGuid(product.getGuid());
            tmp.setTotalInventory(product.getTotalInventory() - 1);
            log.info("获取到的库存：{},现在的库存：{}", product.getTotalInventory(), tmp.getTotalInventory());
            boolean flag = productService.updateById(tmp);
            if (flag) {
                ConcurrentMap<String, Object> result = Maps.newConcurrentMap();
                result.put("code", 1);
                result.put("message", "success");
                return result;
            } else {
                ConcurrentMap<String, Object> result = Maps.newConcurrentMap();
                result.put("code", 0);
                result.put("message", "failure");
                return result;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            lock.unlock();
        }

        throw new IllegalArgumentException("获取锁失败");
    }
}
