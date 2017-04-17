FROM mariadb

ENV MYSQL_ROOT_PASSWORD=
ENV MYSQL_DATABASE=casosAcad
# Se almacena el SQL en la carpeta que, la imagen de mariadb ejecuta por defecto y 
# dichos datos son volcados sobre la base de datos indicada en la variable de entorno MYSQL_DATABASE 
ADD /casosAcad.sql /docker-entrypoint-initdb.d/casosAcad.sql
EXPOSE 3306
ENTRYPOINT ["docker-entrypoint.sh"]
CMD ["mysqld"]

