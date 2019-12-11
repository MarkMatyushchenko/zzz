package com.github.markmatyushchenko.vt1.service.roomtype;

import com.github.markmatyushchenko.vt1.entity.roomtype.AvailableRoomTypes;
import com.github.markmatyushchenko.vt1.service.AppService;
import com.github.markmatyushchenko.vt1.service.roomtype.port.AvailableRoomsViewModel;
import com.github.markmatyushchenko.vt1.service.utils.Pagination;
import com.github.markmatyushchenko.vt1.utils.Either;
import com.github.markmatyushchenko.vt1.utils.Pair;

import java.util.ArrayList;
import java.util.List;

public class AppAvailableRoomTypesModel implements AvailableRoomTypesModel {

	private AppService appService;
	private AvailableRoomsViewModel viewModel;

	private List<AvailableRoomTypes> allRoomTypes;

	public AppAvailableRoomTypesModel(AppService appService) {
		this.appService = appService;
		allRoomTypes = new ArrayList<>();
	}

	@Override
	public void addViewModel(AvailableRoomsViewModel viewModel) {
		this.viewModel = viewModel;
	}

	@Override
	public void getRoomTypesOnPage(int page, boolean useCache) {
		int offset = (page - 1) * viewModel.getRecordsPerPage();
		int count = viewModel.getRecordsPerPage();
		if (useCache && offset + count <= allRoomTypes.size()) {
			viewModel.setActualAvailableRoomTypes(allRoomTypes.subList(offset, offset + count));
		} else {
			loadRoomTypesOnPage(page);
		}
		viewModel.setPage(page);
	}

	@Override
	public AvailableRoomTypes getSelectedRoomType() {
		return viewModel.getSelectedRoomType();
	}

	private void loadRoomTypesOnPage(int page) {
		appService.getAccountModel().getAccount()
				.ifPresent(user -> {
					int offset = (page - 1) * viewModel.getRecordsPerPage();
					Either<Pair<List<AvailableRoomTypes>, Pagination>, Exception> newRoomTypes =
							viewModel.getFilter().isPresent() ?
									appService.getDataProvider().getAvailableRoomTypes(user,
											new Pagination(viewModel.getRecordsPerPage(), page),
											viewModel.getFilter().get()) :
									appService.getDataProvider().getAvailableRoomTypes(user,
											new Pagination(viewModel.getRecordsPerPage(), page));
					if (newRoomTypes.isRight()) {
						var exceptionMessage = newRoomTypes.getRight().getMessage();
						appService.getViewModel().setError(exceptionMessage);
					} else {
						var list = newRoomTypes.getLeft().getFirst();
						var pagination = newRoomTypes.getLeft().getSecond();

						int rewrited = 0;
						for (int i = offset; i < allRoomTypes.size() && rewrited < list.size(); i++) {
							allRoomTypes.set(i, list.get(rewrited));
							rewrited++;
						}

						for (int i = rewrited; i < list.size(); i++) {
							allRoomTypes.add(list.get(i));
						}

						viewModel.setPage(pagination.getPage());
						viewModel.setTotalCount(pagination.getTotalCount());
						viewModel.setActualAvailableRoomTypes(list);
					}
				});
	}
}
