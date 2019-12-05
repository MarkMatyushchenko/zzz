package com.github.markmatyushchenko.vt1.view;

import com.github.markmatyushchenko.vt1.entity.user.Administrator;
import com.github.markmatyushchenko.vt1.entity.user.Client;
import com.github.markmatyushchenko.vt1.entity.user.User;
import com.github.markmatyushchenko.vt1.service.account.AccountModel;
import com.github.markmatyushchenko.vt1.service.account.port.AccountViewModel;

import java.util.Optional;

public class ProfileView implements AccountViewModel,CLIView {


    private AccountModel accountModel;
    private  User user;

    public ProfileView(AccountModel accountModel)
    {
        this.accountModel = accountModel;
    }

    @Override
    public void setAccount(User account) {

        user = account;
        getAccount();
    }

    @Override
    public Optional<User> getAccount() {
        return Optional.of(user);
    }

    @Override
    public void render() {
    }
}
