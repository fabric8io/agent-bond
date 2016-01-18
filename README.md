## Agent Bond

> The name's Bond, Agent Bond ...

Agent Bond is a super agent, which wraps and dispatches on several
other agents. That way, you only have to install a single agent within
your JVM with a single set of configuration data (which contains
multiple separate parts). 

The following agents are currently supported:

* [Jolokia](https://github.com/rhuss/jolokia) - JMX-over-HTTP bridge
* [jmx_exporter](https://github.com/prometheus/jmx_exporter) - [Prometheus'](http://prometheus.io/) JMX Agent

### Usage

```
java -javaagent=agent-bond.jar=jolokia{{port=8778}},jmx_exporter{{9779:config.json}}
```

The argument passed to the agent has the general format: 

```
agent1_type{{ options }}, agent2_type{{ options }}, ... 
```

Only agents referenced are enabled. The possible agent types are:

* **jolokia** : Options for the Jolokia JVM agents as they are specified in the 
  [reference manual](https://jolokia.org/reference/html/agents.html#jvm-agent). 
  The configuration for Jolokia can be omitted, it will take then the default values
* **jmx_exporter** : Options for [jmx_exporter](https://github.com/prometheus/jmx_exporter) which are mandatory. 
  You need the port and the path to the configuration file like in `jmx_exporter{{9779:config.yaml}}` 
 
Alternatively instead of providing the configuration on the command line you can also specify the path to 
a property file like in `-javaagent:agent-bond.jar=agent.properties` where the properties hold the configuration
line by line:

```
jolokia=port=8778,host=0.0.0.0
jmx_exporter=9779:config.yaml
```

If both, a property file and specific agent configuration is provided on the command line, then the configuration 
on the command line overwrites the one in the configuration.

An agent is only enabled when it is mentioned in the configuration, even with an empty agent configuration 
(like `jolokia{{}}`)

### Docker Base Image
 
There is also a Docker base image available: [fabric8/java-agent-bond](https://registry.hub.docker.com/u/fabric8/java-agent-bond/)

You can use this to easily enable `Jolokia` and/or `jmx_exporter` for your Java applications. Some sample usage for 
[Tomcat](docker/tomcat) and [Wildfly](docker/wildfly) are included.