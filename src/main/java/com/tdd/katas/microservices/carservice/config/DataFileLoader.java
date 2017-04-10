package com.tdd.katas.microservices.carservice.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdd.katas.microservices.carservice.model.CarData;
import com.tdd.katas.microservices.carservice.repository.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Component
@Profile(Profiles.PROFILE_INTEGRATION_TEST)
public class DataFileLoader {

    private static final Logger logger = LoggerFactory.getLogger(DataFileLoader.class);

    @Autowired
    private Environment environment;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private ObjectMapper objectMapper;

    public static final String DATA_FILE_PROPERTY = "integration-testing.data.file";

    public void loadData() {
        logger.info("Data file loading started");

        File dataFile = findFile();
        if (dataFile == null)
            return;

        logger.info("Processing file: [{}]", dataFile.getAbsolutePath());

        if (!processFile(dataFile)) {
            logger.info("File could not be correctly processed: [{}]", dataFile.getAbsolutePath());
            return;
        }
        else {
            logger.info("File processed correctly: [{}]", dataFile.getAbsolutePath());
        }

        logger.info("Data file loading completed");
    }

    private boolean processFile(File dataFile) {
        Map<String,CarData> CarDataMap;
        try {
            CarDataMap = objectMapper.readValue(dataFile, new TypeReference<Map<String,CarData>>(){});
            logger.info("Found: {} entries", CarDataMap.size());
        } catch (IOException e) {
           logger.error("I/O error loading data file", e);
            return false;
        }catch (Exception e) {
            logger.error("Unexpected error loading data file", e);
            return false;
        }

        for (Map.Entry<String,CarData> CarData : CarDataMap.entrySet()) {
            carRepository.createCarData(CarData.getKey(), CarData.getValue());

            try {
                logger.info("Created entry: [{}]", objectMapper.writeValueAsString(CarData));
            }
            catch (Exception e) {
                logger.info("Created entry: [{}]", CarData.toString());
            }
        }
        return true;
    }

    private File findFile() {
        // Get the data file location
        String dataFileLocation = environment.getProperty(DATA_FILE_PROPERTY);
        if (dataFileLocation==null || dataFileLocation.length()==0) {
            logger.info("Data file loading: No data file specified");
            return null;
        }

        // Check if the specified file exists and can be opened
        File dataFile = new File(dataFileLocation);
        if (!dataFile.exists() || !dataFile.canRead()){
            logger.info("Data file loading: File doesn't exist or cannot be read [{}]", dataFile.getAbsolutePath());
            return null;
        }

        logger.info("Found file: [{}]", dataFile.getAbsolutePath());
        return dataFile;
    }

}
