package com.example.demo.controller;

import com.example.demo.entity.Item;
import com.example.demo.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/items")
public class ItemController {

    // ItemControllerクラスは、アイテムに関連するHTTPリクエストを処理するコントローラーです。

    @Autowired
    private ItemService itemService;

    // 全てのアイテムを取得するエンドポイント
    @GetMapping
    public List<Item> getAllItems() {
        // ItemServiceを使用して全てのアイテムを取得します。
        return itemService.findAll();
    }

    // 特定のIDに基づいてアイテムを取得するエンドポイント
    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        // 指定されたIDのアイテムを取得し、存在する場合はOKレスポンスを返し、存在しない場合は404を返します。
        Optional<Item> item = itemService.findById(id);
        return item.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 新しいアイテムを作成するエンドポイント
    @PostMapping
    public Item createItem(@RequestBody Item item) {
        // リクエストボディからアイテムを受け取り、保存します。
        return itemService.save(item);
    }

    // 特定のIDに基づいてアイテムを削除するエンドポイント
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        // 指定されたIDのアイテムを削除し、成功した場合は204レスポンスを返します。
        itemService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // ファイルをアップロードするエンドポイント
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        // ファイルが空でないか確認します。
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("ファイルが空です。");
        }

        // ファイルを保存する処理をここに追加します。
        // 例: file.transferTo(new File("保存先パス"));

        return ResponseEntity.ok("ファイルが正常にアップロードされました。");
    }

    // ファイルをダウンロードするエンドポイント
    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        // ファイルのパスを指定します。
        Path filePath = Paths.get("保存先パス").resolve(filename).normalize();

        // ファイルが存在するか確認します。
        Resource resource;
        try {
            resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }

        // ファイルをダウンロードするためのレスポンスを作成します。
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    // アップロードされたファイルを基にデータベースを更新するエンドポイント
    @PostMapping("/update-from-file")
    public ResponseEntity<String> updateDatabaseFromFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("ファイルが空です。");
        }

        try {
            // ファイルの内容を読み取ります。
            List<String> lines = new BufferedReader(new InputStreamReader(file.getInputStream()))
                    .lines().collect(Collectors.toList());

            // 各行を処理してデータベースを更新します。
            for (String line : lines) {
                // ここで行を解析してItemオブジェクトを作成します。
                Item item = parseLineToItem(line);
                itemService.save(item); // ItemServiceを使用してデータベースを更新します。
            }

            return ResponseEntity.ok("データベースが正常に更新されました。");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ファイルの処理中にエラーが発生しました。");
        }
    }

    // 行を解析してItemオブジェクトを作成するヘルパーメソッド
    private Item parseLineToItem(String line) {
        // 行を解析してItemオブジェクトを作成するロジックを実装します。
        // 例: CSV形式の行を解析する
        String[] parts = line.split(",");
        Item item = new Item();
        item.setId(Long.parseLong(parts[0]));
        item.setName(parts[1]);
        item.setPrice(Double.parseDouble(parts[2]));
        return item;
    }
}