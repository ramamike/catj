package com.ramamike.cat.models.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.ramamike.cat.constants.Constants;
import com.ramamike.cat.utils.Utils;

public class ConfigService {

	private static final Logger log = LoggerFactory.getLogger(ConfigService.class);

	public void parseConfiguration(Configuration configuration) {
		// Create config file at the first start
		Path configPath = Paths.get(Constants.CONFIG_PATH);

		if (!Files.exists(configPath)) {
			Path newFilePath = Paths.get(Constants.CONFIG_NAME);
			try {
				Files.createFile(newFilePath);
			} catch (IOException e) {
				log.error("Error during the first config cration: ", e);
			}
			configuration.setTitle("config");
			saveCongif(configuration);
		}

		// parse DOM ducument
		DocumentBuilder builder = null;
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			Utils.showExceptionDialog(e1);
			log.error("Error during config file reading: ", e1);
		}

		Document document = null;
		try {
			document = builder.parse(new File(Constants.CONFIG_PATH));
			document.getDocumentElement().normalize();
		} catch (SAXException | IOException e) {
			Utils.showExceptionDialog(e);
			log.error("Config file parsing error: ", e);
		}

		// Read config and create Java objects
		Node root = document.getFirstChild();

		configuration.setTitle(root.getNodeName());

		NodeList childNode = root.getChildNodes();
		List<Feature> features = configuration.getFeatures();

		for (int i = 0; i < childNode.getLength(); i++) {
			Node node = childNode.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {

				NodeList featureCongigsNodeList = node.getChildNodes();
				HashMap<String, String> featureCongigs = new HashMap<>();

				for (int j = 0; j < featureCongigsNodeList.getLength(); j++) {
					Node featureNode = featureCongigsNodeList.item(j);
					if (featureNode.getNodeType() == Node.ELEMENT_NODE) {
						featureCongigs.put(featureNode.getNodeName(),
								featureNode.getChildNodes().item(0).getTextContent().trim());
					}
				}

				Feature feature = new Feature(node.getNodeName(), featureCongigs);
				features.add(feature);
			}

		}

	}

	public void saveCongif(Configuration configuration) {
		String config = configToString(configuration);
		Utils.saveData(config, Constants.CONFIG_PATH);
	}

	public String configToString(Configuration configuration) {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\n");
		sb.append((configuration.getTitle() == null) ? "<title>" : "<" + configuration.getTitle() + ">" + "\n");
		// Features start
		configuration.getFeatures().forEach(f -> {
			sb.append("<" + f.getTitle() + ">" + "\n");
			f.getFeutureConfig().forEach((k, v) -> {
				sb.append("\t<" + k + ">" + "\n");
				sb.append("\t\t" + v + "\n");
				sb.append("\t</" + k + ">" + "\n");
			});
			sb.append("</" + f.getTitle() + ">" + "\n");
		});
		// Features end
		sb.append((configuration.getTitle() == null) ? "</title>" : "</" + configuration.getTitle() + ">");
		return sb.toString();
	}

}
