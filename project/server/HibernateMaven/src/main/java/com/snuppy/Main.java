package com.snuppy;

import java.awt.*;
import java.io.IOException;
import java.util.logging.Level;

public class Main extends ControlPanel {
    public Main() throws Exception {
        TrayActivity trayActivity = new TrayActivity() {
            @Override
            public void controlAction() {
                super.controlAction();
                setVisible(true);

            }

            @Override
            public void startAction() {
                super.startAction();
                serverStart();
            }

            @Override
            public void stopAction() {
                super.stopAction();
                serverStop();
            }

            @Override
            public void restartAction() {
                super.restartAction();
                serverRestart();
            }

            @Override
            public void exitAction() {
                super.exitAction();
                System.exit(0);
            }
        };
    }

    public static void main(String[] args) {
        java.util.logging.Logger.getLogger("com.jcg.hibernate.maven").setLevel(Level.OFF);
        try {
            Main main = new Main();
        } catch (AWTException e) {
            e.printStackTrace();
        } catch (HeadlessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
