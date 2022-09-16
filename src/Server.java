import javax.swing.*;
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
                createGUI();
                clientSocket = serverSocket.accept();
                System.out.println("Connected to client");

                input = new InputStreamReader(clientSocket.getInputStream());
                output = new OutputStreamWriter(clientSocket.getOutputStream());
                bufferRead = new BufferedReader(input);
                bufferWrite = new BufferedWriter(output);

                while (true) {
                    try {
                        String clientRequest = bufferRead.readLine();
                        bufferWrite.write("Server: Message received");
                        bufferWrite.newLine();
                        bufferWrite.flush();
                        if (clientRequest != null) { System.out.println("Client: " + clientRequest); }
                    } catch (Exception e) {
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

    private void createGUI() {
        JFrame serverFrame = new JFrame("ServerUI");
        serverFrame.setContentPane(new ServerUI().serverPane);
        serverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        serverFrame.pack();
        serverFrame.setBounds(10,10,350,300);
        serverFrame.setVisible(true);
    }

    public static void main(String[] args) {
        Server testServer = new Server();
        testServer.startServer(1234);
    }
}