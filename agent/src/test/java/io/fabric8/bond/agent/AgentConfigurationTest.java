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

package io.fabric8.bond.agent;

import java.io.*;
import java.util.*;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author roland
 * @since 17/07/15
 */
public class AgentConfigurationTest {

    private Set<String> agentIds;

    @Before
    public void setUp() throws Exception {
        agentIds = new HashSet<>();
        agentIds.add("jolokia");
        agentIds.add("jmx_exporter");
    }

    @Test
    public void empty() {
        for (String arg : new String[] { "", null }) {
            AgentConfiguration config = new AgentConfiguration(agentIds, arg);
            assertNull(config.getArgs("jolokia"));
        }
    }

    @Test
    public void cli() throws IOException {
        String p = createTestProps("port=8577","9999:other.json");
        String[] data = new String[] {
                p,"port=8577","9999:other.json",
                p + ",jmx_exporter{{567:config.json}}","port=8577","567:config.json",
                "jmx_exporter{{567:config.json}}," + p,"port=8577","567:config.json",
                "jolokia{{port=1234,host=localhost}},jmx_exporter{{567:config.json}}","port=1234,host=localhost","567:config.json",
                "jolokia{{port=1234,host=localhost}}", "port=1234,host=localhost", null,
        };
        for (int i = 0; i < data.length; i += 3) {
            AgentConfiguration config = new AgentConfiguration(agentIds,data[i]);
            assertEquals(data[i+1],config.getArgs("jolokia"));
            assertEquals(data[i+2],config.getArgs("jmx_exporter"));
        }
    }

    private String createTestProps(String jolokia, String jmx_exporter) throws IOException {
        File temp = File.createTempFile("agent",".props");
        Properties props = new Properties();
        props.put("jolokia",jolokia);
        props.put("jmx_exporter", jmx_exporter);
        props.store(new FileWriter(temp),"Test data");
        return temp.getCanonicalPath();
    }
}
