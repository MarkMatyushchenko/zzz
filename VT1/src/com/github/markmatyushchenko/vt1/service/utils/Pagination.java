package com.github.markmatyushchenko.vt1.service.utils;

public class Pagination {

	private int recordsPerPage;
	private int page;
	private int totalCount;

	public Pagination(int recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
		this.page = 0;
		this.totalCount = 0;
	}

	public Pagination(int recordsPerPage, int page) {
		this.recordsPerPage = recordsPerPage;
		this.page = page;
		this.totalCount = 0;
	}

	public Pagination(int recordsPerPage, int page, int totalCount) {
		this.recordsPerPage = recordsPerPage;
		this.page = page;
		this.totalCount = totalCount;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public int getTotalPages() {
		return (int) Math.ceil((double) totalCount / recordsPerPage);
	}

	public int getRecordsPerPage() {
		return recordsPerPage;
	}

	public int getPage() {
		return page;
	}

	@Override
	public String toString() {
		return String.format("Pagination(page=%d, recordsPerPage=%d, totalCount=%d)", page, recordsPerPage, totalCount);
	}
}
