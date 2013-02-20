package models;

import java.util.ArrayList;
import java.util.List;

import javax.validation.*;

import org.bson.types.ObjectId;

import com.google.code.morphia.query.Query;

import controllers.MorphiaObject;

import play.Logger;
import play.data.validation.Constraints.*;

public class User {
    
    @Required
    @MinLength(4)
    public String username;
    
    @Required
    @Email
    public String email;
    
    @Required
    @MinLength(6)
    public String password;

    @Valid
    public Profile profile;
    
    public User() {}
    
    public User(String username, String email, String password, Profile profile) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.profile = profile;
    }
    
    public static class Profile {
        
        @Required
        public String country;
        
        public String address;
        
        @Min(18) @Max(100)
        public Integer age;
        
        public Profile() {}
        
        public Profile(String country, String address, Integer age) {
            this.country = country;
            this.address = address;
            this.age = age;
        }
        
    }
    
    public static User getUser(String username) {
		if (MorphiaObject.datastore != null) {
			return MorphiaObject.datastore.find(User.class, "username", username).get();
		} else {
			return new User();
		}
	}

	public static void create(User user) {
		MorphiaObject.datastore.save(user);
	}
	
	public static void delete(String userToDelete) {
		//User toDelete = MorphiaObject.datastore.find(User.class).field("username").equal(new ObjectId(userToDelete)).get();
		Query <User> toDelete = MorphiaObject.datastore.find(User.class).field("username").equal(userToDelete);
		if (toDelete != null) {
			Logger.info("toDelete: " + toDelete);
			MorphiaObject.datastore.delete(toDelete);
			Logger.info("toDelete: " + toDelete);
		} else {
			Logger.debug("ID No Found: " + userToDelete);
		}
	}
	
	@Override
	public String toString() {
		return username;
	}
    
}