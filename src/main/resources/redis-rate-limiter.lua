-- Leaky Bucket

local key = KEYS[1];
local qps = tonumber(ARGV[1]) - 2;
local nowTime = tonumber(ARGV[2]);
local maxBucket = qps / 30;
local interval = 1000 / qps;
local lastClearTimeKey = 'lastTimeOf' .. key
local lastClearTime = redis.call('GET', lastClearTimeKey);
local existKey = redis.call('EXISTS', key);

if existKey == 1 then
    local diff = tonumber(nowTime) - tonumber(lastClearTime);
    local value = tonumber(redis.call('GET', key));
    if maxBucket < 1 then
        maxBucket = 1;
    end
    if diff > interval then
        local maxValue = value + diff / interval;
        if maxValue > maxBucket then
            value = maxBucket;
        else
            value = maxValue;
        end
        redis.call('SET', lastClearTimeKey, nowTime);
        redis.call('SET', key, value);
    end

    if value < 1 then
        return 0;
    else
        redis.call('SET', key, value - 1);
    end
else
    redis.call('SET', key, 0);
    redis.call('SET', lastClearTimeKey, nowTime);
end
return 1;
