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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parse arguments and build up a agent-per-config map
 *
 * @author roland
 * @since 17/07/15
 */
public class AgentConfiguration {

    // parsed arguments
    private Properties arguments;

    // specific agent config given on the command line
    private HashMap<Object, Object> agentCliArgs;

    public AgentConfiguration(Set<String> agents, String args) {
        String remaining = extractAgentArgs(agents, args == null ? "" : args);

        // Fecth property provided on the command line
        String propFile = extractPropertyFile(remaining);
        arguments = loadProperties(propFile);

        // Command line args overwrite properties
        arguments.putAll(agentCliArgs);
    }

    // The rest are configuration files
    private String extractPropertyFile(String remaining) {
        StringTokenizer tok = new StringTokenizer(remaining,",");
        List<String> propFiles = new ArrayList<>();
        while (tok.hasMoreTokens()) {
            propFiles.add(tok.nextToken());
        }
        if (propFiles.size() == 0) {
            return null;
        } else if (propFiles.size() > 1) {
            throw new IllegalArgumentException("More than one property file declared: " + remaining);
        } else {
            return propFiles.get(0);
        }
    }

    // load from file
    private Properties loadProperties(String propFile) {
        Properties ret = new Properties();
        Properties prop = extractProperties(propFile);
        if (prop != null) {
            ret.putAll(prop);
        }
        return ret;
    }

    private String extractAgentArgs(Set<String> agents, String args) {
        agentCliArgs = new HashMap<>();
        Pattern splitPattern = Pattern.compile("(?:^|,)\\s*([^,{]+)\\{{2}(.*?)}{2}\\s*");
        Matcher matcher = splitPattern.matcher(args);
        StringBuffer rest = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(rest,"");
            String agent = matcher.group(1);
            String agentArgs = matcher.group(2);
            if (!agents.contains(agent)) {
                throw new IllegalArgumentException("No agent '" + agent + "' available from [" + agents + "]");
            }
            agentCliArgs.put(agent, agentArgs);
        }
        matcher.appendTail(rest);
        return rest.toString();
    }

    public String getArgs(String agentId) {
        return arguments.getProperty(agentId);
    }

    // =================================================================================================

    private Properties extractProperties(String propertyFile) {
        Properties ret = new Properties();
        if (propertyFile != null) {
            try {
                FileReader reader = new FileReader(propertyFile);
                ret.load(reader);
                reader.close();
            } catch (FileNotFoundException e) {
                throw new IllegalArgumentException("No property file " + propertyFile + " found",e);
            } catch (IOException e) {
                throw new RuntimeException("Can not read " + propertyFile + ": " + e,e);
            }
        }
        return ret;
    }
}
