package seven.g0;

import java.util.ArrayList;
import java.io.*;

import seven.ui.Letter;
import seven.ui.Player;
import seven.ui.PlayerBids;
import seven.ui.SecretState;

public class StingyPlayer implements Player {


	static final Word[] wordlist;
	// my comment!
	static {
		BufferedReader r;
		String line = null;
		ArrayList<Word> wtmp = new ArrayList<Word>(55000);
		try {
			r = new BufferedReader(new FileReader("src/seven/g1/super-small-wordlist.txt"));
			while (null != (line = r.readLine())) {
				wtmp.add(new Word(line.trim()));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		wordlist = wtmp.toArray(new Word[wtmp.size()]);
	}

	ArrayList<Character> currentLetters;
	private int ourID;
	private ArrayList<PlayerBids> cachedBids;

	public int Bid(Letter bidLetter, ArrayList<PlayerBids> PlayerBidList,
			int total_rounds, ArrayList<String> PlayerList,
			SecretState secretstate, int PlayerID) {
		if (PlayerBidList.isEmpty()) {
			cachedBids = PlayerBidList;
		}

		if (null == currentLetters) {
			currentLetters = new ArrayList<Character>(7);
			ourID = PlayerID;
			for (Letter l : secretstate.getSecretLetters()) {
				currentLetters.add(l.getAlphabet());
			}
		} else {
			if (cachedBids.size() > 0) {
				checkBid(cachedBids.get(cachedBids.size() - 1));
			}
		}

		return 0;
	}

	private void checkBid(PlayerBids b) {
		if (ourID == b.getWinnerID()) {
			currentLetters.add(b.getTargetLetter().getAlphabet());
		}
	}

	public void Register() {
		// no-op
	}

	public String returnWord() {
		checkBid(cachedBids.get(cachedBids.size() - 1));
		char c[] = new char[7];
		for (int i = 0; i < 7; i++) {
			c[i] = currentLetters.get(i);
		}
		String s = new String(c);
		Word ourletters = new Word(s);
		Word bestword = new Word("");
		for (Word w : wordlist) {
			if (ourletters.contains(w)) {
				if (w.score > bestword.score) {
					bestword = w;
				}

			}
		}
		currentLetters = null;
		return bestword.word;
	}

}
