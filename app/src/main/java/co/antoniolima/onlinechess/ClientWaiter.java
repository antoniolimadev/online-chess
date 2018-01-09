package co.antoniolima.onlinechess;

import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static android.content.ContentValues.TAG;

public class ClientWaiter  implements Runnable {

    protected GameController gameController;
    protected Socket toClientSocket;
    protected Boolean connected;
    protected ObjectInputStream input;
    protected ObjectOutputStream output;

    public ClientWaiter(Socket clientSocket, GameController gameController) {

        this.gameController = gameController;
        this.toClientSocket = clientSocket;
        this.connected = true;
        try {
            input = new ObjectInputStream(clientSocket.getInputStream());
            output = new ObjectOutputStream(clientSocket.getOutputStream());

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void run() {

        Log.i(TAG, "Cliente ligado - " +
                toClientSocket.getInetAddress().getHostAddress() +
                ":" + toClientSocket.getPort());

        try {
            while (connected) {

                Message novaJogada = (Message) input.readObject();
                if (novaJogada.getPosition() != 1337){
                    gameController.selectPosition(novaJogada.getPosition());
                    gameController.updateBoard();
                }
                int teste = 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
