package com.example.demo.service;

import com.example.demo.entity.Item;
import com.example.demo.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    // コンストラクタインジェクションを使用
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    /**
     * データベース内のすべてのアイテムを取得します
     * 
     * @return アイテムのリスト
     */
    @Transactional(readOnly = true)
    public List<Item> findAll() {
        log.debug("Fetching all items");
        return itemRepository.findAll();
    }

    /**
     * 指定されたIDのアイテムを取得します
     * 
     * @param id アイテムID (null不可)
     * @return アイテムのOptional
     * @throws IllegalArgumentException idがnullの場合
     */
    @Transactional(readOnly = true)
    public Optional<Item> findById(Long id) {
        log.debug("Fetching item by id: {}", id);
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return itemRepository.findById(id);
    }

    /**
     * アイテムを保存または更新します
     * 
     * @param item 保存するアイテム (null不可)
     * @return 保存されたアイテム
     * @throws IllegalArgumentException itemがnullの場合
     */
    @Transactional
    public Item save(Item item) {
        log.debug("Saving item: {}", item);
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        return itemRepository.save(item);
    }

    /**
     * 指定されたIDのアイテムを削除します
     * 
     * @param id 削除するアイテムのID (null不可)
     * @throws IllegalArgumentException idがnullの場合
     */
    @Transactional
    public void deleteById(Long id) {
        log.debug("Deleting item by id: {}", id);
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        itemRepository.deleteById(id);
    }

    @Transactional
    public List<Item> saveAll(List<Item> items) {
        if (items == null) {
            throw new IllegalArgumentException("Items list cannot be null");
        }
        return itemRepository.saveAll(items);
    }
}