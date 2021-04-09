package dev.kieranintehdas.reading.readinglist;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ReadingListRepository extends CrudRepository<ReadingList, UUID> {}