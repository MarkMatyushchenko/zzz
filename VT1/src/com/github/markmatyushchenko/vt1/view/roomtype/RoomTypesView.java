package com.github.markmatyushchenko.vt1.view.roomtype;

import com.github.markmatyushchenko.vt1.entity.roomtype.RoomType;
import com.github.markmatyushchenko.vt1.service.roomtype.RoomTypeFilter;
import com.github.markmatyushchenko.vt1.service.roomtype.RoomTypeModel;
import com.github.markmatyushchenko.vt1.service.roomtype.port.RoomTypeViewModel;
import com.github.markmatyushchenko.vt1.view.CLIView;
import com.github.markmatyushchenko.vt1.view.Navigation;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class RoomTypesView implements CLIView, RoomTypeViewModel {

	private RoomTypeModel model;
	private Navigation navigation;

	private int recordsPerPage = 2;
	private int page = 1;
	private int totalCount = 0;

	public RoomTypesView(RoomTypeModel model, Navigation navigation) {
		this.model = model;
		this.navigation = navigation;
	}

	@Override
	public void render() {
		page = 1;
		totalCount = 0;
		Scanner in = new Scanner(System.in);
		int action = -1;

		while (action != 0) {
			System.out.println("┌─────────────────────────────────────────────────┐");
			System.out.println("│   Type Name   |  Cost  | Num of Places |  Area  │");
			System.out.println("└─────────────────────────────────────────────────┘");
			model.getRoomTypesOnPage(page, true);

			System.out.println("┌────────────────────────────────────┐");
			System.out.println("│      Please choice the action      │");
			System.out.println("├────────────────────────────────────┤");
			System.out.println("├─ 1 - Next page                     │");
			System.out.println("├─ 2 - Previous page                 │");
			System.out.println("├─ 3 - Refresh                       │");
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
			}
		}
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
	public Optional<RoomTypeFilter> getFilter() {
		return Optional.empty();
	}

	@Override
	public int getRecordsPerPage() {
		return recordsPerPage;
	}

	@Override
	public void setActualRoomTypes(List<RoomType> roomTypes) {
		roomTypes.forEach((roomType -> {
			System.out.printf("│ %-14s│ %-7d│ %-14d│ %-7.2f│\n",
					roomType.getTypeName(),
					roomType.getCost(),
					roomType.getNumOfPlaces(),
					roomType.getArea()
			);
		}));
		System.out.println("└─────────────────────────────────────────────────┘");
		System.out.println(" Show " + roomTypes.size() + " of " + totalCount);
	}
}
