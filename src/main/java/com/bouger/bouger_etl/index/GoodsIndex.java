package com.bouger.bouger_etl.index;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;

/**
 * @program:bouger_etl
 * @description:
 * @author:Bouger
 * @date:2021-05-23 18:11:15
 **/
@Document(indexName = "javashop")
public class GoodsIndex implements Serializable {
    @Id
    private Integer goodsId;
    @Field(analyzer = "ik_max_word")
    private String goodsName;

    private String connet;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getConnet() {
        return connet;
    }

    public void setConnet(String connet) {
        this.connet = connet;
    }
}
