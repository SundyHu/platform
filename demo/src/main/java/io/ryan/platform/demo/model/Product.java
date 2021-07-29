package io.ryan.platform.demo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Product
 *
 * @author hkc
 * @version 1.0.0
 * @date 2021-07-29 10:34
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tb_product")
public class Product extends Model<Product> {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long guid;

    private String productName;

    private Long totalInventory;

    @Override
    public Serializable pkVal() {
        return this.guid;
    }
}
