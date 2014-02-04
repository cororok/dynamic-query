package examples;

import java.sql.Timestamp;

/**
 * QueryUtil access member variables including private ones directly. But it
 * does not use methods.
 * 
 * @author songduk.park cororok@gmail.com
 * 
 */
public class User extends BasicUser {

	private double amount;
	private String emailAddress; // column in a table should be 'email_address'
	private Timestamp created;
	private String memo;

	public Double getAmount() {
		return amount;
	}

	public Timestamp getCreated() {
		return created;
	}

	String getEmailAddress() {
		return emailAddress;
	}

	public String getMemo() {
		return memo;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String toString() {
		return "id=" + getId() + ", userName=" + getUserName() + ", amount="
				+ amount + ", memo=" + memo + ", created=" + created;
	}
}
