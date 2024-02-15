package client;

import server.ServerController;

/**
 * This class creates the ClientController object and starts the client.
 *
 * @author Carolin Nordstrom
 * @version 1.0
 */
public class StartClient {

  public static void main(String[] args) {
    ClientController client = new ClientController();
    ServerController serverController = new ServerController();
  }
}
