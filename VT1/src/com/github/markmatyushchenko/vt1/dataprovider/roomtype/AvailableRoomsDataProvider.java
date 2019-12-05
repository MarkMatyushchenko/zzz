package com.github.markmatyushchenko.vt1.dataprovider.roomtype;

import com.github.markmatyushchenko.vt1.service.port.roomtype.AvailableRoomTypesApi;
import com.github.markmatyushchenko.vt1.service.roomtype.AvailableRoomTypesFilter;
import com.github.markmatyushchenko.vt1.service.utils.Pagination;
import com.github.markmatyushchenko.vt1.dataprovider.AppDataProvider;
import com.github.markmatyushchenko.vt1.entity.roomtype.AvailableRoomTypes;
import com.github.markmatyushchenko.vt1.entity.roomtype.RoomType;
import com.github.markmatyushchenko.vt1.entity.user.User;
import com.github.markmatyushchenko.vt1.utils.Either;
import com.github.markmatyushchenko.vt1.utils.Pair;
import com.github.markmatyushchenko.vt1.utils.Range;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class AvailableRoomsDataProvider implements AvailableRoomTypesApi {

	private AppDataProvider dataProvider;
	private CSVRoomTypeSerializer roomTypeSerializer;
	private Path roomTypesPath;

	public AvailableRoomsDataProvider(Path roomTypePath, AppDataProvider parent) {
		this.roomTypesPath = roomTypePath;
		this.dataProvider = parent;
		roomTypeSerializer = new CSVRoomTypeSerializer();
	}

	@Override
	public Either<Pair<List<AvailableRoomTypes>, Pagination>, Exception> getAvailableRoomTypes(User user, Pagination pagination) {
		Either<Pair<List<RoomType>, Pagination>, Exception> roomTypes = dataProvider.getRoomTypes(user, pagination);
		if (roomTypes.isLeft()) {
			List<RoomType> list = roomTypes.getLeft().getFirst();
			Pagination pagination1 = roomTypes.getLeft().getSecond();
			List<AvailableRoomTypes> actualList = list.stream()
					.map(rt -> new AvailableRoomTypes(
							rt, (int) rt.getRoomNumbers().count())
					)
					.collect(Collectors.toList());
			return Either.left(new Pair<>(actualList, pagination1));
		} else return Either.right(roomTypes.getRight());
	}

	@Override
	public Either<Pair<List<AvailableRoomTypes>, Pagination>, Exception> getAvailableRoomTypes(User user, Pagination pagination, AvailableRoomTypesFilter filter) {
		try {
			List<String> totalList = Files.readAllLines(roomTypesPath.resolve("roomtypes.csv"));
			List<String> filterServices = filter.getServices().collect(Collectors.toList());
			List<RoomType> filteredResult = totalList.stream()
					.map(roomTypeSerializer::roomTypeFromString)
					.filter(Optional::isPresent)
					.map(Optional::get)
					.filter(rt -> filter.getTypeName().isEmpty() || rt.getTypeName().equals(filter.getTypeName().get()))
					.filter(rt -> filter.getNumOfPlaces().contains(rt.getNumOfPlaces()))
					.filter(rt -> filter.getCost().contains(rt.getCost()))
					.filter(rt -> filter.getArea().contains(rt.getArea()))
					.filter(rt -> filterServices.isEmpty() || rt.getServices().collect(Collectors.toList()).containsAll(filterServices))
					.collect(Collectors.toList());


			List<AvailableRoomTypes> actualResult = filteredResult.stream()
					.map(rt -> {
						int requestsCount = dataProvider.getRequestsDataProvider()
								.getConfirmedRequestsForRoomType(new Range<>(
										filter.getArrivalDate().orElse(null),
										filter.getDepartureDate().orElse(null)
								), rt.getTypeName()).orElse(Collections.emptyList()).size();
						return new AvailableRoomTypes(rt, (int) rt.getRoomNumbers().count() - requestsCount);
					})
					.collect(Collectors.toList());

			List<AvailableRoomTypes> subList = actualResult
					.subList(
							(pagination.getPage() - 1) * pagination.getRecordsPerPage(),
							(pagination.getPage() - 1) * pagination.getRecordsPerPage() +
									pagination.getRecordsPerPage() <= filteredResult.size() ?
									(pagination.getPage() - 1) * pagination.getRecordsPerPage() +
											pagination.getRecordsPerPage() : filteredResult.size()
					);
			return Either.left(new Pair(
					subList,
					new Pagination(pagination.getRecordsPerPage(), pagination.getPage(), actualResult.size())
			));
		} catch (IOException exc) {
			exc.printStackTrace();
			return Either.right(new Exception("Can't read storage"));
		}
	}
}
