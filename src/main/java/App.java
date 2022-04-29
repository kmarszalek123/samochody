import static spark.Spark.*;

import com.fasterxml.uuid.Generators;
import com.google.gson.Gson;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import spark.Request;
import spark.Response;

import java.io.FileOutputStream;
import java.util.*;

class App {
    public static void main(String[] args) {
        arr();
        staticFiles.location("/public");
        externalStaticFileLocation("C:\\appfolder\\src\\main\\resources\\public");
        post("/add", (req, res) -> add(req, res));
        post("/json", (req, res) -> json(req, res));
        post("/delete", (req, res) -> del(req, res));
        post("/update", (req, res) -> update(req, res));
        post("/faktura", (req, res) -> fakturaTrue(req, res));
        post("/losowo", (req, res) -> losowo());
        get("/", (req, res) -> {res.redirect("index.html"); return null;});
        get("/cars", (req, res) -> {res.redirect("update.html"); return null;});
        get("/admin", (req, res) -> {res.redirect("admin.html"); return null;});

    }
    static int ilosc = 1;
    static ArrayList<Car> samochody = new ArrayList<Car>();
    static String testFunction(Request req, Response res) {
        String test = "response from server";
        res.type("application/json");
        return test;
    }
    static String add(Request req, Response res) {
        Gson gson = new Gson();
        int id = ilosc;
        ilosc++;
        Car car = gson.fromJson(req.body(), Car.class);
        car.setUuid(Generators.randomBasedGenerator().generate());
        car.setId(id);
        car.setFaktura(false);
        samochody.add(car);
        res.type("application/json");
        return gson.toJson(car);
        }
    static String json(Request req, Response res) {
        Gson gson = new Gson();
        //ArrayList<String> json = new ArrayList<String>();
        Map<String, String> map = new HashMap<>();
        for (int a = 0; a < samochody.size(); a++){
           String tekst = gson.toJson(samochody.get(a));
           map.put(String.valueOf(a), tekst);
        }
        //map.put("dane", json);
        return gson.toJson(map);
    }
    static String del(Request req, Response res) {
        Gson gson = new Gson();
        Map<String,String> map = gson.fromJson(req.body(), Map.class);
        String uuid = map.get("uuid");
        for (int a = 0; a < samochody.size(); a++){
            if (Objects.equals(samochody.get(a).getUuid(), uuid)){
                samochody.remove(a);
            }
        }
        Map<String, String> map1 = new HashMap<>();
        for (int a = 0; a < samochody.size(); a++){
            String tekst = gson.toJson(samochody.get(a));
            map1.put(String.valueOf(a), tekst);
        }
        return gson.toJson(map1);
    }
    static String update(Request req, Response res) {
        Gson gson = new Gson();
        Map<String,String> map = gson.fromJson(req.body(), Map.class);
        String uuid = map.get("uuid");
        String rok = map.get("rok");
        String model = map.get("model");
        for (int a = 0; a < samochody.size(); a++){
            if (Objects.equals(samochody.get(a).getUuid(), uuid)){
                samochody.get(a).setRok(rok);
                samochody.get(a).setModel(model);
            }
        }
        Map<String, String> map1 = new HashMap<>();
        for (int a = 0; a < samochody.size(); a++){
            String tekst = gson.toJson(samochody.get(a));
            map1.put(String.valueOf(a), tekst);
        }
        return gson.toJson(map1);
    }
    static ArrayList<String> modele = new ArrayList<>();
    static ArrayList<String> roczniki = new ArrayList<String>();
    static ArrayList<String> kolory = new ArrayList<String>();
    static ArrayList<String> poduszkiTF = new ArrayList<String>();
    static ArrayList<String> poduszki = new ArrayList<String>();
    static void arr(){
        modele.add("Audi");
        modele.add("BMW");
        modele.add("Citroen");
        modele.add("Dacia");
        roczniki.add("1998");
        roczniki.add("1999");
        roczniki.add("2000");
        roczniki.add("2001");
        roczniki.add("2002");
        kolory.add("red");
        kolory.add("green");
        kolory.add("blue");
        poduszkiTF.add("true");
        poduszkiTF.add("false");
        poduszki.add("kierowca");
        poduszki.add("pasazer");
        poduszki.add("kanapa");
        poduszki.add("boczne");
    }

    static String losowo() {
        Gson gson = new Gson();
        for (int a = 0; a < 10; a++) {
            Random random2 = new Random();
            Random random3 = new Random();
            Random random4 = new Random();
            ArrayList <Map> arr = new ArrayList<>();
            for (int b = 0; b < 4; b++){
                Random random1 = new Random();
                int idPoduszki = random1.nextInt(2);
                Map<String, String> mapPoduszki= new HashMap<>();
                mapPoduszki.put(poduszki.get(b), poduszkiTF.get(idPoduszki));
                arr.add(mapPoduszki);
            }
            int idModel = random2.nextInt(4);
            int idRocznik = random3.nextInt(5);
            int idKolor = random4.nextInt(3);
            //String poduszki1 = poduszki.get(idPoduszki);
            String model1 = modele.get(idModel);
            String rocznik1 = roczniki.get(idRocznik);
            String kolor1 = kolory.get(idKolor);
            Car car = new Car(model1, rocznik1, arr, kolor1);
            car.setId(ilosc);
            car.setUuid(Generators.randomBasedGenerator().generate());
            ilosc++;
            car.setFaktura(false);
            samochody.add(car);

        }
        Map<String, String> map1 = new HashMap<>();
        for (int a = 0; a < samochody.size(); a++){
            String tekst = gson.toJson(samochody.get(a));
            map1.put(String.valueOf(a), tekst);
        }
        return gson.toJson(map1);

    }
    static String fakturaTrue(Request req, Response res) {
        Gson gson = new Gson();
        Map<String,String> map = gson.fromJson(req.body(), Map.class);
        String uuid = map.get("uuid");
        for (int a = 0; a < samochody.size(); a++){
            if (Objects.equals(samochody.get(a).getUuid(), uuid)){
                samochody.get(a).setFaktura(true);
            }
        }
        Map<String, String> map1 = new HashMap<>();
        for (int a = 0; a < samochody.size(); a++){
            String tekst = gson.toJson(samochody.get(a));
            map1.put(String.valueOf(a), tekst);
        }
        return gson.toJson(map1);
    }
    static void faktura(){
        //Document document = new Document(); // dokument pdf
        //String path = "katalog/plik.pdf"; // lokalizacja zapisu
        //try{
        //    PdfWriter.getInstance(document, new FileOutputStream(path));
        //}throws{}



        //document.open();
        //Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        //Chunk chunk = new Chunk("tekst", font); // akapit

        //document.add(chunk);
        //document.close();
    }
    static class Car {
        private String model;
        private String rok;
        private ArrayList<Map> poduszki;
        private String kolor;
        private int id;
        private UUID uuid;
        private boolean faktura;
        public Car(String model, String rok, ArrayList<Map> poduszki, String kolor) {
            this.model = model;
            this.rok = rok;
            this.poduszki = poduszki;
            this.kolor = kolor;
        }
        public void setId(int id) {
            this.id = id;
        }
        public void setUuid(UUID uuid) {
            this.uuid = uuid;
        }
        public void setRok(String rok) {
            this.rok = rok;
        }
        public void setModel(String model) {
            this.model = model;
        }

        public void setFaktura(boolean faktura) {
            this.faktura = faktura;
        }

        public String getUuid() {
            return String.valueOf(uuid);
        }

    }
}