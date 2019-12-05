package com.github.markmatyushchenko.vt1.view;

import com.github.markmatyushchenko.vt1.service.auth.RegisterModel;
import com.github.markmatyushchenko.vt1.service.auth.port.RegisterViewModel;

import java.util.Scanner;

public class RegisterView implements CLIView, RegisterViewModel {

    private RegisterModel registerModel;
    private Navigation navigation;

    public RegisterView(RegisterModel registerModel, Navigation navigation)
    {
        this.registerModel = registerModel;
        this.navigation = navigation;
    }

    @Override
    public void setLogin(String login) {

    }

    @Override
    public void setLoginError(String loginError) {
        System.out.println("Ошибка");
    }

    @Override
    public void setPassword(String password) {

    }

    @Override
    public void setEmail(String email) {

    }

    @Override
    public void setFirstName(String firstName) {

    }

    @Override
    public void setLastName(String lastName) {

    }

    @Override
    public void setPhoneNumber(String phoneNumber) {

    }

    @Override
    public void render() {
        Scanner in = new Scanner(System.in);

        System.out.println("Input login");
        String login = in.nextLine();
        System.out.println("Input password");
        String password = in.nextLine();
        System.out.println("Email");
        String email = in.nextLine();
        System.out.println("First name");
        String name = in.nextLine();
        System.out.println("Last name");
        String lastName = in.nextLine();
        System.out.println("Phone number");
        String phoneNumber = in.nextLine();


        registerModel.register(login,password,email,name,lastName,phoneNumber);


        while (true) {
            System.out.println("######################################");
            System.out.println("#      Please choice the action      #");
            System.out.println("######################################");
            System.out.println("# 1 - Show room types");
            System.out.println("# 2 - Show available rooms");
            System.out.println("# 3 - ????");
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
                    System.out.println("requestsView");
                    break;
                case ("0"):
                    return;
            }
        }
    }
}
