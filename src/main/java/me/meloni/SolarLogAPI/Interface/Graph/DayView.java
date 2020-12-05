package me.meloni.SolarLogAPI.Interface.Graph;

import me.meloni.SolarLogAPI.DataConversion.Entries;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * This Class represents the implementation of a simple graph for daily scale.
 * @author ChaosMelone9
 * @since 0.1.0
 */
public class DayView extends JPanel {

    private final List<List<Double>> data;

    private Color GridColor = Color.DARK_GRAY;
    private Color BackgroundColor = Color.GRAY;
    private Color GraphBackgroundColor = Color.WHITE;
    private Color AxisColor = Color.BLACK;
    private Color LabelColor = Color.BLACK;
    private Color Row1Color = new Color(191, 97, 106);
    private Color Row2Color = new Color(94, 129, 172);
    private Color Row3Color = new Color(235, 203, 139);
    private Color Row4Color = new Color(136, 192, 208);
    private Color Row5Color = new Color(163, 190, 140);

    private int padding = 25;
    private int labelPadding = 25;
    private int valuePadding = 10;

    private boolean Row1Visible = true;
    private boolean Row2Visible = true;
    private boolean Row3Visible = true;
    private boolean Row4Visible = true;
    private boolean Row5Visible = true;

    private boolean mouseGUI = true;

    private int mouseX;
    private int mouseY;

    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);

    public DayView(List<List<Double>> data) {
            this.data = data;
        }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        double xScale = ((double) getWidth() - (2 * padding) - labelPadding - labelPadding) / (data.size() - 1);

        Stroke stroke = g2.getStroke();

        double yaScale = ((double) getHeight() - 2 * padding - labelPadding) / (getMaxAScore() - getMinScore());
        double ybScale = ((double) getHeight() - 2 * padding - labelPadding) / (getMaxBScore() - getMinScore());

        List<Point> Row1 = new ArrayList<>();
        List<Point> Row2 = new ArrayList<>();
        List<Point> Row3 = new ArrayList<>();
        List<Point> Row4 = new ArrayList<>();
        List<Point> Row5 = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            int x = (int) (i * xScale + padding + labelPadding);

            int Row1y = (int) ((getMaxAScore() - data.get(i).get(0)) * yaScale + padding);
            int Row2y = (int) ((getMaxBScore() - data.get(i).get(1)) * ybScale + padding);
            int Row3y = (int) ((getMaxAScore() - data.get(i).get(2)) * yaScale + padding);
            int Row4y = (int) ((getMaxBScore() - data.get(i).get(3)) * ybScale + padding);
            int Row5y = (int) ((getMaxAScore() - data.get(i).get(4)) * yaScale + padding);
            Row1.add(new Point(x, Row1y));
            Row2.add(new Point(x, Row2y));
            Row3.add(new Point(x, Row3y));
            Row4.add(new Point(x, Row4y));
            Row5.add(new Point(x, Row5y));
        }

        //paint background
        g2.setColor(BackgroundColor);
        g2.fillRect(0,0,getWidth(),getHeight());

        g2.setColor(GraphBackgroundColor);
        g2.fillRect(padding + labelPadding, padding, getWidth() - (padding * 2) - (labelPadding * 2), getHeight() - (padding * 2) - labelPadding);

        g2.setColor(AxisColor);

        // create hatch marks and grid lines for y axis.
        int pointWidth = 4;
        int numberYDivisions = 10;
        for (int i = 0; i < numberYDivisions + 1; i++) {
            int x0 = padding + labelPadding;
            int x1 = pointWidth + padding + labelPadding;
            int y0 = getHeight() - ((i * (getHeight() - padding * 2 - labelPadding)) / numberYDivisions + padding + labelPadding);
            g2.setColor(GridColor);
            g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding - labelPadding, y0);
            g2.setColor(LabelColor);
            String y1Label = ((int) ((getMinScore() + (getMaxAScore() - getMinScore()) * ((i * 1.0) / numberYDivisions)) * 100)) / 100 + "";
            String y2Label = ((int) ((getMinScore() + (getMaxBScore() - getMinScore()) * ((i * 1.0) / numberYDivisions)) * 100)) / 100 + "";
            FontMetrics metrics = g2.getFontMetrics();
            int label1Width = metrics.stringWidth(y1Label);
            g2.drawString(y1Label, x0 - label1Width - 5, y0 + (metrics.getHeight() / 2) - 3);
            g2.drawString(y2Label, getWidth() - padding - labelPadding + 5, y0 + (metrics.getHeight() / 2) - 3);
            g2.drawLine(x0, y0, x1, y0);
        }

        // and for x axis
        for (int i = 0; i < 25; i++) {
            int x0 = i * (getWidth() - padding * 2 - labelPadding * 2) / 24 + padding + labelPadding;
            int y0 = getHeight() - padding - labelPadding;
            int y1 = y0 - pointWidth;
            int i2 = 96;
            if(getWidth() > 740) {
                i2 = i2 * 2;
            }
            if(getWidth() > 1560) {
                i2 = i2 * 3;
            }
            if ((i % ((data.size() / i2) + 1)) == 0) {
                g2.setColor(GridColor);
                g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth , x0, padding);
                g2.setColor(LabelColor);
                String xLabel = i + ":00";
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(xLabel);
                g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
            }
            g2.drawLine(x0, y0, x0, y1);
        }

        // create x and y*2 axes
        g2.setColor(AxisColor);
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() - padding - labelPadding , getHeight() - padding - labelPadding);
        g2.drawLine(getWidth() - padding - labelPadding, getHeight() - padding - labelPadding, getWidth() - padding - labelPadding ,   padding);

        g2.setStroke(GRAPH_STROKE);
        if(Row1Visible) {
            g2.setColor(Row1Color);
            for (int i = 0; i < Row1.size() - 1; i++) {
                int x1 = Row1.get(i).x;
                int y1 = Row1.get(i).y;
                int x2 = Row1.get(i + 1).x;
                int y2 = Row1.get(i + 1).y;
                g2.drawLine(x1, y1, x2, y2);
            }
        }
        if(Row2Visible) {
            g2.setColor(Row2Color);
            for (int i = 0; i < Row2.size() - 1; i++) {
                int x1 = Row2.get(i).x;
                int y1 = Row2.get(i).y;
                int x2 = Row2.get(i + 1).x;
                int y2 = Row2.get(i + 1).y;
                g2.drawLine(x1, y1, x2, y2);
            }
        }
        if(Row3Visible) {
            g2.setColor(Row3Color);
            for (int i = 0; i < Row3.size() - 1; i++) {
                int x1 = Row3.get(i).x;
                int y1 = Row3.get(i).y;
                int x2 = Row3.get(i + 1).x;
                int y2 = Row3.get(i + 1).y;
                g2.drawLine(x1, y1, x2, y2);
            }
        }
        if(Row4Visible) {
            g2.setColor(Row4Color);
            for (int i = 0; i < Row4.size() - 1; i++) {
                int x1 = Row4.get(i).x;
                int y1 = Row4.get(i).y;
                int x2 = Row4.get(i + 1).x;
                int y2 = Row4.get(i + 1).y;
                g2.drawLine(x1, y1, x2, y2);
            }
        }
        if(Row5Visible) {
            g2.setColor(Row5Color);
            for (int i = 0; i < Row5.size() - 1; i++) {
                int x1 = Row5.get(i).x;
                int y1 = Row5.get(i).y;
                int x2 = Row5.get(i + 1).x;
                int y2 = Row5.get(i + 1).y;
                g2.drawLine(x1, y1, x2, y2);
            }
        }

        if(mouseX >= labelPadding + padding & mouseX <= getWidth() - labelPadding - padding & mouseY >= padding & mouseY <= getHeight() - padding - labelPadding & mouseGUI & visibleRows() > 0){
            g2.setStroke(stroke);
            g2.setColor(GraphBackgroundColor);
            g2.fillRect(padding + labelPadding, padding, 200, ((valuePadding + 12) * visibleRows()) + 20);
            g2.setColor(GridColor);
            g2.drawRect(padding + labelPadding, padding, 200, ((valuePadding + 12) * visibleRows()) + 20);
            double ExactMouseXValue = (mouseX - padding -labelPadding) / xScale;

            g2.setColor(LabelColor);
            g2.drawString("Values at " + Entries.timestamps().get((int)Math.floor(ExactMouseXValue)), padding + labelPadding + valuePadding, padding + valuePadding * 2);

            int i = 2;
            if(Row1Visible) {
                g2.drawString("verbrauchw: " + Math.round(data.get((int)ExactMouseXValue).get(0)), padding + labelPadding + valuePadding, padding + (valuePadding * 2) * i);
                i++;
            }
            if(Row2Visible) {
                g2.drawString("verbrauchkwh: " + Math.round(data.get((int)ExactMouseXValue).get(1)), padding + labelPadding + valuePadding, padding + (valuePadding * 2) * i);
                i++;
            }
            if(Row3Visible) {
                g2.drawString("leistungw: " + Math.round(data.get((int)ExactMouseXValue).get(2)), padding + labelPadding + valuePadding, padding + (valuePadding * 2) * i);
                i++;
            }
            if(Row4Visible) {
                g2.drawString("ertragkwh: " + Math.round(data.get((int)ExactMouseXValue).get(3)), padding + labelPadding + valuePadding, padding + (valuePadding * 2) * i);
                i++;
            }
            if(Row5Visible) {
                g2.drawString("energieverbrauchw: " + Math.round(data.get((int)ExactMouseXValue).get(4)), padding + labelPadding + valuePadding, padding + (valuePadding * 2) * i);
            }
        }

        if(mouseGUI) {
            addMouseMotionListener(new MouseMotionListener() {
                @Override
                public void mouseDragged(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseMoved(MouseEvent mouseEvent) {
                mouseX = mouseEvent.getX();
                mouseY = mouseEvent.getY();

                repaint();
            }
            });
        }
    }

    private double getMinScore() {
        double minScore = Double.MAX_VALUE;
        assert data != null;
        for (List<Double> score : data) {
            for (Double aDouble : score) minScore = Math.min(minScore, aDouble);
        }
        return minScore;
    }

    private double getMaxAScore() {
        double maxScore = Double.MIN_VALUE;
        assert data != null;
        for (List<Double> score : data) {
            maxScore = Math.max(maxScore, score.get(0));
            maxScore = Math.max(maxScore, score.get(2));
            maxScore = Math.max(maxScore, score.get(4));
        }
        maxScore = Math.ceil(maxScore / 1000) * 1000;
        return maxScore;
    }

    private double getMaxBScore() {
        double maxScore = Double.MIN_VALUE;
        for (List<Double> score : data) {
            maxScore = Math.max(maxScore, score.get(1));
            maxScore = Math.max(maxScore, score.get(3));
        }
        maxScore = Math.ceil(maxScore / 1000) * 1000;
        return maxScore;
    }

    public int visibleRows() {
        int i = 0;
        if(Row1Visible) {i++;}
        if(Row2Visible) {i++;}
        if(Row3Visible) {i++;}
        if(Row4Visible) {i++;}
        if(Row5Visible) {i++;}
        return i;
    }

    public void setGridColor(Color color){
            GridColor = color;
        }

    public void setBackgroundColor(Color color){
            BackgroundColor = color;
        }

    public void setGraphBackgroundColor(Color graphBackgroundColor) { GraphBackgroundColor = graphBackgroundColor; }

    public void setAxisColor(Color color){
            AxisColor = color;
        }

    public void setLabelColor(Color color){
            LabelColor = color;
        }

    public void setRow1Color(Color color){
            Row1Color = color;
        }

    public void setRow2Color(Color color){
            Row2Color = color;
        }

    public void setRow3Color(Color color){
            Row3Color = color;
        }

    public void setRow4Color(Color color){
            Row4Color = color;
        }

    public void setRow5Color(Color color){
            Row5Color = color;
        }

    public void setPadding(int i){
            padding = i;
        }

    public void setLabelPadding(int i){
            labelPadding = i;
        }

    public void setValuePadding(int i) { valuePadding = i; }

    public void setRow1Visible(boolean rowVisible) {
            Row1Visible = rowVisible;
        }

    public void setRow2Visible(boolean rowVisible) {
            Row2Visible = rowVisible;
        }

    public void setRow3Visible(boolean rowVisible) {
            Row3Visible = rowVisible;
        }

    public void setRow4Visible(boolean rowVisible) {
            Row4Visible = rowVisible;
        }

    public void setRow5Visible(boolean rowVisible) {
            Row5Visible = rowVisible;
        }

    public void setMouseGUIVisible(boolean mouseGUIVisible) {
            mouseGUI = mouseGUIVisible;
        }
}