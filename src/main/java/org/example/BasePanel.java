package org.example;

import javax.swing.*;

//Panellerimin ana sınıfı olan BasePanel
public class BasePanel extends JPanel {
    protected JFrame frame;

    /*Main sinifimda olusturdugum frame i önce alt panellere gönderiyorum sonra o panellerden super() metoduyla
    ana siniflari olan bu panele gonderiyorum. */
    public BasePanel(JFrame frame) {
        this.frame = frame;
    }

    /*Bu metodda ise paneller arasi gecisi sagliyorum alt siniflarım bana bir panel gonderiyor bu fonksiyonu kullandigimda
    bu sekilde panellerim de ulasmis oluyorum .Yani bu panele frame de geliyor panel de geliyor bu sekilde frame in icine paneli
    atarak asıl goruntu isimi bu sinifta yapiyorum.
     */

    public void switchToPanel(JPanel panel) {
        frame.setContentPane(panel);
        frame.revalidate();
        frame.repaint();
    }
}
