
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package GUI;

/**
 *
 * @author Anuradha Sanjeewa
 */
public class painterDaemon implements Runnable {
    private final TableSet TS;

    public painterDaemon(TableSet TS) {
        this.TS = TS;
    }

    @Override
    public void run() {
        int i = 0;

        while (true) {
            this.TS.repaint();

            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {}
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
