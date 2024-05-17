package Bai2;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int port = 9876;

        try (Socket socket = new Socket(serverAddress, port);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            // Nhận và in thông báo nhập username từ server
            String serverResponse = reader.readLine();
            System.out.println(serverResponse);

            // Nhập username từ người dùng
            String username = scanner.nextLine();
            writer.println(username);

            // Đọc và gửi tin nhắn với username
            String message;
            while (true) {
                message = scanner.nextLine();
                writer.println(username + ": " + message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
