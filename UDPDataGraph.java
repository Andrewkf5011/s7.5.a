import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.awt.event.*;

import graph.*;

public class UDPDataGraph extends JFrame {
    static public final long serialVersionUID = 1L;
    InetSocketAddress destination_address;

    static Hashtable<String,String> datasets = new Hashtable<String,String>();

    public static void main( String[] args ) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new UDPDataGraph();
            }
        });
    }

    public UDPDataGraph() {
        super("Send Text via UDP");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel content = new JPanel( );
        content.setLayout( new BoxLayout( content, BoxLayout.Y_AXIS) );
        SocketAddressPanel svr = new SocketAddressPanel();
        content.add( svr );

        GraphPanel pots = new GraphPanel("potentiometers",
                                    new Dimension(300,100),
                                    new Scale(0,1));
        pots.dataSource(datasets);
        content.add(pots);

        GraphPanel acc = new GraphPanel("Acellerometer",
                                    new Dimension(300,200),
                                    new Scale(-1.5,1.5));
        acc.dataSource(datasets);
        content.add(acc);

        this.setContentPane(content);
        this.pack();
        this.setVisible(true);

        pots.addTrace("POT 1",0.25,Color.blue);
        pots.addTrace("POT 2",0.5,Color.green.darker());
        acc.addTrace("X", 0, Color.red.darker());
        acc.addTrace("Y", 0, Color.green.darker());
        acc.addTrace("Z", 0, Color.blue);

        new Thread( svr ).start();
        new javax.swing.Timer(20, pots).start();
        new javax.swing.Timer(20, acc).start();
    }

    static class SocketAddressPanel extends JPanel implements Runnable {
        public static final long serialVersionUID = 1L;
        static public JLabel ip;
        static private JLabel port;
        static InetAddress Iam;
        static public InetSocketAddress getSocketAddress() {
            return new InetSocketAddress(
                            ip.getText(),
                            Integer.parseInt( port.getText() )
                        );
        }

        public SocketAddressPanel() {
            super( new FlowLayout(FlowLayout.LEFT, 5, 0) );
            try{
            setBorder( BorderFactory.createTitledBorder("Internet Socket Address (listening)") );

            add( new JLabel("IP:") );
            ip = new JLabel("192.168.");
            Iam = InetAddress.getLocalHost();
            ip.setText(Iam.getHostAddress());

            add(ip);
            add( new JLabel(" port:") );
            port = new JLabel("65500");
            add(port);
        }catch(Exception e){}}

        public void run(){
            try{
                byte[] buffer = new byte[512];
                DatagramSocket socket = new DatagramSocket(getSocketAddress());
                while(true){
                    DatagramPacket msg = new DatagramPacket(buffer, buffer.length);
                    socket.receive(msg);
                    String message = new String(msg.getData());
                    message.trim();
                    String[] lines = message.split("\n");
                    for(String l : lines ) {
                        l = l.trim();
                        if( l.length()>0 && l.indexOf(':')>0 ) {
                            String[] pair = l.trim().split(":");
                            datasets.put(pair[0], pair[1]);
                        }
                    }
                }
            }catch(Exception e){
                System.err.println(e);
            }
        }
    }

}
