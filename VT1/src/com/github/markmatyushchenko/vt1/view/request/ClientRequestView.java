package com.github.markmatyushchenko.vt1.view.request;

import com.github.markmatyushchenko.vt1.entity.request.ConfirmedRequest;
import com.github.markmatyushchenko.vt1.entity.request.RejectedRequest;
import com.github.markmatyushchenko.vt1.entity.request.Request;
import com.github.markmatyushchenko.vt1.service.request.ClientRequestsModel;
import com.github.markmatyushchenko.vt1.service.request.RequestsFilter;
import com.github.markmatyushchenko.vt1.service.request.port.RequestsViewModel;
import com.github.markmatyushchenko.vt1.view.CLIView;
import com.github.markmatyushchenko.vt1.view.Navigation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ClientRequestView implements CLIView, RequestsViewModel {

	private ClientRequestsModel clientRequestsModel;
	private Navigation navigation;

	private int recordsPerPage = 2;
	private int page = 1;
	private int totalCount = 0;

	private List<Request> actualRequests;
	private DateFormat dateFormat;

	public ClientRequestView(ClientRequestsModel clientRequestsModel, Navigation navigation) {
		this.clientRequestsModel = clientRequestsModel;
		this.navigation = navigation;

		actualRequests = Collections.emptyList();
		dateFormat = new SimpleDateFormat("dd.MM.yyyy");
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
	public Optional<RequestsFilter> getFilter() {
		return Optional.empty();
	}

	@Override
	public int getRecordsPerPage() {
		return recordsPerPage;
	}

	@Override
	public void setActualRequests(List<Request> requests) {
		actualRequests = requests;
		for (int i = 0; i < requests.size(); i++) {
			String status = "Processing";
			if (requests.get(i) instanceof RejectedRequest) {
				status = "Rejected";
			} else if (requests.get(i) instanceof ConfirmedRequest) {
				status = "Confirmed";
			}
			System.out.printf("│ %-3d│ %-15s│ %-12s│ %-12s│ %-8d│ %-11s│\n",
					i + 1,
					requests.get(i).getRoomType().getTypeName(),
					dateFormat.format(requests.get(i).getArrivalDate()),
					dateFormat.format(requests.get(i).getDepartureDate()),
					requests.get(i).getNumberOfPersons(),
					status
			);
		}
		System.out.println("└────────────────────────────────────────────────────────────────────────┘");
		System.out.println(" Show " + requests.size() + " of " + totalCount);
	}

	@Override
	public void render() {
		page = 1;
		totalCount = 0;
		Scanner in = new Scanner(System.in);
		int action = -1;

		while (action != 0) {
			System.out.println("┌────────────────────────────────────────────────────────────────────────┐");
			System.out.println("│    │ Room Type Name │   Arrival   │  Departure  │ Persons │   Status   │");
			System.out.println("└────────────────────────────────────────────────────────────────────────┘");
			clientRequestsModel.getRequestsOnPage(page, true);

			System.out.println("┌────────────────────────────────────┐");
			System.out.println("│      Please choice the action      │");
			System.out.println("├────────────────────────────────────┤");
			System.out.println("├─ 1 - Next page                     │");
			System.out.println("├─ 2 - Previous page                 │");
			System.out.println("├─ 3 - Refresh                       │");
			System.out.println("├─ 4 - Cancel request                │");
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
					System.out.println("Input request index:");
					int index = in.nextInt();
					try {
						Optional<Request> result =
								clientRequestsModel.cancelRequest(actualRequests.get(index - 1));
						if (result.isPresent()) {
							System.out.println("Successfully deleted request for " + result.get().getRoomType().getTypeName());
						} else {
							System.out.println("Can't delete request");
						}
					} catch (IndexOutOfBoundsException exc) {
						System.out.println("Index out of bounds");
					}
					break;
			}
		}
	}
}
