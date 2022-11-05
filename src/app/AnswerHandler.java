package app;

/**
 * This class takes in a string of '0's and '1's given by yes or no answers to survey questions.
 * It then creates a weight total to determine likelihood of COVID
 */

public class AnswerHandler {

	/* String of '1' and '0' based of answers given to question in survey */
	private String answerString;
	/* Number of yes answers used to determine likelihood of COVID */
	private int answerWeight;

	/**
	 * Takes in string value of yes and no answers and creates a weight total for likelihood of COVID
	 * 
	 * @param answerString
	 */
	public AnswerHandler(String answerString) {
		this.answerString = answerString;
		this.answerWeight = 0;
		for (int i = 0; i < answerString.length(); i++) {
			if (answerString.charAt(i) == '1') {
				answerWeight++;
			}
		}
	}
	
	public String getLikelihood() {
		switch(answerWeight) {
		case 0:
		case 1:
		case 2:
		case 3:
			return "Unlikely";
		case 4:
		case 5:
		case 6:
			return "Likely";
		case 7:
		case 8:
		case 9:
		case 10:
			return "Confirmed";
		default:
			return "Inconclusive";
		}
	}

	/**
	 * @return Weight total for likelihood of COVID
	 */
	public int getAnswerWeight() {
		return answerWeight;
	}

	/**
	 * @return String used to determine weight
	 */
	public String getAnswerString() {
		return answerString;
	}
}