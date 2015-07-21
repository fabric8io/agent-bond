FROM centos

ENV AGENT_BOND_VERSION 0.1.0

# telnet is required by some fabric command. without it you have silent failures
RUN yum install -y java-1.7.0-openjdk java-1.7.0-openjdk-devel which telnet unzip openssh-server sudo openssh-clients
# enable no pass and speed up authentication
RUN sed -i 's/#PermitEmptyPasswords no/PermitEmptyPasswords yes/;s/#UseDNS yes/UseDNS no/' /etc/ssh/sshd_config

# enabling sudo group
RUN echo '%wheel ALL=(ALL) ALL' >> /etc/sudoers
# enabling sudo over ssh
RUN sed -i 's/.*requiretty$/#Defaults requiretty/' /etc/sudoers

# command line goodies
RUN echo "export JAVA_HOME=/usr/lib/jvm/jre" >> /etc/profile
RUN echo "alias ll='ls -l --color=auto'" >> /etc/profile
RUN echo "alias grep='grep --color=auto'" >> /etc/profile

# Add environment setup script
ADD agent-bond-opts /bin/
# Helper programs & download
RUN chmod 755 /bin/agent-bond-opts \
 && mkdir /opt/agent-bond 

ADD jmx_exporter_config.json /opt/agent-bond/
ADD http://central.maven.org/maven2/io/fabric8/agent-bond-agent/${AGENT_BOND_VERSION}/agent-bond-agent-${AGENT_BOND_VERSION}.jar /opt/agent-bond/agent-bond.jar

# Default ports exposed
EXPOSE 22 8080 8778 9779

# Print out help message et al.
CMD java -jar /opt/agent-bond/agent-bond.jar
