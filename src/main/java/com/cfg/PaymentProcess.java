package com.cfg;

public class PaymentProcess {
	
	private String templateID;
	
	private String emailSub;
	
	private String lob;
	
	private String statementMonth;
	
	private String statementPeriod;
	
	private String cardMemberName;
	
	private String statementBalance;
	
	private String minDue;
	
	private String paymentDueDate;
	
	private String channel;

	/**
	 * @return the templateID
	 */
	public String getTemplateID() {
		return templateID;
	}

	/**
	 * @param templateID the templateID to set
	 */
	public void setTemplateID(String templateID) {
		this.templateID = templateID;
	}

	/**
	 * @return the emailSub
	 */
	public String getEmailSub() {
		return emailSub;
	}

	/**
	 * @param emailSub the emailSub to set
	 */
	public void setEmailSub(String emailSub) {
		this.emailSub = emailSub;
	}

	/**
	 * @return the lob
	 */
	public String getLob() {
		return lob;
	}

	/**
	 * @param lob the lob to set
	 */
	public void setLob(String lob) {
		this.lob = lob;
	}

	/**
	 * @return the statementMonth
	 */
	public String getStatementMonth() {
		return statementMonth;
	}

	/**
	 * @param statementMonth the statementMonth to set
	 */
	public void setStatementMonth(String statementMonth) {
		this.statementMonth = statementMonth;
	}

	/**
	 * @return the statementPeriod
	 */
	public String getStatementPeriod() {
		return statementPeriod;
	}

	/**
	 * @param statementPeriod the statementPeriod to set
	 */
	public void setStatementPeriod(String statementPeriod) {
		this.statementPeriod = statementPeriod;
	}

	/**
	 * @return the cardMemberName
	 */
	public String getCardMemberName() {
		return cardMemberName;
	}

	/**
	 * @param cardMemberName the cardMemberName to set
	 */
	public void setCardMemberName(String cardMemberName) {
		this.cardMemberName = cardMemberName;
	}

	/**
	 * @return the statementBalance
	 */
	public String getStatementBalance() {
		return statementBalance;
	}

	/**
	 * @param statementBalance the statementBalance to set
	 */
	public void setStatementBalance(String statementBalance) {
		this.statementBalance = statementBalance;
	}

	/**
	 * @return the minDue
	 */
	public String getMinDue() {
		return minDue;
	}

	/**
	 * @param minDue the minDue to set
	 */
	public void setMinDue(String minDue) {
		this.minDue = minDue;
	}

	/**
	 * @return the paymentDueDate
	 */
	public String getPaymentDueDate() {
		return paymentDueDate;
	}

	/**
	 * @param paymentDueDate the paymentDueDate to set
	 */
	public void setPaymentDueDate(String paymentDueDate) {
		this.paymentDueDate = paymentDueDate;
	}

	
	/**
	 * @return the channel
	 */
	public String getChannel() {
		return channel;
	}

	/**
	 * @param channel the channel to set
	 */
	public void setChannel(String channel) {
		this.channel = channel;
	}

	@Override
	public String toString() {
		return "PaymentProcess [templateID=" + templateID + ", emailSub=" + emailSub + ", lob=" + lob
				+ ", statementMonth=" + statementMonth + ", statementPeriod=" + statementPeriod + ", cardMemberName="
				+ cardMemberName + ", statementBalance=" + statementBalance + ", minDue=" + minDue + ", paymentDueDate="
				+ paymentDueDate + ", channel=" + channel + "]";
	}

}