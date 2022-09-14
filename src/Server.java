import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private InputStreamReader input;
    private OutputStreamWriter output;
    private BufferedReader bufferRead;
    private BufferedWriter bufferWrite;

    public void startServer(int port) {
        try {

            while (true) {
                serverSocket = new ServerSocket(port);
                clientSocket = serverSocket.accept();
                System.out.println("Connected to client");

                input = new InputStreamReader(clientSocket.getInputStream());
                output = new OutputStreamWriter(clientSocket.getOutputStream());
                bufferRead = new BufferedReader(input);
                bufferWrite = new BufferedWriter(output);

                while (true) {
                    String clientRequest = bufferRead.readLine();
                    bufferWrite.write("Server: Message received");
                    bufferWrite.newLine();
                    bufferWrite.flush();

                    System.out.println("Client: " + clientRequest);

                    if (clientRequest.equals("q")) {
                        disconnect();
                        System.out.println("Client Disconnected\n");
                        break;
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void disconnect() {
        try {
            clientSocket.close();
            serverSocket.close();
            input.close();
            output.close();
            bufferRead.close();
            bufferWrite.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        System.out.println("Server Started\n");
        Server testServer = new Server();
        testServer.startServer(1234);
    }
}