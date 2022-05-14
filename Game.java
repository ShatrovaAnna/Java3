package com.company;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.*;


public class Game extends JFrame implements IGame {
    public Game() {
        super("Game Window");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JPanel toolbarPanel = new JPanel(new GridLayout(1, 2, 2, 2));

        m_message = new JLabel("Игра не начата");
        m_message.setSize(new Dimension(80, 40));

        m_newGame = new JButton("Новая игра");
        m_newGame.setSize(new Dimension(80, 40));
        m_newGame.addActionListener(new GameStartActionListener());

        toolbarPanel.add(m_message);
        toolbarPanel.add(m_newGame);

        JPanel gameFieldPanel = new JPanel();
        m_gameField = new GameField(this);
        gameFieldPanel.add(m_gameField);

        // настройка главного окна
        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);

        mainPanel.add(toolbarPanel, BorderLayout.NORTH);
        mainPanel.add(gameFieldPanel, BorderLayout.SOUTH);
        setSize(500, 500);

        m_gameField.setActive(false);
    }

    public class GameStartActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            m_gameField.setActive(true);
            m_gameField.start();

            setMoveText(); //чей ход

        }
    }

    @Override
    public void win(GameCell.Type playerType) {

        m_gameField.setActive(false);

        if (m_gameField.currentMove() == GameCell.Type.X) {
            m_message.setText("Победил: Х");
        }
        if (m_gameField.currentMove() == GameCell.Type.O) {
            m_message.setText("Победил: O");
        }
    }

    @Override
    public void nextMove() {
        setMoveText();
    }

    private void setMoveText() {
        if (m_gameField.currentMove() == GameCell.Type.X) {
            m_message.setText("Ход: Х");
        }
        if (m_gameField.currentMove() == GameCell.Type.O) {
            m_message.setText("Ход: O");
        }
    }

    private GameField m_gameField;
    private JLabel m_message;
    private JButton m_newGame;
}
