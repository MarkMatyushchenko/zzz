package com.github.markmatyushchenko.vt1.repository.jaxbrepository;

import com.github.markmatyushchenko.vt1.repository.DAO;
import com.github.markmatyushchenko.vt1.entity.request.ConfirmedRequest;
import com.github.markmatyushchenko.vt1.entity.request.RejectedRequest;
import com.github.markmatyushchenko.vt1.entity.request.Request;
import com.github.markmatyushchenko.vt1.entity.roomtype.AvailableRoomTypes;
import com.github.markmatyushchenko.vt1.entity.roomtype.RoomType;
import com.github.markmatyushchenko.vt1.entity.user.Administrator;
import com.github.markmatyushchenko.vt1.entity.user.Client;
import com.github.markmatyushchenko.vt1.entity.user.User;
import com.github.markmatyushchenko.vt1.repository.jaxbrepository.adapter.*;
import com.github.markmatyushchenko.vt1.repository.jaxbrepository.adapter.entity.*;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class XMLDAO<T> implements DAO<T> {

	private Marshaller marshaller;
	private Unmarshaller unmarshaller;
	private File file;
	private File validationFile;

	public XMLDAO(File file, File validationFile) {
		this.file = file;
		this.validationFile = validationFile;
		XMLConfigurationManager configurationManager = XMLConfigurationManager.getInstance();
		marshaller = configurationManager.getMarshaller(validationFile);
		unmarshaller = configurationManager.getUnmarshaller(validationFile);
	}

	public XMLDAO(File file) {
		this.file = file;
		XMLConfigurationManager configurationManager = XMLConfigurationManager.getInstance();
		marshaller = configurationManager.getMarshaller();
		unmarshaller = configurationManager.getUnmarshaller();
	}

	@Override
	public void save(Iterable<T> objects) {
		try {
			XMLObjectsList list = new XMLObjectsList();
			list.setList(StreamSupport.stream(objects.spliterator(), false)
					.map(this::convertToXml)
					.collect(Collectors.toList()));
			marshaller.marshal(list, file);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Iterable<T> read() {
		try {
			XMLObjectsList list = (XMLObjectsList) unmarshaller.unmarshal(file);
			return (Iterable<T>) list
					.getList()
					.stream()
					.map(this::convertToObjects)
					.collect(Collectors.toList());
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}

	private Object convertToXml(Object o) {
		if (o instanceof Client) {
			return XMLClientAdapter.toXml((Client) o);
		} else if (o instanceof Administrator) {
			return XMLAdministratorAdapter.toXml((Administrator) o);
		} else if (o instanceof User) {
			return XMLUserAdapter.toXml((User) o);
		} else if (o instanceof AvailableRoomTypes) {
			return XMLAvailableRoomTypeAdapter.toXmlRoomType((AvailableRoomTypes) o);
		} else if (o instanceof RoomType) {
			return XMLRoomTypeAdapter.toXmlRoomType((RoomType) o);
		} else if (o instanceof ConfirmedRequest) {
			return XMLConfirmedRequestAdapter.toXmlRequest((ConfirmedRequest) o);
		} else if (o instanceof RejectedRequest) {
			return XMLRejectedRequestAdapter.toXmlRequest((RejectedRequest) o);
		} else if (o instanceof Request) {
			return XMLRequestAdapter.toXmlRequest((Request) o);
		}
		return o;
	}

	private T convertToObjects(Object o) {
		try {
			if (o instanceof XMLClient) {
				return (T) XMLClientAdapter.toClient((XMLClient) o);
			} else if (o instanceof XMLAdministrator) {
				return (T) XMLAdministratorAdapter.toAdministrator((XMLAdministrator) o);
			} else if (o instanceof XMLUser) {
				return (T) XMLUserAdapter.toUser((XMLUser) o);
			} else if (o instanceof XMLAvailableRoomType) {
				return (T) XMLAvailableRoomTypeAdapter.toAvailableRoomTypes((XMLAvailableRoomType) o);
			} else if (o instanceof XMLRoomType) {
				return (T) XMLRoomTypeAdapter.toRoomType((XMLRoomType) o);
			} else if (o instanceof XMLConfirmedRequest) {
				return (T) XMLConfirmedRequestAdapter.toRequest((XMLConfirmedRequest) o);
			} else if (o instanceof XMLRejectedRequest) {
				return (T) XMLRejectedRequestAdapter.toRequest((XMLRejectedRequest) o);
			} else if (o instanceof XMLRequest) {
				return (T) XMLRequestAdapter.toRequest((XMLRequest) o);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (T) o;
	}

}
