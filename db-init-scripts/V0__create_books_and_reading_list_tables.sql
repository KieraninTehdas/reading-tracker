CREATE TABLE IF NOT EXISTS books (
	id UUID, 
	title TEXT,
	author TEXT
);

CREATE TABLE IF NOT EXISTS reading_lists(
  id UUID,
  name TEXT
);

CREATE TABLE IF NOT EXISTS reading_list_books(
  reading_list_id UUID,
  book_id UUID
);