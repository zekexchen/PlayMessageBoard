package models;

import java.util.*;
import play.modules.mongodb.jackson.MongoDB;
import net.vz.mongodb.jackson.JacksonDBCollection;
import net.vz.mongodb.jackson.Id;
import net.vz.mongodb.jackson.ObjectId;
import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.DateTime;

import com.mongodb.BasicDBObject;

import javax.persistence.*;

public class Task {

	@Id
	@ObjectId
	public String id;

	public String label;

	public String txtContext;

	public DateTime dt;
	
//	public static int iResult = 20;

	private static JacksonDBCollection<Task, String> coll = MongoDB.getCollection("tasks", Task.class, String.class);

	public Task() {

	}

	public Task(String label, String txtContext) {
		this.label = label;
		this.txtContext = txtContext;
		this.dt = DateTime.now();
	}

	public static List<Task> all() {
		return Task.coll.find().skip(0).limit(20).toArray();
	}

	public static List<Task> range(Integer i) {
		if (i < Task.coll.find().toArray().size()) {
			return Task.coll.find().skip(i).limit(20).toArray();
		}
		return null;
	}	

	public static void create(Task task) {
		Task.coll.save(task);
	}

	public static void create(String label, String txtContext) {
		create(new Task(label, txtContext));
	}

	public static void delete(String id) {
		Task task = Task.coll.findOneById(id);
		if (task != null)
			Task.coll.remove(task);
	}

	public static void removeAll() {
		Task.coll.drop();
	}

}
