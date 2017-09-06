 FROM jboss/wildfly

 ADD ./target/pdp.war /opt/jboss/wildfly/standalone/deployments/

 EXPOSE 9763
