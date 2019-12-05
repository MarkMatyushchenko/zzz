package com.github.markmatyushchenko.vt1.utils;

public class Either<L, R> {

	private L left;
	private R right;

	private Either(L left, R right) {
		this.left = left;
		this.right = right;
	}

	public L getLeft() {
		return left;
	}

	public R getRight() {
		return right;
	}

	public boolean isLeft() {
		return left != null;
	}

	public boolean isRight() {
		return right != null;
	}

	public static <L, R> Either left(L left) {
		return new Either<L, R>(left, null);
	}

	public static <L, R> Either right(R right) {
		return new Either<L, R>(null, right);
	}
}
