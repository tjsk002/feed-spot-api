CREATE EXTENSION IF NOT EXISTS pgcrypto;

INSERT INTO USERS (user_name, nick_name, password, role, gender, is_active, type, description, created_at, updated_at,
                   deleted_at)
VALUES ('user', 'Nick1', crypt('password1!', gen_salt('bf')), 'USER', 'male', TRUE, 'back',
        'user description',
        NOW(), NOW(), null),
       ('user_2', 'Nick2', crypt('password1!', gen_salt('bf')), 'USER', 'female', TRUE, 'front',
        'user_2 description', NOW(), NOW(), null),
       ('user_3', 'Nick3', crypt('password1!', gen_salt('bf')), 'USER', 'male', FALSE, 'back',
        'user_3 description',
        NOW(), NOW(), null),
       ('user_4', 'Nick4', crypt('password1!', gen_salt('bf')), 'USER', 'female', TRUE, 'admin',
        'user_4 description', NOW(), NOW(), null),
       ('user_5', 'Nick5', crypt('password1!', gen_salt('bf')), 'USER', 'male', TRUE, 'infra',
        'user_5 description',
        NOW(), NOW(), null),
       ('user_6', 'Nick6', crypt('password1!', gen_salt('bf')), 'USER', 'female', FALSE, 'front',
        'user_6 description', NOW(), NOW(), null),
       ('user_7', 'Nick7', crypt('password1!', gen_salt('bf')), 'USER', 'male', TRUE, 'back',
        'user_7 description',
        NOW(), NOW(), null),
       ('user_8', 'Nick8', crypt('password1!', gen_salt('bf')), 'USER', 'female', FALSE, 'admin',
        'user_8 description', NOW(), NOW(), null),
       ('user_9', 'Nick9', crypt('password1!', gen_salt('bf')), 'USER', 'male', TRUE, 'infra',
        'user_9 description',
        NOW(), NOW(), null),
       ('user0', 'Nick10', crypt('password1!', gen_salt('bf')), 'USER', 'female', FALSE, 'back',
        'user0 description', NOW(), NOW(), null);

INSERT INTO ADMINS (user_name, nick_name, role, password, created_at, updated_at, deleted_at)
VALUES ('admin1', 'adminNickname1', 'ADMIN', crypt('password1!', gen_salt('bf')), NOW(), NOW(), null),
       ('admin2', 'adminNickname2', 'ADMIN', crypt('password1!', gen_salt('bf')), NOW(), NOW(), null),
       ('admin3', 'adminNickname3', 'ADMIN', crypt('password1!', gen_salt('bf')), NOW(), NOW(), null),
       ('admin4', 'adminNickname4', 'ADMIN', crypt('password1!', gen_salt('bf')), NOW(), NOW(), null);

