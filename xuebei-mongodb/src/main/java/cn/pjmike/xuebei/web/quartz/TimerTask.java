package cn.pjmike.xuebei.web.quartz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

/**
 * 定时任务，定时删除关于建群操作构建的数据库
 *
 * @author pjmike
 * @create 2018-02-02 9:55
 **/
@Component
public class TimerTask {
    private final Logger logger= LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MongoTemplate mongoTemplate;
    public void dropCollection() {
        mongoTemplate.dropCollection("venueofuser");
        logger.info("成功删除数据库");
    }
}
