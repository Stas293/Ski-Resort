
ALTER TABLE resort
    ADD city_id BIGINT;

ALTER TABLE resort
    ADD CONSTRAINT FK_RESORT_ON_CITY FOREIGN KEY (city_id) REFERENCES city (id);

ALTER TABLE resort
    DROP CONSTRAINT fk_resort_on_continent;

ALTER TABLE resort
    DROP COLUMN continent_id;

ALTER TABLE user_roles
ADD PRIMARY KEY (user_id, role_id);

ALTER TABLE resort
ALTER COLUMN title SET NOT NULL;

ALTER TABLE resort
ALTER COLUMN description SET NOT NULL;

ALTER TABLE resort
ALTER COLUMN image SET NOT NULL;

ALTER TABLE resort
ALTER COLUMN image_alt SET NOT NULL;