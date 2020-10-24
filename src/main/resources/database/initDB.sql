CREATE TABLE IF NOT EXISTS pages(
    id BIGSERIAL PRIMARY KEY,
    address VARCHAR NOT NULL,
    UNIQUE (address)
);

CREATE TABLE IF NOT EXISTS words(
    id BIGSERIAL PRIMARY KEY,
    page_id BIGINT NOT NULL,
    body VARCHAR NOT NULL,
    word_count BIGINT,
    FOREIGN KEY (page_id) REFERENCES pages (id),
);