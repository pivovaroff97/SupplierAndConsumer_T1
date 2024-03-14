# SupplierService_t1

Цель: Создать два микросервиса для управления данными с использованием Spring JPA, Spring Boot и RestTemplate. 
Один микросервис будет являться источником данных (Supplier Service), предоставляющим информацию о продуктах. 
Второй микросервис будет потребителем этих данных (Consumer Service), обрабатывающим информацию о продуктах

Supplier endpoints:

POST /products: Создать новый продукт.

GET /products: Получить список всех продуктов.
    возможные параметры:
    1. search=(product_field:field_value) - фильтрация по продуктам
    на место product_field вставить поле продукта, вместе field_value значение поля
    ":" - equals
    ">" - equals or greater than
    "<" - equals or less than
    /products?search=category:category_id - фильтр по полю category только через id Category
    2. name - поиск по полю name
    3. description - поиск по полю description

GET /products/{id}: Получить информацию о продукте по его идентификатору.

PUT /products/{id}: Обновить информацию о продукте.

DELETE /products/{id}: Удалить продукт по его идентификатору.

POST /categories: Создать новую категорию.

GET /categories: Получить список всех категорий.

GET /categories/{id}: Получить информацию о категории по ее идентификатору.

PUT /categories/{id}: Обновить информацию о категории.

DELETE /categories/{id}: Удалить категорию по ее идентификатору