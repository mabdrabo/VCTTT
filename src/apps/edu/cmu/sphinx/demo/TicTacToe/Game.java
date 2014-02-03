package edu.cmu.sphinx.demo.TicTacToe;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Game {

	private JFrame frame;
	
	private TicTacToe backend;
	private JLabel[] boxes = new JLabel[9];

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Game window = new Game();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Game() {
		initialize();
		backend = new TicTacToe();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		final JPanel startPanel = new JPanel();
		startPanel.setBounds(58, 5, 304, 183);
		frame.getContentPane().add(startPanel);
		startPanel.setLayout(new BoxLayout(startPanel, BoxLayout.PAGE_AXIS));
		
		JLabel lblVoiceControlledTic = new JLabel("<html>Voice Controlled<br>Tic Tac Toe<html>");
		lblVoiceControlledTic.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblVoiceControlledTic.setFont(new Font("Lucida Grande", Font.PLAIN, 38));
		startPanel.add(lblVoiceControlledTic);
		
		Component verticalStrut = Box.createVerticalStrut(60);
		startPanel.add(verticalStrut);
		
		JButton btnPlay = new JButton("PLAY");
		btnPlay.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		btnPlay.setAlignmentX(Component.CENTER_ALIGNMENT);
		startPanel.add(btnPlay);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		
		final JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel boardPanel = new JPanel();
		panel.add(boardPanel);
		boardPanel.setLayout(new GridLayout(3, 3, 0, 0));
		
		for (int i=0; i<9; i++) {
			boxes[i] = new JLabel("" + i);
			boxes[i].setHorizontalAlignment(SwingConstants.CENTER);
			boardPanel.add(boxes[i]);
		}
		
		final JButton startBtn = new JButton("Click to PLay (player 1)");
		panel.add(startBtn);
		
		startBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] response;
				do {
					response = backend.start();
				} while(response == null);
				boxes[Integer.parseInt(response[1])].setText(response[0]);
				if (backend.checkForWin(((backend.player1sTurn)? 0 : 3)))
					JOptionPane.showMessageDialog(frame,
						    "Player " + ((backend.player1sTurn)? 2 : 1) + " Won!!");
				startBtn.setText("Click to Play (player " + ((backend.player1sTurn)? "1" : "2") + ")");
			}
		});
		
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startPanel.setVisible(false);;
				frame.getContentPane().add(panel);
			}
		});
	}

}
