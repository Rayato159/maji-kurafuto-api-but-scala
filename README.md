<h1>Maji Kurafuto CRUD</h1>

<h2>🐳 Start MySQL on Docker</h2>

<p>Pull MySQL image</p>

```bash
docker pull mysql
```

<p>Run container</p>

```bash
docker run --name maji-kurafuto-mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 -d mysql
```

<p>Deep into container</p>

```bash
docker exec -it maji-kurafuto-mysql bash
```

<p>Config MySQL</p>

```bash
mysql -u root -p
```

```bash
ALTER USER 'root' IDENTIFIED WITH mysql_native_password BY '123456';
```
```bash
flush privileges;
```

<p>Creating a data</p>

```bash
CREATE DATABASE maji_kurafuto_mysql;
```
```bash
USE maji_kurafuto_mysql;
```

<p>Database Url</p>

```bash
jdbc:mysql://localhost:3306/maji_kurafuto_db?characterEncoding=utf8
```