package com.example.demo;

import com.example.demo.controller.ItemController;
import com.example.demo.entity.Item;
import com.example.demo.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class ItemControllerTest {

    private static final String ITEM_NAME_1 = "Item1";
    private static final String TEST_FILE_NAME = "test.txt";
    private static final String TEST_FILE_CONTENT = "Test file content";

    @InjectMocks
    private ItemController itemController;

    @Mock
    private ItemService itemService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // 基本CRUD操作のテスト
    @Test
    public void testGetAllItems() {
        // Arrange
        Item item1 = new Item(1L, ITEM_NAME_1, 10.0);
        Item item2 = new Item(2L, "Item2", 20.0);
        List<Item> itemList = Arrays.asList(item1, item2);
        when(itemService.findAll()).thenReturn(itemList);

        // Act
        List<Item> result = itemController.getAllItems();

        // Assert
        assertEquals(2, result.size());
        assertEquals(ITEM_NAME_1, result.get(0).getName());
    }

    @Test
    public void testGetItemById_Found() {
        // Arrange
        Item item = new Item(1L, ITEM_NAME_1, 10.0);
        when(itemService.findById(1L)).thenReturn(Optional.of(item));

        // Act
        ResponseEntity<Item> response = itemController.getItemById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ITEM_NAME_1, response.getBody().getName());
    }

    @Test
    public void testGetItemById_NotFound() {
        // Arrange
        when(itemService.findById(anyLong())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Item> response = itemController.getItemById(99L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testCreateItem() {
        // Arrange
        Item item = new Item(1L, "NewItem", 15.0);
        when(itemService.save(any(Item.class))).thenReturn(item);

        // Act
        Item result = itemController.createItem(item);

        // Assert
        assertEquals("NewItem", result.getName());
    }

    @Test
    public void testDeleteItem() {
        // Act
        ResponseEntity<Void> response = itemController.deleteItem(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(itemService, times(1)).deleteById(1L);
    }

    // ファイルアップロード/ダウンロードのテスト
    @Test
    public void testUploadFile_Success() throws Exception {
        // Arrange
        MultipartFile file = new MockMultipartFile(
                "file",
                TEST_FILE_NAME,
                MediaType.TEXT_PLAIN_VALUE,
                TEST_FILE_CONTENT.getBytes());

        // Act
        ResponseEntity<String> response = itemController.uploadFile(file);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("ファイルが正常にアップロードされました。", response.getBody());
    }

    @Test
    public void testUploadFile_EmptyFile() {
        // Arrange
        MultipartFile emptyFile = new MockMultipartFile(
                "file",
                TEST_FILE_NAME,
                MediaType.TEXT_PLAIN_VALUE,
                new byte[0]);

        // Act
        ResponseEntity<String> response = itemController.uploadFile(emptyFile);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("ファイルが空です。", response.getBody());
    }

    @Test
    public void testDownloadFile_Success() throws Exception {
        // Arrange
        // 実際のファイルシステムをモックしないため、コントローラーのロジックをテストする簡易版
        // 本番ではFileStorageServiceをモックする必要あり

        // Act
        ResponseEntity<Resource> response = itemController.downloadFile(TEST_FILE_NAME);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(
                "attachment; filename=\"" + TEST_FILE_NAME + "\"",
                response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION));
    }

    @Test
    public void testDownloadFile_NotFound() throws Exception {
        // Arrange
        // ファイルが存在しない場合のテスト（コントローラー側で404を返すことを想定）

        // Act
        ResponseEntity<Resource> response = itemController.downloadFile("nonexistent.txt");

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateDatabaseFromFile_Success() throws Exception {
        // Arrange
        String csvContent = "1,Item1,10.0\n2,Item2,20.0";
        MultipartFile file = new MockMultipartFile(
                "file",
                "data.csv",
                MediaType.TEXT_PLAIN_VALUE,
                csvContent.getBytes());

        // Act
        ResponseEntity<String> response = itemController.updateDatabaseFromFile(file);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("データベースが正常に更新されました。", response.getBody());
    }
}