# java-filmorate
Template repository for Filmorate project.

##Images ER diagram
![Link on ER-diagram ->](ER/ER_diagramma.png)

###Examples SQL requests
#### Получение списка друзей /{id}/friends]
   
    SELECT
        f.friend_id
    FROM friend AS f
    WHERE user_id = 1`

#### Получение общих друзей

    SELECT
        f.friend_id
    FROM friends AS f
    WHERE user_id = 1 
    AND user_id = 2
    GROUP BY id

#### Получить список популярных фильмов

    SELECT
        f.film_id
    FROM film AS f
    JOIN like AS l ON l.film_id = f.film_id
    ORDER BY l.id DESC
    LIMIT 10