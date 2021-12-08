/*
    This file is part of the HeavenMS MapleStory Server
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
/* Oasis near Ariant Castle
 */

importPackage(Packages.client);

var reward = new Array(
        ["You take a drink of water and feel replenished... but that's about it.", 0],
        ["The vision of a bag of Mesos appears in the water and you feel a bit richer.", 0], // meso
        ["It looks like the outline of a trophy is visible from the water. You should grab them quick!", 4008002], //trophy
        ["Woah, the Red Bandits must have been hiding all their trophies here. I dont think they'd mind if you take some.", 4008002]); // trophy

function isTigunMorphed(ch) {
        return ch.getBuffSource(MapleBuffStat.MORPH) == 2210005;
}

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode < 0)
        cm.dispose();
    else {
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0 && mode == 1) {
                if(cm.isQuestStarted(3900) && cm.getPlayer().getQuestInfo(3900) != 5) {
                        cm.sendOk("#b(You drink the water from the oasis and feel refreshed.)", 2);
                        cm.getPlayer().updateQuestInfo(3900, "5");
                } else if(cm.isQuestCompleted(3938)) {
                        if(cm.canHold(2210005)) {
                                if(!cm.haveItem(2210005) && !isTigunMorphed(cm.getPlayer())) {
                                        cm.gainItem(2210005, 1);
                                        cm.sendOk("You found a lock of hair (probably Tigun's) floating by the water and catched it. Remembering how #bJano#k made it last time, you crafted a new #t2210005#", 2);
                                }
                        } else {
                                cm.sendOk("You don't have a USE slot available.", 2);
                        }
                        cm.dispose();
                } else if(cm.isQuestStarted(3934) || (cm.isQuestCompleted(3934) && !cm.isQuestCompleted(3935))) {
                        if(cm.canHold(2210005)) {
                                if(!cm.haveItem(2210005) && !isTigunMorphed(cm.getPlayer())) {
                                        cm.gainItem(2210005, 1);
                                        cm.sendOk("You managed to find a strange flask floating on the river. It seems like a transformation bottle mimicking one of the guards of the castle, maybe with it you will be able to roam inside freely.", 2);
                                }
                        } else {
                                cm.sendOk("You found a strange flask floating on the river. But you decided to ignore it since you don't have a USE slot available.", 2);
                        }
                        cm.dispose();
                } else { // perform all quest checks first then prioritize daily challenge
                        if (!cm.hasDailyEntry("OASIS")) {       
                                cm.sendYesNo("#d#eBoswell Daily#k\r\n \r\n#nThis Oasis is exactly what you need to beat this intense heat! Would you like to look into the water?");
                        } else {
                                cm.sendOk("You have already completed this daily challenge!");
                        }
                }
         } else if (status == 1) {
                if (mode == 0) {//decline
                        //cm.sendNext("no");
                } else {
                        var num = ~~(Math.random() * 4);
                        var amount =  num == 3 ? 25 : 10;
                        cm.sendNext("It's way too hot to not check out this Oasis! \r\n \r\n #e" + reward[num][0] + "#n");
                        switch(num) {
                            case 1:
                                cm.gainMeso(50000);
                                break;
                            case 2:
                            case 3: 
                                cm.gainItem(reward[num][1], amount);
                                break;
                        }
                cm.completeDaily("OASIS", false);
                }
        }
    }
}