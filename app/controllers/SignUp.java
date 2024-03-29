package controllers;

import play.mvc.*;
import play.data.*;

import views.html.signup.*;

import models.*;

public class SignUp extends Controller {
    
    /**
     * Defines a form wrapping the User class.
     */ 
    final static Form<User> signupForm = form(User.class);
  
    /**
     * Display a blank form.
     */ 
    public static Result blank() {
        return ok(form.render(signupForm));
    }
  
    /**
     * Display a form pre-filled with an existing account.
     */
    public static Result edit() {
        User existingUser = new User(
            "fakeuser", "fake@gmail.com", "secret",
            new User.Profile("France", null, 30)
        );
        return ok(form.render(signupForm.fill(existingUser)));
    }
  
    /**
     * Handle the form submission.
     */
    public static Result submit() {
        Form<User> filledForm = signupForm.bindFromRequest();
        
        
        // Check accept conditions
        if(!"true".equals(filledForm.field("accept").value())) {
            filledForm.reject("accept", "You must accept the terms and conditions");
        }
        
        // Check repeated password
        if(!filledForm.field("password").valueOr("").isEmpty()) {
            if(!filledForm.field("password").valueOr("").equals(filledForm.field("repeatPassword").value())) {
                filledForm.reject("repeatPassword", "Password don't match");
            }
        }
        
        // Check if the username is valid
        if(!filledForm.hasErrors()) {
            if(filledForm.get().username.equals("admin") || filledForm.get().username.equals("guest")) {
                filledForm.reject("username", "This username is already taken");
            }
        }
        
        if(filledForm.hasErrors()) {
            return badRequest(form.render(filledForm));
        } else {
            //save
            User.create(filledForm.get());
            return ok(form.render(filledForm));
        }
    }
    
    /**
     * Create user
     * 
     * @param username
     * @return
     */
    public static Result newUser() {
    	
    	Form<User> filledForm = signupForm.bindFromRequest();
        
        //저장
        User.create(filledForm.get());
        return ok(form.render(filledForm));
    }
    
    /**
     * Search user
     * 
     * @param username
     * @return
     */
    public static Result getUser(String username) {
    	User user = User.getUser(username);
    	if(user == null){
    		return ok(form.render(signupForm));
    	}else{
    		return ok(summary.render(user));
    	}
    }
    
    /**
     * Delete user
     * 
     * @param username
     * @return
     */
    public static Result deleteUser(String username) {
		User.delete(username);
		//return rediret(form.render(signupForm));
		return redirect(routes.SignUp.blank());
	}
    
}