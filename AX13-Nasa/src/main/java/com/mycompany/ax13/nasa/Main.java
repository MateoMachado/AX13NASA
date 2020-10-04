package com.mycompany.ax13.nasa;

import java.util.Scanner;

public class Main {

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
