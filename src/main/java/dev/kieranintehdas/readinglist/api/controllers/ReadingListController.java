package dev.kieranintehdas.readinglist.api.controllers;

import dev.kieranintehdas.readinglist.api.ReadingListManager;
import dev.kieranintehdas.readinglist.api.requests.CreateReadingListRequest;
import dev.kieranintehdas.readinglist.api.requests.ModifyReadingListRequest;
import dev.kieranintehdas.readinglist.storage.ReadingList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RequestMapping("reading-lists")
@RestController
@RequiredArgsConstructor
public class ReadingListController {

    private final ReadingListManager readingListManager;

    @PostMapping
    public ResponseEntity<ReadingList> createReadingList(
            @RequestBody @Valid final CreateReadingListRequest createReadingListRequest) {

        return ResponseEntity.ok(readingListManager.createReadingList(createReadingListRequest));
    }

    @PatchMapping("{readingListId}")
    public ResponseEntity<ReadingList> modifyReadingList(
            @PathVariable final UUID readingListId,
            @RequestBody final ModifyReadingListRequest modifyReadingListRequest) {

        return ResponseEntity.ok(readingListManager.modifyReadingList(readingListId,modifyReadingListRequest));
    }

}
