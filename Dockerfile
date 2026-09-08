FROM quay.io/wildfly/wildfly:26.1.2.Final-jdk17

COPY backend/final/WebLab4.war /opt/jboss/wildfly/standalone/deployments/WebLab4.war
COPY standalone.xml /opt/jboss/wildfly/standalone/configuration/standalone.xml
COPY postgresql-42.7.2.jar /opt/jboss/wildfly/standalone/deployments/