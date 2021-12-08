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
package client.command.commands.player;

import client.command.Command;
import client.MapleClient;
import client.MapleCharacter;
import constants.ServerConstants;

public class StatIntCommand extends Command {
    {
        setDescription("<amount>");
    }

    @Override
    public void execute(MapleClient c, String[] params) {
        MapleCharacter player = c.getPlayer();
        int remainingAp = player.getRemainingAp();

        int amount;
        if (params.length > 0) {
            try {
                amount = Math.min(Integer.parseInt(params[0]), remainingAp);
            } catch (NumberFormatException e) {
                player.dropMessage("That is not a valid number!");
                return;
            }
        } else {
            amount = Math.min(remainingAp, ServerConstants.MAX_AP - player.getInt());
        }
        if (!player.assignInt(Math.max(amount, 0))) {
            player.dropMessage("Please make sure your AP is not over " + ServerConstants.MAX_AP + " and you have enough to distribute.");
        }
    }
}
