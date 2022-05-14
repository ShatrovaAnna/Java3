package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameCell extends JButton {
    enum Type { X, O, Empty };

    public GameCell(int x, int y, IGameField callback) {
        super("");
        m_x = x;
        m_y = y;
        m_callback = callback;
        set(Type.Empty);

        addActionListener(new ClickActionListener());
    }

    public class ClickActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            m_callback.onCellClicked(m_x, m_y);
        }
    }
    void set(Type type) {
        m_type = type;
        switch(m_type) {
            case Empty:
                setText(" ");
                break;
            case X:
                setText(" X");
                break;
            case O:
                setText(" O");
                break;
        }
    }

    Type type() {
        return m_type;
    }

    static GameCell.Type otherType(GameCell.Type type) {
        switch(type) {
            case X:
                return Type.O;
            case O:
                return Type.X;
            default:
                return Type.Empty;
        }
    }

    private int m_x, m_y;
    private IGameField m_callback;
    Type m_type;
}
