package model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class Country {

    public static List<String> getCountryList() {
        List<String> countries;
        String[] isos = Locale.getISOCountries();
        countries = Arrays.stream(isos)
                .map(x -> (new Locale("", x)).getDisplayCountry())
                .sorted()
                .collect(Collectors.toList());
        return countries;
    }

}
