package lxf.kafka.controller;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {
    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;

    //kafka生产者
    @GetMapping("/message/send")
    public boolean send(@RequestParam(value = "name",required = true, defaultValue = "hello") String message)
    {
        kafkaTemplate.send("myTest",message);
        return true;
    }
    //生产者，多线程创建消息
    @GetMapping("/message/sendThreads")
    public boolean sendThreads(@RequestParam(value = "name",required = true, defaultValue = "hello") String message) {
        for (int i=0; i<10; i++) {
            final  int newI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("线程名="+Thread.currentThread().getName()+",生产i="+newI);
                    kafkaTemplate.send("myTest","线程名="+Thread.currentThread().getName()+",i="+newI+","+message);
                }
            }).start();
        }
        return true;
    }
    //kafka 消费者 通过 @KafkaListener监听器实现
    @KafkaListener(topics = "myTest")
    //public void onMessage(String message){
    public void onMessage(ConsumerRecord<?,?> consumer){
        //insertIntoDb(buffer);//这里为插入数据库代码
        //System.out.println(message);
        System.out.println("consume-1-topic名称:"+consumer.topic()
                +",key:"+consumer.key()
                +",value:"+ consumer.value()
                /*
                   该分区位置就是kafka某个broker日志标记，
                   kafka/logs/myTest-日志标记（如：myTest-0）对应打印：分区位置：0
                 */
                +",分区位置："+consumer.partition()
                + ",offset下标："+consumer.offset());
    }
    //kafka 消费者 通过 @KafkaListener监听器实现
    //@KafkaListener(topics = "myTest")
    //public void onMessage(String message){
    public void onMessage2(ConsumerRecord<?,?> consumer){
        //insertIntoDb(buffer);//这里为插入数据库代码
        //System.out.println(message);
        System.out.println("consumer-2--topic名称:"+consumer.topic()
                +",key:"+consumer.key()
                +",value:"+ consumer.value()
                /*
                   该分区位置就是kafka某个broker日志标记，
                   kafka/logs/myTest-日志标记（如：myTest-0）对应打印：分区位置：0
                 */
                +",分区位置："+consumer.partition()
                + ",offset下标："+consumer.offset());
    }
    //kafka 消费者 通过 @KafkaListener监听器实现
    //@KafkaListener(topics = "myTest")
    //public void onMessage(String message){
    public void onMessage3(ConsumerRecord<?,?> consumer){
        //insertIntoDb(buffer);//这里为插入数据库代码
        //System.out.println(message);
        System.out.println("consumer-3--topic名称:"+consumer.topic()
                +",key:"+consumer.key()
                +",value:"+ consumer.value()
                /*
                   该分区位置就是kafka某个broker日志标记，
                   kafka/logs/myTest-日志标记（如：myTest-0）对应打印：分区位置：0
                 */
                +",分区位置："+consumer.partition()
                + ",offset下标："+consumer.offset());
    }
}
