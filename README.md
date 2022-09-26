# Explore With Me
Сервис для создания и поиска мероприятий

Explore with me состоит из двух микросервисов:
1) Основной сервис — содержит всё необходимое для работы продукта.
2) Сервис статистики — хранит количество просмотров и позволяет делать различные выборки для анализа работы приложения.

Основной сервис делится на 3 уровня доступа: Public, Private и Admin.
Первая — публичная, доступна без регистрации любому пользователю сети. Вторая — закрытая, доступна только авторизованным пользователям. Третья — административная, для администраторов сервиса.

Сервис статистики собирает информацию о количестве обращений пользователей к спискам событий, а также о количестве запросов к подробной информации о событии. 