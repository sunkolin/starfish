package com.starfish.module.redis;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RedisComponent
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-03-20
 */
@SuppressWarnings(value = "unused")
@Slf4j
public class RedisUtil {

    private ShardedJedisPool pool;

    public RedisUtil(ShardedJedisPool pool) {
        this.pool = pool;
    }

    /**
     * 获取字符串
     *
     * @param key 键
     * @return 值
     */
    public String getString(String key) {
        ShardedJedis redis = null;
        String value = "";
        try {
            redis = pool.getResource();
            value = redis.get(key);
        } catch (Exception e) {
            log.error("redis get value failed", e);
        } finally {
            pool.returnResource(redis);
        }
        return value;
    }

    /**
     * 存储字符串
     *
     * @param key   键
     * @param value 值
     */
    public void setString(String key, String value) {
        ShardedJedis redis = null;
        try {
            redis = pool.getResource();
            redis.set(key, value);
        } catch (Exception e) {
            log.error("redis set value failed", e);
        } finally {
            pool.returnResource(redis);
        }
    }

    /**
     * 附加字符串
     *
     * @param key         键
     * @param appendValue 附加值
     */
    public void appendString(String key, String appendValue) {
        ShardedJedis redis = null;
        try {
            redis = pool.getResource();
            redis.append(key, appendValue);
        } catch (Exception e) {
            log.error("redis append value failed", e);
        } finally {
            pool.returnResource(redis);
        }
    }

    /**
     * 删除
     *
     * @param key 键
     */
    public void delete(String key) {
        ShardedJedis redis = null;
        try {
            redis = pool.getResource();
            redis.del(key);
        } catch (Exception e) {
            log.error("redis del failed", e);
        } finally {
            pool.returnResource(redis);
        }
    }

    /**
     * 获取对象
     *
     * @param key 键
     * @param cls 类
     * @param <T> T
     * @return 对象
     */
    public <T> T getObject(String key, Class<T> cls) {
        ShardedJedis redis = null;
        Object object = null;
        try {
            redis = pool.getResource();
            String value = redis.get(key);
            object = JSON.parseObject(value, cls);
        } catch (Exception e) {
            log.error("redis get object failed", e);
        } finally {
            pool.returnResource(redis);
        }
        return cls.cast(object);
    }

    /**
     * 存储对象
     *
     * @param key    键
     * @param object 值
     */
    public void setObject(String key, Object object) {
        ShardedJedis redis = null;
        try {
            redis = pool.getResource();
            redis.set(key, JSON.toJSONString(object));
        } catch (Exception e) {
            log.error("redis set object failed", e);
        } finally {
            pool.returnResource(redis);
        }
    }

    /**
     * 获取列表从start到end的数据
     *
     * @param key   键
     * @param start 开始
     * @param end   结束
     * @param cls   类
     * @return 结果
     */
    public List<?> getList(String key, long start, long end, Class<?> cls) {
        ShardedJedis redis = null;
        List<Object> list = new ArrayList<>();
        try {
            redis = pool.getResource();
            List<String> value = redis.lrange(key, start, end);
            for (String s : value) {
                list.add(JSON.parseObject(s, cls));
            }
        } catch (Exception e) {
            log.error("redis get list failed", e);
        } finally {
            pool.returnResource(redis);
        }
        return list;
    }

    /**
     * 获取列表指定下标的元素
     *
     * @param key   键
     * @param index 下标
     * @return 结果
     */
    public String getListIndex(String key, long index) {
        ShardedJedis redis = null;
        String value = "";
        try {
            redis = pool.getResource();
            value = redis.lindex(key, index);
        } catch (Exception e) {
            log.error("redis get list index failed", e);
        } finally {
            pool.returnResource(redis);
        }
        return value;
    }

    /**
     * 获取列表大小
     *
     * @param key 键
     * @return 结果
     */
    public Long getListSize(String key) {
        ShardedJedis redis = null;
        Long size = 0L;
        try {
            redis = pool.getResource();
            size = redis.llen(key);
        } catch (Exception e) {
            log.error("redis get list size failed", e);
        } finally {
            pool.returnResource(redis);
        }
        return size;
    }

    /**
     * 设置列表指定下表的值
     *
     * @param key   键
     * @param value 值
     * @param index 下标
     */
    public void setListIndex(String key, Object value, long index) {
        ShardedJedis redis = null;
        try {
            redis = pool.getResource();
            redis.lset(key, index, JSON.toJSONString(value));
        } catch (Exception e) {
            log.error("redis set list index failed", e);
        } finally {
            pool.returnResource(redis);
        }
    }

    /**
     * 设置列表的所有数据
     *
     * @param key  键
     * @param list 列表
     */
    public void setList(String key, List<Object> list) {
        ShardedJedis redis = null;
        try {
            redis = pool.getResource();
            redis.del(key);
            for (Object value : list) {
                redis.lpush(key, JSON.toJSONString(value));
            }
        } catch (Exception e) {
            log.error("redis set list failed", e);
        } finally {
            pool.returnResource(redis);
        }
    }

    /**
     * 附加值到列表
     *
     * @param key  键
     * @param list 列表
     */
    public void appendList(String key, List<Object> list) {
        ShardedJedis redis = null;
        try {
            redis = pool.getResource();
            for (Object value : list) {
                redis.lpush(key, JSON.toJSONString(value));
            }
        } catch (Exception e) {
            log.error("redis append list failed", e);
        } finally {
            pool.returnResource(redis);
        }
    }

    /**
     * 删除List指定下标数据(Java客户端删除) 取回List所有数据，删除指定下标，重新写回redis，大数据时可能会很慢
     *
     * @param key   键
     * @param index 下标
     */
    public void deleteListIndex(String key, int index) {
        ShardedJedis redis = null;
        List<String> list = new ArrayList<>();
        try {
            redis = pool.getResource();
            long listSize = redis.llen(key);
            List<String> value = redis.lrange(key, 0, listSize);
            list.addAll(value);
            list.remove(index);
            for (String v : list) {
                redis.lpush(key, v);
            }
        } catch (Exception e) {
            log.error("redis del list index failed", e);
        } finally {
            pool.returnResource(redis);
        }
    }

    /**
     * 存储Map数据
     *
     * @param key 键
     * @param map map
     */
    public void setMap(String key, Map<Object, Object> map) {
        ShardedJedis redis = null;
        try {
            redis = pool.getResource();
            redis.del(key);
            //object对象转json字符串
            HashMap<String, String> tmp = new HashMap<>();
            for (Map.Entry<Object, Object> entry : map.entrySet()) {
                tmp.put(JSON.toJSONString(entry.getKey()), JSON.toJSONString(entry.getValue()));
            }
            redis.hmset(key, tmp);
        } catch (Exception e) {
            log.error("redis set map failed", e);
        } finally {
            pool.returnResource(redis);
        }
    }

    /**
     * 追加存储Map
     *
     * @param key 键
     * @param map map
     * @return 结果
     */
    public String appendMap(String key, Map<Object, Object> map) {
        ShardedJedis redis = null;
        String res = "";
        try {
            redis = pool.getResource();
            //object对象转json字符串
            HashMap<String, String> tmp = new HashMap<>();
            for (Map.Entry<Object, Object> entry : map.entrySet()) {
                tmp.put(JSON.toJSONString(entry.getKey()), JSON.toJSONString(entry.getValue()));
            }
            res = redis.hmset(key, tmp);
        } catch (Exception e) {
            log.error("redis append map failed", e);
        } finally {
            pool.returnResource(redis);
        }
        return res;
    }

    /**
     * 设置map指定key的值
     *
     * @param key    键
     * @param mapKey 指定的键
     * @param value  值
     * @return 结果
     */
    public Long setMapSpecificField(String key, Object mapKey, Object value) {
        ShardedJedis redis = null;
        Long res = 0L;
        try {
            redis = pool.getResource();
            res = redis.hset(key, JSON.toJSONString(mapKey), JSON.toJSONString(value));
        } catch (Exception e) {
            log.error("redis set map specific field failed", e);
        } finally {
            pool.returnResource(redis);
        }
        return res;
    }

    /**
     * 获取Map数据
     *
     * @param key 键
     * @return 结果
     */
    public Map<String, String> getMap(String key) {
        ShardedJedis redis = null;
        Map<String, String> map = null;
        try {
            redis = pool.getResource();
            map = redis.hgetAll(key);
        } catch (Exception e) {
            log.error("redis get map failed", e);
        } finally {
            pool.returnResource(redis);
        }
        return map;
    }

    /**
     * 获取map指定field的值
     *
     * @param key   键
     * @param field field
     * @return 结果
     */
    public String getMapSpecificField(String key, Object field) {
        ShardedJedis redis = null;
        String value = "";
        try {
            redis = pool.getResource();
            value = redis.hget(key, JSON.toJSONString(field));
        } catch (Exception e) {
            log.error("redis get map specific field failed", e);
        } finally {
            pool.returnResource(redis);
        }
        return value;
    }

    /**
     * 获取map指定多个fields的值
     *
     * @param key     键
     * @param mapKeys 键列表
     * @return 结果
     */
    public List<String> getMapSpecificFields(String key, List<Object> mapKeys) {
        ShardedJedis redis = null;
        List<String> list = null;
        try {
            redis = pool.getResource();
            //转json
            StringBuilder tmp = new StringBuilder();
            for (Object object : mapKeys) {
                tmp.append(JSON.toJSONString(object));
                tmp.append(" ");
            }
            String str = tmp.toString();
            if (str.endsWith(" ")) {
                str = str.substring(0, str.length() - 1);
            }
            list = redis.hmget(key, str);
        } catch (Exception e) {
            log.error("redis get map specific fields failed", e);
        } finally {
            pool.returnResource(redis);
        }
        return list;
    }

    /**
     * 获取map大小
     *
     * @param key 键
     * @return 数量
     */
    public Long getMapSize(String key) {
        ShardedJedis redis = null;
        Long size = 0L;
        try {
            redis = pool.getResource();
            size = redis.hlen(key);
        } catch (Exception e) {
            log.error("redis get map size failed", e);
        } finally {
            pool.returnResource(redis);
        }
        return size;
    }

    /**
     * 删除map指定key的值
     *
     * @param key    键
     * @param mapKey map键
     */
    public void delMapSpecific(String key, Object mapKey) {
        ShardedJedis redis = null;
        try {
            redis = pool.getResource();
            redis.hdel(key, JSON.toJSONString(mapKey));
        } catch (Exception e) {
            log.error("redis del map specific failed", e);
        } finally {
            pool.returnResource(redis);
        }
    }

}
