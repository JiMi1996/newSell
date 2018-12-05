package com.seller.util.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @program: newsell
 * @description:
 * @author: fbl
 * @create: 2018-07-15 00:26
 **/
@Data
public class ProductListResult<T>  implements Serializable {

    private static final long serialVersionUID = 318765071379227581L;

    @JsonProperty("name")
    private String name;

    @JsonProperty("type")
    private Integer type;

    @JsonProperty("foods")
    private List<ProductInfoResult> productInfoResultsList;
}
