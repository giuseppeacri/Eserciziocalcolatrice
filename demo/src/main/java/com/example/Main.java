package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
        public static void main(String[] args) throws IOException {
            ServerSocket ss = new ServerSocket(3000);
            Socket s = ss.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            
            String operazione = " ";
            String temp1 = " ";
            String temp2 = " ";
            
            
            float risultato = 0; 


            while (!operazione.equals("exit")) {
                temp1 = in.readLine();
                temp2 = in.readLine();
                float n1=Float.parseFloat(temp1);
                float n2=Float.parseFloat(temp2);
                operazione = in.readLine();
                
                if (operazione.equals("-")){
                    risultato=n1-n2;
                }
                else if (operazione.equals("+")){
                    risultato=n1+n2;
                }
                else  if (operazione.equals("*")){
                    risultato=n1*n2;

                }
                else if (operazione.equals("/")){
                    if (n2==0){
                        System.out.println("ERRORE : DIVISIONE per 0");
                        break;
                    }else{
                        out.println(risultato);
                    }
                

                }

                out.println(risultato);

                
            }

            ss.close();

    }
}