package com.manage.reactive.apis.id_generator;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class SnowFlakeIdGenerator {

    private final long twEpoch = 1288834974657L;
    private final long timestampBits = 41L;
    private final long serverIdBits = 5L;
    private final long datacenterIdBits = 5L;
    private final long serialBits = 12L;
    private final long serialIdShiftFromRight = 12L;
    private final long datacenterIdShiftFromRight = 17L;
    private final long timestampLeftShiftFromRight = 22L;
    private final long signLeftShiftFromRight = 63L;
    private final long serialMask = 4095L;
    private long oldTimestamp = -1L;
    private long serialNum = 1L;

    public SnowFlakeIdGenerator() {
    }

    public synchronized Long generateNewId(long serverId, long dataCenterId) throws Exception {
        long timestamp = this.getCurrentTimeStamp();
        long signedId = 0L;
        long timestampId = timestamp - 1288834974657L;
        long newTime;
        if (timestamp == this.oldTimestamp) {
            this.serialNum = this.serialNum + 1L & 4095L;
            if (this.serialNum == 0L) {
                for(newTime = this.getCurrentTimeStamp(); timestamp != newTime; newTime = this.getCurrentTimeStamp()) {
                }
            }
        } else {
            this.serialNum = 1L;
        }

        this.oldTimestamp = timestamp;
        newTime = signedId << 63 | timestampId << 22 | dataCenterId << 17 | serverId << 12 | this.serialNum;
        return newTime;
    }

    private long getCurrentTimeStamp() {
        ZoneId zoneid = ZoneId.of("Asia/Seoul");
        long timestamp = LocalDateTime.now().atZone(zoneid).toInstant().toEpochMilli();
        return timestamp;
    }

    private String timeStampToBit(long timestamp) {
        String timeBinary = Long.toBinaryString(timestamp);
        System.out.println("현재 시각 milliSeconds는 다음과 같습니다: " + timestamp);
        System.out.println("현재 시각(milliseconds)를 Binary로 바꾸면 다음과 같습니다: " + timeBinary);
        System.out.println("Binary의 길이는 다음과 같습니다: " + timeBinary.length());
        return timeBinary;
    }

    private long bitToTimeStamp(String binaryTime) {
        long timestamp = Long.parseLong(binaryTime, 2);
        System.out.println("Binary를 역변환하면 다음과 같습니다: " + timestamp);
        return timestamp;
    }
}
