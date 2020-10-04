package com.mycompany.ax13.nasa;

import java.util.Scanner;

public class Main {

    

    public static void main(String[] args) {
       // Chatbot bot = new Chatbot(APIKEY, ASSISTANTID, DATE);
        //System.out.println(bot.sendMessage(""));

        //conver(bot);
        //bot.finishSession();
    }

    public static void conver(Chatbot bot) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        while (!input.equals("::q")) {
            String respuesta = bot.sendMessage(input);
            System.out.println(respuesta);
            input = sc.nextLine();
        }
    }
}
