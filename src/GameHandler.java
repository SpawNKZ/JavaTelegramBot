import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;

public class GameHandler {

    private static class Game {

        // for upcoming tutorial
        private ImageGuess guessGame = null;
    }

    // to play separate games on separate groups
    private static HashMap<Long, Game> games = new HashMap<Long, Game>();

    public static void check(Bot spawnkzBot, Update update) {
        // create a new game if this group doesn't have one already, otherwise grab from HashMap
        Game game = new Game();
        long chatId = update.getMessage().getChatId();
        if (games.containsKey(chatId))
            game = games.get(chatId);
        else {
            games.put(chatId, game);
        }

        // variables
        Message message = update.getMessage();
        String message_text = message.getText();
        String message_text_lower = message_text.toLowerCase();
        String username = message.getFrom().getUserName();

        // check if starting a new guess game
        if (message_text_lower.equals("/guess") || message_text_lower.equals("???? guess")) {
            // only create a new guess game if there isn't one
            if (game.guessGame == null) {
                game.guessGame = ImageGuess.random();
            }
            spawnkzBot.sendPhoto(Long.toString(chatId), game.guessGame.getUrl());
        }
        // check if guess game answered correctly
        else if (game.guessGame != null && message_text_lower.contains(game.guessGame.getAnswer())) {
            Database.getInstance().incrementGuessScore(username);
            // reset the guess game!
            game.guessGame = null;
            spawnkzBot.sendMessage(Long.toString(chatId), username + " has won!");
        } else if (message_text_lower.equals("/score")) {
            Database d = Database.getInstance();
            spawnkzBot.sendMessage(Long.toString(chatId), "Guess: " + d.getGuessScore(username));
        }
    }
}

