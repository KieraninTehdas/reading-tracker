package dev.kieranintehdas.readingtracker.readinglist;

import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("reading-lists")
@RestController
@RequiredArgsConstructor
public class ReadingListController {

  private final ReadingListManager readingListManager;

  @PostMapping
  public ResponseEntity<ReadingListDto> createReadingList(
      @RequestBody @Valid final CreateReadingListRequest createReadingListRequest) {

    return ResponseEntity.ok(
        readingListManager.createReadingList(createReadingListRequest).constructDto());
  }

  @GetMapping("{readingListId}")
  public ResponseEntity<ReadingListDto> getReadingListById(@PathVariable final UUID readingListId) {
    return readingListManager
        .getReadingListById(readingListId)
        .map(readingList -> ResponseEntity.ok(readingList.constructDto()))
        .orElse(ResponseEntity.notFound().build());
  }

  @PatchMapping("{readingListId}/modify")
  public ResponseEntity<ReadingListDto> modifyReadingList(
      @PathVariable final UUID readingListId,
      @RequestBody final ModifyReadingListRequest modifyReadingListRequest) {

    return ResponseEntity.ok(
        readingListManager
            .modifyReadingList(readingListId, modifyReadingListRequest)
            .constructDto());
  }
}
