ALTER TABLE event
ALTER COLUMN date SET DATA TYPE timestamp without time zone;

ALTER TABLE city
ALTER COLUMN name SET NOT NULL;

ALTER TABLE city
ADD CONSTRAINT city_name_unique UNIQUE (name);

ALTER TABLE city
    ALTER COLUMN code SET NOT NULL;

ALTER TABLE city
    ADD CONSTRAINT city_code_unique UNIQUE (code);

ALTER TABLE continent
    ALTER COLUMN name SET NOT NULL;

ALTER TABLE continent
    ADD CONSTRAINT continent_name_unique UNIQUE (name);

ALTER TABLE continent
    ALTER COLUMN code SET NOT NULL;

ALTER TABLE continent
    ADD CONSTRAINT continent_code_unique UNIQUE (code);

ALTER TABLE country
    ALTER COLUMN name SET NOT NULL;

ALTER TABLE country
    ADD CONSTRAINT country_name_unique UNIQUE (name);

ALTER TABLE country
    ALTER COLUMN code SET NOT NULL;

ALTER TABLE country
    ADD CONSTRAINT country_code_unique UNIQUE (code);
