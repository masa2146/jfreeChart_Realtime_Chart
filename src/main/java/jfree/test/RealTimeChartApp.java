package jfree.test;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

/**
 * @author Fatih
 */
public class RealTimeChartApp {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Test Chart");
        RealTimeChart rtcp = new RealTimeChart("Random Data", "Random Number", "Value");
        frame.getContentPane().add(rtcp, new BorderLayout().CENTER);
        frame.pack();
        frame.setVisible(true);
        (new Thread(rtcp)).start();
        Timer timer = new Timer(1, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        rtcp.update();
                    }
                });
            }
        });
//        timer.start();

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowevent) {
                System.exit(0);
            }

        });
    }
}
