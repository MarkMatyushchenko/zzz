package com.github.markmatyushchenko.vt1.service;

import com.github.markmatyushchenko.vt1.service.account.AccountModel;
import com.github.markmatyushchenko.vt1.service.auth.LoginModel;
import com.github.markmatyushchenko.vt1.service.auth.RegisterModel;
import com.github.markmatyushchenko.vt1.service.port.DataProvider;
import com.github.markmatyushchenko.vt1.service.request.AdminRequestsModel;
import com.github.markmatyushchenko.vt1.service.request.ClientRequestsModel;
import com.github.markmatyushchenko.vt1.service.roomtype.AvailableRoomTypesModel;
import com.github.markmatyushchenko.vt1.service.roomtype.RoomTypeModel;

public interface AppService {

	DataProvider getDataProvider();
	AppViewModel getViewModel();

	AccountModel getAccountModel();
	LoginModel getLoginModel();
	RegisterModel getRegisterModel();
	RoomTypeModel getRoomTypeModel();
	AvailableRoomTypesModel getAvailableRoomTypesModel();
	ClientRequestsModel getClientRequestsModel();
	AdminRequestsModel getAdminRequestsModel();
}
