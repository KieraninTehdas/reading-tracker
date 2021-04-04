package dev.kieranintehdas.readinglist.api.controllers;

import dev.kieranintehdas.readinglist.api.ReadingListManager;
import dev.kieranintehdas.readinglist.api.requests.CreateReadingListRequest;
import dev.kieranintehdas.readinglist.api.requests.AddBooksToReadingListRequest;
import dev.kieranintehdas.readinglist.storage.ReadingList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReadingListControllerTest {

    @Mock
    private ReadingListManager readingListManagerMock;

    private final String readingListName = "My Reading List";
    private final ReadingList readingList = ReadingList.builder()
            .name(readingListName)
            .books(Collections.emptySet())
            .build();

    private ReadingListController controller;

    @BeforeEach
    void setUp() {
        controller = new ReadingListController(readingListManagerMock);
    }

}