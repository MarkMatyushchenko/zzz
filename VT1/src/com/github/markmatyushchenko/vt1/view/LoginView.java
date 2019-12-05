package com.github.markmatyushchenko.vt1.view;

import com.github.markmatyushchenko.vt1.service.auth.LoginModel;
import com.github.markmatyushchenko.vt1.service.auth.port.LoginViewModel;

import java.util.Scanner;

public class LoginView implements CLIView, LoginViewModel {

    private Navigation navigation;
    private LoginModel loginModel;

    public LoginView(LoginModel loginModel, Navigation navigation) {

        this.loginModel = loginModel;
        this.navigation = navigation;
    }

    @Override
    public void setLogin(String login) {

    }

    @Override
    public void setPassword(String password) {

    }

    @Override
    public void setLoginError(String error) {
        System.out.println(error);
    }

    @Override
    public void setPasswordError(String error) {
        System.out.println(error);
    }

    @Override
    public void render() {
        Scanner in = new Scanner(System.in);

        System.out.println("Введите  логин");
        String login = in.nextLine();

        System.out.println("Введите пароль");
        String password = in.nextLine();

        loginModel.login(login, password);

        //navigation.navigateTo("requestsView");
        while (true) {
            System.out.println("######################################");
            System.out.println("#      Please choice the action      #");
            System.out.println("######################################");
            System.out.println("# 1 - Show room types");
            System.out.println("# 2 - Show available rooms");
            System.out.println("# 3 - Requests");
            System.out.println("# 0 - Exit");
            System.out.println("######################################");
            String action = in.nextLine();
            switch (action) {
                case ("1"):
                    navigation.navigateTo("roomTypeView");
                    break;
                case ("2"):
                    System.out.println("availableRoomsView");
                    break;
                case ("3"):
                    navigation.navigateTo("requestsView");
                    break;
                case ("0"):
                    return;
            }
        }
    }
}