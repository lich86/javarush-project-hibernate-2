# project-hibernate-2

Проект JavaRush hibernate #2:

https://javarush.com/quests/lectures/jru.module4.lecture07

## Предложения по улучшению БД:

1. Можно сделать поле email уникальным, чтобы было проще искать клиентов в таблице customer.

2. Поля language_id, rental_duration, rate, replacement_cost и special_features можно перенести в таблицу inventory, так как они скорее относятся к конкретному экземпляру фильма. Также для поля special_features можно сделать отдельную таблицу и связь one-to-one.

3. В таблицу inventory можно добавить boolean поле available для облегчения поиска по свободным фильмам.

4. При условии сохранения таблицы film_text, можно перенести в нее поле description. Но, вообще, от неё можно отказаться совсем.

5. Можно во все таблицы добавить поле create_time