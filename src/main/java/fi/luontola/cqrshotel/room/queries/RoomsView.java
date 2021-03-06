// Copyright © 2016-2017 Esko Luontola
// This software is released under the Apache License 2.0.
// The license text is at http://www.apache.org/licenses/LICENSE-2.0

package fi.luontola.cqrshotel.room.queries;

import fi.luontola.cqrshotel.framework.EventListener;
import fi.luontola.cqrshotel.framework.EventStore;
import fi.luontola.cqrshotel.framework.Projection;
import fi.luontola.cqrshotel.room.events.RoomCreated;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class RoomsView extends Projection {

    private final ConcurrentMap<UUID, RoomDto> roomsById = new ConcurrentHashMap<>();

    public RoomsView(EventStore eventStore) {
        super(eventStore);
    }

    public List<RoomDto> findAll() {
        return new ArrayList<>(roomsById.values());
    }

    @EventListener
    public void apply(RoomCreated event) {
        RoomDto room = new RoomDto();
        room.roomId = event.roomId;
        room.number = event.number;
        roomsById.put(room.roomId, room);
    }
}
