package com.example.demo.repository;

import com.example.demo.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Update;
import org.seasar.doma.jdbc.Config;

public interface ItemRepository extends JpaRepository<Item, Long> {
    // Additional query methods can be defined here
}

// Doma2を使用してデータベース操作を行うためのDAOインターフェース
@Dao
public interface ItemDomaDao {
    // アイテムを更新するメソッド
    @Update
    int update(Item item);

    // アイテムを挿入するメソッド
    @Insert
    int insert(Item item);
}

// 利用方法:
// 1. ItemDomaDaoインターフェースを実装したクラスを作成します。
// 2. Doma2のConfigを使用して、DAOのインスタンスを取得します。
// 3. updateメソッドを使用して、既存のアイテムを更新します。
// 4. insertメソッドを使用して、新しいアイテムをデータベースに挿入します。
// 例:
// ItemDomaDao dao = ...; // DAOのインスタンスを取得
// Item item = new Item();
// item.setId(1L);
// item.setName("新しいアイテム名");
// dao.update(item); // アイテムを更新
// dao.insert(item); // アイテムを挿入