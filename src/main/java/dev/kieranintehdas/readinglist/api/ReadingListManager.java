package dev.kieranintehdas.readinglist.api;

import dev.kieranintehdas.readinglist.api.requests.CreateReadingListRequest;
import dev.kieranintehdas.readinglist.api.requests.ModifyReadingListRequest;
import dev.kieranintehdas.readinglist.storage.Book;
import dev.kieranintehdas.readinglist.storage.BookRepository;
import dev.kieranintehdas.readinglist.storage.ReadingList;
import dev.kieranintehdas.readinglist.storage.ReadingListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReadingListManager {

    private final BookRepository bookRepository;
    private final ReadingListRepository readingListRepository;

    public ReadingList createReadingList(final CreateReadingListRequest createReadingListRequest) {
        final Set<Book> booksToAddToList = StreamSupport.stream(
                bookRepository.findAllById(createReadingListRequest.getBookIds()).spliterator(),
                false
        ).collect(Collectors.toSet());

        return readingListRepository.save(
                ReadingList.builder()
                        .name(createReadingListRequest.getName())
                        .books(booksToAddToList)
                        .build()
        );
    }

    public Optional<ReadingList> getReadingListById(final UUID readingListId) {
        return readingListRepository.findById(readingListId);
    }

    public ReadingList modifyReadingList(
            final UUID readingListToModifyId,
            final ModifyReadingListRequest modifyReadingListRequest) {

        final ReadingList readingListToModify = getReadingListById(readingListToModifyId)
                .orElseThrow(
                        () -> new NotFoundException(
                                readingListToModifyId.toString(),
                                ReadingList.class
                        )
                );


        return readingListToModify;
    }

}
