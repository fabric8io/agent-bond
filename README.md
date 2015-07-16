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

