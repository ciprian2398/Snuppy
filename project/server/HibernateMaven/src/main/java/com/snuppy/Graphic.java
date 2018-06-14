package com.snuppy;

import javax.swing.*;
import java.awt.*;

public class Graphic extends JPanel {
    private int cols;
    private int colWidth;
    private int[] ints;
    private int curCols;
    private int top;


    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getColWidth() {
        return colWidth;
    }

    public void setColWidth(int colWidth) {
        this.colWidth = colWidth;
    }


    public Graphic(int colWidth) {
        this.colWidth = colWidth;
        ints = new int[1];
        cols = 1;
        curCols = 0;
    }

    public void addValue(int value) {
        if (curCols < cols) curCols++;
        insertValue(value);
        repaint();
    }


    private void insertValue(int val) {
        init();
        for (int i = curCols - 1; i > 0; i--) {
            ints[i] = ints[i - 1];
        }
        ints[0] = val;
    }

    public void init() {
        int newCols = 0;
        if (getWidth() > 1) newCols = getWidth() / getColWidth() + 1;
        if (newCols != cols && newCols != 0) {
            int[] intsAux = new int[newCols + 1];
            curCols = Math.min(ints.length, intsAux.length);
            for (int i = 0; i < curCols; i++) {
                intsAux[i] = ints[i];
            }
            ints = intsAux;
            cols = newCols + 1;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g.drawString("Total: " + top, 10, 15);
        g.drawString("Active: " + ints[0], 10, 30);

        g.setColor(Color.BLUE);

        g2.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        for (int i = curCols - 1; i > 0; i--) {
            g.drawLine(
                    colWidth * (curCols - i - 1),
                    // cit este  (cit % reprezinta  ints[i] din top)% getHight
                    getHeight() - ((ints[i] * 100 / top) * getHeight() / 100),
                    colWidth * (curCols - i),
                    getHeight() - ((ints[i - 1] * 100 / top) * getHeight() / 100)
            );
        }
    }
}

