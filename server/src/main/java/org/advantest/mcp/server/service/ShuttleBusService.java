package org.advantest.mcp.server.service;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Create on 2025/06/25
 * Author: mengen.dai@outlook.com
 */
@Service
public class ShuttleBusService {

    static class ShuttleBus {
        String route;
        String departure;
        String destination;
        LocalTime time;

        public ShuttleBus(String route, String departure, String destination, LocalTime time) {
            this.route = route;
            this.departure = departure;
            this.destination = destination;
            this.time = time;
        }

        @Override
        public String toString() {
            return String.format("路线 %s：从 %s 出发，前往 %s，发车时间：%s",
                    route, departure, destination, time.toString());
        }
    }

    private final List<ShuttleBus> busList = new ArrayList<>();

    public ShuttleBusService() {
        // 模拟的中文班车数据
        busList.add(new ShuttleBus("A1", "大门口", "5号楼", LocalTime.of(8, 0)));
        busList.add(new ShuttleBus("A2", "1号楼", "食堂", LocalTime.of(8, 30)));
        busList.add(new ShuttleBus("B1", "5号楼", "大门口", LocalTime.of(17, 45)));
        busList.add(new ShuttleBus("C3", "3号楼", "停车场", LocalTime.of(12, 15)));
        busList.add(new ShuttleBus("D1", "大门口", "2号楼", LocalTime.of(9, 10)));
    }

    @Tool(description = "Retrieve all current shuttle bus information.")
    public String getShuttleBusInfo() {
        return busList.stream()
                .map(ShuttleBus::toString)
                .collect(Collectors.joining("\n"));
    }

    @Tool(description = "Retrieve shuttle bus information for a specified time range, in the format HH:mm-HH:mm (e.g., 08:00-12:00).")
    public String getShuttleBusInfoByTime(String timeRange) {
        try {
            String[] parts = timeRange.split("-");
            LocalTime start = LocalTime.parse(parts[0].trim());
            LocalTime end = LocalTime.parse(parts[1].trim());

            List<ShuttleBus> filtered = busList.stream()
                    .filter(bus -> !bus.time.isBefore(start) && !bus.time.isAfter(end))
                    .toList();

            if (filtered.isEmpty()) {
                return "在指定时间段内没有找到班车信息。";
            }

            return filtered.stream()
                    .map(ShuttleBus::toString)
                    .collect(Collectors.joining("\n"));
        } catch (Exception e) {
            return "时间格式错误，请使用格式：HH:mm-HH:mm，例如 08:00-12:00";
        }
    }
}
