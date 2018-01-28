## [Getting started](#getting-started)

ID Resolver analyses researcher and show the relations among them.

### [Create database](#create-database)

Connect to your local mysql database: `mysql -uroot`

Execute the following:

```sql
CREATE DATABASE id_resolver;
grant all on id_resolver.* to 'root'@'localhost';
```

### [Starting](#starting)

Start the server in dev modus - which means you do not need to login - with:
```
cd server
mvn spring-boot:run -Dspring.profiles.active=dev
```
Start the client with:
```
cd gui
yarn install
yarn start
```

### [Security](#security)

The server API has two securities: one for the API intended to be used by organisations and one for the
JS GUI. The first is a stateless API and secured with Basic Authentication. The second is exactly the same
Basic Authentication, but a secure cookie will be set to remember the user for the duration of the session.

The users are provisioned with the yaml file defined in the `application.yml`:

```
security:
  api_users_config_path: classpath:/id-resolver-api-users.yml
```

The passwords in this file are one-way encrypted. To get the encrypted value for a password you can use the un-secured
endpoint in the application:

```
curl  http://localhost:8080/client/users/encodePassword/secret
```

The location of the user / password file will be overridden in the ansible script. All users can reside in the ansible script
and the encrypted passwords are stored in a private git repo. See the ansible [README](ansible/README.md) for more information.

Users can only insert researchers of their own organisation and subsequently only update and delete authors of their
own organization.

### [Testing](#testing)

```
curl --user user:secret http://localhost:8080/api/resolver/researchers | python -m json.tool
curl --user user:secret http://localhost:8080/api/resolver/researchers/1 | python -m json.tool
curl --user user:secret http://localhost:8080/api/resolver/find/researchers?q=44 | python -m json.tool
curl --user user:secret http://localhost:8080/api/resolver/stats | python -m json.tool
curl --user user:secret http://localhost:8080/client/users/me | python -m json.tool

```
