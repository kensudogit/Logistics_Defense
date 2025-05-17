package com.example.demo.service;

import com.example.demo.entity.Item;
import com.example.demo.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    // ItemRepositoryを自動的に注入します。
    @Autowired
    private ItemRepository itemRepository;

    // クラスの説明: このクラスは、アイテムに関連するビジネスロジックを提供するサービスクラスです。

    // メソッドの説明: データベース内のすべてのアイテムを取得します。
    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    // メソッドの説明: 指定されたIDのアイテムを取得します。アイテムが存在しない場合は空のOptionalを返します。
    public Optional<Item> findById(Long id) {
        return itemRepository.findById(id);
    }

    // メソッドの説明: 新しいアイテムを保存または既存のアイテムを更新します。
    public Item save(Item item) {
        return itemRepository.save(item);
    }

    // メソッドの説明: 指定されたIDのアイテムを削除します。
    public void deleteById(Long id) {
        itemRepository.deleteById(id);
    }
}