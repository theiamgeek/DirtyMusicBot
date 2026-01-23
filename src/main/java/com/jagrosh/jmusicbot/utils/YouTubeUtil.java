/*
 * Copyright 2024.
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
package com.jagrosh.jmusicbot.utils;

import com.sedmelluq.lava.extensions.youtuberotator.planner.*;
import com.sedmelluq.lava.extensions.youtuberotator.tools.ip.IpBlock;
import com.sedmelluq.lava.extensions.youtuberotator.tools.ip.Ipv4Block;
import com.sedmelluq.lava.extensions.youtuberotator.tools.ip.Ipv6Block;

import java.net.InetAddress;
import java.util.List;
import java.util.function.Predicate;

public class YouTubeUtil {
    public enum RoutingPlanner {
        ROTATING_NANO("RotatingNanoIpRoutePlanner"),
        ROTATING("RotatingIpRoutePlanner"),
        NANO("NanoIpRoutePlanner"),
        BALANCING("BalancingIpRoutePlanner"),
        NONE("None");

        private final String name;

        RoutingPlanner(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static RoutingPlanner fromName(String name) {
            for (RoutingPlanner planner : values()) {
                if (planner.getName().equalsIgnoreCase(name)) {
                    return planner;
                }
            }
            return NONE;
        }
    }

    public static AbstractRoutePlanner createRouterPlanner(RoutingPlanner planner, List<IpBlock> ipBlocks) {
        switch (planner) {
            case ROTATING_NANO:
                return new RotatingNanoIpRoutePlanner(ipBlocks, addr -> true);
            case ROTATING:
                return new RotatingIpRoutePlanner(ipBlocks);
            case NANO:
                return new NanoIpRoutePlanner(ipBlocks, true);
            case BALANCING:
                return new BalancingIpRoutePlanner(ipBlocks);
            default:
                return null;
        }
    }

    public static IpBlock createIpBlock(String ipBlockString) {
        if (ipBlockString.contains(":")) {
            return new Ipv6Block(ipBlockString);
        } else {
            return new Ipv4Block(ipBlockString);
        }
    }
}
