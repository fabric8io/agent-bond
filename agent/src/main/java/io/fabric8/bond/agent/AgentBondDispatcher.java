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

import java.lang.instrument.Instrumentation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import io.fabric8.bond.core.AgentDescriptor;

/**
 * Iterate over all sub-agents.
 *
 * @author roland
 * @since 16/07/15
 */
public class AgentBondDispatcher {

    /**
     * Entry point for dispatching to the agents.
     *
     * @param agentArgs arguments as given on the command line
     */
    public static void premain(String agentArgs, Instrumentation instrumentation) {

        // Extract agents first
        ServiceLoader<AgentDescriptor> subAgentLoader = ServiceLoader.load(AgentDescriptor.class);
        List<AgentDescriptor> agents = new ArrayList<>();
        Set<String> agentIds = new HashSet<>();
        for (AgentDescriptor agent : subAgentLoader) {
            agents.add(agent);
            agentIds.add(agent.getId());
        }

        AgentConfiguration config = new AgentConfiguration(agentIds,agentArgs);

        // Call all premains ...
        for (AgentDescriptor agent : agents) {
            String args = config.getArgs(agent.getId());
            callPremain(agent.getId(), agent.getPremainClass(), args, instrumentation);
        }
    }

    private static void callPremain(String agentId, Class premainClass, String args, Instrumentation instrumentation) {
        Method method;
        try {
            try {
                method = premainClass.getMethod("premain", String.class, Instrumentation.class);
                method.invoke(null,args != null ? args : "",instrumentation);
            } catch (NoSuchMethodException e) {
                try {
                    method = premainClass.getMethod("premain", String.class);
                    method.invoke(null,args != null ? args : "");
                } catch (NoSuchMethodException exp) {
                    throw new IllegalArgumentException(agentId + ": Internal, no method premain() found for class " + premainClass);
                }
            }
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(agentId + ": Cannot initialize with arguments = " + args);
        }
    }
}
