package e_commerce;

import static spark.Spark.*;
import com.google.gson.Gson;
import org.example.model.User;

import java.util.HashMap;
import java.util.Map;

public class App {
    /*
    In particolare, devono essere esposte tramite API le seguenti operazioni:

- aggiungi prodotto: aggiunge un prodotto alla lista dei prodotti disponibili
- elimina prodotto: rimuove un prodotto dalla lista dei prodotti disponibili
- elenco prodotti disponibili: ritorna la lista dei prodotti disponibili
- PLUS 🛵: acquista prodotto (specificando l'id del prodotto e la quantità)

Utilizzate i giusti codici di stato per gestire ad esempio prodotti inesistenti, stock
terminato ecc.
     */
    public static void main(String[] args) {
        //Inizializzazione
        Prodotto maglia1= new Prodotto(1,"maglia1","una bellissima maglia1",33,6.0);
        Prodotto maglia2= new Prodotto(2,"maglia2","una bellissima maglia2",33,6.0);
        Prodotto maglia3= new Prodotto(3,"maglia3","una bellissima maglia3",33,6.0);
        Prodotto maglia4= new Prodotto(4,"maglia4","una bellissima maglia4",33,6.0);

        //creiamo una mappa
        Map<Integer,Prodotto> products= new HashMap<>();
        products.put(maglia1.getId(),maglia1);
        products.put(maglia2.getId(),maglia2);
        products.put(maglia3.getId(),maglia3);
        products.put(maglia4.getId(),maglia4);
        //- elenco prodotti disponibili: ritorna la lista dei prodotti disponibili
        port(8081);

        path("/api/e_commerce", () ->{

            get("/allproducts", (req,res)->{
                res.type("application/json");
                return new Gson().toJsonTree(products.values());
            });

            post("/addproduct", (req, res)->{
                Prodotto prodottoCreato= new Gson().fromJson(req.body(), Prodotto.class);
                products.put(prodottoCreato.getId(),prodottoCreato);
                res.type("application/json");
                return new Gson().toJson(prodottoCreato);

            });

            //elimina prodotto: rimuove un prodotto dalla lista dei prodotti disponibili

            delete("/delete", (req,res)->{
                int id= Integer.valueOf(req.queryParams("id"));
                Prodotto prodottoRimosso=products.remove(id);
                res.type("application/json");
                return new Gson().toJson(prodottoRimosso);
            });

            // PLUS 🛵: acquista prodotto (specificando l'id del prodotto e la quantità)
            get("/buy",(req,res)->{
                int id= Integer.valueOf(req.queryParams("id"));
                int stock= Integer.valueOf(req.queryParams("stock"));
                //if
                if((products.get(id).getStock()-stock)<0){
                    int quantità_venduta= products.get(id).getStock();
                    products.get(id).setStock(0);
                    return new Gson().toJson("Ne hai acquistati solo: " + quantità_venduta +" su "+ stock);
                }
                products.get(id).setStock(products.get(id).getStock()-stock);
                return new Gson().toJson("Prodotto : "+id+" acquistato, in quantità: "+stock);
            });

        });



        /*
         * TODO:
         *  1. Implementare ricerca prodotto per ID e per name
         *  2. Aggiungere persistenza (DB)
         *  3. Bonus: modificare il codice per aggiungere JUnit (es creare classe DummyDB dove
         * gestire i prodotti e testare quella classe)
         * */


//        get("/allproducts", (req,res)->{
//            res.type("application/json");
//            return new Gson().toJsonTree(products.values());
//        });

    }
}
