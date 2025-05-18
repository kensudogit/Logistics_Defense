package com.example.demo.service;

import com.example.demo.entity.Item;
import com.example.demo.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    private Item item1;
    private Item item2;

    @BeforeEach
    void setUp() {
        item1 = new Item();
        item1.setId(1L);
        item1.setName("Item1");

        item2 = new Item();
        item2.setId(2L);
        item2.setName("Item2");
    }

    @Test
    void testFindAll() {
        when(itemRepository.findAll()).thenReturn(Arrays.asList(item1, item2));

        List<Item> items = itemService.findAll();

        assertEquals(2, items.size());
        verify(itemRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item1));

        Optional<Item> foundItem = itemService.findById(1L);

        assertTrue(foundItem.isPresent());
        assertEquals(item1, foundItem.get());
        verify(itemRepository, times(1)).findById(1L);
    }

    @Test
    void testSave() {
        when(itemRepository.save(item1)).thenReturn(item1);

        Item savedItem = itemService.save(item1);

        assertNotNull(savedItem);
        assertEquals(item1, savedItem);
        verify(itemRepository, times(1)).save(item1);
    }

    @Test
    void testDeleteById() {
        doNothing().when(itemRepository).deleteById(1L);

        itemService.deleteById(1L);

        verify(itemRepository, times(1)).deleteById(1L);
    }
}