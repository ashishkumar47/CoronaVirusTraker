package com.springboot.coronavirustracker.models;

import java.io.Serializable;

public class LocationStats implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String state;
	private String country;
	private Integer latestTotalCases;
	private Integer diffFromPrevDay;
	public LocationStats() {
		super();
	}

	public LocationStats(String state, String country, Integer latestTotalCases, Integer diffFromPrevDay) {
		super();
		this.state = state;
		this.country = country;
		this.latestTotalCases = latestTotalCases;
		this.diffFromPrevDay = diffFromPrevDay;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Integer getLatestTotalCases() {
		return latestTotalCases;
	}
	public void setLatestTotalCases(Integer latestTotalCases) {
		this.latestTotalCases = latestTotalCases;
	}

	public Integer getDiffFromPrevDay() {
		return diffFromPrevDay;
	}

	public void setDiffFromPrevDay(Integer diffFromPrevDay) {
		this.diffFromPrevDay = diffFromPrevDay;
	}
	@Override
	public String toString() {
		return "LocationStats [state=" + state + ", country=" + country + ", latestTotalCases=" + latestTotalCases
				+ ", diffFromPrevDay=" + diffFromPrevDay + "]";
	}


}
