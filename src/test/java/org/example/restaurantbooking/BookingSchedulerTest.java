package org.example.restaurantbooking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class BookingSchedulerTest {
    public static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
    public static final LocalDateTime ON_THE_HOUR = LocalDateTime.parse("2021/03/26 09:00", FORMAT);
    public static final LocalDateTime NOT_ON_THE_HOUR = LocalDateTime.parse("2021/03/26 09:05", FORMAT);
    public static final int UNDER_CAPACITY = 1;
    public static final int CAPACITY_PER_HOUR = 3;


    //    public static final Customer CUSTOMER = new Customer("Fake name", "010-1234-5678");
    @Mock
    public Customer CUSTOMER;
    @Mock(answer = Answers.RETURNS_MOCKS)
    public Customer CUSTOMER_WITH_MAIL;

    private TestableSmsSender testableSmsSender;
    private TestableMailSender testableMailSender;
    @Mock
    private SmsSender smsSender;
    @Mock
    private MailSender mailSender;

    //    private BookingScheduler bookingScheduler;
    @Spy
    private BookingScheduler bookingScheduler = new BookingScheduler(CAPACITY_PER_HOUR);

    @BeforeEach
    void setUp() {
//        testableSmsSender = new TestableSmsSender();
//        testableMailSender = new TestableMailSender();
//        bookingScheduler = new BookingScheduler(CAPACITY_PER_HOUR);
        bookingScheduler.setSmsSender(smsSender);
        bookingScheduler.setMailSender(mailSender);
    }

    @Test
    public void 예약은_정시에만_가능하다_정시가_아닌경우_예약불가() {
        Schedule schedule = new Schedule(NOT_ON_THE_HOUR, UNDER_CAPACITY, CUSTOMER);
        assertThatThrownBy(() -> {
            bookingScheduler.addSchedule(schedule);
        }).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void 예약은_정시에만_가능하다_정시인_경우_예약가능() {
        Schedule schedule = new Schedule(ON_THE_HOUR, UNDER_CAPACITY, CUSTOMER);
        bookingScheduler.addSchedule(schedule);
        assertThat(bookingScheduler.hasSchedule(schedule)).isEqualTo(true);
    }

    @Test
    public void 시간대별_인원제한이_있다_같은_시간대에_Capacity_초과할_경우_예외발생() {
        Schedule schedule = new Schedule(ON_THE_HOUR, CAPACITY_PER_HOUR, CUSTOMER);
        bookingScheduler.addSchedule(schedule);
        try {
            Schedule newSchedule = new Schedule(ON_THE_HOUR, UNDER_CAPACITY, CUSTOMER);
            bookingScheduler.addSchedule(newSchedule);
            fail();
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo("Number of people is over restaurant capacity per hour");
        }
    }

    @Test
    public void 시간대별_인원제한이_있다_같은_시간대가_다르면_Capacity_차있어도_스케쥴_추가_성공() {
        Schedule schedule = new Schedule(ON_THE_HOUR, CAPACITY_PER_HOUR, CUSTOMER);
        bookingScheduler.addSchedule(schedule);
        LocalDateTime differentHour = ON_THE_HOUR.plusHours(1);
        Schedule newSchedule = new Schedule(differentHour, UNDER_CAPACITY, CUSTOMER);
        bookingScheduler.addSchedule(newSchedule);
        assertThat(bookingScheduler.hasSchedule(schedule)).isEqualTo(true);
    }

    @Test
    public void 예약완료시_SMS는_무조건_발송() {
        Schedule schedule = new Schedule(ON_THE_HOUR, UNDER_CAPACITY, CUSTOMER);
        bookingScheduler.addSchedule(schedule);
//        assertThat(testableSmsSender.isSendMethodIsCalled()).isEqualTo(true);
        verify(smsSender, times(1)).send(schedule);
    }

    @Test
    public void 이메일이_없는_경우에는_이메일_미발송() {
        Schedule schedule = new Schedule(ON_THE_HOUR, UNDER_CAPACITY, CUSTOMER);
        bookingScheduler.addSchedule(schedule);
//        assertThat(testableMailSender.getCountSendMailMethodIsCalled()).isEqualTo(0);
        verify(mailSender, times(0)).sendMail(schedule);
    }

    @Test
    public void 이메일이_있는_경우에는_이메일_발송() {
        Customer customerWithMail = new Customer("Fake Name", "010-1234-5678", "test@test.com");
        Schedule schedule = new Schedule(ON_THE_HOUR, UNDER_CAPACITY, customerWithMail);
        bookingScheduler.addSchedule(schedule);
//        assertThat(testableMailSender.getCountSendMailMethodIsCalled()).isEqualTo(1);
        verify(mailSender, times(1)).sendMail(schedule);
    }

    @Test
    public void 현재날짜가_일요일인_경우_예약불가_예외처리() {
//        bookingScheduler = new TestableBookingScheduler(CAPACITY_PER_HOUR, "2021/03/28 17:00");
        LocalDateTime sunday = LocalDateTime.parse("2021/03/28 17:00", FORMAT);
        when(bookingScheduler.getNow()).thenReturn(sunday);

        try {
            Schedule newSchedule = new Schedule(ON_THE_HOUR, UNDER_CAPACITY, CUSTOMER);
            bookingScheduler.addSchedule(newSchedule);
            fail();
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo("Booking system is not available on sunday");
        }
    }

    @Test
    public void 현재날짜가_일요일이_아닌경우_예약가능() {
        bookingScheduler = new TestableBookingScheduler(CAPACITY_PER_HOUR, "2021/03/29 09:00");
        Schedule newSchedule = new Schedule(ON_THE_HOUR, UNDER_CAPACITY, CUSTOMER);
        bookingScheduler.addSchedule(newSchedule);
        assertThat(bookingScheduler.hasSchedule(newSchedule)).isEqualTo(true);
    }
}
