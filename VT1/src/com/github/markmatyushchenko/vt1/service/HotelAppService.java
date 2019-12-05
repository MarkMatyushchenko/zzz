package com.github.markmatyushchenko.vt1.service;

import com.github.markmatyushchenko.vt1.service.account.AccountModel;
import com.github.markmatyushchenko.vt1.service.account.AppAccountModel;
import com.github.markmatyushchenko.vt1.service.auth.AppLoginModel;
import com.github.markmatyushchenko.vt1.service.auth.AppRegisterModel;
import com.github.markmatyushchenko.vt1.service.auth.LoginModel;
import com.github.markmatyushchenko.vt1.service.auth.RegisterModel;
import com.github.markmatyushchenko.vt1.service.port.DataProvider;
import com.github.markmatyushchenko.vt1.service.request.AdminRequestsModel;
import com.github.markmatyushchenko.vt1.service.request.AppAdminRequestsModel;
import com.github.markmatyushchenko.vt1.service.request.AppClientRequestsModel;
import com.github.markmatyushchenko.vt1.service.request.ClientRequestsModel;
import com.github.markmatyushchenko.vt1.service.roomtype.AppAvailableRoomTypesModel;
import com.github.markmatyushchenko.vt1.service.roomtype.AppRoomTypeModel;
import com.github.markmatyushchenko.vt1.service.roomtype.AvailableRoomTypesModel;
import com.github.markmatyushchenko.vt1.service.roomtype.RoomTypeModel;

public class HotelAppService implements AppService {

	private AccountModel accountModel;
	private LoginModel loginModel;
	private RegisterModel registerModel;
	private ClientRequestsModel clientRequestsModel;
	private AdminRequestsModel adminRequestsModel;
	private RoomTypeModel roomTypeModel;
	private AvailableRoomTypesModel availableRoomTypesModel;

	private DataProvider dataProvider;
	private AppViewModel appViewModel;

	public HotelAppService(DataProvider dataProvider, AppViewModel appViewModel) {
		this.dataProvider = dataProvider;
		this.appViewModel = appViewModel;

		accountModel = new AppAccountModel(this);
		loginModel = new AppLoginModel(this);
		registerModel = new AppRegisterModel(this);
		clientRequestsModel = new AppClientRequestsModel(this);
		adminRequestsModel = new AppAdminRequestsModel(this);
		roomTypeModel = new AppRoomTypeModel(this);
		availableRoomTypesModel = new AppAvailableRoomTypesModel(this);
	}

	@Override
	public AccountModel getAccountModel() {
		return accountModel;
	}

	@Override
	public LoginModel getLoginModel() {
		return loginModel;
	}

	@Override
	public RegisterModel getRegisterModel() {
		return registerModel;
	}

	@Override
	public RoomTypeModel getRoomTypeModel() {
		return roomTypeModel;
	}

	@Override
	public AvailableRoomTypesModel getAvailableRoomTypesModel() {
		return availableRoomTypesModel;
	}

	@Override
	public ClientRequestsModel getClientRequestsModel() {
		return clientRequestsModel;
	}

	@Override
	public AdminRequestsModel getAdminRequestsModel() {
		return adminRequestsModel;
	}

	@Override
	public DataProvider getDataProvider() {
		return dataProvider;
	}

	@Override
	public AppViewModel getViewModel() {
		return appViewModel;
	}
}
