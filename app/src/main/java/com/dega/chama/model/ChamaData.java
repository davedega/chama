package com.dega.chama.model;

import java.util.ArrayList;

/**
 * Created by davedega on 03/01/17.
 */

public class ChamaData {

    public static User fetchData() {
        User user = new User();
        user.setFollowers(3);
        user.setName("pepe");
        user.setFollowing(5);
        return user;
    }

    public static ArrayList<Repo> fetchRepos() {

        ArrayList<Repo> repos = new ArrayList<Repo>();
        repos.add(new Repo(8646, "cosas", "juan/cosas", "este es un repo para describir la " +
                "fruncionalidadde las aplicacione sde valparaiso", "https://avatars.githubusercontent.com/u/838391?v=3"));

        repos.add(new Repo(4567, "valparaiso-api", "juan/valparaiso-api", "Primer verison de la APi" +
                " que provee de los metosos necesariosn para interactuar con" +
                "el gestor de ....ntiene los webservices ", "https://avatars.githubusercontent.com/u/838391?v=3"));

        repos.add(new Repo(9876, "valparaiso-frontend", "juan/valparaiso-frontend", "contiene" +
                "el nuevo front end creado con React native, Express", "https://avatars.githubusercontent.com/u/838391?v=3"));

        repos.add(new Repo(74, "chama-app", "juan/chama-app", "App de ejemplo para la gente" +
                "de Chama donde se usa Rx y retrofit", "https://avatars.githubusercontent.com/u/838391?v=3"));

        repos.add(new Repo(876, "elements-app", "juan/elements-app", "Ejercicio en Android usando" +
                "RxJava, Retrofit para la empresa Elements", "https://avatars.githubusercontent.com/u/838391?v=3"));

        return repos;
    }
}
