package dev.kieranintehdas.readinglist.api;

import dev.kieranintehdas.readinglist.api.requests.AddBooksToReadingListRequest;
import dev.kieranintehdas.readinglist.api.requests.CreateReadingListRequest;
import dev.kieranintehdas.readinglist.api.requests.RemoveBooksFromReadingListRequest;
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
    final Set<Book> booksToAddToList =
        StreamSupport.stream(
                bookRepository.findAllById(createReadingListRequest.getBookIds()).spliterator(),
                false)
            .collect(Collectors.toSet());

    return readingListRepository.save(
        ReadingList.builder()
            .name(createReadingListRequest.getName())
            .books(booksToAddToList)
            .build());
  }

  public Optional<ReadingList> getReadingListById(final UUID readingListId) {
    return readingListRepository.findById(readingListId);
  }

  public ReadingList addBooksToReadingList(
      final UUID readingListToModifyId,
      final AddBooksToReadingListRequest addBooksToReadingListRequest) {

    ReadingList readingListToModify =
        getReadingListById(readingListToModifyId)
            .orElseThrow(
                () -> new NotFoundException(readingListToModifyId.toString(), ReadingList.class));

    final Set<Book> existingBooksToAdd =
        StreamSupport.stream(
                bookRepository.findAllById(addBooksToReadingListRequest.getBookIds()).spliterator(),
                false)
            .collect(Collectors.toSet());

    readingListToModify.getBooks().addAll(existingBooksToAdd);

    return readingListRepository.save(readingListToModify);
  }

  public ReadingList removeBooksFromReadingList(
      final UUID readingListToModifyId,
      final RemoveBooksFromReadingListRequest removeBooksFromReadingListRequest) {

    ReadingList readingListToModify =
        getReadingListById(readingListToModifyId)
            .orElseThrow(
                () -> new NotFoundException(readingListToModifyId.toString(), ReadingList.class));

    final Set<Book> booksToRemove =
        readingListToModify.getBooks().stream()
            .filter(book -> removeBooksFromReadingListRequest.getBookIds().contains(book.getId()))
            .collect(Collectors.toSet());

    readingListToModify.getBooks().removeAll(booksToRemove);

    return readingListRepository.save(readingListToModify);
  }
}
