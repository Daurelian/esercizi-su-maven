package TreeExpress;

import com.google.gson.Gson;
import e_commerce.Prodotto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static spark.Spark.*;
import static spark.Spark.get;

public class App {
    public static void main(String[] args) {
        //initialaiz user
        Map<Integer,User> userz= new TreeMap<>();

        User u1= new User(1,"n1","c1","v1");
        User u2= new User(2,"n2","c2","v2");
        User u3= new User(3,"n3","c3","v3");

        userz.put(u1.getId(),u1);
        userz.put(u2.getId(),u2);
        userz.put(u3.getId(),u3);

        Map<Integer,Order> orderz=new TreeMap<>();

        port(8081);

        path("/api/TreeExpress", () ->{

            get("/allusers", (req,res)->{
                res.type("application/json");
                return new Gson().toJsonTree(userz.values());
            });

            post("/adduser", (req, res)->{
                User newUser= new Gson().fromJson(req.body(), User.class);
                userz.put(newUser.getId(),newUser);
                res.type("application/json");
                return new Gson().toJson(newUser);

            });

            //elimina prodotto: rimuove un prodotto dalla lista dei prodotti disponibili
            get("/allorder", (req,res)->{
                res.type("application/json");
                return new Gson().toJsonTree(orderz.values());
            });
            delete("/deleteOrder", (req,res)->{
                int id= Integer.valueOf(req.queryParams("id"));
                if(!orderz.containsKey(id)|| orderz.get(id).getState()==State.Received)
                    return new Gson().toJson("Order Unavailable");
                Order orderRemove=orderz.remove(id);
                res.type("application/json");
                return new Gson().toJson(orderRemove);
            });

            // PLUS 🛵: acquista prodotto (specificando l'id del prodotto e la quantità)
            post("/addOrder",(req,res)->{
                //int id_s= Integer.valueOf(req.queryParams("id_send"));
                //int id_r= Integer.valueOf(req.queryParams("id_receiver"));
                //double size= Integer.valueOf(req.queryParams("weight"));
                Order nO= new Gson().fromJson(req.body(), Order.class);
                Order newOrder= new Order(nO.getId_send(), nO.getId_receiver(),nO.getWeight());
                //newOrder.setId();
                //newOrder.setState(State.Sending);
                if(!(userz.containsKey(newOrder.getId_send())&&userz.containsKey(newOrder.getId_receiver()))){
                    return new Gson().toJson("Users not found");
                }
                //Order newOrder= new Order(id_s,id_r,size);
                orderz.put(newOrder.getId(),newOrder);
                return new Gson().toJson("Order : "+newOrder.getId()+" from: "+userz.get(newOrder.getId_send()).getName()+" to: "+userz.get(newOrder.getId_receiver()).getName());
            });

            put("/setState",(req,res)->{
                int id=Integer.valueOf(req.queryParams("id"));
                if(!orderz.containsKey(id)||orderz.get(id).getState()==State.Received)
                    return new Gson().toJson("Order Unavailable");
                //Order upOrder= new Gson().fromJson(req.body(),Order.class);
                orderz.get(id).setState(State.Received);
                return new Gson().toJson("Order: "+id+" Received: "+(orderz.get(id).getState()==State.Received));
            });

            get("/ordertrans",(req,res)->{
                List<Order> transfer=new ArrayList<>();
                for(Order o:orderz.values())
                    if(o.getState()==State.Sending)
                        transfer.add(o);
                res.type("application/json");
                return new Gson().toJson(transfer);
            });

            get("/receiverorder",(req,res)->{
                int id=Integer.valueOf(req.queryParams("id"));
                if(!userz.containsKey(id))
                    return new Gson().toJson("Users not Found");

                List<Order> transfer=new ArrayList<>();
                for(Order o:orderz.values())
                    if(o.getId_receiver()==id)
                        transfer.add(o);
                if(transfer.size()==0)
                    return new Gson().toJson("User: "+userz.get(id).getName()+" Do not have any Order");
                res.type("application/json");
                return new Gson().toJson("User: "+userz.get(id).getName()+" \\n Is Receiver of: "+transfer);
            });
        });

    }
}
