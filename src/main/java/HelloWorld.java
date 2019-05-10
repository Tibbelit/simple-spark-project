import static spark.Spark.get;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

/**
 * VelocityTemplateRoute example.
 */
public final class HelloWorld {
    
    public static void main(final String[] args) {
    	staticFileLocation("/public"); 

        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            
            
            
            model.put("name", "Anton");
            model.put("lastName", request.queryParams("lastname"));
            
            String q = "SELECT * FROM games WHERE genre='" + request.queryParams("filter") + "' ORDER BY " + request.queryParams("sort");
            model.put("dbQuery", q);
            
            List<Game> games = new ArrayList<>();            
            games.add(new Game("Star Wars: Battlefront 2", 2013));
            games.add(new Game("Fifa 19", 2018));
            
            model.put("games", games);
            
            // The vm files are located under the resources directory
            return new ModelAndView(model, "templates/index.html");
        }, new VelocityTemplateEngine());
        
        get("/game/:id", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            
            model.put("id", request.params("id"));
            
            if (Integer.parseInt(request.params("id")) == 1) {
            	model.put("game", new Game("Star Wars: Battlefront", 2013));
            } else if (Integer.parseInt(request.params("id")) == 2) {
            	model.put("game", new Game("Fifa 19", 2018));
            } else if (Integer.parseInt(request.params("id")) == 3) {
            	model.put("game", new Game("Halo", 2002));
            }
            
            // The vm files are located under the resources directory
            return new ModelAndView(model, "templates/game.html");
        }, new VelocityTemplateEngine());

    }
   
}