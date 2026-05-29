package org.example.restaurantbooking;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookingScheduler {
    private int capacityPerHour;
    private List<Schedule> schedules;
    private SmsSender smsSender;
    private MailSender mailSender;

    public BookingScheduler(int capacityPerHour) {
        this.schedules = new ArrayList<Schedule>();
        this.capacityPerHour = capacityPerHour;
        this.smsSender = new SmsSender();
        this.mailSender = new MailSender();
    }

    public void addSchedule(Schedule schedule) {
        if (schedule.getDateTime().getMinute() != 0) {
            throw new RuntimeException("Booking should be on the hour.");
        }

        if (getNow().getDayOfWeek() == DayOfWeek.SUNDAY) {
            throw new RuntimeException("Booking system is not available on sunday");
        }

        int numberOfPeople = schedule.getNumberOfPeople();
        for (Schedule bookedSchedule : schedules) {
            if (bookedSchedule.getDateTime().isEqual(schedule.getDateTime())) {
                numberOfPeople += bookedSchedule.getNumberOfPeople();
            }
        }
        if (numberOfPeople > capacityPerHour) {
            throw new RuntimeException("Number of people is over restaurant capacity per hour");
        }

        schedules.add(schedule);
        smsSender.send(schedule);
        if (schedule.getCustomer().getEmail() != null) {
            mailSender.sendMail(schedule);
        }
    }

    public LocalDateTime getNow() {
        return LocalDateTime.now();
    }

    public boolean hasSchedule(Schedule schedule) {
        return schedules.contains(schedule);
    }

    public void setSmsSender(SmsSender smsSender) {
        this.smsSender = smsSender;
    }

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }
}

