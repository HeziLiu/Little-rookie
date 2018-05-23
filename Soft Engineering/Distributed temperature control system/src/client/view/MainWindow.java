package client.view;



import client.Configure;
import client.model.conditioner;

import javax.swing.*;
import java.net.Inet4Address;
import java.net.UnknownHostException;

public class MainWindow extends JFrame {
    private OverViewPanel panel;
    protected conditioner model;

    public MainWindow() throws UnknownHostException{
        panel=new OverViewPanel();
        add(panel);
        model=new conditioner(Configure.ROOM_ID,Inet4Address.getLocalHost().getHostAddress());
        model.setTargetTemp(Configure.DEFAULT_TARGET_TEMP);
        model.setWorkMode(conditioner.COLD_MODE);

       // ViewController controller=new ViewController(panel,model);

        setSize(400,200);
        setVisible(true);
    }
}
