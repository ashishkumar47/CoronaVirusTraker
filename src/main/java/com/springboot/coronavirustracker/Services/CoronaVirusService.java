package com.springboot.coronavirustracker.Services;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.springboot.coronavirustracker.models.LocationStats;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service
public class CoronaVirusService {
	private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
	private final static OkHttpClient httpClient = new OkHttpClient();
	Logger logger = LoggerFactory.getLogger(CoronaVirusService.class);

	@PostConstruct
	@Scheduled(cron = "0 0 */8 ? * *")
	public List<LocationStats> fetchVirusData() throws IOException {
		List<LocationStats> stats = new ArrayList<>();
		Request request = new Request.Builder().url(VIRUS_DATA_URL).build();
		Response response = null;
		try {
			 response = httpClient.newCall(request).execute();
			StringReader csvReader = new StringReader(response.body().string());
			logger.info("started fetching data from CSV file");
			Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvReader);
			for (CSVRecord record : records) {
				int size = record.size();
				int latestCases = 0;
				if (StringUtils.isBlank(record.get(size - 1))) {
					latestCases = Integer.parseInt(record.get(size - 2));
				}
				else {
					latestCases = Integer.parseInt(record.get(size - 1));
				}
				int prevCases=Integer.parseInt(record.get(size - 2));
				LocationStats locationStats = new LocationStats(record.get("Province/State"),
						record.get("Country/Region"), latestCases, latestCases - prevCases);
				stats.add(locationStats);
			}
			logger.info("Finished fetching data from CSV file");
			return stats;
		}
		catch(Exception exception) {
			logger.error("Exception in parsing data from CSV file", exception);
			throw new IOException("Unexpected code " + response);
		}

    }
}
