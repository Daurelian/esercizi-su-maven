package org.example;

import com.google.gson.Gson;
import org.example.model.User;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;
/**
 * Hello world!
 *
 */
public class App
{
    private static Map<Integer,User> users= new HashMap<>();
    public static void main( String[] args )
    {
        User u1= new User(1,"giggino1", "giggimodm1.gmc.cc");
        User u2= new User(2,"giggino2", "giggimodm2.gmc.cc");
        User u3= new User(3,"giggino3", "giggimodm3.gmc.cc");
        users.put(1,u1);
        users.put(2,u2);
        users.put(3,u3);

        //
        System.out.println( "Hello World!" );
        port(8080);
        get("/helloworld",(req,res)->{return "Hello World! Giggino!";});

        get("/user",(req,res)->{
            int userId= Integer.valueOf(req.queryParams("id"));
            String username= req.queryParams("username");
            User u4= new User(userId, username, "giggino@gm.cc");
            //if id=0....
            if(userId==0){
                res.status(404);
                return  new Gson().toJson("User not found");
            }
            res.type("application/json");
            return new Gson().toJson(u4);});

        get("/users",(req,res)->
        {
            res.type("application/json");
            return new Gson().toJsonTree(users.values());
        });

        delete("/user", (req,res)->{
            int userId= Integer.valueOf(req.queryParams("id"));
            User userDeleted= users.remove(userId);
            res.type("application/json");
            return new Gson().toJson(userDeleted); //nn obbligatorio il return
        });

        post("/user", (req,res)->{
            //req.body-> dal body della richiesta
            //User.class-> classe per mapping
            User newUser= new Gson().fromJson(req.body(),User.class);
            //TODO newUser.setID(randomico)
            newUser.setId(4);
            users.put(newUser.getId(), newUser);
            res.type("application/json");
            return new Gson().toJson(newUser); //nn obbligatorio il return
        });

        put("/user", (req, res)->{

            User user2update = new Gson().fromJson(req.body(), User.class);
            users.put(user2update.getId(), user2update);

            res.type("application/json");
            return new Gson().toJson(user2update);
        });


    }


}
