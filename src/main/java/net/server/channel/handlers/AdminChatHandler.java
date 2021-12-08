package net.server.channel.handlers;

import client.MapleClient;
import constants.ServerConstants;
import net.AbstractMaplePacketHandler;
import tools.LogHelper;
import tools.MaplePacketCreator;
import tools.data.input.SeekableLittleEndianAccessor;

/**
 *
 * @author kevintjuh93
 */
public class AdminChatHandler extends AbstractMaplePacketHandler {

    @Override
    public final void handlePacket(SeekableLittleEndianAccessor slea, MapleClient c) {
        if (!c.getPlayer().isGM()) {//if ( (signed int)CWvsContext::GetAdminLevel((void *)v294) > 2 )
            return;
        }
        byte mode = slea.readByte();
        //not saving slides...
        String message = slea.readMapleAsciiString();
        byte[] packet = MaplePacketCreator.serverNotice(slea.readByte(), message);//maybe I should make a check for the slea.readByte()... but I just hope gm's don't fuck things up :)
        switch (mode) {
            case 0:// /alertall, /noticeall, /slideall
                c.getWorldServer().broadcastPacket(packet);
                if (ServerConstants.USE_ENABLE_CHAT_LOG) {
                    LogHelper.logChat(c, "Alert All", message);
                }
                break;
            case 1:// /alertch, /noticech, /slidech
                c.getChannelServer().broadcastPacket(packet);
                if (ServerConstants.USE_ENABLE_CHAT_LOG) {
                    LogHelper.logChat(c, "Alert Ch", message);
                }
                break;
            case 2:// /alertm /alertmap, /noticem /noticemap, /slidem /slidemap
                c.getPlayer().getMap().broadcastMessage(packet);
                if (ServerConstants.USE_ENABLE_CHAT_LOG) {
                    LogHelper.logChat(c, "Alert Map", message);
                }
                break;

        }
    }
}
