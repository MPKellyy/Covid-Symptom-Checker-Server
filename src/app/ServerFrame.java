package app;
import javax.swing.*;

import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class is responsible controlling which panel is displayed to the user.
 * This class also serves the main frame for the software.
 */
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

    /**
     * Constructor for ServerFrame.
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

    /**
     * Sets-up a server socket for client communication.
     * @param port specifies a port to establish the socket.
     */
    public void startServer(int port) {
        try {
            // Looks for client connections until server is shut down.
            while (true) {
                // Preliminary socket set-up
                serverSocket = new ServerSocket(port);
                clientSocket = serverSocket.accept();

                // Establishing read/write behavior
                input = new InputStreamReader(clientSocket.getInputStream());
                output = new OutputStreamWriter(clientSocket.getOutputStream());
                bufferRead = new BufferedReader(input);
                bufferWrite = new BufferedWriter(output);

                // Notify user when a client connects
                System.out.println("Connected to client");
                updateText("Connected to Client ");

                // While the client is connected
                while (true) {
                    try {
                        // Read client response
                        String clientRequest = bufferRead.readLine();
                        // If connection is still established with client
                        if (clientRequest != null) {
                            // Respond to client with calculated covid likelihood
                            bufferWrite.write((new AnswerHandler(clientRequest).getLikelihood()));
                            bufferWrite.newLine();
                            bufferWrite.flush();
                            // Display client's responses as binary string to server user
                            System.out.println("Client: " + clientRequest);
                            updateText(clientRequest);
                        }
                    } catch (Exception e) {
                        disconnect();
                        System.out.println("Client Disconnected\n");
                        break;
                    }
                }
                // Notify to user that no clients are connected
                updateText("No Clients Connected");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Function used to close the server socket connection.
     */
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
    /**
     * Updates current panel on server frame.
     * @param message binary string of client's responses.
     */
    public void updateText(String message) {
        viewSet.add(new MessagePanel(message), message);
        cardlayout.show(viewSet, message);
        viewSet.remove(0);
    }

    /**
     * Main function for server.
     * @param args
     */
    public static void main(String[] args) {
        try {
            // Starting server services here
            ServerFrame serverFrame = new ServerFrame();
            serverFrame.startServer(1234);
        }
        catch (Exception e) {
            System.out.println("Something went wrong...");
            System.out.println(e);
        }
    }

}
