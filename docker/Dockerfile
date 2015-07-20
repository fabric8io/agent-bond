FROM centos

ENV AGENT_BOND_VERSION 0.0.1

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
ADD agent_bond_opts /bin/

# Helper programs & download
RUN chmod 755 /bin/agent_bond_opts \
 && mkdir /opt/agent-bond \
 && wget http://central.maven.org/maven2/io/fabric8/agent-bond/${AGENT_BOND_VERSION}/agent-bond-${AGENT_BOND_VERSION}-agent.jar \
 &&      -O /opt/agent-bond/agent-bond.jar

# Default ports exposed
EXPOSE 22 8080 8778 9779

# Print out help message et al.
CMD java -jar /opt/agent-bond/agent-bond.jar
