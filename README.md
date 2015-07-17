## Agent Bond

> My name is Bond, Agent Bond ...

Agent Bond is a super agent, which wraps and dispatches on several
other agents. That way, you only have to install a single agent within
your JVM with a single set of configuration data (which contains
multiple separate parts). 

Currently supported are the following agents:

* [Jolokia](https://github.com/rhuss/jolokia) - JMX-over-HTTP bridge
* [jmx_exporter](https://github.com/prometheus/jmx_exporter) - Agent
exporting JMX in an Prometheus understandable format

### Usage

```
java -javaagent=agent-bond.jar=jolokia{{port=8778,host=0.0.0.0}},jmx_exporter{{9779:config.json}}
```

The argument passed to the agent as the general format: *agent1 type*`{{`*agent options*`}},`*agent2 type*`{{`*options`}},` *...* 

The possible agent types are:

* **jolokia** : Options for the Jolokia JVM agents as they are specified in the [reference manual](https://jolokia.org/reference/html/agents.html#jvm-agent). 
  The configuration for Jolokia can be omitted, it will take then the default values
* **jmx_exporter** : Options for [jmx_exporter](https://github.com/prometheus/jmx_exporter) which are mandatory. You need the port and the path to the 
  configuration file like in `jmx_exporter{{9779,config.json}}` 
 
Algernatively instead of providing the configuration on the command line you can also specify the path to 
a property file like in `-javaagent:agent-bond.jar=agent.properties` where the properties hold the configuration
line by line:

```
jolokia=port=8778,host=0.0.0.0
jmx_exporter=9779,config.json
```

### Limitations

* No way to switch off an agent now. Will be added soon.
* Currently the agent might not contain dependencies with different versions since 
  they are ll packed into the same classloader. Depending on the build order the latter 
  agent will override already existing class files within the agent.
