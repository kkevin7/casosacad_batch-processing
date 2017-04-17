FROM payara/server-full

# Se provee como variables de entorno la direccion y el nombre del archivo controlador para myslq
ENV JDBC_URL=https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java-5.0.8.zip  
ENV JDBCL_PKG=mysql-connector-java-5.0.8.zip
ADD pass.txt /pass.txt
ADD mysql-connector-java-5.0.8-bin.jar /opt/payara41/glassfish/lib/mysql-connector-java-5.0.8-bin.jar
COPY ./wait-for-mariadb.sh ./
COPY ./como.uesocc.mvnee.casosAcad-ear-1.0-SNAPSHOT.ear /opt/payara41/glassfish/domains/domain1/autodeploy/


# Se descarga el controlador de mysql y se decomprime en la carpeta lib de payara


# Se da la creacion del pool en el servidor (+ jndi) , con el detalle a recordad que, en lugar de localhost se porvee el nombre del contenedor en las propiedades 
RUN /opt/payara41/bin/asadmin start-domain
RUN /opt/payara41/bin/asadmin restart-domain && \
/opt/payara41/bin/asadmin create-jdbc-connection-pool --datasourceclassname=com.mysql.jdbc.jdbc2.optional.MysqlDataSource --restype=javax.sql.DataSource --property='user=root:password=12345:url="jdbc:mysql://172.17.0.2:3306/casosAcad":portNumber=3306:dataBaseName=casosAcad:driverClass=com.mysql.jdbc.Driver:serverName=localhost' ues_pool_CasosAcad --user admin --passwordfile /pass.txt && \
/opt/payara41/bin/asadmin create-jdbc-resource --connectionpoolid ues_pool_CasosAcad ues_jndi_casosAcad --user admin --passwordfile /pass.txt







