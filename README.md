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

```bash
-- Create the maji table
CREATE TABLE maji (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(255) NOT NULL DEFAULT '',
    damage INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Insert sample data into the maji table
INSERT INTO maji (title, description, damage) VALUES
    ('Fireball', 'A basic fire spell that deals fire damage to a single target.', 50),
    ('Frost Bolt', 'A basic frost spell that deals ice damage to a single target and may freeze it.', 40),
    ('Lightning Strike', 'A basic lightning spell that deals electric damage to a single target and has a chance to stun.', 60),
    ('Earth Spike', 'A basic earth spell that deals physical damage to a single target and may cause a knockback.', 45),
    ('Healing Wave', 'A basic healing spell that restores health to a single target.', 30);
```

<p>Database Url</p>

```bash
jdbc:mysql://localhost:3306/maji_kurafuto_db?characterEncoding=utf8
```

<h2>🤯 API Lists</h2>
<p>***All exmaple is in the Postman collection</p>
<ul>
    <li>GET /maji -> find one maji</li>
    <li>GET /maji/:maji_id -> find majis</li>
    <li>POST /maji -> create maji</li>
    <li>PATCH /maji/:maji_id -> edit maji</li>
    <li>DELETE /maji/:maji_id -> delete maji</li>
</ul>