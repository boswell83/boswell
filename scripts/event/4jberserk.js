/*
	This file is part of the OdinMS Maple Story Server
    Copyright (C) 2008 Patrick Huy <patrick.huy@frz.cc>
		       Matthias Butz <matze@odinms.de>
		       Jan Christian Meyer <vimes@odinms.de>

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
var exitMap = 105090800;
var minPlayers = 3;

function init() {}

function monsterValue(eim, mobId) {
    return 1;
}

function setup(level, lobbyid) {
    exitMap = em.getChannelServer().getMapFactory().getMap(105090800); // <exit>
    
    var eim = em.newInstance("4jberserk_" + lobbyid);
    eim.setProperty("level", level);
	
    var mf = eim.getMapFactory();
    
    var map = mf.getMap(910500200);
    map.addMapTimer(3*60);
    em.schedule("timeOut", 20 * 60000);

    //you can't warp up to the rocks until all rogs are dead, I think?
    eim.setProperty("canWarp","false");
	
    return eim;
}

function afterSetup(eim) {}

function playerEntry(eim, player) {
    var map = eim.getMapInstance(910500200);
    player.changeMap(map, map.getPortal(0));
	
//TODO: hold time across map changes
//player.getClient().announce(tools.MaplePacketCreator.getClock(1800));
}

function playerDead(eim, player) {
}

function playerRevive(eim, player) {
    //if (eim.isLeader(player)) { //check for party leader
    //boot whole party and end
    var party = eim.getPlayers();
    for (var i = 0; i < party.size(); i++) {
        playerExit(eim, party.get(i));
    }
    eim.dispose();
/*/}
	else { //boot dead player
		// If only 2 players are left, uncompletable:
		var party = eim.getPlayers();
		if (party.size() <= minPlayers) {
			for (var i = 0; i < party.size(); i++) {
				playerExit(eim,party.get(i));
			}
			eim.dispose();
		}
		else
			playerExit(eim, player);
	}*/
}

function playerDisconnected(eim, player) {
    //if (eim.isLeader(player)) { //check for party leader
    //boot whole party and end
    var party = eim.getPlayers();
    for (var i = 0; i < party.size(); i++) {
        if (party.get(i).equals(player)) {
            removePlayer(eim, player);
        }
        else {
            playerExit(eim, party.get(i));
        }
    }
    eim.dispose();
/*/}
	else { //boot d/ced player
		// If only 2 players are left, uncompletable:
		var party = eim.getPlayers();
		if (party.size() < minPlayers) {
			for (var i = 0; i < party.size(); i++) {
				playerExit(eim,party.get(i));
			}
			eim.dispose();
		}
		else
			playerExit(eim, player);
	}*/
}

function leftParty(eim, player) {			
    // If only 2 players are left, uncompletable:
    var party = eim.getPlayers();
    if (true) {
        for (var i = 0; i < party.size(); i++) {
            playerExit(eim,party.get(i));
        }
        eim.dispose();
    }
    else
        playerExit(eim, player);
}

function disbandParty(eim) {
    //boot whole party and end
    var party = eim.getPlayers();
    for (var i = 0; i < party.size(); i++) {
        playerExit(eim, party.get(i));
    }
    eim.dispose();
}

function playerUnregistered(eim, player) {}

function playerExit(eim, player) {
    eim.unregisterPlayer(player);
    player.changeMap(exitMap, exitMap.getPortal(0));
}

//for offline players
function removePlayer(eim, player) {
    eim.unregisterPlayer(player);
    player.getMap().removePlayer(player);
    player.setMap(exitMap);
}

function clearPQ(eim) {
    var party = eim.getPlayers();
    for (var i = 0; i < party.size(); i++)
        playerExit(eim, party.get(i));
    eim.dispose();
}

function monsterKilled(mob, eim) {}

function allMonstersDead(eim) {
    eim.setProperty("canWarp","true");
}

function cancelSchedule() {}

function timeOut() {
    var iter = em.getInstances().iterator();
    while (iter.hasNext()) {
        var eim = iter.next();
        if (eim.getPlayerCount() > 0) {
            var pIter = eim.getPlayers().iterator();
            while (pIter.hasNext())
                playerExit(eim, pIter.next());
        }
        eim.dispose();
    }
}


// ---------- FILLER FUNCTIONS ----------

function dispose() {}

function scheduledTimeout(eim) {}

function changedLeader(eim, leader) {}

