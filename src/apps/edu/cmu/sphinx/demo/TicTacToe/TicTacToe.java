/*
 * Copyright 1999-2004 Carnegie Mellon University.
 * Portions Copyright 2004 Sun Microsystems, Inc.
 * Portions Copyright 2004 Mitsubishi Electric Research Laboratories.
 * All Rights Reserved.  Use is subject to license terms.
 *
 * See the file "license.terms" for information on usage and
 * redistribution of this file, and for a DISCLAIMER OF ALL
 * WARRANTIES.
 *
 */

package edu.cmu.sphinx.demo.TicTacToe;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;


public class TicTacToe {

	Recognizer recognizer;
	Microphone mic;
	ConfigurationManager cm;
	
	int[] board = new int[]{-3, -3, -3,
							-3, -3, -3,
							-3, -3, -3};
	
	boolean player1sTurn = true;	// if false, then it's player2's turn
	static final String PLAYER1 = "x";
	static final String PLAYER2 = "o";
	
	public TicTacToe() {
		cm = new ConfigurationManager(TicTacToe.class.getResource("TicTacToe.config.xml"));
        recognizer = (Recognizer) cm.lookup("recognizer");
        recognizer.allocate();
	}
	
	public String[] start() {
		// start the microphone or exit if the program if this is not possible
        mic = (Microphone) cm.lookup("microphone");
        mic.clear();
		if (!mic.startRecording()) {
            System.out.println("Cannot start microphone.");
            recognizer.deallocate();
            System.exit(1);
        }
		String resultText;
		String[] test;
		do {
			System.out.println("Start speaking.");
			Result result = recognizer.recognize();
			System.out.println("recognizer: " + result);
			resultText = result.getBestFinalResultNoFiller();
			test = parse(resultText);
		} while (test == null);
		
		mic.stopRecording();
		return test;
	}

	private String[] parse(String input) {
		Matcher matcher = Pattern.compile("(x|o) at ([0-8])").matcher(input);
		if (matcher.find()) {
			System.out.println(board.length);
			if (board[Integer.parseInt(matcher.group(2))] < 0) {	// was not set by either player before
				if (player1sTurn) {
					if (matcher.group(1).equals(PLAYER1)) {
						board[Integer.parseInt(matcher.group(2))] = 1;	// x is denoted by a 1
						player1sTurn = !player1sTurn;
						System.out.println(board.toString());
						return new String[]{PLAYER1, matcher.group(2)};
					}
				}
				else {
					if (matcher.group(1).equals(PLAYER2)) {
						board[Integer.parseInt(matcher.group(2))] = 0;	// o is denoted by a 0
						player1sTurn = !player1sTurn;
						System.out.println(board.toString());
						return new String[]{PLAYER2, matcher.group(2)};
					}
				}
			}
		}
		return null;
	}
	
	public boolean checkForWin(int winningVaue) {
		if (board[0] + board[1] + board[2] == winningVaue) return true;
		if (board[3] + board[4] + board[5] == winningVaue) return true;
		if (board[6] + board[7] + board[8] == winningVaue) return true;
		
		if (board[0] + board[3] + board[6] == winningVaue) return true;
		if (board[1] + board[4] + board[7] == winningVaue) return true;
		if (board[2] + board[5] + board[8] == winningVaue) return true;
		
		if (board[0] + board[4] + board[8] == winningVaue) return true;
		if (board[2] + board[4] + board[6] == winningVaue) return true;
		
		return false;
	}
	
}
