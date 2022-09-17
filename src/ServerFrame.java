import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerFrame extends JFrame {
    // Java Swing Attributes
    private JPanel viewSet;
    private CardLayout cardlayout;

    // Server Attributes
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private InputStreamReader input;
    private OutputStreamWriter output;
    private BufferedReader bufferRead;
    private BufferedWriter bufferWrite;

    // Constructor
    /**
     * Constructor for ClientFrame
     */
    public ServerFrame() {
        // Set up the frame with a few settings.
        viewSet = new JPanel(new CardLayout());
        System.out.println("Server Started\n");
        viewSet.add(new MessagePanel("Server Started"), "message");
        cardlayout = (CardLayout) (viewSet.getLayout());
        cardlayout.show(viewSet, "message");
        this.add(viewSet);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                disconnect();
                System.out.println("Server Disconnected");
            }
        });
        this.setSize(600, 400);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setTitle("ServerUI");
        this.setVisible(true);
    }

    public void startServer(int port) {
        try {
            while (true) {
                serverSocket = new ServerSocket(port);
                clientSocket = serverSocket.accept();

                input = new InputStreamReader(clientSocket.getInputStream());
                output = new OutputStreamWriter(clientSocket.getOutputStream());
                bufferRead = new BufferedReader(input);
                bufferWrite = new BufferedWriter(output);

                System.out.println("Connected to client");
                updateText("Connected to Client ");

                while (true) {
                    try {
                        String clientRequest = bufferRead.readLine();
                        bufferWrite.write("Server: Message received");
                        bufferWrite.newLine();
                        bufferWrite.flush();
                        if (clientRequest != null) {
                            System.out.println("Client: " + clientRequest);
                            updateText(clientRequest);
                        }
                    } catch (Exception e) {
                        disconnect();
                        System.out.println("Client Disconnected\n");
                        break;
                    }
                }
                updateText("No Clients Connected");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void disconnect() {
        try {
            // Allows server to disconnect even when no clients are connected
            if (clientSocket != null) { clientSocket.close(); }
            if (serverSocket != null) { serverSocket.close(); }
            if (input != null) { input.close(); }
            if (output != null) { output.close(); }
            if (bufferRead != null) { bufferRead.close(); }
            if (bufferRead != null) { bufferWrite.close(); }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Java Swing Functions
    public void updateText(String message) {
        viewSet.add(new MessagePanel(message), message);
        cardlayout.show(viewSet, message);
        viewSet.remove(0);
    }

    public static void main(String[] args) {
        try {
            // Starting server services here
            ServerFrame serverFrame = new ServerFrame();
            serverFrame.startServer(1234);
        }
        catch (Exception e) {
            System.out.println("Something went wrong...");
        }
    }

}
