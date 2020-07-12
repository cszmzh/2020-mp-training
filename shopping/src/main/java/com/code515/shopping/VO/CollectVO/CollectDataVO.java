package com.code515.shopping.VO.CollectVO;

import lombok.Data;

import java.util.List;

@Data
public class CollectDataVO {

    private Integer total;
    private List<CollectVO> collectVOList;

}
