package edu.gac.mcs270.hvidsten.tictactoe;


import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class TicTacView extends JFrame {
	private TicTacController gameCtrl;
	private JPanel btnPanel;
	private JButton btns[][]; 
	
	public TicTacView() {
		this.setTitle("Tic-Tac-Toe");
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
        getContentPane().add(mainPanel);

        btnPanel = new JPanel(new GridLayout(3, 3));
        mainPanel.add(btnPanel, new GridBagConstraints());
        btnPanel.setMaximumSize(new Dimension(300, 300));

        setPreferredSize(new Dimension(600, 800));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
        setVisible(true);
	}
	
	public TicTacController getGameCtrl() {
		return gameCtrl;
	}

	public void setGameCtrl(TicTacController gameCtrl) {
		this.gameCtrl = gameCtrl;
	}
	
	public void setBoard(Position[][] board) {
		int size =gameCtrl.getBoardSize();
		btns = new JButton[size][size];
		for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
            	btns[i][j]=makeBoardButton(board[i][j]);
            	btnPanel.add(btns[i][j]);
            }
		}
		pack();
        setVisible(true);
	}
	
	public JButton makeBoardButton(Position p){
		JButton btn = new JButton();
		
		Font curFont = this.getFont();
		btn.setPreferredSize(new Dimension(100, 100));
		btn.setFont(new Font(curFont.getFontName(), curFont.getStyle(), 22));
		btn.setFocusable(false);
		btn.setText(Position.NOT_PLAYED);
		
		final int i = p.getLoc_i();
		final int j = p.getLoc_j();
		
		btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	gameCtrl.handlePlayerMove(i,j);
			}});
		return btn;
	}

	public void sendErrorMsg(String msg) {
		JOptionPane.showMessageDialog(this,msg);
	}

	public void announceWinner(Player curPlayer) {
		JOptionPane.showMessageDialog(this,
				      "Player "+curPlayer.getName()+" has won!");
		gameCtrl.resetGame();
	}
	
	public void announceTie() {
		JOptionPane.showMessageDialog(this,"Game is a Draw!");
		gameCtrl.resetGame();
	}

	public void updateView(Position[][] board) {
		for (int i = 0; i < gameCtrl.getBoardSize(); i++) {
            for (int j = 0; j < gameCtrl.getBoardSize(); j++) {
            	btns[i][j].setText(board[i][j].getValue());
            }
		}
	}

}
