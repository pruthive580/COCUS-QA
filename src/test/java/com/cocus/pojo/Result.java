package com.cocus.pojo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Result{
	@JsonProperty("gender") 
	public String gender;
	@JsonProperty("name") 
	public Name name;
	@JsonProperty("location") 
	public Location location;
	@JsonProperty("email") 
	public String email;
	@JsonProperty("login") 
	public Login login;
	@JsonProperty("dob") 
	public Dob dob;
	@JsonProperty("registered") 
	public Registered registered;
	@JsonProperty("phone") 
	public String phone;
	@JsonProperty("cell") 
	public String cell;
	@JsonProperty("id") 
	public Id id;
	@JsonProperty("picture") 
	public Picture picture;
	@JsonProperty("nat") 
	public String nat;

	@Data
	public class Registered{
		@JsonProperty("date") 
		public Date date;
		@JsonProperty("age") 
		public int age;
	}

	@Data
	public static class Info{

		@JsonProperty("seed") 
		public String seed;
		@JsonProperty("results") 
		public int results;
		@JsonProperty("page") 
		public int page;
		@JsonProperty("version") 
		public String version;
	}

	@Data
	public class Picture{
		@JsonProperty("large") 
		public String large;
		@JsonProperty("medium") 
		public String medium;
		@JsonProperty("thumbnail") 
		public String thumbnail;
	}
	@Data
	public class Login{
		@JsonProperty("uuid") 
		public String uuid;
		@JsonProperty("username") 
		public String username;
		@JsonProperty("password") 
		public String password;
		@JsonProperty("salt") 
		public String salt;
		@JsonProperty("md5") 
		public String md5;
		@JsonProperty("sha1") 
		public String sha1;
		@JsonProperty("sha256") 
		public String sha256;
	}
	@Data
	public class Id{
		@JsonProperty("name") 
		public String name;
		@JsonProperty("value") 
		public String value;
	}

	@Data
	public class Dob{
		@JsonProperty("date") 
		public Date date;
		@JsonProperty("age") 
		public int age;
	}

	@Data
	public class Name{
		@JsonProperty("title") 
		public String title;
		@JsonProperty("first") 
		public String first;
		@JsonProperty("last") 
		public String last;
	}
	@Data
	public class Location{
		@JsonProperty("street") 
		public Street street;
		@JsonProperty("city") 
		public String city;
		@JsonProperty("state") 
		public String state;
		@JsonProperty("country") 
		public String country;
		@JsonProperty("postcode") 
		public String postcode;
		@JsonProperty("coordinates") 
		public Coordinates coordinates;
		@JsonProperty("timezone") 
		public Timezone timezone;
		@Data
		public class Coordinates{
			@JsonProperty("latitude") 
			public String latitude;
			@JsonProperty("longitude")
			public String longitude;
		}
		@Data
		public class Street{
			@JsonProperty("number") 
			public int number;
			@JsonProperty("name") 
			public String name;
		}
		@Data
		public class Timezone{
			@JsonProperty("offset") 
			public String offset;
			@JsonProperty("description") 
			public String description;
		}
	}
}

