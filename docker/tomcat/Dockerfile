FROM fabric8/java-agent-bond:latest

EXPOSE 8080 8778 9779

ENV TOMCAT_VERSION 8.0.23
ENV DEPLOY_DIR /maven
ENV AB_JOLOKIA_CONFIG=/opt/agent-bond/jolokia.properties

# Get and Unpack Tomcat
RUN curl http://archive.apache.org/dist/tomcat/tomcat-8/v${TOMCAT_VERSION}/bin/apache-tomcat-${TOMCAT_VERSION}.tar.gz | tar xz -C /opt \
 && ln -s /opt/apache-tomcat-${TOMCAT_VERSION} /opt/tomcat 

# Add roles
ADD tomcat-users.xml /opt/apache-tomcat-${TOMCAT_VERSION}/conf/

# Jolokia config
ADD jolokia.properties /opt/agent-bond/jolokia.properties

# Startup script
ADD deploy-and-run.sh /opt/apache-tomcat-${TOMCAT_VERSION}/bin/
ADD jmx_exporter_config.json /opt/agent-bond/

# Remove unneeded apps
RUN rm -rf /opt/tomcat/webapps/examples /opt/tomcat/webapps/docs 

VOLUME ["/opt/tomcat/logs", "/opt/tomcat/work", "/opt/tomcat/temp", "/tmp/hsperfdata_root" ]

ENV CATALINA_HOME /opt/tomcat
ENV PATH $PATH:$CATALINA_HOME/bin

CMD /opt/tomcat/bin/deploy-and-run.sh
