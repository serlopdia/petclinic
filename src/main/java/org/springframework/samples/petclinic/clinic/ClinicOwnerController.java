package org.springframework.samples.petclinic.clinic;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;

import org.springframework.samples.petclinic.cause.Cause;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Controller
public class ClinicOwnerController {
	
	@GetMapping("/translate")
	public String initCreationForm(ModelMap model) {
		Cause cause = new Cause();
		model.addAttribute("cause", cause);
		return "clinicOwner/form";
	}
	
	@PostMapping("/translate")
	public String callAPI(Cause cause, BindingResult result, ModelMap modelMap) throws IOException
	{
		OkHttpClient client = new OkHttpClient();

		RequestBody body = new FormBody.Builder()
			.add("q", cause.getDescription())
			.add("target", "en")
			.add("source", "es")
			.build();

		Request request = new Request.Builder()
			.url("https://google-translate1.p.rapidapi.com/language/translate/v2")
			.post(body)
			.addHeader("content-type", "application/x-www-form-urlencoded")
			.addHeader("Accept-Encoding", "application/gzip")
			.addHeader("X-RapidAPI-Host", "google-translate1.p.rapidapi.com")
			.addHeader("X-RapidAPI-Key", "604d8c151bmshb6bb6675405b568p195ff4jsn8378cfe0e30a")
			.build();

		Response response = client.newCall(request).execute();
		modelMap.addAttribute("response",response.body().string()
				.replace("{\"data\":{\"translations\":[{\"translatedText\":\"", "")
				.replace("\"}]}}", "")); 
		
		return "clinicOwner/show";
	}
	
	@GetMapping("/sites")
	public String getSites(ModelMap model) throws IOException
	{
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		
		Request request = new Request.Builder()
		  .url("https://api.geoapify.com/v2/places?categories=leisure&conditions=dogs.yes&filter=rect:-6.033378698352724,37.43285068001863,-5.910136800245876,37.33421306108495&limit=20&apiKey=8c39984c5c444ee897965967637f31ce")
		  .method("GET", null).build();
		Response response = client.newCall(request).execute();
		List<String> sevilla = new ArrayList<>();
		JsonArray arr = Json.createReader(new StringReader(response.body().string())).readObject().get("features").asJsonArray();
		for(int i = 0;i < arr.size();i++) 
		{
			JsonObject json = arr.get(i).asJsonObject().get("properties").asJsonObject();
			String aux ="Lugar: " + json.get("name").toString() + ", " + json.get("neighbourhood") + ", " + json.get("suburb") + ", Dirección: " + json.get("formatted");
			sevilla.add(aux);
		}
	
		request = new Request.Builder()
			.url("https://api.geoapify.com/v2/places?categories=leisure.park&filter=rect:-4.4745874247945245,36.7266860095018,-4.412966475741026,36.67692229740344&limit=20&apiKey=8c39984c5c444ee897965967637f31ce")
			.method("GET", null).build();
		response = client.newCall(request).execute();
		List<String> malaga = new ArrayList<>();
		arr = Json.createReader(new StringReader(response.body().string())).readObject().get("features").asJsonArray();
		for(int i = 0;i < 4;i++) 
		{
			JsonObject json = arr.get(i).asJsonObject().get("properties").asJsonObject();
			String aux ="Lugar: " + json.get("name").toString() + ", " + json.get("district") +  ", Dirección: " + json.get("formatted");
			malaga.add(aux);
		}
						
	    request = new Request.Builder()
	    		.url("https://api.geoapify.com/v2/places?categories=leisure.park&filter=rect:-5.428705765096424,37.337325845440645,-5.399504733486822,37.31777611238833&limit=20&apiKey=8c39984c5c444ee897965967637f31ce")
	    		.method("GET", null)
				.build();
		response = client.newCall(request).execute();
	    List<String> marchena = new ArrayList<>();
		arr = Json.createReader(new StringReader(response.body().string())).readObject().get("features").asJsonArray();
		for(int i = 7;i < 10;i = i + 2) 
		{
			JsonObject json = arr.get(i).asJsonObject().get("properties").asJsonObject();
			String aux ="Lugar: " + json.get("name").toString() + ", Dirección: " + json.get("formatted");
			marchena.add(aux);
		}
								
		request = new Request.Builder()
			.url("https://api.geoapify.com/v2/places?categories=leisure.park&filter=rect:-5.334700752227334,35.911161900828276,-5.28948196543125,35.878899385132186&limit=20&apiKey=8c39984c5c444ee897965967637f31ce")
			.method("GET", null)
			 .build();
		response = client.newCall(request).execute();
		List<String> ceuta = new ArrayList<>();
		arr = Json.createReader(new StringReader(response.body().string())).readObject().get("features").asJsonArray();
		for(int i = 0;i < 2;i++) 
		{
				JsonObject json = arr.get(i).asJsonObject().get("properties").asJsonObject();
				String aux ="Lugar: " + json.get("name").toString() + ", Dirección: " + json.get("formatted");
				ceuta.add(aux);
			}
			model.addAttribute("response2",sevilla);
			model.addAttribute("malaga",malaga);
			model.addAttribute("marchena",marchena);
			model.addAttribute("ceuta",ceuta);
			return "clinicOwner/hotspot";
	}

}
