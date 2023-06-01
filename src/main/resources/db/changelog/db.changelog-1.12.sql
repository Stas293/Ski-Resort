
ALTER TABLE country
    ADD continent_id BIGINT;

ALTER TABLE city
    ADD country_id BIGINT;

ALTER TABLE city
    ADD CONSTRAINT FK_CITY_ON_COUNTRY FOREIGN KEY (country_id) REFERENCES country (id);

ALTER TABLE country
    ADD CONSTRAINT FK_COUNTRY_ON_CONTINENT FOREIGN KEY (continent_id) REFERENCES continent (id);
