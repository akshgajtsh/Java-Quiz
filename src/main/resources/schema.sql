DROP TABLE IF EXISTS quiz_four_choices CASCADE;

CREATE TABLE quiz_four_choices (
    id SERIAL PRIMARY KEY,
    question VARCHAR(255) NOT NULL,
    choice_1 VARCHAR(255) NOT NULL,
    choice_2 VARCHAR(255) NOT NULL,
    choice_3 VARCHAR(255) NOT NULL,
    choice_4 VARCHAR(255) NOT NULL,
    answer INT NOT NULL,
    author VARCHAR(100)
);