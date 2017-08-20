package ua.http.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import ua.http.Response;

public class SuccessResponse implements Response{

	private final HttpResponse response;

	public SuccessResponse(HttpResponse response) {
		this.response = response;
	}

	@Override
	public <T> T json(Class<T> classType) {
		try{
			String json = EntityUtils.toString(response.getEntity(), "UTF-8");
			ObjectMapper mapper = new ObjectMapper();
			JavaType type = mapper.getTypeFactory().constructFromCanonical(classType.getCanonicalName());
			if(mapper.canDeserialize(type)){
				T value = mapper.readValue(json.getBytes(Charset.forName("UTF-8")), classType);
				closeResponse();
				return value;
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
		closeResponse();
		return null;
	}
	
	@Override
	public String text() {
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
			String output = "";
			String line;
			while ((line = br.readLine()) != null) {
				if(!output.isEmpty()) output+="\n";
				output+=line;
			}
			closeResponse();
			return output;
		}catch (IOException e) {
			e.printStackTrace();
		}
		closeResponse();
		return null;
	}
	
	private void closeResponse(){
		try {
			System.out.println("Closed!!!!!!!");
			response.getEntity().getContent().close();
		} catch (UnsupportedOperationException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
		closeResponse();
	}
}
