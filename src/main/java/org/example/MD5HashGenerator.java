package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5HashGenerator {
    public static void main(String[] args) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File inputFile = Paths.get("src/main/resources/input.json").toFile();
            JsonNode rootNode = objectMapper.readTree(inputFile);

            String firstName = rootNode.path("student").path("first_name").asText().toLowerCase();

            String rollNumber = rootNode.path("student").path("roll_number").asText().toLowerCase();

            String combinedString = firstName + rollNumber;

            String md5Hash = generateMD5Hash(combinedString);

            try (FileWriter writer = new FileWriter("output.txt")) {
                writer.write(md5Hash);
            }

            System.out.println("MD5 Hash generated and saved to output.txt: " + md5Hash);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String generateMD5Hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] hashBytes = md.digest(input.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexHash = new StringBuilder();
            for (byte b : hashBytes) {
                hexHash.append(String.format("%02x", b));
            }


            return hexHash.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm is unavailable", e);
        }
    }
}
