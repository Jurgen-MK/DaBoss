package com.boss.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

@Service
public class NameGenerator {

	public List<String[]> loadNameParts(String partPath) throws IOException {
		List<String[]> nameParts = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new FileReader(ResourceUtils.getFile(partPath)));
		String currentLine = "";
		while ((currentLine = reader.readLine()) != null) {
			nameParts.add(currentLine.split(","));			
		}
		reader.close();
		return nameParts;
	}

	public String[] generateOrk() throws IOException {
		Random random = new Random();
		// String name = loadNameParts().stream().map(np ->
		// np[random.nextInt(np.length)]).collect(Collectors.joining(""));
		String name = "";
		String title = "";
		List<String[]> nameParts = loadNameParts("classpath:static/text/orknames.txt");
		switch (random.nextInt(4)) {
		case (0):
			name = nameParts.get(0)[random.nextInt(nameParts.get(0).length)]
					+ nameParts.get(1)[random.nextInt(nameParts.get(1).length)] + " "
					+ nameParts.get(2)[random.nextInt(nameParts.get(2).length)]
					+ nameParts.get(3)[random.nextInt(nameParts.get(3).length)];
			break;
		case (1):
			name = nameParts.get(0)[random.nextInt(nameParts.get(0).length)]
					+ nameParts.get(1)[random.nextInt(nameParts.get(1).length)] + " "
					+ nameParts.get(0)[random.nextInt(nameParts.get(0).length)]
					+ nameParts.get(1)[random.nextInt(nameParts.get(1).length)];
			break;
		case (2):
			name = nameParts.get(0)[random.nextInt(nameParts.get(0).length)]
					+ nameParts.get(1)[random.nextInt(nameParts.get(1).length)] + " "
					+ nameParts.get(0)[random.nextInt(nameParts.get(0).length)] + " "
					+ nameParts.get(2)[random.nextInt(nameParts.get(2).length)]
					+ nameParts.get(3)[random.nextInt(nameParts.get(3).length)];
			break;
		case (3):
			name = nameParts.get(0)[random.nextInt(nameParts.get(0).length)]
					+ nameParts.get(1)[random.nextInt(nameParts.get(1).length)] + " "
					+ nameParts.get(0)[random.nextInt(nameParts.get(0).length)] + " "
					+ nameParts.get(2)[random.nextInt(nameParts.get(2).length)]
					+ nameParts.get(3)[random.nextInt(nameParts.get(3).length)] + " "
					+ nameParts.get(0)[random.nextInt(nameParts.get(0).length)] + "a";
			break;
		}
		title = nameParts.get(4)[random.nextInt(nameParts.get(4).length)] + " "
				+ nameParts.get(5)[random.nextInt(nameParts.get(5).length)]
				+ nameParts.get(6)[random.nextInt(nameParts.get(6).length)] + " of "
				+ nameParts.get(7)[random.nextInt(nameParts.get(7).length)]
				+ nameParts.get(8)[random.nextInt(nameParts.get(8).length)]
				+ nameParts.get(9)[random.nextInt(nameParts.get(9).length)]
				+ nameParts.get(10)[random.nextInt(nameParts.get(10).length)];
		return new String[] { name, title };
	}

	public String[] generateEldar() throws IOException {
		Random random = new Random();
		// String name = loadNameParts().stream().map(np ->
		// np[random.nextInt(np.length)]).collect(Collectors.joining(""));
		String name = "";
		String title = "";
		List<String[]> nameParts = loadNameParts("classpath:static/text/eldarnames.txt");		
		switch (random.nextInt(6)) {
		case (0):
			name = nameParts.get(0)[random.nextInt(nameParts.get(0).length)]
					+ nameParts.get(1)[random.nextInt(nameParts.get(1).length)] + "-"
					+ nameParts.get(5)[random.nextInt(nameParts.get(5).length)]
					+ nameParts.get(6)[random.nextInt(nameParts.get(6).length)];
			break;
		case (1):
			name = nameParts.get(2)[random.nextInt(nameParts.get(2).length)]
					+ nameParts.get(4)[random.nextInt(nameParts.get(4).length)] + " "
					+ nameParts.get(5)[random.nextInt(nameParts.get(5).length)]
					+ nameParts.get(6)[random.nextInt(nameParts.get(6).length)];
			break;
		case (2):
			name = nameParts.get(2)[random.nextInt(nameParts.get(2).length)]
					+ nameParts.get(4)[random.nextInt(nameParts.get(4).length)] + " "
					+ nameParts.get(7)[random.nextInt(nameParts.get(7).length)]
					+ nameParts.get(8)[random.nextInt(nameParts.get(8).length)];
			break;
		case (3):
			name = nameParts.get(2)[random.nextInt(nameParts.get(2).length)]
					+ nameParts.get(4)[random.nextInt(nameParts.get(4).length)] + " "
					+ nameParts.get(9)[random.nextInt(nameParts.get(9).length)]
					+ nameParts.get(10)[random.nextInt(nameParts.get(10).length)];
			break;
		case (4):
			name = nameParts.get(2)[random.nextInt(nameParts.get(2).length)]
					+ nameParts.get(3)[random.nextInt(nameParts.get(3).length)]
					+ nameParts.get(4)[random.nextInt(nameParts.get(4).length)] + " "
					+ nameParts.get(5)[random.nextInt(nameParts.get(5).length)]
					+ nameParts.get(6)[random.nextInt(nameParts.get(6).length)];
			break;
		case (5):
			name = nameParts.get(2)[random.nextInt(nameParts.get(2).length)]
					+ nameParts.get(3)[random.nextInt(nameParts.get(3).length)]
					+ nameParts.get(4)[random.nextInt(nameParts.get(4).length)] + " "
					+ nameParts.get(7)[random.nextInt(nameParts.get(7).length)]
					+ nameParts.get(8)[random.nextInt(nameParts.get(8).length)];
			break;
		case (6):
			name = nameParts.get(2)[random.nextInt(nameParts.get(2).length)]
					+ nameParts.get(3)[random.nextInt(nameParts.get(3).length)]
					+ nameParts.get(4)[random.nextInt(nameParts.get(4).length)] + " "
					+ nameParts.get(9)[random.nextInt(nameParts.get(9).length)]
					+ nameParts.get(10)[random.nextInt(nameParts.get(10).length)];
			break;
		}
		title = nameParts.get(11)[random.nextInt(nameParts.get(11).length)] + " "
				+ nameParts.get(12)[random.nextInt(nameParts.get(12).length)] + " of "
				+ nameParts.get(13)[random.nextInt(nameParts.get(13).length)] + " "
				+ nameParts.get(14)[random.nextInt(nameParts.get(14).length)];
		return new String[] { name, title };
	}

}
