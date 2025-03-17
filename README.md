# Job Aggregator

**Job Aggregator** — это Android-приложение для поиска вакансий. Оно позволяет пользователям находить работу, используя API HeadHunter, а также сохранять понравившиеся вакансии в локальной базе данных. Проект написан на Kotlin с использованием архитектурного подхода MVVM+.

<img src="images2/home.png" alt="home" width="23%"/> <img src="images2/filters.png" alt="filters" width="23%"/> <img src="images2/search.png" alt="search" width="23%"/> <img src="images2/details.png" alt="details" width="23%"/>

## Функциональность
- 🔎 **Поиск вакансий** —  находите актуальные вакансии по ключевым словам и фильтрам.
- ⚙️ **Фильтрация вакансий** —  настраивайте поиск по стране, зарплате, отрасли и региону.
- ⭐️ **Сохранение избранных вакансий** — добавляйте интересные предложения в избранное.
- 📄 **Просмотр деталей вакансии** — изучайте описание, требования и условия работы.

## API
Приложение использует [API HeadHunter](https://github.com/hhru/api?tab=readme-ov-file) для получения данных о вакансиях, компаниях и требованиях. Это позволяет пользователям находить актуальные вакансии и просматривать их подробности.

## Используемые технологии и библиотеки
- **Kotlin Coroutines**: для асинхронных операций.
- **Navigation Component**: для управления навигацией.
- **Room**: для локального хранения данных.
- **Koin**: для внедрения зависимостей.
- **Retrofit и Gson**: для работы с API и парсинга JSON.
- **Glide**: для загрузки и отображения изображений.
- **MVVM+**: архитектурный подход для удобного разделения логики.
- **Clean Architect**: структурирование кода с использованием принципов чистой архитектуры для повышения модульности и тестируемости приложения.
