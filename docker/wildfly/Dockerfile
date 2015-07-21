FROM fabric8/java-agent-bond:latest

# Set the WILDFLY_VERSION env variable
ENV WILDFLY_VERSION 8.2.0.Final

# Add the WildFly distribution to /opt, and make wildfly the owner of the extracted tar content
# Make sure the distribution is available from a well-known place
RUN curl http://download.jboss.org/wildfly/$WILDFLY_VERSION/wildfly-$WILDFLY_VERSION.tar.gz | tar zx -C /tmp/\
 && mv /tmp/wildfly-$WILDFLY_VERSION /opt/wildfly

# Set the JBOSS_HOME env variable
ENV JBOSS_HOME /opt/wildfly

# Fixes for Wildfly logging (see also https://issues.jboss.org/browse/WFLY-895) & agent bond options
ENV JBOSS_MODULES_SYSTEM_PKGS org.jboss.byteman,org.jboss.logmanager
RUN echo 'JAVA_OPTS="${JAVA_OPTS} -Djava.util.logging.manager=org.jboss.logmanager.LogManager -Xbootclasspath/p:/opt/wildfly/modules/system/layers/base/org/jboss/logmanager/main/jboss-logmanager-1.5.2.Final.jar"' >> /opt/wildfly/bin/standalone.conf
RUN echo 'JAVA_OPTS="${JAVA_OPTS} $(agent-bond-opts)"' >> /opt/wildfly/bin/standalone.conf

# Expose the ports we're interested in
EXPOSE 8080 8778 9779

# Set the default command to run on boot
# This will boot WildFly in the standalone mode and bind to all interface
CMD ["/opt/wildfly/bin/standalone.sh", "-b", "0.0.0.0"]
