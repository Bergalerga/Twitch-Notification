package frontend;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;

/**
 * Class responsible for initializing and handling events related to the system tray.
 * Created by berg on 21/07/15.
 */
public class Systemtray {

    private final SystemTray tray;
    private final PopupMenu popup;
    private TrayIcon trayIcon;


    private MenuItem exit = null;
    private MenuItem about = null;

    public Systemtray() {
        popup = new PopupMenu();
        tray = SystemTray.getSystemTray();
        initSystemTray();
    }

    private void initSystemTray() {
        if (!SystemTray.isSupported()) {
            System.out.println("Systemtray is not supported");
            return;
        }
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("static/img/trayIcon.png"));
        }
        catch (IOException e) {
            System.out.println("Could not read file");
        }
        //Defining the items
        exit = new MenuItem("Exit");
        about = new MenuItem("About");

        //Adding items
        popup.add(exit);
        popup.add(about);

        trayIcon = new TrayIcon(img);
        trayIcon.setPopupMenu(popup);
        try {
            tray.add(trayIcon);
        }
        catch (AWTException e) {
            System.out.println("Could not add trayIcon");
        }
        initActionListeners();
    }
    private void initActionListeners() {
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int reply = JOptionPane.showConfirmDialog(null, "Are you sure?", "", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
                else {
                    return;
                }
            }
        });
        about.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Developed and administered by Fredrik Christoffer Berg \n fredrik.c.berg1@gmail.com");
            }
        });
    }

    /**
     * Method to display a popup message, displaying if a streamer has gone online or offline.
     * @param isOnline Whether or not the streamer has gone online or offline.
     */
    public void displayPopup(String userName) {
        String twitchURL = "www.twitch.tv/" + userName;

        trayIcon.displayMessage(userName + " just went live!", "click here to go to stream", TrayIcon.MessageType.INFO);
        /*
         * Double click action performed
         */
        trayIcon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                URI uri = null;
                try {
                    uri = new URI(twitchURL);
                }
                catch (Exception ex) {
                    System.out.println("Could not create link");
                }
                try {
                    java.awt.Desktop.getDesktop().browse(uri);
                } catch (Exception ex) {

                }
            }
        });
    }
}
