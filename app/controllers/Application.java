package controllers;

import play.mvc.*;
import play.api.mvc.Request;
import play.api.mvc.Session;
import play.data.*;

import javax.servlet.http.HttpSession;

import models.*;

public class Application extends Controller {
	static Form<Task> taskForm = Form.form(Task.class);
	static int iCount = 0;

	public static Result index() {
		iCount = 0;
		return redirect(routes.Application.tasks());
	}

	public static Result tasks() {
		return ok(views.html.index.render(Task.all(), taskForm));
	}

	public static Result tasksRange(Integer i) {
		if (i == 20) {
			iCount = 20;
		}else{
			iCount += 20;
		}			
		return ok(views.html.index.render(Task.range(iCount), taskForm));
	}

	public static Result newTask() {
		Form<Task> filledForm = taskForm.bindFromRequest();
		if (filledForm.hasErrors()) {
			return badRequest(views.html.index.render(Task.all(), filledForm));
		} else {
			Task.create(filledForm.get());
			return redirect(routes.Application.tasks());
		}
	}

	public static Result deleteTask(String id) {
		Task.delete(id);
		return redirect(routes.Application.tasks());
	}

}
