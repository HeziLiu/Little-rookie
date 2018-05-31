package airsys;


import airsys.view.MainWindow;

import javax.swing.*;

public class ClientApp {
    private MainWindow mainWindow;
    public ClientApp() throws Exception{
        org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
        UIManager.put("RootPane.setupButtonVisible",false);
        mainWindow=new MainWindow();
    }

    public static void main(String args[]) throws Exception{
        new ClientApp();
    }
}
