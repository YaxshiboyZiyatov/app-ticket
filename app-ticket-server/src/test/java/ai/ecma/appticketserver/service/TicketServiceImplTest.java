package ai.ecma.appticketserver.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ai.ecma.appticketserver.entity.Attachment;
import ai.ecma.appticketserver.entity.BronTariff;
import ai.ecma.appticketserver.entity.Event;
import ai.ecma.appticketserver.entity.EventSession;
import ai.ecma.appticketserver.entity.Place;
import ai.ecma.appticketserver.entity.Role;
import ai.ecma.appticketserver.entity.Ticket;
import ai.ecma.appticketserver.entity.User;
import ai.ecma.appticketserver.enums.PermissionEnum;
import ai.ecma.appticketserver.enums.RoleTypeEnum;
import ai.ecma.appticketserver.enums.SeatStatusEnum;
import ai.ecma.appticketserver.exception.RestException;
import ai.ecma.appticketserver.payload.ApiResult;
import ai.ecma.appticketserver.payload.TicketBuyReqDto;
import ai.ecma.appticketserver.payload.TicketResDto;
import ai.ecma.appticketserver.repository.EventSessionRepository;
import ai.ecma.appticketserver.repository.TicketRepository;
import ai.ecma.appticketserver.repository.UserRepository;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {TicketServiceImpl.class})
@ExtendWith(SpringExtension.class)
class TicketServiceImplTest {
    @MockBean
    private EventSessionRepository eventSessionRepository;

    @MockBean
    private TicketRepository ticketRepository;

    @Autowired
    private TicketServiceImpl ticketServiceImpl;

    @MockBean
    private UserRepository userRepository;

    @Test
    void testBuyTicket() {
        Role role = new Role();
        role.setId(UUID.randomUUID());
        role.setUpdatedAt(mock(Timestamp.class));
        role.setPermission(new HashSet<PermissionEnum>());
        role.setCreatedById(UUID.randomUUID());
        role.setCreatedAt(mock(Timestamp.class));
        role.setDeleted(true);
        role.setName("Name");
        role.setRoleType(RoleTypeEnum.STAFF);
        role.setDescription("The characteristics of someone or something");
        role.setUpdatedById(UUID.randomUUID());

        User user = new User();
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setId(UUID.randomUUID());
        user.setUpdatedAt(mock(Timestamp.class));
        user.setAccountNonLocked(true);
        user.setCreatedById(UUID.randomUUID());
        user.setCredentialsNonExpired(true);
        user.setDeleted(true);
        user.setEnabled(true);
        user.setPhoneNumber("4105551212");
        user.setRole(role);
        user.setFirstName("Jane");
        user.setAccountNonExpired(true);
        user.setCreatedAt(mock(Timestamp.class));
        user.setUpdatedById(UUID.randomUUID());
        Optional<User> ofResult = Optional.<User>of(user);
        when(this.userRepository.findById((UUID) any())).thenReturn(ofResult);

        Role role1 = new Role();
        role1.setId(UUID.randomUUID());
        role1.setUpdatedAt(mock(Timestamp.class));
        role1.setPermission(new HashSet<PermissionEnum>());
        role1.setCreatedById(UUID.randomUUID());
        role1.setCreatedAt(mock(Timestamp.class));
        role1.setDeleted(true);
        role1.setName("Name");
        role1.setRoleType(RoleTypeEnum.STAFF);
        role1.setDescription("The characteristics of someone or something");
        role1.setUpdatedById(UUID.randomUUID());

        User user1 = new User();
        user1.setLastName("Doe");
        user1.setPassword("iloveyou");
        user1.setId(UUID.randomUUID());
        user1.setUpdatedAt(mock(Timestamp.class));
        user1.setAccountNonLocked(true);
        user1.setCreatedById(UUID.randomUUID());
        user1.setCredentialsNonExpired(true);
        user1.setDeleted(true);
        user1.setEnabled(true);
        user1.setPhoneNumber("4105551212");
        user1.setRole(role1);
        user1.setFirstName("Jane");
        user1.setAccountNonExpired(true);
        user1.setCreatedAt(mock(Timestamp.class));
        user1.setUpdatedById(UUID.randomUUID());

        Attachment attachment = new Attachment();
        attachment.setId(UUID.randomUUID());
        attachment.setUpdatedAt(mock(Timestamp.class));
        attachment.setContentType("text/plain");
        attachment.setCreatedById(UUID.randomUUID());
        attachment.setCreatedAt(mock(Timestamp.class));
        attachment.setDeleted(true);
        attachment.setName("Name");
        attachment.setUpdatedById(UUID.randomUUID());
        attachment.setSize(3L);

        Attachment attachment1 = new Attachment();
        attachment1.setId(UUID.randomUUID());
        attachment1.setUpdatedAt(mock(Timestamp.class));
        attachment1.setContentType("text/plain");
        attachment1.setCreatedById(UUID.randomUUID());
        attachment1.setCreatedAt(mock(Timestamp.class));
        attachment1.setDeleted(true);
        attachment1.setName("Name");
        attachment1.setUpdatedById(UUID.randomUUID());
        attachment1.setSize(3L);

        Place place = new Place();
        place.setId(UUID.randomUUID());
        place.setUpdatedAt(mock(Timestamp.class));
        place.setCreatedById(UUID.randomUUID());
        place.setCreatedAt(mock(Timestamp.class));
        place.setDeleted(true);
        place.setLat(10.0);
        place.setAddress("42 Main St");
        place.setLon(10.0);
        place.setUpdatedById(UUID.randomUUID());

        Event event = new Event();
        event.setId(UUID.randomUUID());
        event.setUpdatedAt(mock(Timestamp.class));
        event.setCreatedById(UUID.randomUUID());
        event.setCreatedAt(mock(Timestamp.class));
        event.setBanner(attachment);
        event.setDeleted(true);
        event.setName("Name");
        event.setDescription("The characteristics of someone or something");
        event.setUpdatedById(UUID.randomUUID());
        event.setSchema(attachment1);
        event.setPlace(place);

        BronTariff bronTariff = new BronTariff();
        bronTariff.setId(UUID.randomUUID());
        bronTariff.setUpdatedAt(mock(Timestamp.class));
        bronTariff.setLifetime(1);
        bronTariff.setCreatedById(UUID.randomUUID());
        bronTariff.setPercent(1);
        bronTariff.setCreatedAt(mock(Timestamp.class));
        bronTariff.setDisabled(true);
        bronTariff.setDeleted(true);
        bronTariff.setUpdatedById(UUID.randomUUID());

        EventSession eventSession = new EventSession();
        eventSession.setEndTime(mock(Timestamp.class));
        eventSession.setId(UUID.randomUUID());
        eventSession.setUpdatedAt(mock(Timestamp.class));
        eventSession.setEvent(event);
        eventSession.setCreatedById(UUID.randomUUID());
        eventSession.setCreatedAt(mock(Timestamp.class));
        eventSession.setDeleted(true);
        eventSession.setStartTime(mock(Timestamp.class));
        eventSession.setUpdatedById(UUID.randomUUID());
        eventSession.setBronTariff(bronTariff);

        Ticket ticket = new Ticket();
        ticket.setId(UUID.randomUUID());
        ticket.setUpdatedAt(mock(Timestamp.class));
        ticket.setCreatedById(UUID.randomUUID());
        ticket.setUser(user1);
        ticket.setEventSession(eventSession);
        ticket.setDeleted(true);
        ticket.setPrice(10.0);
        ticket.setStatusEnum(SeatStatusEnum.VACANT);
        ticket.setRow(1);
        ticket.setCreatedAt(mock(Timestamp.class));
        ticket.setSoldTime(mock(Timestamp.class));
        ticket.setBookedTime(mock(Timestamp.class));
        ticket.setTicketUsed(true);
        ticket.setSection("Section");
        ticket.setUpdatedById(UUID.randomUUID());
        ticket.setSeatName("Seat Name");
        Optional<Ticket> ofResult1 = Optional.<Ticket>of(ticket);
        when(this.ticketRepository.findById((UUID) any())).thenReturn(ofResult1);

        Attachment attachment2 = new Attachment();
        attachment2.setId(UUID.randomUUID());
        attachment2.setUpdatedAt(mock(Timestamp.class));
        attachment2.setContentType("text/plain");
        attachment2.setCreatedById(UUID.randomUUID());
        attachment2.setCreatedAt(mock(Timestamp.class));
        attachment2.setDeleted(true);
        attachment2.setName("Name");
        attachment2.setUpdatedById(UUID.randomUUID());
        attachment2.setSize(3L);

        Attachment attachment3 = new Attachment();
        attachment3.setId(UUID.randomUUID());
        attachment3.setUpdatedAt(mock(Timestamp.class));
        attachment3.setContentType("text/plain");
        attachment3.setCreatedById(UUID.randomUUID());
        attachment3.setCreatedAt(mock(Timestamp.class));
        attachment3.setDeleted(true);
        attachment3.setName("Name");
        attachment3.setUpdatedById(UUID.randomUUID());
        attachment3.setSize(3L);

        Place place1 = new Place();
        place1.setId(UUID.randomUUID());
        place1.setUpdatedAt(mock(Timestamp.class));
        place1.setCreatedById(UUID.randomUUID());
        place1.setCreatedAt(mock(Timestamp.class));
        place1.setDeleted(true);
        place1.setLat(10.0);
        place1.setAddress("42 Main St");
        place1.setLon(10.0);
        place1.setUpdatedById(UUID.randomUUID());

        Event event1 = new Event();
        event1.setId(UUID.randomUUID());
        event1.setUpdatedAt(mock(Timestamp.class));
        event1.setCreatedById(UUID.randomUUID());
        event1.setCreatedAt(mock(Timestamp.class));
        event1.setBanner(attachment2);
        event1.setDeleted(true);
        event1.setName("Name");
        event1.setDescription("The characteristics of someone or something");
        event1.setUpdatedById(UUID.randomUUID());
        event1.setSchema(attachment3);
        event1.setPlace(place1);

        BronTariff bronTariff1 = new BronTariff();
        bronTariff1.setId(UUID.randomUUID());
        bronTariff1.setUpdatedAt(mock(Timestamp.class));
        bronTariff1.setLifetime(1);
        bronTariff1.setCreatedById(UUID.randomUUID());
        bronTariff1.setPercent(1);
        bronTariff1.setCreatedAt(mock(Timestamp.class));
        bronTariff1.setDisabled(true);
        bronTariff1.setDeleted(true);
        bronTariff1.setUpdatedById(UUID.randomUUID());

        EventSession eventSession1 = new EventSession();
        eventSession1.setEndTime(mock(Timestamp.class));
        eventSession1.setId(UUID.randomUUID());
        eventSession1.setUpdatedAt(mock(Timestamp.class));
        eventSession1.setEvent(event1);
        eventSession1.setCreatedById(UUID.randomUUID());
        eventSession1.setCreatedAt(mock(Timestamp.class));
        eventSession1.setDeleted(true);
        eventSession1.setStartTime(mock(Timestamp.class));
        eventSession1.setUpdatedById(UUID.randomUUID());
        eventSession1.setBronTariff(bronTariff1);
        Optional<EventSession> ofResult2 = Optional.<EventSession>of(eventSession1);
        when(this.eventSessionRepository.findById((UUID) any())).thenReturn(ofResult2);

        TicketBuyReqDto ticketBuyReqDto = new TicketBuyReqDto();
        ticketBuyReqDto.setTicketId(UUID.randomUUID());
        ticketBuyReqDto.setUserId(UUID.randomUUID());
        ticketBuyReqDto.setEventSessionId(UUID.randomUUID());
        ApiResult<TicketResDto> actualBuyTicketResult = this.ticketServiceImpl.BuyTicket(ticketBuyReqDto);
        assertEquals("This ticket has already booked bratan", actualBuyTicketResult.getMessage());
        assertFalse(actualBuyTicketResult.isSuccess());
        assertNull(actualBuyTicketResult.getData());
        verify(this.userRepository).findById((UUID) any());
        verify(this.ticketRepository).findById((UUID) any());
        verify(this.eventSessionRepository).findById((UUID) any());
    }

    @Test
    void testBuyTicket2() {
        when(this.userRepository.findById((UUID) any())).thenReturn(Optional.<User>empty());

        Role role = new Role();
        role.setId(UUID.randomUUID());
        role.setUpdatedAt(mock(Timestamp.class));
        role.setPermission(new HashSet<PermissionEnum>());
        role.setCreatedById(UUID.randomUUID());
        role.setCreatedAt(mock(Timestamp.class));
        role.setDeleted(true);
        role.setName("Name");
        role.setRoleType(RoleTypeEnum.STAFF);
        role.setDescription("The characteristics of someone or something");
        role.setUpdatedById(UUID.randomUUID());

        User user = new User();
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setId(UUID.randomUUID());
        user.setUpdatedAt(mock(Timestamp.class));
        user.setAccountNonLocked(true);
        user.setCreatedById(UUID.randomUUID());
        user.setCredentialsNonExpired(true);
        user.setDeleted(true);
        user.setEnabled(true);
        user.setPhoneNumber("4105551212");
        user.setRole(role);
        user.setFirstName("Jane");
        user.setAccountNonExpired(true);
        user.setCreatedAt(mock(Timestamp.class));
        user.setUpdatedById(UUID.randomUUID());

        Attachment attachment = new Attachment();
        attachment.setId(UUID.randomUUID());
        attachment.setUpdatedAt(mock(Timestamp.class));
        attachment.setContentType("text/plain");
        attachment.setCreatedById(UUID.randomUUID());
        attachment.setCreatedAt(mock(Timestamp.class));
        attachment.setDeleted(true);
        attachment.setName("Name");
        attachment.setUpdatedById(UUID.randomUUID());
        attachment.setSize(3L);

        Attachment attachment1 = new Attachment();
        attachment1.setId(UUID.randomUUID());
        attachment1.setUpdatedAt(mock(Timestamp.class));
        attachment1.setContentType("text/plain");
        attachment1.setCreatedById(UUID.randomUUID());
        attachment1.setCreatedAt(mock(Timestamp.class));
        attachment1.setDeleted(true);
        attachment1.setName("Name");
        attachment1.setUpdatedById(UUID.randomUUID());
        attachment1.setSize(3L);

        Place place = new Place();
        place.setId(UUID.randomUUID());
        place.setUpdatedAt(mock(Timestamp.class));
        place.setCreatedById(UUID.randomUUID());
        place.setCreatedAt(mock(Timestamp.class));
        place.setDeleted(true);
        place.setLat(10.0);
        place.setAddress("42 Main St");
        place.setLon(10.0);
        place.setUpdatedById(UUID.randomUUID());

        Event event = new Event();
        event.setId(UUID.randomUUID());
        event.setUpdatedAt(mock(Timestamp.class));
        event.setCreatedById(UUID.randomUUID());
        event.setCreatedAt(mock(Timestamp.class));
        event.setBanner(attachment);
        event.setDeleted(true);
        event.setName("Name");
        event.setDescription("The characteristics of someone or something");
        event.setUpdatedById(UUID.randomUUID());
        event.setSchema(attachment1);
        event.setPlace(place);

        BronTariff bronTariff = new BronTariff();
        bronTariff.setId(UUID.randomUUID());
        bronTariff.setUpdatedAt(mock(Timestamp.class));
        bronTariff.setLifetime(1);
        bronTariff.setCreatedById(UUID.randomUUID());
        bronTariff.setPercent(1);
        bronTariff.setCreatedAt(mock(Timestamp.class));
        bronTariff.setDisabled(true);
        bronTariff.setDeleted(true);
        bronTariff.setUpdatedById(UUID.randomUUID());

        EventSession eventSession = new EventSession();
        eventSession.setEndTime(mock(Timestamp.class));
        eventSession.setId(UUID.randomUUID());
        eventSession.setUpdatedAt(mock(Timestamp.class));
        eventSession.setEvent(event);
        eventSession.setCreatedById(UUID.randomUUID());
        eventSession.setCreatedAt(mock(Timestamp.class));
        eventSession.setDeleted(true);
        eventSession.setStartTime(mock(Timestamp.class));
        eventSession.setUpdatedById(UUID.randomUUID());
        eventSession.setBronTariff(bronTariff);

        Ticket ticket = new Ticket();
        ticket.setId(UUID.randomUUID());
        ticket.setUpdatedAt(mock(Timestamp.class));
        ticket.setCreatedById(UUID.randomUUID());
        ticket.setUser(user);
        ticket.setEventSession(eventSession);
        ticket.setDeleted(true);
        ticket.setPrice(10.0);
        ticket.setStatusEnum(SeatStatusEnum.VACANT);
        ticket.setRow(1);
        ticket.setCreatedAt(mock(Timestamp.class));
        ticket.setSoldTime(mock(Timestamp.class));
        ticket.setBookedTime(mock(Timestamp.class));
        ticket.setTicketUsed(true);
        ticket.setSection("Section");
        ticket.setUpdatedById(UUID.randomUUID());
        ticket.setSeatName("Seat Name");
        Optional<Ticket> ofResult = Optional.<Ticket>of(ticket);
        when(this.ticketRepository.findById((UUID) any())).thenReturn(ofResult);

        Attachment attachment2 = new Attachment();
        attachment2.setId(UUID.randomUUID());
        attachment2.setUpdatedAt(mock(Timestamp.class));
        attachment2.setContentType("text/plain");
        attachment2.setCreatedById(UUID.randomUUID());
        attachment2.setCreatedAt(mock(Timestamp.class));
        attachment2.setDeleted(true);
        attachment2.setName("Name");
        attachment2.setUpdatedById(UUID.randomUUID());
        attachment2.setSize(3L);

        Attachment attachment3 = new Attachment();
        attachment3.setId(UUID.randomUUID());
        attachment3.setUpdatedAt(mock(Timestamp.class));
        attachment3.setContentType("text/plain");
        attachment3.setCreatedById(UUID.randomUUID());
        attachment3.setCreatedAt(mock(Timestamp.class));
        attachment3.setDeleted(true);
        attachment3.setName("Name");
        attachment3.setUpdatedById(UUID.randomUUID());
        attachment3.setSize(3L);

        Place place1 = new Place();
        place1.setId(UUID.randomUUID());
        place1.setUpdatedAt(mock(Timestamp.class));
        place1.setCreatedById(UUID.randomUUID());
        place1.setCreatedAt(mock(Timestamp.class));
        place1.setDeleted(true);
        place1.setLat(10.0);
        place1.setAddress("42 Main St");
        place1.setLon(10.0);
        place1.setUpdatedById(UUID.randomUUID());

        Event event1 = new Event();
        event1.setId(UUID.randomUUID());
        event1.setUpdatedAt(mock(Timestamp.class));
        event1.setCreatedById(UUID.randomUUID());
        event1.setCreatedAt(mock(Timestamp.class));
        event1.setBanner(attachment2);
        event1.setDeleted(true);
        event1.setName("Name");
        event1.setDescription("The characteristics of someone or something");
        event1.setUpdatedById(UUID.randomUUID());
        event1.setSchema(attachment3);
        event1.setPlace(place1);

        BronTariff bronTariff1 = new BronTariff();
        bronTariff1.setId(UUID.randomUUID());
        bronTariff1.setUpdatedAt(mock(Timestamp.class));
        bronTariff1.setLifetime(1);
        bronTariff1.setCreatedById(UUID.randomUUID());
        bronTariff1.setPercent(1);
        bronTariff1.setCreatedAt(mock(Timestamp.class));
        bronTariff1.setDisabled(true);
        bronTariff1.setDeleted(true);
        bronTariff1.setUpdatedById(UUID.randomUUID());

        EventSession eventSession1 = new EventSession();
        eventSession1.setEndTime(mock(Timestamp.class));
        eventSession1.setId(UUID.randomUUID());
        eventSession1.setUpdatedAt(mock(Timestamp.class));
        eventSession1.setEvent(event1);
        eventSession1.setCreatedById(UUID.randomUUID());
        eventSession1.setCreatedAt(mock(Timestamp.class));
        eventSession1.setDeleted(true);
        eventSession1.setStartTime(mock(Timestamp.class));
        eventSession1.setUpdatedById(UUID.randomUUID());
        eventSession1.setBronTariff(bronTariff1);
        Optional<EventSession> ofResult1 = Optional.<EventSession>of(eventSession1);
        when(this.eventSessionRepository.findById((UUID) any())).thenReturn(ofResult1);

        TicketBuyReqDto ticketBuyReqDto = new TicketBuyReqDto();
        ticketBuyReqDto.setTicketId(UUID.randomUUID());
        ticketBuyReqDto.setUserId(UUID.randomUUID());
        ticketBuyReqDto.setEventSessionId(UUID.randomUUID());
        assertThrows(RestException.class, () -> this.ticketServiceImpl.BuyTicket(ticketBuyReqDto));
        verify(this.userRepository).findById((UUID) any());
        verify(this.ticketRepository).findById((UUID) any());
    }
}

