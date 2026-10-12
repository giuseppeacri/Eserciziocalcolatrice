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
        System.out.println("Server Calcolatrice Multithread avviato sulla porta 3000...");
        
        while (true) {
            try {
                Socket s = ss.accept();
                System.out.println("Nuovo client connesso da: " + s.getRemoteSocketAddress());

                
                Thread clientThread = new Thread(new ClientHandler(s));
                clientThread.start();

            } catch (IOException e) {
                System.err.println("Errore nell'accettare la connessione: " + e.getMessage());
            }
        }
        
    }

    
    private static class ClientHandler implements Runnable {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
           
            try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
            ) {
                String operazione = " ";
                String temp1 = " ";
                String temp2 = " ";
                float risultato = 0;

              
                while (!operazione.equals("exit")) {
                    temp1 = in.readLine();
                    if (temp1 == null) break; 
                    
                    temp2 = in.readLine();
                    if (temp2 == null) break;

                    float n1 = Float.parseFloat(temp1);
                    float n2 = Float.parseFloat(temp2);
                    
                    operazione = in.readLine();
                    if (operazione == null || operazione.equalsIgnoreCase("exit")) {
                        break;
                    }

                    boolean erroreDivisione = false;

                    if (operazione.equals("-")) {
                        risultato = n1 - n2;
                    } else if (operazione.equals("+")) {
                        risultato = n1 + n2;
                    } else if (operazione.equals("*")) {
                        risultato = n1 * n2;
                    } else if (operazione.equals("/")) {
                        if (n2 == 0) {
                            out.println("ERRORE: DIVISIONE per 0");
                            erroreDivisione = true;
                        } else {
                            risultato = n1 / n2;
                        }
                    }

                    
                    if (!erroreDivisione) {
                        out.println(risultato);
                    }
                }

            } catch (IOException e) {
                System.out.println("Connessione interrotta con un client.");
            } catch (NumberFormatException e) {
                System.out.println("Il client ha inviato dati non numerici.");
            } finally {
                try {
                    socket.close();
                    System.out.println("Connessione chiusa con il client.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
