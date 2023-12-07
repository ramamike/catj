package com.ramamike.cat.models.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class Configuration {

	private static Configuration configuration;
	private String title;
	private List<Feature> features = new ArrayList<Feature>();
	private static ConfigService service;

	public static Configuration getInstance() {
		if (configuration == null) {
			configuration = new Configuration();
			service = new ConfigService();
			service.parseConfiguration(configuration);
		}
		return configuration;
	}

	private Configuration() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Feature> getFeatures() {
		return features;
	}

	public void setFeatures(List<Feature> features) {
		this.features = features;
	}

	public void save() {
		service.saveCongif(configuration);
	}

	public String getConfigFeutureValue(String feature, String key) {

		Feature configFeature = configuration.getFeatures().stream().filter(f -> f.getTitle().equals(feature)).findAny()
				.orElse(null);

		if (configFeature == null) {
			HashMap<String, String> newFeatureMap = new HashMap<String, String>();
			newFeatureMap.put(key, FileUtils.getUserDirectoryPath());
			Feature newFeature = new Feature(feature, newFeatureMap);
			configuration.getFeatures().add(newFeature);
			configuration.save();
			configFeature = configuration.getFeatures().stream().filter(f -> f.getTitle().equals(feature)).findAny()
					.orElse(null);
		}

		return configFeature.getFeutureConfig().get(key);

	}

	public void setConfigFeutureValue(String feature, String key, String value) {

		Feature configFeature = configuration.getFeatures().stream().filter(f -> f.getTitle().equals(feature)).findAny()
				.orElse(null);
		HashMap<String, String> featureMap = new HashMap<String, String>();
		if (configFeature == null) {
			featureMap = new HashMap<String, String>();
			featureMap.put(key, value);
			Feature newFeature = new Feature(feature, featureMap);
			configuration.getFeatures().add(newFeature);
			configuration.save();
		} else {
			featureMap = configFeature.getFeutureConfig();
			if (featureMap.containsKey(key)) {
				featureMap.put(key, value);
				configuration.save();
			}
		}

	}
}
