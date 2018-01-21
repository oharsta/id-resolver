### [Create database](#create-database)

Connect to your local mysql database: `mysql -uroot`

Execute the following:

```sql
CREATE DATABASE id_resolver;
grant all on id_resolver.* to 'root'@'localhost';
```

### [Testing](#testing)

```
curl --user user:secret http://localhost:8080/api/resolver/researchers | python -m json.tool
curl --user user:secret http://localhost:8080/api/resolver/researchers/1 | python -m json.tool
curl --user user:secret http://localhost:8080/api/resolver/find/researchers?q=44 | python -m json.tool
curl --user user:secret http://localhost:8080/api/resolver/stats | python -m json.tool
```
