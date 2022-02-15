package com.osttra.scheduler;

import com.osttra.MetalApplication;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduleEveryHour {

    public void printMinMaxBidEveryHour(MetalApplication ma) {

        ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
        ses.scheduleAtFixedRate(() -> {
                ma.printLowestHighestBid(ma.METALS);
                ma.clearBids();
        }, 2, 60, TimeUnit.MINUTES);
    }
}
