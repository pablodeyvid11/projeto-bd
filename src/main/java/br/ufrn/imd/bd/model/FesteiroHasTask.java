package br.ufrn.imd.bd.model;

public class FesteiroHasTask {
	private Long taskId;
	private Long festeiroId;
	private Double pointsWin;
	private Boolean isValidated;

	public FesteiroHasTask() {
	}

	public FesteiroHasTask(Long taskId, Long festeiroId, Double pointsWin, Boolean isValidated) {
		this.taskId = taskId;
		this.festeiroId = festeiroId;
		this.pointsWin = pointsWin;
		this.isValidated = isValidated;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Long getFesteiroId() {
		return festeiroId;
	}

	public void setFesteiroId(Long festeiroId) {
		this.festeiroId = festeiroId;
	}

	public Double getPointsWin() {
		return pointsWin;
	}

	public void setPointsWin(Double pointsWin) {
		this.pointsWin = pointsWin;
	}

	public Boolean getIsValidated() {
		return isValidated;
	}

	public void setIsValidated(Boolean isValidated) {
		this.isValidated = isValidated;
	}

}
