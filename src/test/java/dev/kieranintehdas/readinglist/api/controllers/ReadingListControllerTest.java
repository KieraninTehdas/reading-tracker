package dev.kieranintehdas.readinglist.api.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import dev.kieranintehdas.readinglist.api.ReadingListManager;
import dev.kieranintehdas.readinglist.api.requests.CreateReadingListRequest;
import dev.kieranintehdas.readinglist.api.responses.ReadingListDto;
import dev.kieranintehdas.readinglist.storage.ReadingList;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class ReadingListControllerTest {

  private final String readingListName = "My Reading List";
  private final ReadingList readingList =
      ReadingList.builder().name(readingListName).books(Collections.emptySet()).build();
  @Mock private ReadingListManager readingListManagerMock;
  private ReadingListController controller;

  @BeforeEach
  void setUp() {
    controller = new ReadingListController(readingListManagerMock);
  }

  @Test
  void createReadingList() {
    when(readingListManagerMock.createReadingList(any())).thenReturn(readingList);

    final ResponseEntity<ReadingListDto> result =
        controller.createReadingList(
            new CreateReadingListRequest(readingListName, Collections.emptySet()));

    assertEquals(ResponseEntity.ok(readingList.constructDto()), result);
  }
}
