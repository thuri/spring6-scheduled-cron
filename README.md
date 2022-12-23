# Scheduled Cron Derivation Demo

When upgrading a project of mine, I did encouter an possible issue with Scheduled Bean Methods using cron expressions.

This example project should demonstrate the issue or help reproducing this on other machines, as it may be a local problem.

The project has one Bean containing a method annotated with `@Scheduled(cron="0 */1 * * * *")`. I expect that this method is called each minute when the second is 0. 

This works well for Spring Boot 2.7.6 but since updating to spring-boot 3.0.0 (or 3.0.1) the method may be called some milli- or nanoseconds before second 0 as you can see in the following log snippet:

```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.0.1)

2022-12-23T14:01:10.173+01:00  INFO 5262 --- [           main] n.l.spring6.scheduled.cron.App           : Starting App using Java 17.0.4.1 with PID 5262 (/home/thuri/Projects/basar/spring6-scheduled-cron/target/classes started by thuri in /home/thuri/Projects/basar/spring6-scheduled-cron)
2022-12-23T14:01:10.189+01:00  INFO 5262 --- [           main] n.l.spring6.scheduled.cron.App           : No active profile set, falling back to 1 default profile: "default"
2022-12-23T14:01:11.893+01:00  INFO 5262 --- [           main] n.l.spring6.scheduled.cron.App           : Started App in 2.471 seconds (process running for 3.648)
2022-12-23T14:02:00.014+01:00 ERROR 5262 --- [   scheduling-1] n.l.spring6.scheduled.cron.CronJob       : now=2022-12-23T13:02:00.001278211Z now.truncated=2022-12-23T13:02:00Z
2022-12-23T14:03:00.000+01:00 ERROR 5262 --- [   scheduling-1] n.l.spring6.scheduled.cron.CronJob       : now=2022-12-23T13:03:00.000262297Z now.truncated=2022-12-23T13:03:00Z
2022-12-23T14:04:00.000+01:00 ERROR 5262 --- [   scheduling-1] n.l.spring6.scheduled.cron.CronJob       : now=2022-12-23T13:04:00.000006132Z now.truncated=2022-12-23T13:04:00Z
2022-12-23T14:05:00.000+01:00 ERROR 5262 --- [   scheduling-1] n.l.spring6.scheduled.cron.CronJob       : now=2022-12-23T13:04:59.999886696Z now.truncated=2022-12-23T13:04:59Z
2022-12-23T14:05:59.999+01:00 ERROR 5262 --- [   scheduling-1] n.l.spring6.scheduled.cron.CronJob       : now=2022-12-23T13:05:59.999691839Z now.truncated=2022-12-23T13:05:59Z
2022-12-23T14:07:00.000+01:00 ERROR 5262 --- [   scheduling-1] n.l.spring6.scheduled.cron.CronJob       : now=2022-12-23T13:06:59.999776591Z now.truncated=2022-12-23T13:06:59Z
2022-12-23T14:08:00.000+01:00 ERROR 5262 --- [   scheduling-1] n.l.spring6.scheduled.cron.CronJob       : now=2022-12-23T13:07:59.999810631Z now.truncated=2022-12-23T13:07:59Z
2022-12-23T14:09:00.000+01:00 ERROR 5262 --- [   scheduling-1] n.l.spring6.scheduled.cron.CronJob       : now=2022-12-23T13:08:59.999755451Z now.truncated=2022-12-23T13:08:59Z
2022-12-23T14:10:00.000+01:00 ERROR 5262 --- [   scheduling-1] n.l.spring6.scheduled.cron.CronJob       : now=2022-12-23T13:09:59.999746421Z now.truncated=2022-12-23T13:09:59Z
```

for spring boot 2.7.6 the same application is producing the following output:

```

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.7.6)

2022-12-23 14:26:35.703  INFO 6326 --- [           main] n.l.spring6.scheduled.cron.App           : Starting App using Java 17.0.4.1 on inci with PID 6326 (/home/thuri/Projects/basar/spring6-scheduled-cron/target/classes started by thuri in /home/thuri/Projects/basar/spring6-scheduled-cron)
2022-12-23 14:26:35.714  INFO 6326 --- [           main] n.l.spring6.scheduled.cron.App           : No active profile set, falling back to 1 default profile: "default"
2022-12-23 14:26:37.569  INFO 6326 --- [           main] n.l.spring6.scheduled.cron.App           : Started App in 2.825 seconds (JVM running for 4.104)
2022-12-23 14:27:00.013 ERROR 6326 --- [   scheduling-1] n.l.spring6.scheduled.cron.CronJob       : now=2022-12-23T13:27:00.001759894Z now.truncated=2022-12-23T13:27:00Z
2022-12-23 14:28:00.001 ERROR 6326 --- [   scheduling-1] n.l.spring6.scheduled.cron.CronJob       : now=2022-12-23T13:28:00.000913412Z now.truncated=2022-12-23T13:28:00Z
2022-12-23 14:29:00.001 ERROR 6326 --- [   scheduling-1] n.l.spring6.scheduled.cron.CronJob       : now=2022-12-23T13:29:00.001071559Z now.truncated=2022-12-23T13:29:00Z
2022-12-23 14:30:00.001 ERROR 6326 --- [   scheduling-1] n.l.spring6.scheduled.cron.CronJob       : now=2022-12-23T13:30:00.001152233Z now.truncated=2022-12-23T13:30:00Z
2022-12-23 14:31:00.001 ERROR 6326 --- [   scheduling-1] n.l.spring6.scheduled.cron.CronJob       : now=2022-12-23T13:31:00.001245318Z now.truncated=2022-12-23T13:31:00Z
``
