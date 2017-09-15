package br.com.cpqd.auth;

public class Result {
	private String decision;
	
	public Result(String decision) {
		this.decision = decision;
	}

	public String getDecision() {
		return decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}
}