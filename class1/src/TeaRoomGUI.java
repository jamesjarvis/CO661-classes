import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import tearoom.*;

public class TeaRoomGUI extends JPanel implements ActionListener {

    private Timer timer = new Timer(1, this);
    public TeaRoom teaRoom;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the machine
        drawBar(g, teaRoom.machine().supply(), 0, Color.DARK_GRAY);
        drawBar(g, teaRoom.machine().profit(), 1, Color.BLUE);

        // Draw the drinkers
        int pos = 2;
        for (Customer drinker : teaRoom.customers()) {
            // Chose their color representation
            Color color = Color.DARK_GRAY;
            switch (drinker.status()) {
                case RESTING:
                    color = Color.DARK_GRAY; break;
                case QUEUEING:
                    color = Color.RED; break;
                case WAITING:
                    color = Color.ORANGE; break;
                case DRINKING:
                    color = Color.GREEN; break;
            }
            drawBar(g, drinker.funds(), pos, color);
            pos++;
        }
    }

    private void drawBar(Graphics g, int size, int pos, Color col) {
        g.setColor(col);
        g.fillRect(20+pos*65, 600-(size*20), 20, size*20);
    }

    @Override
    public Dimension getPreferredSize() {
        // so that our GUI is big enough
        return new Dimension(300+50*teaRoom.customers().size(), 650);
    }

    public TeaRoomGUI() {
        timer.start();
    }

    public void actionPerformed(ActionEvent ev){
        if(ev.getSource()==timer) {
            repaint();// this will call at every 1 second
        }
    }

    // create the GUI explicitly on the Swing event thread
    private static void createAndShowGui() {
        TeaRoomGUI mainPanel = new TeaRoomGUI();

        TeaRoom teaRoom = new TeaRoomInitial(50, 3);
        mainPanel.teaRoom = teaRoom;

        JFrame frame = new JFrame("Tea room");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(WindowEvent winEvt) {
                System.out.println("Vending machine profit = " + teaRoom.machine().profit());
            }
        });

        /// Draw labels
        JLabel machineLbl1 = new JLabel("Supply    ", JLabel.LEFT);
        machineLbl1.setVerticalAlignment(JLabel.BOTTOM);
        mainPanel.add(machineLbl1);

        JLabel machineLbl2 = new JLabel("Profit   ", JLabel.LEFT);
        machineLbl2.setVerticalAlignment(JLabel.BOTTOM);
        mainPanel.add(machineLbl2);

        for(int i=0; i<teaRoom.customers().size(); i++) {
            JLabel lbl = new JLabel("Drinker " + i + " ", JLabel.LEFT);
            lbl.setVerticalAlignment(JLabel.BOTTOM);
            mainPanel.add(lbl);
        }

        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGui();
            }
        });
    }

}