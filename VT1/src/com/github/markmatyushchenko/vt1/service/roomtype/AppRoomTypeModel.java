package com.github.markmatyushchenko.vt1.service.roomtype;

import com.github.markmatyushchenko.vt1.service.roomtype.port.RoomTypeViewModel;
import com.github.markmatyushchenko.vt1.entity.roomtype.RoomType;
import com.github.markmatyushchenko.vt1.service.AppService;
import com.github.markmatyushchenko.vt1.service.utils.Pagination;
import com.github.markmatyushchenko.vt1.utils.Either;
import com.github.markmatyushchenko.vt1.utils.Pair;

import java.util.ArrayList;
import java.util.List;

public class AppRoomTypeModel implements RoomTypeModel {

	private AppService appService;
	private RoomTypeViewModel viewModel;

	private List<RoomType> allAvailableRoomTypes;

	public AppRoomTypeModel(AppService appService) {
		this.appService = appService;
		allAvailableRoomTypes = new ArrayList<>();
	}

	@Override
	public void addViewModel(RoomTypeViewModel viewModel) {
		this.viewModel = viewModel;
	}

	@Override
	public void getRoomTypesOnPage(int page, boolean useCache) {
		int offset = (page - 1) * viewModel.getRecordsPerPage();
		int count = viewModel.getRecordsPerPage();
		if (useCache && offset + count <= allAvailableRoomTypes.size()) {
			viewModel.setActualRoomTypes(allAvailableRoomTypes.subList(offset, offset + count));
		} else {
			loadRoomTypesOnPage(page);
		}
		viewModel.setPage(page);
	}

	@Override
	public void getAvailableServices() {
		appService.getAccountModel().getAccount()
				.ifPresent(user -> {
					Either<List<String>, Exception> response =
							appService.getDataProvider().getAvailableServices(user);
					if (response.isRight()) {
						appService.getViewModel().setError(response.getRight().getMessage());
					} else {
						viewModel.setAvailableServices(response.getLeft());
					}
				});
	}

	private void loadRoomTypesOnPage(int page) {
		appService.getAccountModel().getAccount()
				.ifPresent(user -> {
					int offset = (page - 1) * viewModel.getRecordsPerPage();
					Either<Pair<List<RoomType>, Pagination>, Exception> newRoomTypesResponse =
							viewModel.getFilter().isPresent() ?
									appService.getDataProvider().getRoomTypes(user,
											new Pagination(viewModel.getRecordsPerPage(), page),
											viewModel.getFilter().get()) :
									appService.getDataProvider().getRoomTypes(user,
											new Pagination(viewModel.getRecordsPerPage(), page));
					if (newRoomTypesResponse.isRight()) {

						var exceptionMessage = newRoomTypesResponse.getRight().getMessage();
						System.out.println(exceptionMessage);
						appService.getViewModel().setError(exceptionMessage);
					} else {
						var list = newRoomTypesResponse.getLeft().getFirst();
						var pagination = newRoomTypesResponse.getLeft().getSecond();

						int rewrited = 0;
						for (int i = offset; i < allAvailableRoomTypes.size() && rewrited < list.size(); i++) {
							allAvailableRoomTypes.set(i, list.get(rewrited));
							rewrited++;
						}

						for (int i = rewrited; i < list.size(); i++) {
							allAvailableRoomTypes.add(list.get(i));
						}

						//System.out.println(list);
						viewModel.setActualRoomTypes(list);
						viewModel.setPage(pagination.getPage());
						viewModel.setTotalCount(pagination.getTotalCount());
					}
				});
	}
}
