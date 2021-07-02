import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import javax.security.auth.login.LoginException;

public class Bot extends ListenerAdapter {

    private static Bot bot;
    public static String channelID;
    public static String token;
    public static boolean isReady;

    private JDA jda;

    public static Bot getBot() {
        if (bot == null)
            bot = new Bot();
        return bot;
    }

    private Bot() {
        try {
            this.jda = JDABuilder.createDefault(token).build();
            jda.addEventListener(this);
        }  catch (LoginException e) {
            System.out.println("Discord Login Issue");
        }
    }

    public void sendMessage(String message) {
        jda.getTextChannelById((String) ServerListener.json.get("channelID")).sendMessage(message).queue();
    }

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        System.out.println("ready");
        isReady = true;
    }
}
