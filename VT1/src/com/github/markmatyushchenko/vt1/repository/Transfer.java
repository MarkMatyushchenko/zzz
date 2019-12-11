package com.github.markmatyushchenko.vt1.repository;

import com.github.markmatyushchenko.vt1.entity.request.ConfirmedRequest;
import com.github.markmatyushchenko.vt1.entity.request.RejectedRequest;
import com.github.markmatyushchenko.vt1.entity.request.Request;
import com.github.markmatyushchenko.vt1.entity.roomtype.RoomType;
import com.github.markmatyushchenko.vt1.entity.user.Administrator;
import com.github.markmatyushchenko.vt1.entity.user.Client;
import com.github.markmatyushchenko.vt1.repository.jaxbrepository.XMLDAO;
import com.github.markmatyushchenko.vt1.repository.mysqlrepository.*;
import io.github.cdimascio.dotenv.Dotenv;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Transfer {

	private static final Logger logger = LogManager.getLogger();

	public static void main(String[] args) {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(new File("db.properties")));
		} catch (IOException exc) {
			exc.printStackTrace();
		}

		String xmlPath = properties.get("xmlPath").toString();
		String xsdPath = properties.get("xsdPath").toString();

		XMLDAO<Client> clientXMLDAO = new XMLDAO<>(
				new File(xmlPath + "clients.xml"), new File(xsdPath + "clients.xsd"));
		XMLDAO<Administrator> adminXMLDAO = new XMLDAO<>(
				new File(xmlPath + "admins.xml"), new File(xsdPath + "admins.xsd"));
		XMLDAO<RoomType> roomTypeXMLDAO = new XMLDAO<>(
				new File(xmlPath + "roomTypes.xml"), new File(xsdPath + "roomTypes.xsd"));
		XMLDAO<Request> requestXMLDAO = new XMLDAO<>(
				new File(xmlPath + "requests.xml"), new File(xsdPath + "requests.xsd"));
		XMLDAO<RejectedRequest> rejectedRequestXMLDAO = new XMLDAO<>(
				new File(xmlPath + "rejectedRequests.xml"), new File(xsdPath + "rejectedRequests.xsd"));
		XMLDAO<ConfirmedRequest> confirmedRequestXMLDAO = new XMLDAO<>(
				new File(xmlPath + "confirmedRequests.xml"), new File(xsdPath + "confirmedRequests.xsd"));

		List<Client> clients = StreamSupport
				.stream(clientXMLDAO.read().spliterator(), false)
				.collect(Collectors.toList());
		logger.info("Read clients: " + clients.size());

		List<Administrator> admins = StreamSupport
				.stream(adminXMLDAO.read().spliterator(), false)
				.collect(Collectors.toList());
		logger.info("Read admins: " + admins.size());

		List<RoomType> roomTypes = StreamSupport
				.stream(roomTypeXMLDAO.read().spliterator(), false)
				.collect(Collectors.toList());
		logger.info("Read room types: " + roomTypes.size());

		List<Request> requests = StreamSupport
				.stream(requestXMLDAO.read().spliterator(), false)
				.collect(Collectors.toList());
		logger.info("Read requests: " + requests.size());

		List<ConfirmedRequest> confirmedRequests = StreamSupport
				.stream(confirmedRequestXMLDAO.read().spliterator(), false)
				.collect(Collectors.toList());
		logger.info("Read confirmed requests: " + confirmedRequests.size());

		List<RejectedRequest> rejectedRequests = StreamSupport
				.stream(rejectedRequestXMLDAO.read().spliterator(), false)
				.collect(Collectors.toList());
		logger.info("Read rejected requests: " + rejectedRequests.size());

		MySqlConfigurationManager configurationManager = MySqlConfigurationManager.getInstance(
				properties.get("host").toString(),
				properties.get("login").toString(),
				properties.get("password").toString()
		);
		MySqlClientDAO sqlClientDAO = new MySqlClientDAO(configurationManager);
		MySqlAdministratorDAO sqlAdministratorDAO = new MySqlAdministratorDAO(configurationManager);
		MySqlRoomTypeDAO mySqlRoomTypeDAO = new MySqlRoomTypeDAO(configurationManager);
		MySqlRequestDAO mySqlRequestDAO = new MySqlRequestDAO(configurationManager, mySqlRoomTypeDAO);
		MySqlRejectedRequestDAO mySqlRejectedRequestDAO = new MySqlRejectedRequestDAO(configurationManager, mySqlRoomTypeDAO);
		MySqlConfirmedRequestDAO mySqlConfirmedRequestDAO = new MySqlConfirmedRequestDAO(configurationManager, mySqlRoomTypeDAO);

		mySqlRequestDAO.clear();
		mySqlConfirmedRequestDAO.clear();
		mySqlRejectedRequestDAO.clear();
		sqlClientDAO.clear();
		sqlAdministratorDAO.clear();
		mySqlRoomTypeDAO.clear();

		sqlClientDAO.save(clients);
		sqlAdministratorDAO.save(admins);
		mySqlRoomTypeDAO.save(roomTypes);
		mySqlRequestDAO.save(requests);
		mySqlConfirmedRequestDAO.save(confirmedRequests);
		mySqlRejectedRequestDAO.save(rejectedRequests);
	}
}
