import com.google.gson.Gson;


import java.io.IOException;
import java.io.InputStream;

import java.nio.charset.StandardCharsets;
import java.util.ListResourceBundle;
import java.util.Locale;


@SuppressWarnings("unused")
public class LocaleAdapter extends ListResourceBundle {
	Locale locale;
	Object[][] localeReal;

	public  LocaleAdapter(Locale l){
		this.locale=l;
		Gson gson = new Gson();
		//System.out.println("/locale/" +locale.getLanguage()+"_"+locale.getCountry() + ".json");
		InputStream localestream = getClass().getResourceAsStream("/locale/" +locale.getLanguage()+"_"+locale.getCountry() +  ".json");
		byte[] localebuf;
		try {
			localebuf = localestream.readAllBytes();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		String localestr = new String(localebuf, StandardCharsets.UTF_8);
		this.localeReal=gson.fromJson(localestr,Object[][].class);
	}

	protected Object[][] getContents() {
		return localeReal;
	}
}
