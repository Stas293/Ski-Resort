
CREATE TABLE user_roles
(
    user_id  BIGINT NOT NULL,
    roles_id BIGINT NOT NULL,
    CONSTRAINT pk_user_roles PRIMARY KEY (user_id, roles_id)
);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_role FOREIGN KEY (roles_id) REFERENCES role (id);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_user FOREIGN KEY (user_id) REFERENCES "user" (id);

ALTER TABLE user_role
    DROP CONSTRAINT fk_user_role_on_role;

ALTER TABLE user_role
    DROP CONSTRAINT fk_user_role_on_user;

DROP TABLE user_role CASCADE;