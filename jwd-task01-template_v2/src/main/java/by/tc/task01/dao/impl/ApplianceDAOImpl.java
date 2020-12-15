package by.tc.task01.dao.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import by.tc.task01.dao.ApplianceDAO;
import by.tc.task01.entity.Appliance;
import by.tc.task01.entity.criteria.Criteria;
import by.tc.task01.entity.factory.ApplianceFactory;

public class ApplianceDAOImpl implements ApplianceDAO {

	@Override
	public ArrayList<Appliance> find(Criteria criteria) {

		ArrayList<Appliance> applianceArray = new ArrayList<Appliance>();

		Set<String> nameCriteria = criteria.getCriteria().keySet();

		int numberCriteria = criteria.getCriteria().size();

		try {

			File myFile = new File("src/main/java/appliances_db.txt");
			FileReader fileReader = new FileReader(myFile);
			BufferedReader reader = new BufferedReader(fileReader);

			String line = null;

			while ((line = reader.readLine()) != null) {

				String[] typeAndCriteria = line.split(" : ");
				String type = typeAndCriteria[0];

				if (type.equals(criteria.getGroupSearchName())) {

					HashMap<String, String> optionAppliance = getOption(typeAndCriteria[1]);

					int counter = 0;

					for (String name : nameCriteria) {

						String valueCriteria = criteria.getCriteria().get(name).toString();

						if (valueCriteria.equals(optionAppliance.get(name))) {
							counter++;
						}

					}

					if (counter == numberCriteria) {

						ApplianceFactory factory = new ApplianceFactory();
						Appliance appliance = factory.createAppliance(type, optionAppliance);
						
						applianceArray.add(appliance);
						
						System.out.println("Создан новый электроприбор - " + appliance.toString());

					}

				}

			}

			reader.close();

		} catch (Exception ex) {

			ex.printStackTrace();

		}

		return applianceArray;
	}

	public HashMap<String, String> getOption(String option) {

		HashMap<String, String> optionAppliance = new HashMap<>();

		String[] criteriaAppliance = option.split(", ");

		for (String criteria : criteriaAppliance) {
			String[] result = criteria.split("=");
			optionAppliance.put(result[0], result[1]);
		}

		return optionAppliance;

	}

	// you may add your own code here

}

//you may add your own new classes