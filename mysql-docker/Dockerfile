FROM mysql:5
COPY cdb.cnf /etc/mysql/conf.d/
COPY 1-SCHEMA.sql /etc/mysql/conf.d/
COPY 2-PRIVILEGES.sql /etc/mysql/conf.d/
COPY 3-ENTRIES.sql /etc/mysql/conf.d/
COPY 1-SCHEMA.sql /docker-entrypoint-initdb.d/
COPY 2-PRIVILEGES.sql /docker-entrypoint-initdb.d/
COPY 3-ENTRIES.sql /docker-entrypoint-initdb.d/
EXPOSE 3306