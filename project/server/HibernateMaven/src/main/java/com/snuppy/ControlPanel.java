package com.snuppy;

import com.sun.net.httpserver.HttpServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.URISyntaxException;

public class ControlPanel extends JFrame {
    private final JButton startButton = new JButton("Start Server");
    private final JButton stopButton = new JButton("Stop Server");
    private final JButton restartButton = new JButton("Restart Server");
    private final JButton exitButton = new JButton("Exit");

    private int port = 8080;
    private HttpServer server = null;
    private Graphic graphic;

    public ControlPanel() throws HeadlessException {

        setTitle("Snuppy Server");
        setSize(400, 400);
        setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getSize().width) / 2,
                (Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLayout(new BorderLayout());


        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serverStart();
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serverStop();
            }
        });

        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serverRestart();

            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        //init butons
        stopButton.setEnabled(false);
        restartButton.setEnabled(false);
        TrayActivity.stopItemEnabled(false);
        TrayActivity.restartItemEnabled(false);

        //add butons to north left panel
        JPanel panel1 = new JPanel(new GridLayout(4, 1));
        panel1.add(startButton);
        panel1.add(stopButton);
        panel1.add(restartButton);
        panel1.add(exitButton);

        //setup north panel
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BorderLayout());
        northPanel.setPreferredSize(new Dimension(400, 120));
        northPanel.add(panel1, BorderLayout.WEST);
        //init graphic
        graphic = new Graphic(30);
        northPanel.add(graphic, BorderLayout.CENTER);
        add(northPanel, BorderLayout.NORTH);
        //console texta area
        JTextArea textArea = new JTextArea();
        PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));
        System.setOut(printStream);
        textArea.setLineWrap(true);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        final Connection connection = new Connection();
        //graphic auto refresh
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if (isVisible()) {
                            //update graphic

                            graphic.setTop(connection.selectUsersCount());
                            graphic.addValue(connection.selectActiveUsersCount());
                        }
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        setVisible(true);
    }


    public void serverStart() {

        TrayActivity.startItemEnabled(false);
        TrayActivity.stopItemEnabled(true);
        TrayActivity.restartItemEnabled(true);

        startButton.setEnabled(false);
        stopButton.setEnabled(true);
        restartButton.setEnabled(true);

        System.out.println("Begin start server...");
        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Can not start server , check port.");
        }

        server.createContext("/img", new RequestHandler());
        server.createContext("/reg", new PostHandler());
        server.createContext("/snuppy", new DownloadHandler());
        server.setExecutor(null);

        System.out.println("Server init on " + port + " port");
        server.start();
    }

    public void serverStop() {
        TrayActivity.startItemEnabled(true);
        TrayActivity.stopItemEnabled(false);
        TrayActivity.restartItemEnabled(false);

        startButton.setEnabled(true);
        stopButton.setEnabled(false);
        restartButton.setEnabled(false);

        server.stop(1);
        System.out.println("Server is stopped.");
        System.out.println("");
    }

    public void serverRestart() {
        TrayActivity.startItemEnabled(false);
        TrayActivity.stopItemEnabled(true);
        TrayActivity.restartItemEnabled(true);

        startButton.setEnabled(false);
        stopButton.setEnabled(true);
        restartButton.setEnabled(true);

        System.out.println("Begin restart server...");
        serverStop();
        serverStart();
    }

}
