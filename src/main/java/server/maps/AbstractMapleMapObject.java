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
package server.maps;

import java.awt.Point;

public abstract class AbstractMapleMapObject implements MapleMapObject {

    private Point position = new Point();
    private int objectId;
    private int fh;

    @Override
    public void setFootHold(MapleFoothold fh) {
        if (fh == null) {
            this.fh = 0;
        } else {
            this.fh = fh.getId();
        }
    }

    @Override
    public void setFootHold(int fh) {
        this.fh = fh;
    }

    @Override
    public int getFootHold() {
        return fh;
    }

    @Override
    public abstract MapleMapObjectType getType();

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public void setPosition(Point position) {
        this.position.x = position.x;
        this.position.y = position.y;
    }

    @Override
    public int getObjectId() {
        return objectId;
    }

    @Override
    public void setObjectId(int id) {
        this.objectId = id;
    }

    @Override
    public void nullifyPosition() {
        this.position = null;
    }
}
