package com.github.markmatyushchenko.vt1.service.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public abstract class ViewModelComposer<ViewModel> {

	private List<ViewModel> viewModels;

	public ViewModelComposer() {
		viewModels = new ArrayList<>();
	}

	public Stream<ViewModel> getViewModels() {
		return viewModels.stream();
	}

	public void addViewModel(ViewModel viewModel) {
		viewModels.add(viewModel);
	}
}
