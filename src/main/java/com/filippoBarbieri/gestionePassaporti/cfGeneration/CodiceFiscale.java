package com.filippoBarbieri.gestionePassaporti.cfGeneration;


import java.util.Date;
import java.io.FileReader;
import java.util.Calendar;
import java.io.IOException;
import java.io.BufferedReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CodiceFiscale {
    private final String name;
    private final String surname;
    private final Boolean male;
    private final Date birthDate;
    private final String birthProvince;
    private final String codCatasto;
    private final char monthCode;

    private static final String COMMA_DELIMITER = ",";
    private static final String DATA_PATH = System.getProperty("user.dir") + "/src/main/resources/cfData";
    private static final Calendar CAL = Calendar.getInstance();
    private static final String monthsCodes = "ABCDEHLMPRST";

    /**
     * This is the parametized constructor.
     * @param name Name person.
     * @param surname Surname person.
     * @param birthDate Birthdate, as [dd/mm/yyyy].
     * @param birthCity Birth city.
     * @param male Binary sex.
     * @param birthProvince Birth province.
     */
    public CodiceFiscale(String name, String surname, Boolean male, Date birthDate, String birthCity, String birthProvince) throws IOException, CityNotFoundException, FormatException {
        this.name = name.trim();
        this.surname = surname.trim();
        this.male = male;
        this.birthDate = birthDate;
        this.birthProvince = birthProvince.trim();
        codCatasto = findCity(birthCity.trim());
        CAL.setTime(birthDate);
        monthCode = monthsCodes.charAt(CAL.get(Calendar.MONTH));
        isValid();
    }

    private void isValid() throws FormatException, CityNotFoundException, IOException {
        if (name.isEmpty() || name.length() < 3 || surname.isEmpty() || surname.length() < 3)
            throw new FormatException();
        if (!validBirth(birthDate.toString()))
            throw new FormatException("Birth not accepted.");
        if (!findProvince(birthProvince))
            throw new CityNotFoundException("Province not found.");
    }

    private Boolean validBirth(String birth) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(birth);
            return true;
        } catch (ParseException e) {
            return false;
        }
	}

    private Boolean findProvince(String province) throws IOException {
        if (province.length() != 2)
			return false;
		else {
			try (BufferedReader br = new BufferedReader(new FileReader(DATA_PATH + "/lista.csv"))) {
				String line;
				while ((line = br.readLine()) != null) {
					String[] values = line.split(COMMA_DELIMITER);
                    if (values[1].equalsIgnoreCase(province))
                        return true;
				}
			}
		}
        return false;
    }

	private String findCity(String dropCity) throws IOException, CityNotFoundException {
		if (dropCity.length() < 3)
			throw new CityNotFoundException("Città formattata male");
        try (BufferedReader br = new BufferedReader(new FileReader(DATA_PATH + "/lista.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                if (values[0].equalsIgnoreCase(dropCity))
                    return values[2];
            }
        }
		throw new CityNotFoundException("Città formattata male o estera!");
	}

    private String consonant(String str, Boolean name) {
        String cons = "aeiou";
        StringBuilder res = new StringBuilder();
        str = str.toLowerCase();
        StringBuilder volw = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (cons.contains("" + str.charAt(i)))
                volw.append(str.charAt(i));
            else
                res.append(str.charAt(i));
        }
        if (res.length() > 3 ) {
            if (name)
                res.deleteCharAt(1);
            return res.substring(0, 3).toUpperCase();
        }
        else {
            if (res.length() == 2) {
                if (!volw.isEmpty())
                    return res.append(volw.charAt(0)).toString().toUpperCase();
                else
                    return res.append("X").toString().toUpperCase();
            }
            else if (res.length() == 1) {
                if (volw.length() > 1)
                    return res.append(volw.charAt(0)).append(volw.charAt(1)).toString().toUpperCase();
                else if (!volw.isEmpty())
                    return res.append(volw.charAt(0)).append("X").toString().toUpperCase();
                else
                    return res.append("XX").toString().toUpperCase();
            }
            return null;
        }
    }

    private String birthSex(int birth, Boolean male) {
        if (!male)
            return String.valueOf(40);
        return (birth > 9) ? String.valueOf(birth) : "0" + birth;
    }

    private int cinConversion(Boolean even, char chr) throws IOException {
        if (even) {
            try (BufferedReader br = new BufferedReader(new FileReader(DATA_PATH + "/cin_pari.csv"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(COMMA_DELIMITER);
                    if (values[0].charAt(0) == chr)
                        return Integer.parseInt(values[1]);
                }
            }
        }
        else {
            try (BufferedReader br = new BufferedReader(new FileReader(DATA_PATH + "/cin_disp.csv"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(COMMA_DELIMITER);
                    if (values[0].charAt(0) == chr)
                        return Integer.parseInt(values[1]);
                }
            }
        }
        return -1;
    }

    private String cin(String partCf) throws IOException {
        int odd = 0, even = 0;
        for (int i = 1; i < partCf.length() + 1; i++) {
            if (i % 2 == 0)
                even += cinConversion(true, partCf.charAt(i-1));
            else
                odd += cinConversion(false, partCf.charAt(i-1));
        }
        even += odd;
        even %= 26;
        try (BufferedReader br = new BufferedReader(new FileReader(DATA_PATH + "/cin_resto.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                if (Integer.parseInt(values[0]) == even)
                    return values[1];
            }
        }
        return "-1";
    }

	public String toString() {
        String shtName = consonant(name, true);
        String shtSur = consonant(surname, false);
        String year = String.valueOf(CAL.get(Calendar.YEAR)).substring(2);
        String birthFmt = birthSex(CAL.get(Calendar.DAY_OF_MONTH), male);
        String res = shtSur + shtName + year  + monthCode + birthFmt + codCatasto;
        try {
            return res + cin(res);
        }
        catch (IOException e) {
            return "Impossibile generare il codice fiscale";
        }
	}
}
