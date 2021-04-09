package dev.kieranintehdas.readingtracker.readinglist;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface ReadingListRepository extends CrudRepository<ReadingList, UUID> {}
