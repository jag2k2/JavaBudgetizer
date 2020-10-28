package flb.application.main.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserSelectsCategory implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
    }
}
