package Bai1;
import java.io.*;
import java.net.*;

public class NumberServer {
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

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
                for (int i = 1; i <= 1000; i++) {
                    writer.println(i);
                    Thread.sleep(1000); // Dừng 1 giây trước khi gửi số tiếp theo
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
