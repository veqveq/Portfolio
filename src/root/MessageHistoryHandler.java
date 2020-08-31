package root;

import root.GUI.ChatWindow;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class MessageHistoryHandler {

    private static String createPath(String sender,String getter){
        Path path1 = Paths.get(String.format("src\\root\\histories\\%s_%s.txt", sender,getter));
        Path path2 = Paths.get(String.format("src\\root\\histories\\%s_%s.txt", getter,sender));
        if (path1.toFile().exists()) {
            return path1.toString();
        }else{
            if (path2.toFile().exists()){
                return path2.toString();
            }
        }
        return path1.toString();
    }

    public static void writeHistory(String sender, ChatWindow.Client client, String message) {
        Path path = Paths.get(createPath(sender,client.getName()));
        try {
            Files.write(path, ("\n"+message).getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            createHistoryFile(sender,client);
            writeHistory(sender,client,message);
        }
    }

    public static String readMessage(String sender, ChatWindow.Client client){
        File historyFile = new File(createPath(sender,client.getName()));
        StringBuilder history = new StringBuilder();
        try {
            RandomAccessFile raf = new RandomAccessFile(historyFile, "r");
            long length = historyFile.length() - 1;
            int stringCounter = 100;
            int lines = 0;
            for (long i = length; i > 0; i--) {
                raf.seek(i);
                char symbol = (char) raf.read();
                if (symbol == '\n') lines += 1;
                if (lines == stringCounter) break;
                history.append(symbol);
            }
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return history.reverse().toString();
    }

    private static void createHistoryFile(String sender, ChatWindow.Client client) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Files.createFile(Path.of(String.format("src\\root\\histories\\%s_%s.txt",sender, client.getName())));
                } catch (IOException e) {
                }
            }
        }).start();
    }
}
