package com.example.androidproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CarColorSelector {
    private static final Map<String, String> colorMap = new HashMap<>();

    static {
        colorMap.put("Black", "#000000");
        colorMap.put("Gray", "#808080");
        colorMap.put("White", "#FFFFFF");
        colorMap.put("Red", "#FF0000");
        colorMap.put("Orange", "#FF5733");
        colorMap.put("Silver", "#C0C0C0");
        colorMap.put("Yellow", "#FFEA00");
        colorMap.put("Dark Blue", "#00008B");
        colorMap.put("Blue", "#0000FF");
    }

    public static List<String> getSelectedColorsHex(String carColor) {
        List<String> selectedColorsHex = new ArrayList<>();

        selectedColorsHex.add(colorMap.get("Black"));
        selectedColorsHex.add(colorMap.get("White"));
        selectedColorsHex.add(colorMap.get("Silver"));

        if ("Black".equals(carColor) || "White".equals(carColor) || "Silver".equals(carColor)) {
            selectedColorsHex.add("#FF0000");
        } else {
            selectedColorsHex.add(colorMap.get(carColor));
        }

        return selectedColorsHex;
    }

    private static String getRandomNonBWSColorHex() {
        List<String> nonBWSColors = new ArrayList<>(colorMap.values());
        nonBWSColors.removeAll(List.of("#000000", "#FFFFFF", "#C0C0C0"));
        Random random = new Random();
        return nonBWSColors.get(random.nextInt(nonBWSColors.size()));
    }

    public static String getColorNameFromHex(String hexCode) {
        for (Map.Entry<String, String> entry : colorMap.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(hexCode)) {
                return entry.getKey();
            }
        }
        return "Unknown";
    }
}