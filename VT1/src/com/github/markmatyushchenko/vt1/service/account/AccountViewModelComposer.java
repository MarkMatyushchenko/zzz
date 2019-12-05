package com.github.markmatyushchenko.vt1.service.account;

import com.github.markmatyushchenko.vt1.service.account.port.AccountViewModel;
import com.github.markmatyushchenko.vt1.entity.user.User;
import com.github.markmatyushchenko.vt1.service.utils.ViewModelComposer;

import java.util.Optional;

public class AccountViewModelComposer extends ViewModelComposer<AccountViewModel> implements AccountViewModel {

	@Override
	public void setAccount(User account) {
		getViewModels().forEach(viewModel -> viewModel.setAccount(account));
	}

	@Override
	public Optional<User> getAccount() {
		return getViewModels()
				.findAny()
				.map(AccountViewModel::getAccount)
				.get();
	}
}
