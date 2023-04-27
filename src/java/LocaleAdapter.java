import com.google.gson.Gson;


import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.Objects;

@SuppressWarnings("unused")
public class LocaleAdapter extends ListResourceBundle {
	Locale locale;

	public  LocaleAdapter(Locale l){
		this.locale=l;
	}

	protected Object[][] getContents() {
		Gson gson = new Gson();
		//System.out.println("/locale/" +locale.getLanguage()+"_"+locale.getCountry() + ".json");
		Reader reslocale = new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/locale/" +locale.getLanguage()+"_"+locale.getCountry() +  ".json")));
		return gson.fromJson(reslocale,Object[][].class);
	}
}
