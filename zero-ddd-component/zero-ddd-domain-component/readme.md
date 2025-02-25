## CQRS模式开发
### 命令使用
1. 使用CommandBus或者CommandHelper进行发送命令
2. 使用@CommandHandler注解进行接收命令  像spring-event使用
```java
@RestController
@RequestMapping("/test")
public class UserController {

    @Autowired
    private CommandBus commandBus;

    @GetMapping
    public ResponseEntity<Void> save() {
        UserCommand userCommand = new UserCommand();
        userCommand.setName("张三");
        userCommand.setAge(18);
        commandBus.emit(userCommand);
        return ResponseEntity.ok().build();
    }
}

@Service
public class UserService {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @CommandHandler
    public void handler(UserCommand command) {
        UserHandler.UserEvent userEvent = new UserHandler.UserEvent();
        userEvent.setName(command.getName());
        userEvent.setAge(command.getAge());
        applicationEventPublisher.publishEvent(userEvent);
    }
}
```

### 事件使用
1. 使用ApplicationEventPublisher发送事件
2. 事件分为内部事件和外部事件，外部事件使用@Externalized注解进行标识
3. 事件处理可以使用spring的事件处理注解或者使用@ApplicationModuleListener
```java
@Component
public class UserHandler {

    @Autowired
    private UserDao userDao;

    @DomainEvent
    @Data
    public static class UserEvent {
        private String name;
        private Integer age;
    }

    @ApplicationModuleListener
    public void on(UserEvent userEvent) {
        System.out.println("UserEventHandler:" + userEvent);

        User user = new User();
        user.setName(userEvent.getName());
        user.setAge(userEvent.getAge());
        userDao.save(user);
    }
}
```


### 对于查询
1. 查询模型对应数据库的表
2. 使用zero-ddd-graphql-component框架