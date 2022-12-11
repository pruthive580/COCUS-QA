package com.cocus.utils;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.cocus.pojo.Result;
import com.cocus.pojo.Result.Name;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class BaseMethods {


	/**
	 * @param url
	 * @param expectedStatusCode
	 * @return
	 */
	public static Result  getDataFromApi(String url,int expectedStatusCode){
		Map<Object,Map<String,String>> map1=null;
		Map<String,String> map=null;
		Result res = null;
		
		try {
			Response  response=given().header("Content-type","application/json").get(url);
			if(response.getStatusCode()==expectedStatusCode) {
				JsonPath jsonpath=response.jsonPath();
				List<Result> obj=jsonpath.getList("results",Result.class);
				res=obj.get(0);
			}
			else {
				System.out.println(response.getStatusCode());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return res;
	}


	/**
	 * @param classObject
	 * @return
	 * @throws Exception
	 */
	public static Map getMapFromPojo(Object classObject) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		if (classObject != null) {
			java.lang.reflect.Method[] methods = classObject.getClass().getMethods();
			for (java.lang.reflect.Method method : methods) {
				String name = method.getName();
				if (name.startsWith("get") && !name.equalsIgnoreCase("getClass")) {
					Object value = "";
					try {
						value =  method.invoke(classObject);
						map.put(name.substring(name.indexOf("get") + 3), value);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			return map;
		}
		return null;
	}
}

