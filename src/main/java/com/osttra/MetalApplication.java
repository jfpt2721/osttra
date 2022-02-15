package com.osttra;

import com.osttra.commons.model.Bid;
import com.osttra.model.BidKey;

import java.time.LocalTime;
import java.util.*;

public class MetalApplication {
    Map<BidKey, Integer> map = new HashMap<>();

    public static final List<String> METALS = Arrays.asList("gold", "silver", "copper", "platinum");


    private boolean validateBidTiming() {
        if(LocalTime.now().getHour() < 6 && LocalTime.now().getHour() > 18)
            return false;
        return true;
    }

    public void processBid(Bid bid) {
        if(!validateBidTiming()){
            System.out.println("We are open from 6000 hrs till 1800hrs.");
            return;
        }
        map.put(new BidKey(bid.getCompany(), bid.getMetal()), bid.getPrice());
    }

    public void printLowestHighestBid(List<String> metals){
        Map<String, List<Bid>> bidsOnMetal = getBidsOnMetal();
        metals.forEach(m -> {
            if(bidsOnMetal.containsKey(m)){
                bidsOnMetal.get(m).sort(Comparator.comparingInt(b -> b.getPrice()));
                System.out.println(m + " :Lowest bid - " + bidsOnMetal.get(m).get(0) + " Highest bid - " + bidsOnMetal.get(m).get(bidsOnMetal.get(m).size() -1));
            } else{
                System.out.println(m + " :Lowest bid - No bid Highest bid -  No bid");
            }
        });
    }

    Map<String, List<Bid>> getBidsOnMetal(){
        Map<String, List<Bid>> metalBidMap = new HashMap<>();
        for(Map.Entry<BidKey, Integer> entry: map.entrySet()){
            if(metalBidMap.containsKey(entry.getKey().getMetal())){
                metalBidMap.get(entry.getKey().getMetal())
                        .add(new Bid(
                                entry.getKey().getCompany(),
                                entry.getKey().getMetal(),
                                entry.getValue())
                        );
            } else {
                List<Bid> list = new ArrayList<>();
                list.add(new Bid(entry.getKey().getCompany(),
                        entry.getKey().getMetal(),
                        entry.getValue())
                );
                metalBidMap.put(entry.getKey().getMetal(), list);
            }
        }
        return metalBidMap;
    }

    public void clearBids() {
        map.clear();
    }
}
