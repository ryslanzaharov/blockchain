CREATE TABLE blockchain
(
  id            SERIAL PRIMARY KEY,
  hash          varchar(64),
  previous_hash varchar(64),
  data          varchar,
  create_date   timestamp,
  nonce         integer
);