package com.mycompany.ax13.nasa;

import java.util.Scanner;

public class Main {

    private static final String APIKEY = "ZCHogALy6CdBNr-qJoWMAkI5HAL0ZhfTwtIVAf1Hgjoc";

    private static final String DATE = "2020-10-03";

    private static final String ASSISTANTID = "95964ca4-2fdb-4866-bd3d-41a6aaabf0e4";

    public static void main(String[] args) {
        Chatbot bot = new Chatbot(APIKEY, ASSISTANTID, DATE);
        System.out.println(bot.sendMessage(""));

        conver(bot);
        bot.finishSession();
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
