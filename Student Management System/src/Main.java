import com.sun.source.util.SourcePositions;

import javax.swing.*;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new smsGUI().setVisible(true);
            }
        });

    }
}