package ru.itis.kpfu.kozlov.social_network_impl.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.itis.kpfu.kozlov.social_network_api.dto.UserDto;

import javax.persistence.Cacheable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class CacheService {

    private class ObjectWrapper{

        private Date puttedAt;
        private Object object;

        public ObjectWrapper(Object user) {
            puttedAt = new Date();
            this.object = object;
        }

        public boolean isExpired(){
            Date now = new Date();
            int days = (int)((now.getTime() - puttedAt.getTime())/(24*60*60*1000));
            if(days>=5){
                return true;
            }
            return false;
        }

        public void setObject(Object o){
            object = o;
        }

        public Object getObject(){
            return object;
        }

        public void setPuttedAt(Date date){
            puttedAt = date;
        }
    }

    private final Map<String, ObjectWrapper> cachedUsers = new HashMap<>();

    public void putUser(String key, Object user){
        ObjectWrapper objectWrapper = new ObjectWrapper(user);
        cachedUsers.put(key, objectWrapper);
    }

    public boolean containsUser(String key){
        return cachedUsers.containsKey(key);
    }

    public Object getUser(String key){
        return cachedUsers.get(key).getObject();
    }

    @Scheduled(cron = "0 0 * * *")
    private void cleanCahe(){
        Set<String> set = cachedUsers.keySet();
        for(String key: set){
            ObjectWrapper obj = cachedUsers.get(key);
            if(obj.isExpired()) cachedUsers.remove(key);
        }
    }

    public boolean updateCache(String key, Object object){
        if(!this.containsUser(key)) return false;
        ObjectWrapper update = this.cachedUsers.get(key);
        update.setPuttedAt(new Date());
        update.setObject(object);
        return true;
    }

}
