package controllers;

import play.mvc.*;
import play.data.*;

import views.html.manage.*;

import models.*;

public class AppManage extends Controller {
    
    /**
     * Defines a form wrapping the Contact class.
     */ 
    final static Form<User> manageForm = form(User.class);
  
    /**
     * Display a blank form.
     */ 
    public static Result blank() {
        return ok(index.render());
    }
  
}