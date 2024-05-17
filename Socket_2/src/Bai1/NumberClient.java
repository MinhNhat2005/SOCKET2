package Bai1;

import java.io.*;
import java.net.*;

public class NumberClient {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int port = 9876;

        try (Socket socket = new Socket(serverAddress, port);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String number;
            while ((number = reader.readLine()) != null) {
                System.out.println("Số từ server: " + number);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
