Сервер Коним-Хахамим
TODO Проверить, как будет работать imageLoadActivity, если addPurchase бросило 500
0.8.4.
Добавлен view actions_summary в базу. Чтобы это работало, надо добавить в my.cnf (my.ini) [mysqld]
sql_mode=STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION
убрав оттуда ONLY_FULL_GROUP_BY

0.8.3 1.04.2021
1. Добавлены изменения в контроллер, сервис и ДАО для получения списка регионов для бота

0.8.2 31.03.2021
1. При вводе нового пользователя проверка уникальности возвращает код сообщения об ошибке, 
а не англоязычную строковую константу