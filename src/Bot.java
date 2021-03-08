import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Bot extends TelegramLongPollingBot {
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            // NEW:
            GameHandler.check(this, update);
        }
    }

    private void log(String username, String chatId, String textReceived, String botResponse) {
        System.out.println("----------------------------\n");
        // print out in PM/AM time
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd h:mm a");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
        System.out.println("Message from " + username + ". (id = " + chatId + ") \n Text - " + textReceived);
        System.out.println("Bot answer: \n Text - " + botResponse);
    }

    public void sendPhoto(String chatId, String url) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(url);

        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String chatId, String message) {
        SendMessage sendMessageRequest = new SendMessage();
        sendMessageRequest.setChatId(chatId);
        sendMessageRequest.setText(message);
        try {
            execute(sendMessageRequest);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "Bot";
    }

    @Override
    public String getBotToken() {
        // Return bot token from BotFather
        return "1683507511:AAFRWBpjFCeZDJc__IJ92VfyIpAQT0tVv1g";
    }
}