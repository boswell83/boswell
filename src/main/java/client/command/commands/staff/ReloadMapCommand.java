/*
    This file is part of the HeavenMS MapleStory Server, commands OdinMS-based
    Copyleft (L) 2016 - 2018 RonanLana

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation version 3 as published by
    the Free Software Foundation. You may not use, modify or distribute
    this program under any other version of the GNU Affero General Public
    License.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

/*
   @Author: Arthur L - Refactored command content into modules
*/
package client.command.commands.staff;

import client.command.Command;
import client.MapleClient;
import client.MapleCharacter;
import server.maps.MapleMap;

import java.util.Collection;

public class ReloadMapCommand extends Command {
    {
        setDescription("");
    }

    @Override
    public void execute(MapleClient c, String[] params) {
        MapleCharacter player = c.getPlayer();
        MapleMap newMap = c.getChannelServer().getMapFactory().resetMap(player.getMapId());
        int callerid = c.getPlayer().getId();

        Collection<MapleCharacter> characters = player.getMap().getAllPlayers();
        
        for (MapleCharacter chr : characters) {
            chr.saveLocationOnWarp();
            chr.changeMap(newMap);
            if (chr.getId() != callerid)
                chr.dropMessage("You have been relocated due to map reloading. Sorry for the inconvenience.");
        }
        newMap.respawn();
    }
}
