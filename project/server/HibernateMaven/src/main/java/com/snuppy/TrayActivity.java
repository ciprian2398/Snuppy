package com.snuppy;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class TrayActivity {
    private static MenuItem startItem = new MenuItem("Start server");
    private static MenuItem stopItem = new MenuItem("Stop server");
    private static MenuItem restartItem = new MenuItem("Restart server");
    private static MenuItem exitItem = new MenuItem("Exit");

    public static void startItemEnabled(boolean b) {
        startItem.setEnabled(b);
    }

    public static void stopItemEnabled(boolean b) {
        stopItem.setEnabled(b);
    }

    public static void restartItemEnabled(boolean b) {
        restartItem.setEnabled(b);
    }

    public TrayActivity() throws Exception {
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }

        SystemTray tray = SystemTray.getSystemTray();
        Toolkit toolkit = Toolkit.getDefaultToolkit();

        PopupMenu menu = new PopupMenu();

        MenuItem managerItem = new MenuItem("Control panel");


        managerItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controlAction();
            }
        });

        startItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //start server
                startAction();
            }
        });

        stopItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //stop server
                stopAction();
            }
        });

        restartItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //restart server
                restartAction();
            }
        });

        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //exit app
                exitAction();
            }
        });


        menu.add(managerItem);
        menu.add(startItem);
        menu.add(stopItem);
        menu.add(restartItem);
        menu.addSeparator();
        menu.add(exitItem);

        Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/tray-64.png"));
        TrayIcon icon = new TrayIcon(image, "Snuppy", menu);

        icon.setImageAutoSize(true);
        tray.add(icon);
    }

    public static byte[] readFully(InputStream input) throws IOException {
        byte[] buffer = new byte[8192];
        int bytesRead;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
        return output.toByteArray();
    }


    public void controlAction() {

    }

    public void startAction() {

    }

    public void stopAction() {

    }

    public void restartAction() {

    }

    public void exitAction() {

    }
}