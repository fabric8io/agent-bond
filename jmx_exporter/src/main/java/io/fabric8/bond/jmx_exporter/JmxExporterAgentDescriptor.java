/*
 * Copyright 2015 Red Hat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.fabric8.bond.jmx_exporter;

import io.fabric8.bond.core.AgentDescriptor;
import io.prometheus.jmx.shaded.io.prometheus.jmx.JavaAgent;

/**
 * @author roland
 * @since 17/07/15
 */
public class JmxExporterAgentDescriptor implements AgentDescriptor {
    @Override
    public String getId() {
        return "jmx_exporter";
    }

    @Override
    public String getHelp() {
        return "This is the Prometheus jmx_exporter";
    }

    @Override
    public Class getPremainClass() {
        return JavaAgent.class;
    }
}
