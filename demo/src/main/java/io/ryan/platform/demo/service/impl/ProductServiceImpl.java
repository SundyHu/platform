package io.ryan.platform.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.ryan.platform.demo.mapper.ProductMapper;
import io.ryan.platform.demo.model.Product;
import io.ryan.platform.demo.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ProductServiceImpl
 *
 * @author hkc
 * @version 1.0.0
 * @date 2021-07-29 10:38
 */
@Service
@Transactional(rollbackFor = Exception.class, timeout = 5000)
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

}
