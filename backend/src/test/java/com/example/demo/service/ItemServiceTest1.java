package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    @Test
    void findById_ShouldThrowException_WhenIdIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> itemService.findById(null));
    }

    @Test
    void save_ShouldThrowException_WhenItemIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> itemService.save(null));
    }

    @Test
    void deleteById_ShouldCallRepository() {
        Long id = 1L;
        itemService.deleteById(id);
        verify(itemRepository).deleteById(id);
    }
}