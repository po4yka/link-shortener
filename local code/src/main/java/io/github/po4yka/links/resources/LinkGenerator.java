package io.github.po4yka.links.resources;

import java.util.Random;

public class LinkGenerator {
    private static final String possibleCharacters = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm1234567890";

    public static String getRandomId() {
        StringBuilder idBuilder = new StringBuilder();
        Random randomGenerator = new Random();
        while (idBuilder.length() < 5) {
            int index = (int) (randomGenerator.nextFloat() * possibleCharacters.length());
            idBuilder.append(possibleCharacters.charAt(index));
        }

        return idBuilder.toString();
    }
}
