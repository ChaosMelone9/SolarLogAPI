package Interface.BasicUI;

import Handling.SolarMap;
import Interface.SimpleFrame;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class GraphCustomizer extends JTabbedPane {
    static JFrame graphFrame = new JFrame();
    static List<JComponent> components = new ArrayList<>();

    public GraphCustomizer(SolarMap data) {
        addTab("Day View",new DayCustomizer(data));
        addTab("Month View",new MonthCustomizer(data));
    }

    public static void run() {
        SolarMap solarMap = BasicSolarMapCustomizer.solarMap();
        JFrame f = new SimpleFrame(new GraphCustomizer(solarMap));
        f.setSize(200,300);
        f.setResizable(false);
        f.setTitle("Visualization");

        graphFrame.setSize(1000, 600);
        graphFrame.setLocationRelativeTo(f);
    }

    public static void setCmp(JComponent c) {
        for (JComponent cmp : components) {
            graphFrame.remove(cmp);
        }
        graphFrame.setTitle("Visualize - ");
        graphFrame.add(c);
        components.add(c);
        graphFrame.repaint();
        graphFrame.setVisible(true);
    }

}
