## Java Image Base image, including Agent Bond ...

This image is based on CentOS and OpenJDK 1.7 image and 
includes an agent-bond Agent with Jolokia and Prometheus' jmx_exporter. 
The agent is installed as `/opt/agent-bond/agent-bond.jar`. 

In order to enable Jolokia for your application you should use this 
image as a base image (via `FROM`) and use the output of `agent-bond-opts` in 
your startup scripts to include it in your startup options. 

For example, the following snippet can be added to a script starting up your 
Java application

    # ...
    export JAVA_OPTIONS="$JAVA_OPTIONS $(agent-bond-opts)"
    # .... us JAVA_OPTIONS when starting your app, e.g. as Tomcat does

You can influence the behaviour `agent-bond-opts` by setting various environment 
variables:

* **AB_OFF** : If set disables activation of agent-bond (i.e. echos an empty value). By default, agent-bond is enabled.
* **AB_ENABLED** : Comma separated list of sub-agents enabled. Currently allowed valuess `jolokia` and `jmx_exporter`. 
  By default both are enabled

So, if you start the container with `docker run -e AB_OFF ...` no agent will be launched.

The following versions and defaults are used:

* Jolokia: version **1.3.1** and port **8778**
* jmx_exporter: version **0.3-SNAPSHOT** and port **9779**  

### Jolokia configuration

* **AB_JOLOKIA_CONFIG** : If set uses this file (including path) as Jolokia JVM agent properties (as described 
  in Jolokia's [reference manual](http://www.jolokia.org/reference/html/agents.html#agents-jvm)). 
  By default this is `/opt/jolokia/jolokia.properties`. If this file exists, it be will taken 
  as configuration and **any other config options are ignored**.  
* **AB_JOLOKIA_HOST** : Host address to bind to (Default: 0.0.0.0)
* **AB_JOLOKIA_PORT** : Port to use (Default: `8778`)
* **AB_JOLOKIA_USER** : User for authentication. By default authentication is switched off.
* **AB_JOLOKIA_PASSWORD** : Password for authentication. By default authentication is switched off.
* **AB_JOLOKIA_ID** : Agent ID to use (`$HOSTNAME` by default, which is the container id)
* **AB_JOLOKIA_OPTS**  : Additional options to be appended to the agent opts. They should be given in the format 
  "key=value,key=value,..."

Some options for integration in various environments

* **AB_JOLOKIA_AUTH_OPENSHIFT** : Switch on OAuth2 authentication for OpenShift. The value of this parameter must be the OpenShift API's 
  base URL (e.g. `https://localhost:8443/osapi/v1/`)


### jmx_exporter configuration 

* **AB_JMX_EXPORTER_OPTS** : Configuration to use for `jmx_exporter` (in the format `<port>:<path to config>`)
* **AB_JMX_EXPORTER_PORT** : Port to use for the JMX Exporter. Default: `9779`
* **AB_JMX_EXPORTER_CONFIG** : Path to configuration to use for `jmx_exporter`: Default: `/opt/agent-bond/jmx_exporter_config.json`


