package dev.kieranintehdas.reading.readinglist;

import dev.kieranintehdas.reading.NotFoundException;
import dev.kieranintehdas.reading.book.Book;
import dev.kieranintehdas.reading.book.BookRepository;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReadingListManager {

  private final BookRepository bookRepository;
  private final ReadingListRepository readingListRepository;

  public ReadingList createReadingList(final CreateReadingListRequest createReadingListRequest) {
    final Set<Book> booksToAddToList = getBooksById(createReadingListRequest.getBookIds());

    return readingListRepository.save(
        ReadingList.builder()
            .name(createReadingListRequest.getName())
            .books(booksToAddToList)
            .build());
  }

  public Optional<ReadingList> getReadingListById(final UUID readingListId) {
    return readingListRepository.findById(readingListId);
  }

  public ReadingList modifyReadingList(
      final UUID readingListToModifyId, final ModifyReadingListRequest modifyReadingListRequest) {
    ReadingList readingListToModify =
        getReadingListById(readingListToModifyId)
            .orElseThrow(
                () -> new NotFoundException(readingListToModifyId.toString(), ReadingList.class));

    // Might be worth doing something to ignore any books that are being both added and removed?
    final Set<UUID> idsOfBooksToAdd = modifyReadingListRequest.getIdsOfBooksToAdd().stream()
        .filter(bookId -> !modifyReadingListRequest.getIdsOfBooksToRemove().contains(bookId))
        .collect(Collectors.toSet());
    final Set<Book> booksToAdd = getBooksById(idsOfBooksToAdd);

    readingListToModify.addBooksToReadingList(booksToAdd);

    final Set<UUID> idsOfBooksToRemove = modifyReadingListRequest.getIdsOfBooksToRemove().stream()
        .filter(bookId -> !modifyReadingListRequest.getIdsOfBooksToAdd().contains(bookId))
        .collect(Collectors.toSet());
    readingListToModify.removeBooksFromReadingList(idsOfBooksToRemove);

    return readingListRepository.save(readingListToModify);
  }

  private Set<Book> getBooksById(final Set<UUID> bookIds) {
    return StreamSupport.stream(
            bookRepository
                .findAllById(bookIds)
                .spliterator(),
            false)
        .collect(Collectors.toSet());
  }

}
