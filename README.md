# Java BACKEND Shop Store

Разработка по API FIRST командой "GOT" ( @Sergeek82 / @Samael_IT / @irish_irish_f )
___
### В проекте реализованы следующие функции:
- регистрация;
- вход/выход;
- получение и обновление профиля;
- смена пароля;
- и что то еще ...

--- 

### Дефолтные данные для входа. В миграции
- user@gmail.com userpass
- admin@gmail.com adminpass

Коллекция для Postman src/test/resources/ShopCollectionForPostman.postman_collection.json

### Запуск через Docker 
- docker load -i "..\fix\adsclientv16.tar"
- docker run --rm -p 3000:3000 adsclient:v16
- docker run --rm -p 3000:3000 skypro-frontend-diploma-image
- docker run --name psql -d -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=root -e POSTGRES_DB=shop -p 5432:5432 --rm postgres:12
------------------------------------------------------------
### Запуск через Docker Compose
- docker load -i "..\fix\adsclientv16.tar"
- docker load -i "..\skypro-frontend-diploma-image.tar.gz"
- docker build -t itsrv23/shop:1.0 .\ShopApp\
- docker build -t itsrv23/shop-admin:1.0 .\ShopAdmin\.
- docker compose up