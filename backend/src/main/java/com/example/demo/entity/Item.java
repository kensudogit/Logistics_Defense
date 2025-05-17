package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// このクラスは、商品を表すエンティティクラスです。
// データベースのテーブルにマッピングされます。

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 商品の一意の識別子
    private String name; // 商品名
    private int quantity; // 在庫数量
    private double price; // 商品の価格

    // ゲッターとセッター
    public Long getId() {
        return id; // 商品のIDを取得します。
    }

    public void setId(Long id) {
        this.id = id; // 商品のIDを設定します。
    }

    public String getName() {
        return name; // 商品名を取得します。
    }

    public void setName(String name) {
        this.name = name; // 商品名を設定します。
    }

    public int getQuantity() {
        return quantity; // 在庫数量を取得します。
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity; // 在庫数量を設定します。
    }

    public double getPrice() {
        return price; // 商品の価格を取得します。
    }

    public void setPrice(double price) {
        this.price = price; // 商品の価格を設定します。
    }
}