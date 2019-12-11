package com.github.markmatyushchenko.vt1.view.roomtype;

import com.github.markmatyushchenko.vt1.entity.roomtype.AvailableRoomTypes;
import com.github.markmatyushchenko.vt1.service.roomtype.AvailableRoomTypesFilter;
import com.github.markmatyushchenko.vt1.service.roomtype.AvailableRoomTypesModel;
import com.github.markmatyushchenko.vt1.service.roomtype.port.AvailableRoomsViewModel;
import com.github.markmatyushchenko.vt1.view.CLIView;
import com.github.markmatyushchenko.vt1.view.Navigation;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class AvailableRoomsView implements CLIView, AvailableRoomsViewModel {

	private AvailableRoomTypesModel availableRoomTypesModel;
	private Navigation navigation;

	private int recordsPerPage = 2;
	private int page = 1;
	private int totalCount = 0;

	private AvailableRoomTypes selectedRoomTypes;
	private List<AvailableRoomTypes> actualRoomTypes;

	private AvailableRoomTypesFilter filter;

	public AvailableRoomsView(AvailableRoomTypesModel availableRoomTypesModel, Navigation navigation) {
		this.availableRoomTypesModel = availableRoomTypesModel;
		this.navigation = navigation;
		filter = new AvailableRoomTypesFilter();
		actualRoomTypes = Collections.emptyList();
	}

	@Override
	public void setPage(int page) {
		this.page = page;
	}

	@Override
	public void setTotalCount(int count) {
		this.totalCount = count;
	}

	@Override
	public Optional<AvailableRoomTypesFilter> getFilter() {
		return Optional.of(filter);
	}

	@Override
	public int getRecordsPerPage() {
		return recordsPerPage;
	}

	@Override
	public AvailableRoomTypes getSelectedRoomType() {
		return selectedRoomTypes;
	}

	@Override
	public void setActualAvailableRoomTypes(List<AvailableRoomTypes> roomTypes) {
		actualRoomTypes = roomTypes;
		for (int i = 0; i < roomTypes.size(); i++) {
			System.out.printf("│ %-3d│ %-14s│ %-10d│ %-7d│ %-14d│ %-7.2f│\n",
					i + 1,
					roomTypes.get(i).getTypeName(),
					roomTypes.get(i).getAvailableRooms(),
					roomTypes.get(i).getCost(),
					roomTypes.get(i).getNumOfPlaces(),
					roomTypes.get(i).getArea()
			);
		}
		System.out.println("└──────────────────────────────────────────────────────────────────┘");
		System.out.println(" Show " + roomTypes.size() + " of " + totalCount);
	}

	@Override
	public void render() {
		page = 1;
		totalCount = 0;
		Scanner in = new Scanner(System.in);
		int action = -1;

		while (action != 0) {
			System.out.println("┌──────────────────────────────────────────────────────────────────┐");
			System.out.println("│    │   Type Name   | Available |  Cost  | Num of Places |  Area  │");
			System.out.println("└──────────────────────────────────────────────────────────────────┘");
			availableRoomTypesModel.getRoomTypesOnPage(page, false);

			System.out.println("┌────────────────────────────────────┐");
			System.out.println("│      Please choice the action      │");
			System.out.println("├────────────────────────────────────┤");
			System.out.println("├─ 1 - Next page                     │");
			System.out.println("├─ 2 - Previous page                 │");
			System.out.println("├─ 3 - Refresh                       │");
			System.out.println("├─ 4 - Add filter                    │");
			System.out.println("├─ 5 - Make request                  │");
			System.out.println("├─ 0 - Back to main menu             │");
			System.out.println("└────────────────────────────────────┘");
			System.out.print("↪ ");

			action = in.nextInt();

			switch (action) {
				case 1:
					if (page < Math.ceil(((double) totalCount) / recordsPerPage)) {
						page++;
					}
					break;
				case 2:
					if (page > 1) {
						page--;
					}
					break;
				case 3:
					break;
				case 4:
					var filterView = new RoomsFilterView();
					filterView.render();
					filter.setArrivalDate(filterView.getFromDate());
					filter.setDepartureDate(filterView.getToDate());
					break;
				case 5:
					System.out.println("Input room type index:");
					int index = in.nextInt();
					try {
						selectedRoomTypes = actualRoomTypes.get(index - 1);
					} catch (IndexOutOfBoundsException exc) {
						System.out.println("Index out of bounds");
					}
					navigation.navigateTo(Navigation.Route.NEW_REQUEST);
					break;
			}
		}
	}
}
