package com.seller.util.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @program: newsell
 * @description:
 * @author: fbl
 * @create: 2018-07-15 00:29
 **/
@Data
public class ProductInfoResult implements Serializable {

    private static final long serialVersionUID = 4132908343747720086L;

    @JsonProperty("id")
    private String productId;

    @JsonProperty("name")
    private String productName;

    @JsonProperty("price")
    private BigDecimal productPrice;

    @JsonProperty("description")
    private String productDescription;

    @JsonProperty("icon")
    private String productIcon;
}
