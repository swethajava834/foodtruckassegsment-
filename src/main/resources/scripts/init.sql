CREATE TABLE IF NOT EXISTS trucks (
  id SERIAL PRIMARY KEY,
  applicant VARCHAR(255),
  facility_type VARCHAR(255),
  location_description VARCHAR(255),
  address VARCHAR(255),
  food_items TEXT,
  latitude DOUBLE PRECISION,
  longitude DOUBLE PRECISION
);

CREATE TABLE IF NOT EXISTS users (
  id SERIAL PRIMARY KEY,
  name VARCHAR(255),
  email VARCHAR(255) UNIQUE,
  password VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS ratings (
  id SERIAL PRIMARY KEY,
  user_id INTEGER,
  truck_id INTEGER,
  rating INTEGER,
  FOREIGN KEY (user_id) REFERENCES users (id),
  FOREIGN KEY (truck_id) REFERENCES trucks (id)
);

ALTER TABLE ratings ADD CONSTRAINT check_rating_range CHECK (rating >= 1 AND rating <= 5);

COPY trucks(applicant, facility_type, location_description, address, food_items, latitude, longitude)
FROM '/csv/Mobile_Food_Facility_Permit.csv' WITH (FORMAT csv, HEADER false, DELIMITER ',', QUOTE '"');
