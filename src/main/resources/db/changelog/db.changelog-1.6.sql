ALTER TABLE "user" ADD
    CONSTRAINT "user_unique_email" UNIQUE ("email");

ALTER TABLE user_roles
    RENAME COLUMN roles_id TO role_id;

ALTER TABLE user_roles ADD
    CONSTRAINT "user_roles_pkey" PRIMARY KEY ("user_id", "role_id");