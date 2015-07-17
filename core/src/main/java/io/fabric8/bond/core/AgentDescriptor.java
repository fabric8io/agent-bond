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

package io.fabric8.bond.core;

/**
 * Interface which needs to implemented by a sub agent
 *
 * @author roland
 * @since 17/07/15
 */
public interface AgentDescriptor {

    /**
     * Get the name of this agent
     *
     * @return id of the agent
     */
    String getId();

    /**
     * Help message specific to this sub agent
     *
     * @return help
     */
    String getHelp();

    /**
     * Get the name of the class holding the premain method
     *
     * @return class name of premain class
     */
    Class getPremainClass();
}

