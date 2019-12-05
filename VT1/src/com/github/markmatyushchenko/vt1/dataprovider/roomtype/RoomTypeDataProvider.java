package com.github.markmatyushchenko.vt1.dataprovider.roomtype;

import com.github.markmatyushchenko.vt1.service.port.roomtype.RoomTypeApi;
import com.github.markmatyushchenko.vt1.service.roomtype.RoomTypeFilter;
import com.github.markmatyushchenko.vt1.service.utils.Pagination;
import com.github.markmatyushchenko.vt1.entity.roomtype.RoomType;
import com.github.markmatyushchenko.vt1.entity.user.User;
import com.github.markmatyushchenko.vt1.utils.Either;
import com.github.markmatyushchenko.vt1.utils.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class RoomTypeDataProvider implements RoomTypeApi {

	private Path roomTypesPath;
	private CSVRoomTypeSerializer roomTypeSerializer;

	public RoomTypeDataProvider(Path roomTypesPath) {
		this.roomTypesPath = roomTypesPath;
		roomTypeSerializer = new CSVRoomTypeSerializer();
	}

	public Optional<RoomType> getRoomTypeByName(String name) {
		try {
			return Files.readAllLines(roomTypesPath.resolve("roomtypes.csv"))
					.stream()
					.map(roomTypeSerializer::roomTypeFromString)
					.filter(Optional::isPresent)
					.map(Optional::get)
					.filter(rt -> rt.getTypeName().equals(name))
					.findFirst();
		} catch (IOException exc) {
			return Optional.empty();
		}
	}

	@Override
	public Either<List<RoomType>, Exception> getAllRoomTypes(User user) {
		try {
			List<RoomType> result = Files.readAllLines(roomTypesPath.resolve("roomtypes.csv"))
					.stream()
					.map(roomTypeSerializer::roomTypeFromString)
					.filter(Optional::isPresent)
					.map(Optional::get)
					.collect(Collectors.toList());
			return Either.left(result);
		} catch (IOException exc) {
			return Either.right(new Exception("Can't read storage"));
		}
	}

	@Override
	public Either<Pair<List<RoomType>, Pagination>, Exception> getRoomTypes(User user, Pagination pagination) {
		return getRoomTypes(user, pagination, new RoomTypeFilter());
	}

	@Override
	public Either<Pair<List<RoomType>, Pagination>, Exception> getRoomTypes(User user, Pagination pagination, RoomTypeFilter filter) {
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

			List<RoomType> subList = filteredResult
					.subList(
							(pagination.getPage() - 1) * pagination.getRecordsPerPage(),
							(pagination.getPage() - 1) * pagination.getRecordsPerPage() +
									pagination.getRecordsPerPage() <= filteredResult.size() ?
									(pagination.getPage() - 1) * pagination.getRecordsPerPage() +
											pagination.getRecordsPerPage() : filteredResult.size()
					);
			return Either.left(new Pair(
					subList,
					new Pagination(pagination.getRecordsPerPage(), pagination.getPage(), filteredResult.size())
			));
		} catch (IOException exc) {
			return Either.right(new Exception("Can't read storage"));
		}
	}

	@Override
	public Either<List<String>, Exception> getAvailableServices(User user) {
		try {
			List<String> services =
					roomTypeSerializer.servicesFromString(
							Files.readAllLines(roomTypesPath.resolve("services.csv"))
									.get(0)
					).orElse(Collections.emptyList());
			return Either.left(services);
		} catch (IOException exc) {
			return Either.right(new Exception("Can't read storage"));
		}
	}
}
