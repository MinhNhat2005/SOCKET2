package Bai2;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static final Set<PrintWriter> clientWriters = new HashSet<>();

    public static void main(String[] args) {
        int port = 9876; // Cổng mặc định

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server đang lắng nghe trên cổng " + port + "...");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client đã kết nối: " + socket);

                // Khởi tạo một luồng riêng để phục vụ client này
                new Thread(new ClientHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private final Socket socket;
        private String username;
        private PrintWriter writer;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

                this.writer = writer;

                // Yêu cầu client nhập username
                writer.println("Nhập username:");
                username = reader.readLine();
                broadcast(username + " đã tham gia phòng chat");

                // Lưu writer của client này vào danh sách
                synchronized (clientWriters) {
                    clientWriters.add(writer);
                }

                // Đọc tin nhắn từ client và broadcast cho tất cả client khác
                String message;
                while ((message = reader.readLine()) != null) {
                    broadcast(username + ": " + message);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // Xóa writer của client khi client đóng kết nối
                if (username != null) {
                    clientWriters.remove(writer);
                    broadcast(username + " đã rời khỏi phòng chat");
                }
            }
        }

        private void broadcast(String message) {
            synchronized (clientWriters) {
                for (PrintWriter writer : clientWriters) {
                    writer.println(message);
                }
            }
        }
    }
}
