package com.mall.product.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**

 *
 * 二级分类id
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Catelog2Vo {

    /**
     * 一级父分类的id
     */
    private String catalog1Id;

    /**
     * 三级子分类
     */
    private List<Catelog3Vo> catelog3List;

    // 2级分类ID
    private String id;
    // 2级分类name
    private String name;


    /**
     * 三级分类vo
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Catelog3Vo {

        /**
         * 父分类、二级分类id
         */
        private String catalog2Id;
        // 3级分类ID
        private String id;
        // 3级分类name
        private String name;
    }

}
