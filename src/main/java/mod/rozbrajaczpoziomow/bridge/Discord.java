package mod.rozbrajaczpoziomow.bridge;

import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.channel.Channel;
import discord4j.core.object.entity.channel.TextChannel;

public class Discord {
    private final DiscordClient client;
    private final GatewayDiscordClient gateway;
    private final TextChannel channel;

    public Discord(String token, String channelId) {
        client = DiscordClient.create(token);
        gateway = client.login().block();
        assert gateway != null;

        Channel channel = gateway.getChannelById(Snowflake.of(channelId)).block();
        assert channel != null && channel.getType() == Channel.Type.GUILD_TEXT;
        this.channel = (TextChannel) channel;

        gateway.on(MessageCreateEvent.class).subscribe(this::messageReceive);
    }

    private void messageReceive(MessageCreateEvent event) {
        if(!event.getMessage().getChannelId().equals(channel.getId())) return;
        // TODO: send message to minecraft chat...somehow.
    }

    public void messageSend(String str) {
        channel.createMessage(str); // don't block here, no reason to.
    }

    public void goodbye() {
        gateway.logout().block();
    }
}
