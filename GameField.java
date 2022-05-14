package com.company;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.JPanel;

public class GameField extends JPanel implements IGameField {
    GameField(IGame game) {
        JPanel gridPanel = new JPanel(new GridLayout(Size, Size, 2, 2));

        add(gridPanel);

        gridPanel.setBackground(Color.darkGray);
        gridPanel.setPreferredSize(new Dimension(500, 500));

        m_buttons = new GameCell[10][];
        for (int i = 0; i < 10; i++) {
            m_buttons[i] = new GameCell[10];
            for (int j = 0; j < 10; j++) {
                m_buttons[i][j] = new GameCell(i, j, this);
                m_buttons[i][j].setMargin(new Insets(0, 0, 0, 0));
                m_buttons[i][j].setEnabled(true);

                gridPanel.add(m_buttons[i][j]);
            }
        }

        m_game = game;
    }

    @Override
    public void onCellClicked(int x, int y) {
        if (m_buttons[x][y].type() != GameCell.Type.Empty)
            return;

        m_buttons[x][y].set(m_currentMove);

        for (int i = 0; i < Size; i++) {
            for (int j = 0; j < Size; j++) {
                if (checkFrom(i, j) == true) {
                    m_game.win(GameCell.otherType(m_buttons[i][j].type()));
                    return;
                }
            }
        }

        m_currentMove = GameCell.otherType(m_currentMove);
        m_game.nextMove();
    }

    void start() {
        m_currentMove = GameCell.Type.X;
        for (int i = 0; i < Size; i++) {
            for (int j = 0; j < Size; j++) {
                m_buttons[i][j].set(GameCell.Type.Empty);
            }
        }
    }

    void setActive(boolean is_active) {
        for (int i = 0; i < Size; i++) {
            for (int j = 0; j < Size; j++) {
                m_buttons[i][j].setEnabled(is_active);
            }
        }
        setEnabled(is_active);
    }

    public GameCell.Type currentMove() {
        return m_currentMove;
    }

    private boolean checkFrom(int x, int y) {
        GameCell.Type player = m_buttons[x][y].type();
        if (player == GameCell.Type.Empty)
            return false;

        boolean xWin = true, yWin = true, xyWin = true;
        for (int i = 0; i < 5; ++i) {
            if (i + x >= Size || m_buttons[i+x][y].type() != player)
                xWin = false;
            if (i + y >= Size || m_buttons[x][i+y].type() != player)
                yWin = false;
            if (i + y >= Size || i + x >= Size || m_buttons[x+i][i+y].type() != player)
                xyWin = false;
        }

        return xyWin || xWin || yWin;
    }

    private GameCell.Type m_currentMove;
    private final GameCell[][] m_buttons;
    public final int Size = 10;
    private final IGame m_game;
}
